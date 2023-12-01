<!DOCTYPE html>
<html>
<head>
    <title>申请表</title>
    <style>
        body {
            background-color: #e6f2ff;
        }
        form {
            display: table;
            margin: auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-shadow: 0 0 5px #ccc;
        }
        label {
            display: table-row;
            margin-bottom: 10px;
        }
        input, textarea {
            display: table-cell;
            padding: 5px;
            border-radius: 3px;
            border: 1px solid #ccc;
            width: 200px;
        }
        input[type="submit"] {
            margin-top: 20px;
            display: block;
            width: 100%;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            padding: 10px;
            font-size: 16px;
            cursor: pointer;
        }
        h1 {
            text-align: center;
            margin-left: -100px; /* Move the heading to the left */
            display: inline-block; /* Add this line to make the heading inline */
        }
        button {
            margin-right: 10px; /* Add some space to the right of the button */
            background-color: #008CBA; /* Change the background color */
            color: white; /* Change the text color */
            border: none;
            border-radius: 5px;
            padding: 10px;
            font-size: 16px;
            cursor: pointer;
            display: inline-block; /* Add this line to make the button inline */
            margin-right: 120px;
        }
        .header {
            text-align: center;
            margin-right: 60px;
        }
    </style>
</head>
<body>
    <div class="header">
        <button onclick="goBack()">返回</button>
        <h1>申请表</h1>
    </div>
    <form action="submit.php" method="POST">
        <label>员工编号:</label>
        <input type="text" name="employee_id"><br><br>
        <label>姓名:</label>
        <input type="text" name="name"><br><br>
        <label>原部门:</label>
        <input type="text" name="old_department"><br><br>
        <label>新部门:</label>
        <input type="text" name="new_department"><br><br>
        <label>原工资:</label>
        <input type="number" name="old_salary"><br><br>
        <label>新工资:</label>
        <input type="number" name="new_salary"><br><br>
        <label>申请理由:</label><br>
        <textarea name="reason" rows="5" cols="30"></textarea><br><br>
        <input type="submit" value="提交">
    </form>
    <script>
        function goBack() {
            window.history.back();
        }
    </script>
</body>
</html>