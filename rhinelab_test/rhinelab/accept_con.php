<?php
/* 建立数据库 */
$Server_name="localhost";
$User_name="root";
$Password="Zz15987324860";
$Databases_name="rhinelab";
$conn=new mysqli($Server_name,$User_name,$Password,$Databases_name);
if($conn->connect_error){
    die("Connection failed :" . $conn->connect_error);
}
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $section_number=$_POST['department'];
    $project_number = $_POST['project-id'];


     if (!empty($section_number)) { 
        foreach ($section_number as $value) {

            $intValue = intval($value); 
            $intproject_number = intval($project_number); 
            $sqluserinsert = "INSERT INTO 接取 (项目编号, 部门编号) VALUES ($intproject_number,$intValue) ";
            $resultuserinsert = mysqli_query($conn, $sqluserinsert);
            
        }
        $project_number = $_POST['project-id'];
        $intproject_number = intval($project_number);
        $sqluserinsert = "UPDATE 项目 SET 项目状态='进行中' WHERE 项目编号=$intproject_number ";
        $resultuserinsert = mysqli_query($conn, $sqluserinsert);
        echo "<script>alert('Accept Successfully!'); window.location.href = './project_acc.php';</script>";
        
    } else {
        echo '未选择任何值！';
    } 
}


        
    $conn->close();
?>