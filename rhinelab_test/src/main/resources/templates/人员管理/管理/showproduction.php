<?php
  session_start();
  define('ROOT_PATH', '');
?>

<!DOCTYPE html>
<html>
<head>
  <title>产品管理</title>
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
  <h1>产品管理</h1>
  <form method="post">
    <label for="name">产品名称：</label>
    <input type="text" name="name" id="name">
    <input type="submit" name="search" value="查询">
    <button><a href='production.php' class='black'>修改</a></button>
    <button ><a href='control.php' class='black'>返回</a></button>
  </form>
  <br>
  <?php
    // Replace the values in these variables with your own database credentials
    $servername = "localhost";
    $username = "root";
    $password = "Zz15987324860";
    $dbname = "rhinelab";

    // Create a connection to the database
    $conn = new mysqli($servername, $username, $password, $dbname);

    // Check if the connection was successful
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }

    // Construct the SQL query to select all products
    $sql = "SELECT 产品编号,产品名称,现存数量,产品单价,产品功能方向,生产日期,产品简介,用户评价,售量,投入金额 FROM 产品";
    $result = $conn->query($sql);

    // Check if any rows were returned
    if ($result->num_rows > 0) {
      // Output the data as an HTML table
      echo "<table id='product-table'>";
      echo "<tr><th>产品编号</th><th>产品名称</th><th>现存数量</th><th>产品单价</th><th>产品功能方向</th><th>生产日期</th><th>产品简介</th><th>用户评价</th><th>售量</th><th>投入金额</th></tr>";
      while ($row = $result->fetch_assoc()) {
        echo "<tr><td>" . $row["产品编号"] . "</td><td>" . $row["产品名称"] . "</td><td>" . $row["现存数量"]. "</td><td>" . $row["产品单价"]. "</td><td>" . $row["产品功能方向"]. "</td><td>" . $row["生产日期"]. "</td><td>" . $row["产品简介"]. "</td><td>" . $row["用户评价"]. "</td><td>" . $row["售量"]. "</td><td>" . $row["投入金额"]. "</td></tr>";
      }
      echo "</table>";
    } else {
      echo "0 results";
    }

    // Check if a search query was submitted
    if (isset($_POST['search'])) {
      $name = $_POST['name'];

      // Construct the SQL query with a prepared statement to prevent SQL injection attacks
      $sql = "SELECT 产品编号,产品名称,现存数量,产品单价,产品功能方向,生产日期,产品简介,用户评价,售量,投入金额 FROM 产品 WHERE 产品名称=?";
      $stmt = $conn->prepare($sql);
      $stmt->bind_param("s", $name);
      $stmt->execute();
      $result = $stmt->get_result();

      // Check if any rows were returned
      if ($result->num_rows > 0) {
        // Output the data as an HTML table
        echo "<table id='product-table'>";
        echo "<tr><th>产品编号</th><th>产品名称</th><th>现存数量</th><th>产品单价</th><th>产品功能方向</th><th>生产日期</th><th>产品简介</th><th>用户评价</th><th>售量</th><th>投入金额</th></tr>";
        while ($row = $result->fetch_assoc()) {
          echo "<tr><td>" . $row["产品编号"] . "</td><td>" . $row["产品名称"] . "</td><td>" . $row["现存数量"]. "</td><td>" . $row["产品单价"]. "</td><td>" . $row["产品功能方向"]. "</td><td>" . $row["生产日期"]. "</td><td>" . $row["产品简介"]. "</td><td>" . $row["用户评价"]. "</td><td>" . $row["售量"]. "</td><td>" . $row["投入金额"]. "</td></tr>";
        }
        echo "</table>";

        } else {
        echo "没有找到相关产品";
      }
      // Hide the table with all products and show only the search result table
      echo "<script>document.getElementById('product-table').classList.add('hidden')</script>";
    }

    // Close the database connection
    $conn->close();
  ?>
</body>
</html>