let url = "http://localhost:2021/question/";

/**
 * De facut - butonul de descarcare a fisierului 
 * functia valideaza nu face nimic
 */

let send_body = {
    'id': 0,
    'title' : "",
    'description' : "",
    'solution' : "",
    'value' : 20,
    'reward' : 0
}

function build_body(entity){
    send_body["title"] = entity["title"];
    send_body["description"] = entity["enunt"];
    send_body["solution"] = entity["solutia"];
    send_body["value"] = parseInt(entity["costa"]);
    send_body["reward"] = entity["costa"] * 2;
}

const data = async (ev) => {
    ev.preventDefault();
    let current_user_id = await didIGetIt();

    let entity = {
        'title' : document.getElementById("titlu").value,
        'enunt': document.getElementById("enunt").value,
        'solutia' : document.getElementById("solutia").value,
        'costa' : document.getElementById("costa").value
    }
    build_body(entity);
    await fetch(url,{
        method : 'POST',
        body : JSON.stringify(send_body),
    }).then(r => r.json()).then(r => {
        console.log(r);
        document.getElementById("titlu").value = '';
        document.getElementById("solutia").value = '';
        document.getElementById("enunt").value = ''
        if(r.status != 404){
            builderHistoryCreate(parseInt(current_user_id.id), parseInt(r.id));
            window.location = '/question/';
        }
    });

}

function getFragment(str){
    return document.createRange().createContextualFragment(str);
}

function valideaza(){
    fetch(`/api/querry/getcsv/`, {
        method : "POST",
        body: JSON.stringify({
            "querry" : document.getElementById("solutia").value,
            "lid" : 21
        })
    }).then( fetchDownload);
}

document.addEventListener("DOMContentLoaded", async() => {
    let current_user_id = await didIGetIt();
    writeProfileCard(current_user_id.id);
    writeClassamentCard();
    document.getElementById("submit").addEventListener('click', data);
    document.getElementById("check").addEventListener('click', valideaza);
});
