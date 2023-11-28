<?php
    session_start();
    define('ROOT_PATH', '');
    $username = $_SESSION['username'];
    $email = $_SESSION['email'];
?>

<!DOCTYPE html>
<html>
<head>
  <title>员工管理</title>
  <style>
    body {
      text-align: center;
    }
    table {
      background-color: #e6f2ff;
      margin: 0 auto;
      text-align: center;
    }
    td, th {
      padding: 5px;
    }
    .hidden {
      display: none;
    }
    .black{
        text-decoration: none;
        color:#000;
    }
  </style>
</head>
<body>
  <h1>员工管理</h1>
  <form method="post">
    <label for="name">姓名：</label>
    <input type="text" name="name" id="name">
    <input type="submit" name="search" value="查询">
    <div>
      <button> <a href = 'modify.php' class='black'>修改</a></button>
      <button> <a href = 'application.php' class='black'>查看申请</a></button>
      <button> <a href = '../../approve.php' class='black'>查看求职</a></button>
      <button> <a href = 'production.php' class='black'>修改产品</a></button>
      <button> <a href = 'signIn.php' class='black'>查看签到</a></button>
      <button><a href='index.php' class='black'>返回</a></button>
    </div>
  </form>
  <br>
  <?php
 
    $servername = "localhost";
    $username = "root";
    $password = "Zz15987324860";
    $dbname = "rhinelab";


    $conn = new mysqli($servername, $username, $password, $dbname);


    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }


    $sql = "SELECT 员工编号,姓名,性别,民族,政治面貌,文化程度,手机号码,邮箱,入职时间,员工基本工资,部门编号,状态 FROM 员工";
    $result = $conn->query($sql);
    if ($result->num_rows > 0) {
      echo "<table id='employee-table'>";
      echo "<tr><th>员工编号</th><th>姓名</th><th>性别</th><th>民族</th><th>政治面貌</th><th>文化程度</th><th>手机号码</th><th>邮箱</th><th>入职时间</th><th>员工基本工资</th><th>部门编号</th><th>状态</th></tr>";
      while ($row = $result->fetch_assoc()) {
        echo "<tr><td>" . $row["员工编号"] . "</td><td>" . $row["姓名"] . "</td><td>" . $row["性别"]. "</td><td>" . $row["民族"]. "</td><td>" . $row["政治面貌"]. "</td><td>" . $row["文化程度"]. "</td><td>" . $row["手机号码"]. "</td><td>" . $row["邮箱"]. "</td><td>" . $row["入职时间"]. "</td><td>" . $row["员工基本工资"]. "</td><td>" . $row["部门编号"]. "</td><td>" . $row["状态"] . "</td></tr>";
      }
      echo "</table>";
    } else {
      echo "0 results";
    }

   
    if (isset($_POST['search'])) {
      $name = $_POST['name'];


      $sql = "SELECT 员工编号,姓名,性别,民族,政治面貌,文化程度,手机号码,邮箱,入职时间,员工基本工资,部门编号,状态 FROM 员工 WHERE 姓名=?";
      $stmt = $conn->prepare($sql);
      $stmt->bind_param("s", $name);
      $stmt->execute();
      $result = $stmt->get_result();

      if ($result->num_rows > 0) {

        echo "<table id='employee-table'>";
        echo "<tr><th>员工编号</th><th>姓名</th><th>性别</th><th>民族</th><th>政治面貌</th><th>文化程度</th><th>手机号码</th><th>邮箱</th><th>入职时间</th><th>员工基本工资</th><th>部门编号</th><th>状态</th></tr>";
        while ($row = $result->fetch_assoc()) {
          echo "<tr><td>" . $row["员工编号"] . "</td><td>" . $row["姓名"] . "</td><td>" . $row["性别"]. "</td><td>" . $row["民族"]. "</td><td>" . $row["政治面貌"]. "</td><td>" . $row["文化程度"]. "</td><td>" . $row["手机号码"]. "</td><td>" . $row["邮箱"]. "</td><td>" . $row["入职时间"]. "</td><td>" . $row["员工基本工资"]. "</td><td>" . $row["部门编号"]. "</td><td>" . $row["状态"] . "</td></tr>";
        }
        echo "</table>";
      } else {
        echo "<p>没有找到相关员工信息。</p>";
      }


      echo "<script>document.getElementById('employee-table').classList.add('hidden')</script>";
    }


    $conn->close();
  ?>
    <script>
        function goToModify() {
            alert('点击确认进入修改界面');
            window.location = 'modify.php';
        }

    </script>
</body>
</html>