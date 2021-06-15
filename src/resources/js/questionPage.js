const url_question_by_id = "http://localhost:2021/question/{id}";
const url_API = "http://localhost/api/check";

/**
 * bun trimit un request de tipul
 * {
 *    'querry' : 'SELECT * FROM YESSIR;'
 * }
 * 
 * trece prin filtru lui adrian care intoarce un raspuns / da sau nu ? ce intoarce? cand trebuie sa descarc fisierul?
 * 
 */

async function sendRequestAPI(data){
    //trimit catre API un request cu DATA
    let raspuns = await fetch(url_API, {
        method : "POST",
        body : JSON.stringify(data)
    }).then(r => r.json);
}

const querrySender = (ev) => {
    ev.preventDefault();
    let entity = {
        'querry' : document.getElementById("solutia").value
    }
    //sendRequestAPI(entity); 
}

function getCurrentQuestionId(){
    return parseInt(window.location.pathname.replace("/question/", ""));
}

function getFragment(str){
    return document.createRange().createContextualFragment(str);
}

async function getQuestionDetails(){
    var current_id = getCurrentQuestionId();
    var url_ = url_question_by_id.replace("{id}", current_id);
    return await fetch(url_, {
        method : 'GET'
    }).then(r => r.json());
}

async function pageQuestion_replace(){
    var details = await getQuestionDetails();
    let container1 = document.getElementById("first_row");
    while(container1.firstChild){
        container1.firstChild.remove();
    }
    container1.appendChild(getFragment(
        `
        <p>${details.title}</p>
        `
    ));

    let container2 = document.getElementById("second_row");
    while(container2.firstChild){
        container2.firstChild.remove();
    }   
    container2.appendChild(getFragment(
        `
        <p>${details.description}</p>
        `
    ));


}

document.addEventListener("DOMContentLoaded", async() => {
    let current_user_id = await didIGetIt();
    writeProfileCard(current_user_id.id);
    writeClassamentCard();
    pageQuestion_replace();
    document.getElementById("raspuns").addEventListener("click", querrySender);
});