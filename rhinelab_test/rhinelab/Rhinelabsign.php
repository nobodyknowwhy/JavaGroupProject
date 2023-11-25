<?php
$servername = "localhost";
$username = "root";
$password = "Zz15987324860";
$dbname = "RHINELAB";


$conn = new mysqli($servername, $username, $password, $dbname);


if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}


if ($_SERVER["REQUEST_METHOD"] == "POST") {

    $name = $_POST["name"];
    $password = $_POST["password"];
    $confirmpassword = $_POST["confirm-password"];
    $gender = $_POST["gender"];
    $email = $_POST["email"];
    $sqluser = "SELECT * FROM 用户 WHERE 姓名 = '$name'";
    $sqlemail = "SELECT * FROM 用户 WHERE 邮箱 = '$email'";
    $resultuser = mysqli_query($conn, $sqluser);
    $resultemail = mysqli_query($conn, $sqlemail);



    if ($password != $confirmpassword) {
        echo "<script>alert('You password and confirm-password do not match!'); window.location.href = './Rhinelab_resign.php';</script>";
    } else {

        if (mysqli_num_rows($resultuser) > 0 || mysqli_num_rows($resultemail) > 0) {
            echo "<script>alert('This user name or email has already been used!'); window.location.href = './Rhinelab_resign.php';</script>";
        } else {
            $sqluserinsert = "INSERT INTO 用户 (姓名, 性别, 密码, 邮箱) VALUES ('$name','$gender', '$password', '$email')";
            $resultuserinsert = mysqli_query($conn, $sqluserinsert);
            if ($resultuserinsert) {
                echo "<script>alert('successfully sign in!'); window.location.href = './Rhinelab_resign.php'; </script>";
            } else {
                echo "<script>alert('fail to sign in!'); window.location.href = './Rhinelab_resign.php'</script>";
            }
        }
    }
}


$conn->close();
