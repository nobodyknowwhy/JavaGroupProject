<!DOCTYPE html>
<html>

<head>
    <title>APPLICATION APPROVAL</title>
    <link rel='stylesheet' type='text/css' href='./project_acc.css'>
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

    $sql = "SELECT * FROM 项目 WHERE 项目状态='待审核' OR 项目状态='进行中';";
    $result = mysqli_query($conn, $sql);

    if (mysqli_num_rows($result) > 0) {

        if(mysqli_num_rows($result) > 0){
            echo "<table><tr><th>Project ID</th><th>Project Name</th><th>Project Type</th><th>Project Significance</th><th>Project Timeline</th><th>Project Budget</th></tr>";
            while ($row = mysqli_fetch_assoc($result)) {
    
                echo "<tr><td>" . $row["项目编号"] . "</td><td>" . $row["项目名称"] . "</td><td>" . $row["项目类型"] . "</td><td>" . $row["项目意义"] . "</td><td>" . $row["项目总进度时间"] . "</td><td>" . $row["项目经费"] . "</td>";
    
                echo "<td><form method='post' action='./accept.php'><input type='hidden' name='project-id' value='" . $row["项目编号"] . "'>";
                echo "<input type='hidden' name='project-name' value='" . $row["项目名称"] . "'>";
                echo "<input type='hidden' name='project-type' value='" . $row["项目类型"] . "'>";
                echo "<input type='hidden' name='project-budget' value='" . $row["项目经费"] . "'>";
                if($row["项目状态"]=="待审核"){
                    echo "<button type='submit' name='deliver'>DELIVER</button></form></td></tr>";
                }
                if($row["项目状态"]=="进行中"){
                    echo "<button type='submit' name='accomplished'>ACCOMPLISHED</button></form></td></tr>";
                }
                
                
            }
            echo "</table>";
        }


    } else {
        echo "<h1>Nothing Recorded</h1>";
    }

    mysqli_close($conn);
    ?>

</body>

</html>