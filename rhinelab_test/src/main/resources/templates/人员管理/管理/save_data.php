<?php
  $conn = mysqli_connect("localhost", "root", "Zz15987324860", "rhinelab");
  if ($conn-> connect_error) {
    die("Connection failed:". $conn-> connect_error);
  }
  $signInTime = $_POST["signInTime"];
  $signOutTime = $_POST["signOutTime"];
  $employeeId = $_POST["employeeId"];
  $workingHours = $_POST["workingHours"];
  $sql = "INSERT INTO 签到 (员工编号, 签到时间, 签退时间, 工时) VALUES ('$employeeId', '$signInTime', '$signOutTime', '$workingHours')";
  if ($conn-> query($sql) === TRUE) {
    echo "success";
  } else {
    echo "Error: ". $sql . "<br>" . $conn-> error;
  }
  $conn-> close();
?>