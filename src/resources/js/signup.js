const VERBOSE = true;

function intitiateWizard() {
    [...document.querySelectorAll("button[data-wizrole]")].forEach( el => {
        if( el.dataset.wizrole == 'next_page') {
            el.addEventListener('click', (e) => {
                let element = e.target;
                while( !element.parentElement.classList.contains('wizzard')) {
                    element = element.parentElement;
                }
                element.classList.add('offline');;
                element.nextElementSibling.classList.remove('offline');
            });
        }
        if( el.dataset.wizrole == 'last_page') {
            el.addEventListener('click', (e) => {
                let element = e.target;
                while( !element.parentElement.classList.contains('wizzard')) {
                    element = element.parentElement;
                }
                element.classList.add('offline');;
                element.previousElementSibling.classList.remove('offline');
            });
        }
        if( el.dataset.wizrole == 'go_login') {
            el.addEventListener('click', (e) => {
                window.location = '/auth/login';
            });
        }
        if( el.dataset.wizrole == 'submit') {
            el.addEventListener('click', sendRegister);
        }
    });
}

let user_body = {
    'id' : 0,
    'name' : '',
    'surname' : '',
    'user_name': '',
    'mail' : '',
    'details' : ''
}
let suser_body = {
    'id' : 0,
    'user_id' : 0,
    'pass' : '',
    'pass_update_at': 0,
    'recovery_mail' : '',
    'recovery_code' : ''
}
let wallet_body = {
    'id' : 0,
    'balancing' : Math.random() * (100, 1000) + 1
}
let uwallet_body = {
    'id' : 0,
    'user_id' : 0,
    'wallet_id' : 0
}
let userPerm_body = {
    'id' : 0,
    'user_id' : 0,
    'role_id' : 1,
    'expiration' : ''
}
//+wallet -> user_wallet
function get_data(){
    return entity = {
        'username' : document.getElementById("username").value,
        'nume' : document.getElementById("nume").value,
        'prenume' : document.getElementById("prenume").value,
        'email' : document.getElementById("email").value,
        'parola' : document.getElementById("parola").value,
        'parola_c' : document.getElementById("parola_c").value,
        'descriere' : document.getElementById("descriere").value
    }
}

/**
 * Read data from dom
 * @param {JSON} entity initial object
 * @returns binded object
 */
function readData(entity = {}) {
    entity.id       = 0;
    entity.username = document.getElementById("username").value;
    entity.nume     = document.getElementById("nume").value;
    entity.prenume  = document.getElementById("prenume").value;
    entity.email    = document.getElementById("email").value;
    entity.parola   = document.getElementById("parola").value;
    entity.parola_c = document.getElementById("parola_c").value;
    entity.descriere = document.getElementById("descriere").value;
    return entity;
}

document.addEventListener("DOMContentLoaded", function(event) {
    intitiateWizard();
});
