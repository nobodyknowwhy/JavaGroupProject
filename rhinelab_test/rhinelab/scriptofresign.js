const loginForm = document.getElementById('login-form');
const registerForm = document.getElementById('register-form');
const loginRadio = document.getElementById('login-radio');
const registerRadio = document.getElementById('register-radio');
const loginCancel = document.getElementById('login-cancel');
const registerClear = document.getElementById('register-clear');
const registerCancel = document.getElementById('register-cancel');


registerCancel.addEventListener('click', () => {
    window.location.href = './RHINE LAB.html';
});

loginRadio.addEventListener('change', () => {
    loginForm.style.display = 'block';
    registerForm.style.display = 'none';
});

registerRadio.addEventListener('change', () => {
    loginForm.style.display = 'none';
    registerForm.style.display = 'block';
});

loginCancel.addEventListener('click', () => {
    window.location.href = './RHINE LAB.html';
});

registerClear.addEventListener('click', () => {
    registerForm.reset();
});