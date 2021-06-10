const url = "http://localhost:2021/user/"; 
const url_2 = "http://localhost:2021/suser/"; 
const url_3 = "http://localhost:2021/wallet/";
const ulr_4 = "http://localhost:2021/wuser/";

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
    'balancing' : 100
}

let uwallet_body = {
    'id' : 0,
    'user_id' : 0,
    'wallet_id' : 0
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

function bind_user(entity){
    user_body['id'] = 0;
    user_body['name'] = entity['nume'];
    user_body['surname'] = entity['prenume'];
    user_body['user_name'] = entity['username'];
    user_body['mail'] = entity['email'];
    user_body['details'] = entity['descriere'];

}

function bind_suser(entity){
    suser_body['id'] = 0;
    suser_body['user_id'] = 0;
    suser_body['pass'] = entity['parola'];
    suser_body['pass_update_at'] = 0;
    suser_body['recovery_mail'] = 'test@yahoo.com';
    suser_body['recovery_code'] = '1234';
}

function clear(document){
    document.getElementById("username").value = '';
    document.getElementById("nume").value = '';
    document.getElementById("prenume").value = '';
    document.getElementById("email").value = '';
    document.getElementById("parola").value = '';
    document.getElementById("parola_c").value = '';
    document.getElementById("descriere").value = '';
}

function verify_pass(entity){
    if(entity['parola'] != entity['parola_c']) return 0;
    return 1;
}

async function create_suser(){
    let response = await fetch(url_2, {
        method : 'POST',
        body : JSON.stringify(suser_body)
    }).then(r => r.json())
}

async function create_uwallet(wallet_id){
    uwallet_body['wallet_id'] = parseInt(wallet_id);
    console.log(uwallet_body);
    let response = await fetch(ulr_4, {
        method : 'POST',
        body : JSON.stringify(uwallet_body)
    }).then(r => r.json())
}

async function create_wallet(){
    let response = await fetch(url_3, {
        method : 'POST',
        body : JSON.stringify(wallet_body)
    }).then(r => r.json())
    create_uwallet(response['id']);
}

async function create_uw(ID_user){
    var today = new Date();

    suser_body['user_id'] = ID_user;
    suser_body["pass_update_at"] = today;
    uwallet_body['user_id'] = ID_user;
    create_suser();
    create_wallet();
}

let verify = async (ev) => {
    ev.preventDefault();
    let entity = get_data();
    
    bind_user(entity);
    bind_suser(entity);
    console.log(suser_body); 

    if(verify_pass(entity) == 0){
        console.warn("eroare la parola"); //facem ceva sa-i trimitem un modal 
        return;
    }
    // fac un nou user -> fac nou usersec / ma folosesc de user-id dat de fetch
    let new_user = await fetch(url , {
        method : 'POST',
        body : JSON.stringify(user_body) 
    }).then(r => r.json())
    clear(document);

    create_uw(parseInt(new_user['id']));

}

document.addEventListener("DOMContentLoaded", function(event) {
    intitiateWizard();
    document.getElementById("submit").addEventListener('click', verify);
});


