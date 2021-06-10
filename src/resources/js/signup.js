const url = "http://localhost:2021/user/"; 
const url_2 = "http://localhost:2021/suser/"; 

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
        if( el.dataset.wizrole == 'submit') {

        }
    });
}

// post imi returneaza un json cu body ul/entitatea introdusa in BD

let body_1 = {
    'id' : 0,
    'name' : '',
    'surname' : '',
    'user_name': '',
    'mail' : '',
    'details' : ''
}
let body_2 = {
    'id' : 0,
    'user_id' : 0,
    'pass' : '',
    'pass_updated_at': 0,
    'recovery_mail' : '',
    'recovery_code' : ''
}

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

function set_body1(entity){
    body_1['id'] = 0;
    body_1['name'] = entity['nume'];
    body_1['surname'] = entity['prenume'];
    body_1['user_name'] = entity['username'];
    body_1['mail'] = entity['email'];
    body_1['details'] = entity['descriere'];

}

function set_body2(entity){
    body_2['id'] = 0;
    body_2['user_id'] = 0; // cu id-ul de-l returneaza fetch ul de pe url
    body_2['pass'] = entity['parola'];
    body_2['pass_updated_at'] = 0;
    body_2['recovery_mail'] = '';
    body_2['recovery_code'] = '';
}

let verify = (ev) => {
    ev.preventDefault();
    let entity = get_data();
    set_body1(entity);
    set_body2(entity);

    if(entity['parola'] != entity['parola_c']){
        console.warn("eroare la parola"); //facem ceva sa-i trimitem un modal 
        stop();
    }

    let new_user = fetch(url , {
        method : 'POST',
        mode : 'no-cors',
        headers : {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body : JSON.stringify(body_1) 
    }).then(r => {
        document.getElementById("username").value = '';
        document.getElementById("nume").value = '';
        document.getElementById("prenume").value = '';
        document.getElementById("email").value = '';
        document.getElementById("parola").value = '';
        document.getElementById("parola_c").value = '';
        document.getElementById("descriere").value = '';
    })
}

document.addEventListener("DOMContentLoaded", function(event) {
    intitiateWizard();
    document.getElementById("submit").addEventListener('click', verify);
});


