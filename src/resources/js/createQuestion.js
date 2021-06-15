let url = "http://localhost:2021/question/";
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

const data = (ev) => {
    ev.preventDefault();
    let entity = {
        'title' : document.getElementById("titlu").value,
        'enunt': document.getElementById("enunt").value,
        'solutia' : document.getElementById("solutia").value,
        'costa' : document.getElementById("costa").value
    }
    console.warn('added', {entity});
    //construiesc send_body
    build_body(entity);
    console.warn('added', {send_body});
    //trimit request ul
    let response = fetch(url,{
        method : 'POST',
        body : JSON.stringify(send_body),

    }).then(r => r.json()).then(r => {
        console.log(r);
        document.getElementById("solutia").value = '';
        document.getElementById("enunt").value = ''
        window.location = '/question/';
    });
}

document.addEventListener("DOMContentLoaded", () => {
    console.log("DOM loaded on createQuestion");
    writeProfileCard(30);
    writeClassamentCard();
    document.getElementById("submit").addEventListener('click', data);
});
