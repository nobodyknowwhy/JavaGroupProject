<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="./prosub.css">
    <link rel="icon" type="image/jpeg" href="./rhinelab/rahinelab_logo.png" sizes="32x32">
    <title>Project Submission</title>
</head>

<body>
    <h1>Project Submission</h1>
    <form action="./prosubafter.php" method="post" enctype="multipart/form-data">
        <label for="team-leader">Team Leader:</label>
        <input type="text" id="team-leader" name="team-leader" placeholder="Please enter your leader's name" required>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
        <label for="project-type">Project Type:</label>
        <select id="project-type" name="project-type" required>
            <option value="">Please choose your project type</option>
            <option value="natural-sciences">Natural Sciences</option>
            <option value="philosophical-society">Philosophical Society</option>
            <option value="social-survey-report">Social Survey Report</option>
            <option value="academic-paper">Academic Paper</option>
            <option value="scientific-technological-invention">Scientific Technological Invention</option>
        </select>
        <label for="project-name">Project Name:</label>
        <input type="text" id="project-name" name="project-name" required>
        <label for="project-brief">Project Brief:</label>
        <textarea id="project-brief" name="project-brief" maxlength="200" required></textarea>
        <label for="project-significance">Project Significance:</label>
        <textarea id="project-significance" name="project-significance" placeholder="Please enter the meaning of your project" required></textarea>
        <label for="file-upload">Files Upload:</label>
        <input type="file" id="file-upload" name="file-upload" required>
        <input type="submit" value="Submit">
    </form>
</body>

</html>