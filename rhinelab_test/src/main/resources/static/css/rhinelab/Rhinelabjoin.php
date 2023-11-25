<?php
$servername = "localhost";
$username = "root";
$password = "Zz15987324860";
$dbname = "rhinelab";


$conn = new mysqli($servername, $username, $password, $dbname);


if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
    exit();
}


if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $name = $_POST["name"];
    $degree = $_POST["degree"];
    $others = $_POST["others"];
    $gender = $_POST["gender"];
    $section = $_POST["section"];
    $nation = $_POST["nation"];
    $birth = $_POST["birth"];
    $phone = $_POST['phone'];
    $email = $_POST['email'];
    $native_place = $_POST['native-place'];
    $politics_status = $_POST['politics-status'];
    $marry = $_POST['marry'];
    $id_card = $_POST['id-card'];
    $fileUpload = $_FILES['file-upload'];
    $fileUpload_p = $_FILES['file-upload-picture'];
    $fileUpload_name = $_FILES['file-upload']['name'];
    $fileUpload_p_name = $_FILES['file-upload-picture']['name'];

    $uploadDir = 'application/';
    $uploadFile = $uploadDir . basename($fileUpload['name']);
    move_uploaded_file($fileUpload['tmp_name'], $uploadFile);

    $uploadDir2 = '人员管理/干员/LinImage/';
    $uploadFile = $uploadDir2 . basename($fileUpload_p['name']);
    move_uploaded_file($fileUpload_p['tmp_name'], $uploadFile);

    switch ($degree) {
        case "academician":
            $degree = "大学";
            break;
        case "associate":
            $degree = "研究生";
            break;
        case "bachelor":
            $degree = "学士";
            break;
        case "master":
            $degree = "导师";
            break;
        case "doctoral":
            $degree = "博士";
            break;
        default:
            
            break;
    }
    


    if (!$others) {
        $sqluserinsert = "INSERT INTO 求职 (姓名, 文化程度, 性别, 申请部门, 民族, 生日, 手机号码, 邮箱, 籍贯, 政治面貌, 婚姻状况, 身份证号, 照片, 申请表)
                          VALUES ('$name', '$degree', '$gender', '$section', '$nation', '$birth', '$phone', '$email', '$native_place', '$politics_status', '$marry', '$id_card', '$fileUpload_p_name', '$fileUpload_name')";
    } else {
        $sqluserinsert = "INSERT INTO 求职 (姓名, 文化程度, 性别, 申请部门, 民族, 生日, 手机号码, 邮箱, 籍贯, 政治面貌, 婚姻状况, 身份证号, 照片, 申请表)
                          VALUES ('$name', '$others', '$gender', '$section', '$nation', '$birth', '$phone', '$email', '$native_place', '$politics_status', '$marry', '$id_card', '$fileUpload_p_name', '$fileUpload_name')";
    }

    $resultuserinsert = mysqli_query($conn, $sqluserinsert);

    if (!$resultuserinsert) {
        printf("Error: %s\n", mysqli_error($conn));
    } else {
        header("Location: ./joinafter.html");
    }
}


$conn->close();
