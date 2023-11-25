<?php
require_once "../includes/class.phpmailer.php";
require_once "../includes/class.smtp.php";

// 连接数据库
$host = 'localhost';
$dbname = 'form';
$username = 'root';
$password = '12345678';
$mysqli = new mysqli($host, $username, $password, $dbname);
if ($mysqli->connect_error) {
    die('数据库连接失败：' . $mysqli->connect_error);
}

// 获取用户输入的用户名和邮箱
$username = $_POST['username'];
$email = $_POST['email'];

echo $username."<br/>".$email;
// 查询数据库中该用户名对应的密码
$stmt = $mysqli->prepare('SELECT password FROM login WHERE username = ? AND email = ?');
$stmt->bind_param('ss', $username, $email);
$stmt->execute();
$stmt->bind_result($password);
if ($stmt->fetch()) {
    // 密码匹配，发送邮件
    // 使用PHPMailer库发送邮件
    $mail = new PHPMailer(true);
    try {
        $mail->isSMTP();
	    $mail->CharSet = "UTF-8";
	    $mail->Host = "smtp.qq.com";
	    $mail->SMTPAuth = true;
	    $mail->Username = "2511950954@qq.com";
	    $mail->Password = "bnsybpgpjtdxdidi";
	    $mail->From = "2511950954@qq.com";
	    $mail->FromName = "林泽佳";
	    $mail->addAddress("$email");
	    $mail->addReplyTo("2511950954@qq.com");
	    $mail->isHTML(true);
	    $mail->Subject = '您的密码已找回';
	    $mail->Body = "<h3>使用PHPMailer库发送电子邮件，您的密码是：$password</h3>";
	    $mail->AltBody = "注意该邮件附有附件！";
        $mail->send();
        echo '密码已发送到您的邮箱，请查收。';
    } catch (Exception $e) {
        echo '邮件发送失败！';
        echo "<p>错误：".$mail->ErrorInfo;
    }
} else {
    // 密码不匹配，提示用户输入错误
    echo '用户名或邮箱不正确，请重新输入。';
}

$stmt->close();
$mysqli->close();
?>
