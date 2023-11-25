<!DOCTYPE html>
<html>
<head>
	<title>Login Page</title>
	<style>
		body {
			display: flex;
			align-items: center;
			justify-content: center;
			height: 100vh;
		}
		form {
			display: flex;
			flex-direction: column;
			align-items: center;
			padding: 20px;
			border: 2px solid gray;
			border-radius: 10px;
			background-color: #f2f2f2;
		}
		input[type=text], input[type=password] {
			width: 100%;
			padding: 12px 20px;
			margin: 8px 0;
			box-sizing: border-box;
			border: none;
			border-radius: 4px;
		}
		button {
			background-color: #4CAF50;
			color: white;
			padding: 14px 20px;
			margin: 8px 0;
			border: none;
			border-radius: 4px;
			cursor: pointer;
			width: 100%;
		}
		button:hover {
			background-color: #45a049;
		}
	</style>
</head>
<body>
	<form method="post">
		<h2>Login</h2>
		<label for="username">Employee Number</label>
		<input type="text" id="username" name="username" placeholder="Enter employee number" required>

		<label for="password">Password</label>
		<input type="password" id="password" name="password" placeholder="Enter password" required>

		<button type="submit">Login</button>
	</form>

	<?php
		if ($_SERVER["REQUEST_METHOD"] == "POST") {
			// 获取输入的员工编号和密码
			$username = $_POST["username"];
			$password = $_POST["password"];

			// 连接数据库
			$servername = "localhost";
			$username_db = "root";
			$password_db = "Zz15987324860";
			$dbname = "rhinelabkl";
			$conn = new mysqli($servername, $username_db, $password_db, $dbname);

			// 检查连接是否成功
			if ($conn->connect_error) {
				die("Connection failed: " . $conn->connect_error);
			}

			// 在 "员工" 表格中查找匹配的员工编号和密码
			$sql = "SELECT * FROM 员工 WHERE 员工编号='$username' AND 密码='$password' and (员工级别='2' or 员工级别='3')";
			$result = $conn->query($sql);


			// 如果查找到匹配的行，则跳转到 a.html
			if ($result->num_rows > 0) {
                $row = $result->fetch_assoc();
                $employee_name = $row["姓名"];

				echo "<script>alert('Welcome to Purchase Information, $employee_name !'); window.location.href = './product_buying.php';</script>" ;
				exit();
			} else {
				// 否则弹出提示框并保留在当前页面
				echo "<script>alert('Access denied.')</script>";
			}

			$conn->close();
		}
	?>
</body>
</html>