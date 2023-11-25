<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $fileUpload = $_FILES['file-upload'];
    $uploadDir = 'tasks/';
    $uploadFile = $uploadDir . basename($fileUpload['name']);
    move_uploaded_file($fileUpload['tmp_name'], $uploadFile);

    echo "<script>alert('Publishing the task Successfully!'); window.location.href = './rhinelabmain.html';</script>" ;
}
?>
