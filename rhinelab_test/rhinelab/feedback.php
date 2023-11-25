<?php
$servername = "localhost";
$username = "root";
$password = "Zz15987324860";
$dbname = "rhinelab";

$conn = new mysqli($servername, $username, $password, $dbname);


if ($conn->connect_error) {
    die("连接失败: " . $conn->connect_error);
}
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $feedback = $_POST['feedback'];

    $sql = "INSERT INTO feedback (feedbackContent) VALUES ('$feedback')";

    if ($conn->query($sql) === TRUE) {
        echo "<script>alert('Successfully submitted!'); window.location.href = './rhinelabmain.html';</script>" ;
    } else {
        echo "<script>alert('Error!'); window.location.href = './rhinelabmain.html';</script>" ;
    }
}

$conn->close();
?>
