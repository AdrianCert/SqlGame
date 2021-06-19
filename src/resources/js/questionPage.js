const url_question_by_id = "http://localhost:2021/question/{id}";
const url_API = `/api/querry/check/${getCurrentQuestionId()}`;

const descarcaFisier = async(ev) =>{
}

const querrySender = async(ev) => {
    ev.preventDefault();
    let tof = await fetch(url_API, {
        method : "POST",
        body : document.getElementById("solutia").value
    }).then(r => r.json());
    console.log(tof);
    sendMessageToUser(tof);
}

function valid(){
    return getFragment(
        `
        <p style="color:green">VALID</p>
        `
    );
}

function eroare(){
    return getFragment(
        `
        <p style="color:red">EROARE</p>
        `
    )
}

function sendMessageToUser(tof){
    let container = document.getElementById("validare");
    while(container.firstChild) container.firstChild.remove();
    if(tof.accepted) container.appendChild(valid());
    else container.appendChild(eroare());
    document.getElementById("validare").style.display = 'block';
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
    document.getElementById("descarcaFisier").addEventListener("click", descarcaFisier());
});