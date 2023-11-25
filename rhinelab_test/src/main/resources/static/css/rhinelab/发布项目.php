<!DOCTYPE html> 
<html> 
<head>
	<title>Project Launch</title> 
	<style>
		body {
			font-family:'Times New Roman', Times, serif;
			background-color: #eee;
		}
		.form-container {
			width: 40%;
			margin: 50px auto;
			background-color: rgba(255, 255, 255, 0.8);
			padding: 20px;
			border-radius: 10px;
		}
        h1 {
			text-align: center;
		}

		form {
			margin-top: 20px;
		}

		input[type="text"],
		select,
		input[type="number"],
        input[type="tel"] {
			padding: 5px;
			border-radius: 5px;
			border: 1px solid #ccc;
			width: 100%;
			margin-bottom: 10px;
		}

		input[type="submit"] {
			padding: 10px;
			background-color: #4CAF50;
			color: white;
			border-radius: 5px;
			border: none;
			cursor: pointer;
		}

		input[type="submit"]:hover {
			background-color: #3e8e41;
		}
	</style>
</head> 
<body> 
	
	<div class="form-container">
    <h1>Project Form</h1>
		<form method="post">
			<label for="customerName">Customer Name:</label>
			<input type="text" name="customerName" id="customerName" placeholder="Customer Name..." required>
			<br><br>

			<label for="contact">Phone:</label>
			<input type="tel" name="contact" id="contact" placeholder="phone number is..." required>
			<br><br>

			<label for="projectName">Project Name:</label>
			<input type="text" name="projectName" id="projectName" placeholder="project name..." required>
			<br><br>

			<label for="projectType">Project Type:</label>
			<select name="projectType" id="projectType" required>
				<option value="">Please Choose Your Project Type</option>
				<option value="矢量级">Vector Magnitude Level</option>
				<option value="轨道级">Orbit Level</option>
				<option value="星尘级">Cosmic Level</option>
				<option value="恒星级">Stellar Level</option>
				<option value="星河级">Galactic Level</option>
			</select>
			<br><br>

			<label for="projectMeaning">Project Significance</label>
			<textarea name="projectMeaning" id="projectMeaning" placeholder="Project significance is..." required></textarea>
			<br><br>

			<label for="projectTime">Project Timeline:</label>
			<input type="number" name="projectTime" id="projectTime" placeholder="the project timeline is..." min="0" max="24" required>
			<br><br>

			<label for="projectBudget">Project Budget:</label>
			<input type="text" name="projectBudget" id="projectBudget" placeholder="the project budget is..." required>
			<br><br>

			<input type="submit" name="submit" value="Submit">
		</form>

		<?php
			// 处理表单提交
			if (isset($_POST["submit"])) {
				// 获取表单数据
				$customerName = $_POST["customerName"];
				$contact = $_POST["contact"];
				$projectName = $_POST["projectName"];
				$projectType = $_POST["projectType"];
				$projectMeaning = $_POST["projectMeaning"];
				$projectTime = $_POST["projectTime"];
				$projectBudget = $_POST["projectBudget"];

				// 检查表单数据是否为空
				if(empty($customerName) || empty($contact) || empty($projectName) || empty($projectType) || empty($projectMeaning) || empty($projectTime) || empty($projectBudget)){
					// 如果有内容为空，则提示不能提交
					echo "<p style='color: red;'>Customer name, phone, project name, project type, project significance, and either the project timeline or project budget cannot be empty. Please fill them out again!</p>";
				} else{
					// 检查项目总进度时间是否为数字
					if (!is_numeric($projectBudget)) {
						echo "<p style='color: red;'>The project timeline must be a number. Please fill it out again!</p>";						
					} else {
						// 连接数据库
						$conn = mysqli_connect("localhost", "root", "Zz15987324860", "rhinelab");
						if (!$conn) {
							die("连接数据库失败：" . mysqli_connect_error());
						}

						// 检查是否已经存在该联系方式
						$sql = "SELECT * FROM 客户 WHERE 联系方式 = '$contact'";
						$result = mysqli_query($conn, $sql);
						if (mysqli_num_rows($result) == 0) {
							// 如果不存在，则插入一条新的客户记录
							$sql = "INSERT INTO 客户 (联系方式, 客户名称) VALUES ('$contact', '$customerName')";
							mysqli_query($conn, $sql);
						}

					

						// 插入一条新的项目记录
						$sql = "INSERT INTO 项目 (联系方式, 项目名称, 项目类型, 项目意义, 项目总进度时间, 项目经费, 项目状态) VALUES ('$contact', '$projectName', '$projectType', '$projectMeaning', '$projectTime', '$projectBudget', '待审核')";
						if (mysqli_query($conn, $sql)) {
							// 提交成功，显示成功信息
							echo "<p style='color: green;'>Project submitted successfully!</p>";
						} else {
							// 提交失败，显示错误信息
							echo "<p style='color: red;'>Project submission failed with the problem:" . mysqli_error($conn) . "</p>";
						}

						// 关闭数据库连接
						mysqli_close($conn);
					}
				}
			}
		?>
	</div>
</body> 
</html>