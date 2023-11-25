<!DOCTYPE html>
<html>


<head>
	<meta charset="UTF-8">
	<title>RHINELAB login</title>
	<link rel="icon" type="image/jpeg" href="./rhinelab/rahinelab_logo.png" sizes="32x32">
	<link rel="stylesheet" href="styleofresign.css">

</head>

<body>
	<div class="container">
		<div class="form-container">
			<div class="form-toggle">
				<input type="radio" name="form-toggle" value="login" id="login-radio" checked>
				<label for="login-radio">Log In</label>
				<input type="radio" name="form-toggle" value="register" id="register-radio">
				<label for="register-radio">Register</label>
			</div>
			<form class="login-form" id="login-form" method="post" action="Rhinelablog.php">
				<label for="email">Email:</label>
				<input type="email" id="email" name="email" required>
				<label for="password">Password:</label>
				<input type="password" id="password" name="password" required>
				<div class="form-buttons">
					<button type="submit">Log In</button>
					<button type="button" id="login-cancel">Cancel</button>
				</div>
			</form>
			<form class="register-form" id="register-form" method="post" action="Rhinelabsign.php">
				<label for="name">Name:</label>
				<input type="text" id="name" name="name" required>
				<label>Gender:</label>
				<div class="gender-radio">
					<label for="male-radio">Male</label>
					<input type="radio" id="male-radio" name="gender" value="male" required>
					<label for="female-radio">Female</label>
					<input type="radio" id="female-radio" name="gender" value="female" required>
				</div>
				<label for="password">Password:</label>
				<input type="password" id="password" name="password" required>
				<label for="confirm-password">Confirm Password:</label>
				<input type="password" id="confirm-password" name="confirm-password" required>
				<span id="confirm-error" style="color: red;"></span>
				<!-- <br>
				<script>
					var password = document.getElementById('password').value;
					var confirm_password = document.getElementById('confirm-password').value;
					if (password != confirm_password) {
						document.getElementById("confirm-error").innerHTML = "请确认你的密码！";
					}
				</script> -->



				<label for="email">Email:</label>
				<input type="email" id="email" name="email" required>
				<div class="form-buttons">
					<button type="submit">Register</button>
					<button type="button" id="register-clear">Clear</button>
					<button type="button" id="register-cancel">Cancel</button>
				</div>
			</form>
		</div>
	</div>
	<script src="scriptofresign.js"></script>


</body>

</html>