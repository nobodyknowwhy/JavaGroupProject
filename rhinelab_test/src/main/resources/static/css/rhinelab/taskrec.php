<!DOCTYPE html>
<html>
<head>
    <link rel="icon" type="image/jpeg" href="./rhinelab/rahinelab_logo.png" sizes="32x32">
    <title>Task Reception</title>
</head>
<body>
	<h1>tasks</h1>
	<ul>
		<?php
			$folder = "C:/AppServ/www/html/tasks";
			$files = scandir($folder);
			foreach ($files as $file) {
				$path = $folder . $file;
				$link = "<a href=\"$path\" download>$file</a>";
				echo "<li>$link</li>";
			}
		?>
	</ul>
</body>
</html>