<!DOCTYPE html>
<html>

<head>
	<title>Login Page For Adminisrat</title>
	<style>
		body {
			background: linear-gradient(to bottom right, #5f5f5f, #444444);
			display: flex;
			align-items: center;
			justify-content: center;
			height: 100vh;
			font-family: sans-serif;
		}

		form {
			display: flex;
			flex-direction: column;
			
			padding: 40px;
			border: 2px solid #ccc;
			border-radius: 10px;
			background-color: #f2f2f2;
			box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.2);
		}

		h2 {
			margin: 0;
			font-size: 28px;
			font-weight: bold;
			color: #333;
			text-align: center;
			margin-bottom: 20px;
			text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
		}

		label {
			font-size: 16px;
			/* 将字体大小从 18px 修改为 16px */
			color: #333;
			margin-bottom: 10px;
			text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
			text-align: left;
			/* 将文本左对齐 */
		}

		input[type=text],
		input[type=password] {
			width: 100%;
			padding: 12px 20px;
			margin: 8px 0;
			box-sizing: border-box;
			border: none;
			border-radius: 4px;
			background-color: #f9f9f9;
			color: #555;
			font-size: 16px;
			font-weight: bold;
			text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
		}

		input[type=text]:focus,
		input[type=password]:focus {
			background-color: #ddd;
			outline: none;
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
			font-size: 18px;
			font-weight: bold;
			text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
			transition: background-color 0.3s ease;
		}

		button:hover {
			background-color: #3e8e41;
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
		$username = $_POST["username"];
		$password = $_POST["password"];

		$servername = "localhost";
		$username_db = "root";
		$password_db = "Zz15987324860";
		$dbname = "rhinelab";
		$conn = new mysqli($servername, $username_db, $password_db, $dbname);

		if ($conn->connect_error) {
			die("Connection failed: " . $conn->connect_error);
		}

		$sql = "SELECT * FROM 员工 WHERE 员工编号='$username' AND 密码='$password' and (员工级别='2' or 员工级别='3')";
		$result = $conn->query($sql);

		if ($result->num_rows > 0) {
			$row = $result->fetch_assoc();
			$employee_name = $row["姓名"];

			echo "<script>alert('Welcome to Purchase Information, $employee_name !'); window.location.href = './product_buying.php';</script>";
			exit();
		} else {
			echo "<script>alert('Access denied.')</script>";
		}

		$conn->close();
	}
	?>
</body>

</html>