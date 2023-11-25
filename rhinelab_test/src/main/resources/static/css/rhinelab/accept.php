<!DOCTYPE html>
<html>

<head>
    <title>表单</title>
    <link rel="stylesheet" href="./accept.css">
</head>

<body>
    <?php
    /* 建立数据库 */
    $Server_name = "localhost";
    $User_name = "root";
    $Password = "Zz15987324860";
    $Databases_name = "rhinelab";
    $conn = new mysqli($Server_name, $User_name, $Password, $Databases_name);
    $project_number = $_POST['project-id'];
    if ($conn->connect_error) {
        die("Connection failed :" . $conn->connect_error);
    }
    if ($_SERVER["REQUEST_METHOD"] == "POST") {
        
        $intproject_number = intval($project_number);

        if (isset($_POST['accomplished'])) {

            $sqluserinsert = "UPDATE 项目 SET 项目状态='已完成' WHERE 项目编号=$intproject_number ";
            $resultuserinsert = mysqli_query($conn, $sqluserinsert);

            $conn->close();
            echo "<script>alert('mission accomplished!'); window.location.href = './project_acc.php';</script>";
        } 
    }


    $conn->close();
    ?>

    <form onsubmit="return validateForm()" action="./accept_con.php" method="post">
        <h1>Registration</h1>
        <label for="username">Name:</label>
        <label for="username">Amiya</label>
        <label for="username">Operation permissions:</label>
        <label for="username">Highest</label>
        <hr width="100%" color="white">
        <p>Welcome back!</p>
        <label for="department">Select:</label>
        <input type="checkbox" name="department[]" value="0">COMPONENTS CONTROL SECTION<br>
        <input type="checkbox" name="department[]" value="1">DEFENSE SECTION<br>
        <input type="checkbox" name="department[]" value="2">BUSINESS SECTION<br>
        <input type="checkbox" name="department[]" value="3">HUMAN RESOURCES INVESTIGATION SECTION<br>
        <input type="checkbox" name="department[]" value="4">ENGINEERING SECTION<br>
        <input type="checkbox" name="department[]" value="5">STRUCTURAL SECTION<br>
        <input type="checkbox" name="department[]" value="6">ENERGY SECTION<br>
        <input type="checkbox" name="department[]" value="7">ORIGINIUM ART SECTION<br>
        <input type="checkbox" name="department[]" value="8">ECOLOGICAL SECTION<br>
        <input type="checkbox" name="department[]" value="9">SCIENTIFIC INVESTIGATION SECTION<br><br>
        <input type="hidden" name="project-id" value="<?php echo $project_number; ?>">
        <label for="password">Note</label>
        <input type="text" id="Note" name="Note">
        <input type="submit" value="Submit">
    </form>


</body>
<script src="./Node.js"></script><!--引入js  -->

</html>