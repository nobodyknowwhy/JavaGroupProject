window.onload = function () {

  var birthdayInput = document.getElementById('birthday');
  var genderSelect = document.getElementById('gender');
  var mottoInput = document.getElementById('motto');
  var introTextarea = document.getElementById('intro');
  var saveBtn = document.getElementById('saveBtn');
  var personalName = document.getElementById('personalName')
  personalName.innerHTML = localStorage.getItem('Name');

  saveBtn.onclick = function () {

    var birthday = birthdayInput.value;
    var gender = genderSelect.value;
    var motto = mottoInput.value;
    var intro = introTextarea.value;

    localStorage.setItem('birthday', birthday);
    localStorage.setItem('gender', gender);
    localStorage.setItem('motto', motto);
    localStorage.setItem('intro', intro);


    alert('successfully save!');
  };


  birthdayInput.value = localStorage.getItem('birthday') || '';
  genderSelect.value = localStorage.getItem('gender') || 'male';
  mottoInput.value = localStorage.getItem('motto') || '';
  introTextarea.value = localStorage.getItem('intro') || '';
};