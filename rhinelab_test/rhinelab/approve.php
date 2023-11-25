<!DOCTYPE html>
<html>

<head>
  <title>APPLICATION APPROVAL</title>
  <link rel='stylesheet' type='text/css' href='approve.css'>
  <link rel="icon" type="image/jpeg" href="./rhinelab/rahinelab_logo.png" sizes="32x32">
</head>

<body>

  <?php
  require_once "../html/includes/class.phpmailer.php";
  require_once "../html/includes/class.smtp.php";
  $servername = "localhost";
  $username = "root";
  $password = 'Zz15987324860';
  $dbname = "rhinelab";
  $conn = mysqli_connect($servername, $username, $password, $dbname);
  if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $id = $_POST['id'];
    $section = $_POST['department'];
    $degree = $_POST['education'];
    $name = $_POST['name'];
    $gender = $_POST['gender'];
    $nation = $_POST['nation'];
    $marry = $_POST['marry'];
    $id_card = $_POST['identical'];
    $politics_status = $_POST['status'];
    $native_place = $_POST['native-place'];
    $phone = $_POST['phone'];
    $email = $_POST['email'];
    $birth = $_POST['birth'];
    $fileUpload_p_name = $_POST['fileUpload-p'];
    $chars = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    $shuffled_chars = str_shuffle($chars);
    $random_password = substr($shuffled_chars, 0, 12);


    if (isset($_POST['approve'])) {
      $sql2 = "select 部门编号 from 部门 where 部门名称='$section'";
      $result = mysqli_query($conn, $sql2);
      $row = mysqli_fetch_assoc($result);
      $id_section =  $row['部门编号'];
      $date = date('Y-m-d'); 
      $mail = new PHPMailer(true);
      try {
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
        $mail->Subject = 'Congratulations on Your Successful Application';

        $mail->Body = nl2br("
        Dear $name,

        &nbsp&nbsp&nbsp&nbsp I am pleased to inform you that your application for the $section Member at RHINE LAB has been successful. We were impressed with your skills, experience, and qualifications, and we believe that you are a great fit for our team.

        &nbsp&nbsp&nbsp&nbsp We would like to formally offer you the position of a member in $section, with a starting date of $date. Attached to this email, you will find the employment contract and other relevant documents that you need to review and sign.

        &nbsp&nbsp&nbsp&nbsp If you have any questions or concerns, please do not hesitate to contact us. We are excited to have you join our team and look forward to working with you.

        &nbsp&nbsp&nbsp&nbsp Your member id is $id and your account's default password is “ $random_password ”, Congratulations once again on your successful application and welcome to RHINE LAB!

        Best regards,

        ZeJia Lin
        RHINE LAB

        ");
        $mail->AltBody = "注意该邮件附有附件！";
        $mail->send();
      } catch (Exception $e) {
        echo '邮件发送失败！';
        echo "<p>错误：" . $mail->ErrorInfo;
      }


      $sql = "INSERT INTO 员工 (员工编号, 姓名, 文化程度, 性别, 部门编号, 民族, 生日, 手机号码, 邮箱, 籍贯, 政治面貌, 婚姻状况, 身份证号, 照片, 入职时间, 员工级别, 员工基本工资, 身份, 密码, 状态)
      VALUES ('$id','$name', '$degree', '$gender', '$id_section', '$nation', '$birth', '$phone', '$email', '$native_place', '$politics_status', '$marry', '$id_card', '$fileUpload_p_name', '$date', '1', 7000, 'general staff', '$random_password', '在职')";




      if (mysqli_query($conn, $sql)) {
        $sql = "DELETE FROM 求职 WHERE 求职编号=$id";
        mysqli_query($conn, $sql);
      } else {
        echo "插入记录时出现错误：" . mysqli_error($conn);
      }
    }
    else{
      
      $mail = new PHPMailer(true);
      try {
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
        $mail->Subject = 'Regretful news regarding your application to RHINE LAB';

        $mail->Body = nl2br("
        Dear $name,

        &nbsp&nbsp&nbsp&nbspThank you for taking the time to apply for a position at our company. We appreciate the effort you put into your application and the interest you showed in joining our team.

        &nbsp&nbsp&nbsp&nbspWe regret to inform you that we have decided not to move forward with your application at this time. While we received many qualified applications, we were unfortunately unable to offer a position to everyone.

        &nbsp&nbsp&nbsp&nbspPlease do not let this discourage you from pursuing other opportunities and we encourage you to keep applying for positions that match your skills and experience.

        &nbsp&nbsp&nbsp&nbspWe would like to thank you once again for your interest in our company, and we wish you the best of luck in your future endeavors.

        Sincerely,
        ZeJia Lin
        RHINE LAB

        ");
        $mail->AltBody = "注意该邮件附有附件！";
        $mail->send();
        $sql = "DELETE FROM 求职 WHERE 求职编号=$id";
        mysqli_query($conn, $sql);
      } catch (Exception $e) {
        echo '邮件发送失败！';
        echo "<p>错误：" . $mail->ErrorInfo;
      }

    }


    


  }

  $sql = "SELECT * FROM 求职";
  $result = mysqli_query($conn, $sql);

  if (mysqli_num_rows($result) > 0) {
    echo "<table><tr><th>Name</th><th>Applying Department</th><th>Education</th><th>Application Form</th><th>Action</th></tr>";
    while ($row = mysqli_fetch_assoc($result)) {
      $folder = "application/";
      $path = $folder . $row['申请表'];

      $link = "<a href=\"$path\" download>" . $row['申请表'] . "</a>";

      echo "<tr><td>" . $row["姓名"] . "</td><td>" . $row["申请部门"] . "</td><td>" . $row["文化程度"] . "</td><td>" . $link . "</td>";

      echo "<td><form method='post'><input type='hidden' name='id' value='" . $row["求职编号"] . "'>";
      echo "<input type='hidden' name='department' value='" . $row["申请部门"] . "'>";
      echo "<input type='hidden' name='name' value='" . $row["姓名"] . "'>";
      echo "<input type='hidden' name='birth' value='" . $row["生日"] . "'>";
      echo "<input type='hidden' name='education' value='" . $row["文化程度"] . "'>";
      echo "<input type='hidden' name='gender' value='" . $row["性别"] . "'>";
      echo "<input type='hidden' name='nation' value='" . $row["民族"] . "'>";
      echo "<input type='hidden' name='status' value='" . $row["政治面貌"] . "'>";
      echo "<input type='hidden' name='marry' value='" . $row["婚姻状况"] . "'>";
      echo "<input type='hidden' name='native-place' value='" . $row["籍贯"] . "'>";
      echo "<input type='hidden' name='identical' value='" . $row["身份证号"] . "'>";
      echo "<input type='hidden' name='phone' value='" . $row["手机号码"] . "'>";
      echo "<input type='hidden' name='email' value='" . $row["邮箱"] . "'>";
      echo "<input type='hidden' name='fileUpload-p' value='" . $row["照片"] . "'>";
      echo "<button type='submit' name='approve'>APPROVE</button>";
      echo "<button type='submit' name='reject'>REJECT</button></form></td></tr>";
    }
    echo "</table>";
  } else {
    echo "<h1>Nothing Recorded</h1>";
  }
  mysqli_close($conn);
  ?>

</body>

</html>