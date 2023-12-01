<!DOCTYPE html>
<html>
<head>
  <title>修改产品信息</title>
  <meta charset="UTF-8">
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
  </style>
</head>
<body>
  <h1>修改产品信息</h1>
  <button onclick="goToShow()">查看所有产品</button>
  <table>
    <tr>
      <th>产品编号</th>
      <th>产品名称</th>
      <th>现存数量</th>
      <th>产品单价</th>
      <th>产品功能方向</th>
      <th>生产日期</th>
      <th>产品简介</th>
      <th>用户评价</th>
      <th>售量</th>
      <th>投入金额</th>
      <th>操作</th>
    </tr>
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
      $sql = "SELECT * FROM 产品";
      $result = $conn->query($sql);

      // Check if any rows were returned
      if ($result->num_rows > 0) {
        // Output the data as an HTML table
        while ($row = $result->fetch_assoc()) {
          echo "<tr><form method='post'>";
          echo "<td>" . $row["产品编号"] . "</td>";
          echo "<td>" . $row["产品名称"] . "</td>";
          echo "<td><input type='text' name='quantity' value='" . htmlspecialchars($row["现存数量"]) . "' style='width: 51px;'></td>";
          echo "<td><input type='text' name='unit_price' value='" . htmlspecialchars($row["产品单价"]) . "' style='width: 61px;'></td>";
          echo "<td>" . $row["产品功能方向"] . "</td>";
          echo "<td>" . $row["生产日期"] . "</td>";
          echo "<td><textarea name='introduction' style='width: 200px;'>" . htmlspecialchars($row["产品简介"]) . "</textarea></td>";
          echo "<td><textarea name='review' style='width: 200px;'>" . htmlspecialchars($row["用户评价"]) . "</textarea></td>";
          echo "<td>" . $row["售量"] . "</td>";
          echo "<td><input type='text' name='investment' value='" . htmlspecialchars($row["投入金额"]) . "' style='width: 61px;'></td>";
          echo "<td><input type='submit' name='confirm' value='确认'><input type='submit' name='cancel' value='取消'></td>";
          echo "<input type='hidden' name='product_id' value='" . $row["产品编号"] . "'>";
          echo "</form></tr>";
        }
      } else {
        echo "<tr><td colspan='11'>没有产品信息。</td></tr>";
      }

      // Check if the confirm button was clicked
      if (isset($_POST['confirm'])) {
        $product_id = $_POST['product_id'];
        $quantity = $_POST['quantity'];
        $unit_price = $_POST['unit_price'];
        $introduction = $_POST['introduction'];
        $review = $_POST['review'];
        $investment = $_POST['investment'];

        // Validate the quantity, unit price, and investment fields
        if (!preg_match('/^[0-9]*$/',$quantity)) {
          echo "<script>alert('现存数量只能为数字。');</script>";
        } else if (!preg_match('/^[0-9]+(?:\.[0-9]{1,2})?$/', $unit_price)) {
          echo "<script>alert('产品单价格式不正确。');</script>";
        } else if (!preg_match('/^[0-9]+(?:\.[0-9]{1,2})?$/', $investment)) {
          echo "<script>alert('投入金额格式不正确。');</script>";
        } else {
          // Construct the SQL query to update the product's information
          $sql = "UPDATE 产品 SET 现存数量='$quantity', 产品单价='$unit_price', 产品简介='$introduction', 用户评价='$review', 投入金额='$investment' WHERE 产品编号='$product_id'";

          // Execute the query and check if it was successful
          if ($conn->query($sql) === TRUE) {
            echo "<script>alert('产品信息已更新。');</script>";
            echo "<script>window.location = 'showproduction.php';</script>";
          } else {
            echo "<script>alert('更新失败。');</script>";
          }
        }
      }

      // Close the database connection
      $conn->close();
    ?>
  </table>

  <script>
    function goToShow() {
        alert('点击确认进入产品展示界面');
        window.location = 'showproduction.php';
    }
  </script>
</body>
</html>