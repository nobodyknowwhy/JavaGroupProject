<?php
// Connect to the database
$servername = "localhost";
$username = "root";
$password = "Zz15987324860";
$dbname = "rhinelab";

$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

// Get the form data
$employee_id = $_POST['employee_id'];
$name = $_POST['name'];
$old_department = $_POST['old_department'];
$new_department = $_POST['new_department'];
$old_salary = $_POST['old_salary'];
$new_salary = $_POST['new_salary'];
$reason = $_POST['reason'];

// Insert the data into the database
$sql = "INSERT INTO 申请 (员工编号, 姓名, 原部门, 新部门, 原工资, 新工资, 申请理由)
VALUES ('$employee_id', '$name', '$old_department', '$new_department', '$old_salary', '$new_salary', '$reason')";

if ($conn->query($sql) === TRUE) {
  // Display success message in popup window
  echo "<script>alert('已递交申请表'); window.location.href = 'shenqing.php';</script>";
} else {
  // Display error message in popup window
  echo "<script>alert('Error: " . $sql . "<br>" . $conn->error . "'); window.location.href = 'shenqing.php';</script>";
}

$conn->close();
?>