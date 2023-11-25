<!DOCTYPE html>
<html>
<head>
    <title>Search Page</title>
    <style type="text/css">
        body {
            font-family: 'Times New Roman', Times, serif;
            background-color: #eee;
        }
        #form-div {
            width: 80%;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px #aaa;
            margin-top: 20px;
            margin-bottom: 20px;
            display: flex;
            align-items: left;
            justify-content: space-between;
        }
        form {
            display: flex;
            align-items: left;
            justify-content: space-between;
            flex: 1;
        }
        label {
            display: block;
            margin-bottom: 10px;
            font-weight: bold;
            font-size: 18px;
        }
        select, input[type="text"], input[type="submit"] {
            padding: 1px;
            font-size: 16px;
            border-radius: 5px;
            width: 50px;
            border: none;
            flex: 1;
            margin-right: 50px;
            box-shadow: 0 0 5px #aaa;
        }
        input[type="submit"] {
            background-color: #4CAF50;
            color: #fff;
            cursor: pointer;
            transition: background-color .3s, color .3s;
        }
        input[type="submit"]:hover {
            background-color: #fff;
            color: #4CAF50;
        }
        #result {
            overflow: auto;
            height: 500px;
            margin-bottom: 20px;
            width: 80%;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px #aaa;
        }
        table {
            border-collapse: collapse;
            width: 100%;
        }
        th, td {
            text-align: left;
            padding: 8px;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #4CAF50;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        .pagination {
            display: inline-block;
            margin-top: 20px;
        }
        .pagination a {
            color: black;
            float: left;
            padding: 8px 16px;
            text-decoration: none;
            transition: background-color .3s;
            border: 1px solid #ddd;
            margin: 0 4px;
        }
        .pagination a.active {
            background-color: #4CAF50;
            color: white;
            border: 1px solid #4CAF50;
        }
        .pagination a:hover:not(.active) {background-color: #ddd;}
    </style>
</head>
<body>
<div id="form-div">

    <form method="post">
		<label for="type">Search Type:</label>
		<select name="type" id="type">
			<option value="">--choice--</option>
			<option value="order">Check order status</option>
			<option value="project">Check project progress</option>
		</select>
		<br><br>
		<label for="contact">Phone</label>
		<input type="text" name="contact" id="contact" placeholder="please enter your phone number">
		<br><br>
		<input type="submit" name="submit" value="Search">
	</form>
</div>
<div id="result">
		<?php
		// 连接数据库
		$conn = mysqli_connect("localhost", "root", "Zz15987324860", "rhinelab");
		if (!$conn) {
			die("连接数据库失败：" . mysqli_connect_error());
		}

		// 处理表单提交
		if (isset($_POST["submit"])) {
			$type = $_POST["type"];
			$contact = $_POST["contact"];

			// 检查表单数据是否为空
			if(empty($type) || empty($contact)){
				// 如果有内容为空，则提示不能提交
				echo "<p style='color: red;'>Search type and phone information cannot be empty. Please fill them out again!</p>";
			} else {
				// 根据查询类型进行查询
				if ($type == "order") {
					$sql = "SELECT * FROM 购买 WHERE 联系方式 = '$contact' ORDER BY 完成状态 DESC, 购买日期 DESC";
				} elseif ($type == "project") {
					$sql = "SELECT * FROM 项目 WHERE 联系方式 = '$contact' ORDER BY CASE 项目状态 WHEN '待审核' THEN 1 WHEN '进行中' THEN 2 ELSE 3 END";
				}

				$result = mysqli_query($conn, $sql);

				if (mysqli_num_rows($result) > 0) {
					// 分页处理
					$records_per_page = 10;
					$total_records = mysqli_num_rows($result);
					$total_pages = ceil($total_records / $records_per_page);
					if (isset($_GET["page"]) && is_numeric($_GET["page"])) {
						$current_page = (int) $_GET["page"];
					} else {
						$current_page = 1;
					}
					if ($current_page < 1) {
						$current_page = 1;
					} elseif ($current_page > $total_pages) {
						$current_page = $total_pages;
					}
					$offset = ($current_page - 1) * $records_per_page;

					// 输出查询结果
					echo "<h2>Search Outcome</h2>";
					echo "<table><tr>";
					if ($type == "order") {
						echo "<th>Order ID</th><th>Product ID</th><th>Phone</th><th>Purchase Quantity</th><th>Total Purchase Price</th><th>Purchase Time</th><th>Completion Status</th><th>Order Address</th>";
					} elseif ($type == "project"){
						echo "<th>Project ID</th><th>Phone</th><th>Project Name</th><th>Project Type</th><th>Project Significance</th><th>Project Timeline</th><th>Project Budget</th><th>Project Status</th>";
					}
					echo "</tr>";
					$count = 0;
					mysqli_data_seek($result, $offset);
					while($row = mysqli_fetch_assoc($result)) {
						if ($count >= $records_per_page) break;
						echo "<tr>";
						foreach ($row as $key => $value) {
							echo "<td>".$value."</td>";
						}
						echo "</tr>";
						$count++;
					}
					echo "</table>";

					// 输出分页链接
					echo "<div class='pagination'>";
					if ($total_pages > 1) {
						if ($current_page > 1) {
							echo "<a href='?type=$type&contact=$contact&page=".($current_page-1)."'>上一页</a>";
						}
						for ($i = 1; $i <= $total_pages; $i++) {
							if ($i == $current_page) {
								echo "<a class='active'>".$i."</a>";
							} else {
								echo "<a href='?type=$type&contact=$contact&page=$i'>".$i."</a>";
							}
						}
						if ($current_page < $total_pages) {
							echo "<a href='?type=$type&contact=$contact&page=".($current_page+1)."'>下一页</a>";
						}
					}
					echo "</div>";
				} else {
					echo "<p>Nothing recorded。</p>";
				}
			}
		}

		// 关闭数据库连接
		mysqli_close($conn);
		?>
	</div>
</body>
</html>