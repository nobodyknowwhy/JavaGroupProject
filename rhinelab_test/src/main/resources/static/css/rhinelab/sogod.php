<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>RHINELAB application</title>
	<link rel="stylesheet" href="temp.css">

</head>

<body>
	<div class="container">
		<div class="form-container">
			<form id="my-form" method="post" action="Rhinelabjoin.php">
				<div class="form-group">
					<label for="name">name:</label>
					<input type="text" class="form-control" id="name" name="name" placeholder="Please enter your name" required>
				</div>
				<div class="form-group">
					<label for="degree">degree:</label>
					<select class="form-control" id="education" name="degree" required>
						<option value="">Please choose your degree</option>
						<option value="academician">academician</option>
						<option value="associate">associate</option>
						<option value="bachelor">bachelor</option>
						<option value="master">master</option>
						<option value="doctoral">doctoral</option>
						<option value="others">others</option>
					</select>
				</div>
				<div class="form-group" id="other-education" style="display:none;">
					<label for="other-education-text">Please enter your degree</label>
					<input type="text" class="form-control" id="other-education-text" placeholder="Please enter your degree" name="others">
				</div>
				<div class="form-group">
					<label for="bio">Biography:</label>
					<textarea class="form-control" id="bio" rows="2" placeholder="Please enter your bio (no more than 300 words)" name="bio" required></textarea>
				</div>
				<div class="form-group">
					<label for="section">Applying Section:</label>
					<select class="form-control" id="section" name="section" required>
						<option value="">Please choose your applying section</option>
						<option value="COMPONENTS CONTROL">COMPONENTS CONTROL</option>
						<option value="DEFENSE">DEFENSE</option>
						<option value="BUSINESS">BUSINESS</option>
						<option value="HUMAN RESOURCES INVESTIGATION">HUMAN RESOURCES INVESTIGATION</option>
						<option value="ENGINEERING">ENGINEERING</option>
						<option value="STRUCTURAL">STRUCTURAL</option>
						<option value="ENERGY">ENERGY</option>
						<option value="ORIGINIUM ART">ORIGINIUM ART</option>
						<option value="ECOLOGICAL">ECOLOGICAL</option>
						<option value="SCIENTIFIC INVESTIGATION">SCIENTIFIC INVESTIGATION</option>
					</select>
				</div>
				<div class="form-group">
					<label for="reason">Applying reasons:</label>
					<textarea class="form-control" id="reason" rows="2" placeholder="Please enter your reason for your application (no more than 300 words)" name="reason" required></textarea>
				</div>
				<div class="form-group">
					<label for="achievement">Achievement:</label>
					<textarea class="form-control" id="achievement" rows="2" placeholder="Please enter your self-achievement (no more than 300 words)" name="achievement" required></textarea>
				</div>
				<div class="form-group">
					<label for="phone">phone:</label>
					<input type="tel" id="phone" name="phone" pattern="[0-9]{11}" class="form-control" required>
					<small>please enter your 11-digit phone number, example:13812345678</small>
				</div>
				<div class="form-group">
					<input type="file" id="file" name="file-upload" required>
					<p class="temp_p">
					<small>please upload your bio file.</small>
					</p>
					
				</div>

				<button type="submit" class="btn btn-primary">Submit</button>
				<button type="button" class="btn btn-secondary" id="clear-button">Clear</button>
				<button type="button" class="btn btn-secondary" id="cancel-button">Cancel</button>
			</form>
		</div>
	</div>
	<script>
		var educationSelect = document.getElementById('education');
		var otherEducation = document.getElementById('other-education');
		var clearButton = document.getElementById('clear-button');
		var cancelButton = document.getElementById('cancel-button');

		educationSelect.addEventListener('change', function() {
			if (this.value === 'others') {
				otherEducation.style.display = 'block';
			} else {
				otherEducation.style.display = 'none';
			}
		});

		clearButton.addEventListener('click', function() {
			document.getElementById('my-form').reset();
		});

		cancelButton.addEventListener('click', function() {
			window.location.href = './RHINE LAB.html';
		});
	</script>
</body>

</html>