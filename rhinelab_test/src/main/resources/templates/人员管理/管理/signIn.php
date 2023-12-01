<?php
    session_start();
    define('ROOT_PATH', '');
    $username = $_SESSION['username'];
    $email = $_SESSION['email'];
?>

<!DOCTYPE html>
<html>
<head>
  <title>签到信息</title>
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
  <h1>签到信息</h1>
  <form method="post">
    <label for="name">姓名：</label>
    <input type="text" name="name" id="name">
    <input type="submit" name="search" value="查询">
    <button><a href='control.php' class='black'>返回</a></button>
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

    // Construct the SQL query to select all sign-in records with employee names
    $sql = "SELECT 签到.员工编号,员工.姓名,签到时间,签退时间,工时 FROM 签到 JOIN 员工 ON 签到.员工编号=员工.员工编号";
    $result = $conn->query($sql);

    // Check if any rows were returned
    if ($result->num_rows > 0) {
      // Output the data as an HTML table
      echo "<table id='signIn-table'>";
      echo "<tr><th>员工编号</th><th>姓名</th><th>签到时间</th><th>签退时间</th><th>工时</th></tr>";
      while ($row = $result->fetch_assoc()) {
        echo "<tr><td>" . $row["员工编号"] . "</td><td>" . $row["姓名"] . "</td><td>" . $row["签到时间"]. "</td><td>" . $row["签退时间"]. "</td><td>" . $row["工时"]. "</td></tr>";
      }
      echo "</table>";
    } else {
      echo "0 results";
    }

    // Check if a search query was submitted
    if (isset($_POST['search'])) {
      $name = $_POST['name'];

      // Construct the SQL query with a prepared statement to prevent SQL injection attacks
      $sql = "SELECT 签到.员工编号,员工.姓名,签到时间,签退时间,工时 FROM 签到 JOIN 员工 ON 签到.员工编号=员工.员工编号 WHERE 姓名=?";
      $stmt = $conn->prepare($sql);
      $stmt->bind_param("s", $name);
      $stmt->execute();
      $result = $stmt->get_result();

      // Check if any rows were returned
      if ($result->num_rows > 0) {
        // Output the data as an HTML table
        echo "<table id='signIn-table'>";
        echo "<tr><th>员工编号</th><th>姓名</th><th>签到时间</th><th>签退时间</th><th>工时</th></tr>";
        while ($row = $result->fetch_assoc()) {
          echo "<tr><td>" . $row["员工编号"] . "</td><td>" . $row["姓名"] . "</td><td>" . $row["签到时间"]. "</td><td>" . $row["签退时间"]. "</td><td>" . $row["工时"]. "</td></tr>";
        }
        echo "</table>";
      } else {
        echo "<p>没有找到相关签到信息。</p>";
      }

      // Hide the entire table except for the header row
      echo "<script>document.getElementById('signIn-table').classList.add('hidden');</script>";
    }

    // Close the database connection
    $conn->close();
  ?>
</body>
</html>