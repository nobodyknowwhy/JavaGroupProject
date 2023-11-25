<?php
$servername = "localhost";
$username = "root";
$password = "Zz15987324860";
$dbname = "rhinelab";


$conn = new mysqli($servername, $username, $password, $dbname);


if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}


if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $email = $_POST["email"];
    $password = $_POST["password"];

    $sqluseronly = "SELECT * FROM 用户 WHERE 邮箱 = '$email'";
    $sqluser = "SELECT * FROM 用户 WHERE 邮箱 = '$email' AND 密码 = '$password'";
    $sqlusername = "SELECT Name FROM 用户 WHERE 邮箱 = '$email'";
    
    $sqluserresult = mysqli_query($conn, $sqluser);
    $sqluserresultonly = mysqli_query($conn, $sqluseronly);
    $sqluserresultName = mysqli_query($conn, $sqlusername);
    $row = mysqli_fetch_assoc($sqluserresultName);
    $name = $row['Name'];


    if(mysqli_num_rows($sqluserresult) > 0){
        echo "<script>
                localStorage.removeItem('Name');
                localStorage.setItem('Name', " . json_encode($name) . ");
              </script>";
        header("Location: ./rhinelabmain.html");
    }
    else{
        if(mysqli_num_rows($sqluserresultonly) > 0){
            echo "<script>alert('password error!'); window.location.href = './Rhinelab_resign.php';</script>" ;
        }
        else{
            echo "<script>alert('user not found!'); window.location.href = './Rhinelab_resign.php';</script>";
        }
    }

}

$conn->close();
