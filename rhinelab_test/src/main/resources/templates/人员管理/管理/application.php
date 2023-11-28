<!DOCTYPE html>
<html>

<head>
  <title>员工调岗申请</title>
  <meta charset="UTF-8">
  <style>
    body {
      text-align: center;
    }

    table {
      background-color: #e6f2ff;
      margin: 0 auto;
      text-align: center;
    }

    td,
    th {
      padding: 5px;
    }

    .hidden {
      display: none;
    }
  </style>
</head>

<body>
  <h1>员工调岗申请</h1>
  <table>
    <tr>
      <th>员工编号</th>
      <th>姓名</th>
      <th>原部门</th>
      <th>新部门</th>
      <th>原工资</th>
      <th>新工资</th>
      <th>申请理由</th>
      <th>操作</th>
    </tr>
    <?php
    require_once "../includes/class.phpmailer.php";
    require_once "../includes/class.smtp.php";
    // Replace the values in these variables with your own database credentials
    $servername = "localhost";
    $username = "root";
    $password = "Zz15987324860";
    $dbname = "rhinelab";

    // Create a connection to the database
    $conn = new mysqli($servername, $username, $password, $dbname);

    // Check if the connection was successful
    if ($conn->connect_error) {
      die("Connection failed: " . $conn->connect_error);
    }

    // Construct the SQL query to select all employee transfer requests
    $sql = "SELECT * FROM 申请";
    $result = $conn->query($sql);

    // Check if any rows were returned
    if ($result->num_rows > 0) {
      // Output the data as an HTML table
      while ($row = $result->fetch_assoc()) {
        echo "<tr>";
        echo "<td>" . $row["员工编号"] . "</td>";
        echo "<td>" . $row["姓名"] . "</td>";
        echo "<td>" . $row["原部门"] . "</td>";
        echo "<td>" . $row["新部门"] . "</td>";
        echo "<td>" . $row["原工资"] . "</td>";
        echo "<td>" . $row["新工资"] . "</td>";
        echo "<td>" . $row["申请理由"] . "</td>";
        echo "<td>" . $row["状态"] . "</td>";
        echo "<td><form method='post'>";
        echo "<input type='hidden' name='transfer_id' value='" . $row["申请编号"] . "'>";
        echo "<input type='submit' name='approve' value='同意'><input type='submit' name='reject' value='拒绝'>";
        echo "</form></td>";
        echo "</tr>";

      }
    } else {
      echo "<tr><td colspan='8'>没有员工调岗申请。</td></tr>";
    }

    // Check if the approve or reject button was clicked
    if (isset($_POST['approve'])) {
      $transfer_id = $_POST['transfer_id'];

      // Construct the SQL query to update the transfer request status to "已同意"
      $sql = "UPDATE 申请 SET 状态='已同意' WHERE 申请编号='$transfer_id'";

      // Execute the query and check if it was successful
      if ($conn->query($sql) === TRUE) {
        // Get the employee ID from the transfer request
        $sql = "SELECT 员工编号, 新部门, 新工资 FROM 申请 WHERE 申请编号='$transfer_id'";
        $result = $conn->query($sql);
        $row = $result->fetch_assoc();
        $employee_id = $row["员工编号"];
        $new_department_id = $row["新部门"];
        $new_salary = $row["新工资"];

        // Construct the SQL query to update the employee table with the new department and salary
        $sql = "UPDATE 员工 SET 部门编号='$new_department_id', 员工基本工资='$new_salary' WHERE 员工编号='$employee_id'";

        // Execute the query and check if it was successful
        if ($conn->query($sql) === TRUE) {


          $sqlx = "SELECT * FROM `员工` WHERE `员工编号` = '$employee_id'";
          echo $sqlx;

          $result = $conn->query($sqlx);
          $row = $result->fetch_assoc(); // 获取查询结果的第一行数据
          $email = $row['邮箱']; // 输出查询结果中的邮箱地址
          $name = $row['姓名'];
          


          try {
            $mail = new PHPMailer();
            $mail->isSMTP();
            $mail->CharSet = "UTF-8";
            $mail->Host = "smtp.qq.com";
            $mail->SMTPAuth = true;
            $mail->Username = "2511950954@qq.com";
            $mail->Password = "bnsybpgpjtdxdidi";
            $mail->From = "2511950954@qq.com";
            $mail->FromName = "Rhine Lab";
            $mail->addAddress("$email");
            $mail->addReplyTo("2511950954@qq.com");
            $mail->isHTML(true);
            $mail->Subject = 'Your Employee Information Update is Complete';

            $mail->Body = nl2br("
            Dear $name,

            &nbsp&nbsp&nbsp&nbsp Thank you for submitting your request for a change in your employee information. We have received your application and have completed the necessary updates to your information.  You will see these changes reflected in your paystub after the next pay period.
            
            &nbsp&nbsp&nbsp&nbspIf you have any questions regarding your new department or salary, please do not hesitate to contact us. We are happy to provide assistance and support.
            
            &nbsp&nbsp&nbsp&nbspThank you once again for your support and cooperation with our company.
            
            Best regards,
            
          ZeJia Lin
          RHINE LAB

          ");
            $mail->AltBody = "注意该邮件附有附件！";
            if ($mail->send()) {
              // 邮件发送成功
              echo "邮件发送成功！";
            } else {
              // 邮件发送失败
              echo "邮件发送失败！";
              echo "<p>错误：" . $mail->ErrorInfo;
            }
          } catch (Exception $e) {
            echo '邮件发送失败！';
            echo "<p>错误：" . $mail->ErrorInfo;
          }





          echo "<script>alert('员工申请已同意。员工信息已更新。');</script>";
          echo "<script>window.location = 'control.php';</script>";
        } else {
          echo "<script>alert('员工申请已同意，但员工信息更新失败。请手动更新。');</script>";
        }
      } else {
        echo "<script>alert('操作失败。');</script>";
      }
    } else if (isset($_POST['reject'])) {
      $transfer_id = $_POST['transfer_id'];

      // Construct the SQL query to update the transfer request status to "已拒绝"
      $sql = "UPDATE 申请 SET 状态='已拒绝' WHERE 申请编号='$transfer_id'";

      // Execute the query and check if it was successful
      if ($conn->query($sql) === TRUE) {

        
        echo "<script>alert('员工申请已拒绝。');</script>";
        echo "<script>window.location = 'control.php';</script>";
      } else {
        echo "<script>alert('操作失败。');</script>";
      }
    }

    // Close the database connection
    $conn->close();
    ?>
  </table>
</body>

</html>