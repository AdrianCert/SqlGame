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

/**
 * Show on screen the error
 * @param {String} err error message
 */
function logErr(err) {
    if (VERBOSE) {
        console.warn(err);
    }
    let err_box = document.querySelector('.error');
    err_box.innerHTML = err;
    err_box.classList.add('active');
}

/**
 * Sent the data to server
 * @param {Event} e event
 * @returns 
 */
async function sendRegister(e) {
    e.preventDefault();
    let entity = readData();

    if ( entity['parola'] !== entity['parola_c']) {
        return logErr("Parolele trebuie sa coiencid")
    }

    fetch('/auth/register', {
        method : 'POST',
        body : JSON.stringify(entity)
    }).then( r => r.json()).then( r => {
        if(r.succes) {
            window.location = '/';
        } else {
            logErr(r.mess);
        }
    });
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
