function login(){
    window.location.href = 'http://localhost/auth/login';
}

function signin(){
    window.location.href = 'http://localhost/auth/register';
}

document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("login").addEventListener('click', login);
    document.getElementById("signup").addEventListener('click', signin);
});