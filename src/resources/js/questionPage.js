/**
 * imi ia solutia trimisa de user -> o verifica 
 */

const url = "http://localhost:2021/querry/{querry}";
const url_2 = "http://localhost:2021/question/{id}";
let verify = (ev) => {
    ev.preventDefault();
    let entity = {
        'solutie' : document.getElementById("solutia").value
    }
    console.warn('added', {entity});
    let url_ = url.replace("{querry}", entity['solutie']);
    console.log(url_);
    let response = fetch(url_, {
        method : 'GET',
        mode : 'no-cors'
    }).then(r => {
        document.getElementById('solutia').value = '';
    });
    let result = response.catch();
    console.log(result.value);
}

document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("submit").addEventListener('click', verify);
});

/**
 * Functie de preluare a informatiilor dupa un anumit ID
 */

async function get_info(ID){
    let url_ = url_2.replace("{id}", id);
    const response = await fetch(url_, {
        method : 'GET',
        mode : 'cors',
        headers:{
            'Content-Type' : 'application/json'
        }
    });
    return response.json();
}