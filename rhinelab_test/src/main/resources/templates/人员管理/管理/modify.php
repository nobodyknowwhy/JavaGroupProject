<!DOCTYPE html>
<html>
<head>
  <title>修改员工信息</title>
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
    td, th {
      padding: 5px;
    }
    .hidden {
      display: none;
    }
  </style>
</head>
<body>
  <h1>修改员工信息</h1>
  <table>
    <tr>
      <th>员工编号</th>
      <th>姓名</th>
      <th>性别</th>
      <th>民族</th>
      <th>政治面貌</th>
      <th>文化程度</th>
      <th>手机号码</th>
      <th>邮箱</th>
      <th>入职时间</th>
      <th>员工基本工资</th>
      <th>部门编号</th>
      <th>状态</th>
      <th>操作</th>
    </tr>
    <?php
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

      // Construct the SQL query to select all employees
      $sql = "SELECT * FROM 员工";
      $result = $conn->query($sql);

      // Check if any rows were returned
      if ($result->num_rows > 0) {
        // Output the data as an HTML table
        while ($row = $result->fetch_assoc()) {
          echo "<tr><form method='post'>";
          echo "<td>" . $row["员工编号"] . "</td>";
          echo "<td>" . $row["姓名"] . "</td>";
          echo "<td>" . $row["性别"] . "</td>";
          echo "<td>" . $row["民族"] . "</td>";
          echo "<td>" . $row["政治面貌"] . "</td>";
          echo "<td><input type='text' name='culture' value='" . htmlspecialchars($row["文化程度"]) . "' style='width: 61px;'></td>";
          echo "<td><input type='text' name='phone' value='" . htmlspecialchars($row["手机号码"]) . "' style='width: 91px;'></td>";
          echo "<td><input type='text' name='email' value='" . htmlspecialchars($row["邮箱"]) . "' style='width: 121px;'></td>";
          echo "<td>" . $row["入职时间"] . "</td>";
          echo "<td><input type='text' name='salary' value='" . htmlspecialchars($row["员工基本工资"]) . "' style='width: 51px;'></td>";
          echo "<td><input type='text' name='department_id' value='" . htmlspecialchars($row["部门编号"]) . "' style='width: 41px;'></td>";
          echo "<td><input type='text' name='status' value='" . htmlspecialchars($row["状态"]) . "' style='width: 51px;'></td>";
          echo "<td><input type='submit' name='confirm' value='确认'><input type='submit' name='cancel' value='取消'></td>";
          echo "<input type='hidden' name='employee_id' value='" . $row["员工编号"] . "'>";
          echo "</form></tr>";
        }
      } else {
        echo "<tr><td colspan='13'>没有员工信息。</td></tr>";
      }

      // Check if the confirm button was clicked
      if (isset($_POST['confirm'])) {
        $employee_id = $_POST['employee_id'];
        $culture = $_POST['culture'];
        $phone = $_POST['phone'];
        $email = $_POST['email'];
        $salary = $_POST['salary'];
        $department_id = $_POST['department_id'];
        $status = $_POST['status'];

        // Validate the phone and email fields
        if (!preg_match('/^[0-9]{9}$/',$phone)) {
          echo "<script>alert('手机号码格式不正确。');</script>";
        } else if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
          echo "<script>alert('邮箱格式不正确。');</script>";
        } else {
          // Construct the SQL query to update the employee's information
          $sql = "UPDATE 员工 SET 文化程度='$culture', 手机号码='$phone', 邮箱='$email', 员工基本工资='$salary', 部门编号='$department_id', 状态='$status' WHERE 员工编号='$employee_id'";

          // Execute the query and check if it was successful
          if ($conn->query($sql) === TRUE) {
            echo "<script>alert('员工信息已更新。');</script>";
            echo "<script>window.location = 'control.php';</script>";
          } else {
            echo "<script>alert('更新失败。');</script>";
          }
        }
      }

      // Close the database connection
      $conn->close();
    ?>
  </table>
</body>
</html>