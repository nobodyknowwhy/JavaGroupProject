<?php
$servername = "localhost";
$username = "root";
$password = "Zz15987324860";
$dbname = "test";

$conn = new mysqli($servername, $username, $password, $dbname);


if ($conn->connect_error) {
    die("连接失败: " . $conn->connect_error);
}
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $teamLeader = $_POST['team-leader'];
    $email = $_POST['email'];
    $projectType = $_POST['project-type'];
    $projectName = $_POST['project-name'];
    $projectBrief = $_POST['project-brief'];
    $projectSignificance = $_POST['project-significance'];
    $fileUpload = $_FILES['file-upload'];
    $uploadDir = 'uploads/';
    $uploadFile = $uploadDir . basename($fileUpload['name']);
    move_uploaded_file($fileUpload['tmp_name'], $uploadFile);

    


    // $sql = "INSERT INTO projectSub (team_leader, email, project_type, project_name, project_brief, project_significance, file_upload) VALUES ('$teamLeader', '$email', '$projectType', '$projectName', '$projectBrief', '$projectSignificance', '$uploadFile')";

    // if ($conn->query($sql) === TRUE) {
    //     echo "<script>alert('Successfully submitted!'); window.location.href = './rhinelabmain.html';</script>" ;
    // } else {
    //     echo "<script>alert('Error!'); window.location.href = './rhinelabmain.html';</script>" ;
    // }
}

$conn->close();
?>
