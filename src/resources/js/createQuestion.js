let url = "http://localhost:2021/question/";
let send_body = {
    'id': 0,
    'title' : "",
    'description' : "",
    'solution' : "",
    'value' : 20,
    'reward' : 0
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
    send_body["title"] = entity["title"];
    send_body["description"] = entity["enunt"];
    send_body["solution"] = entity["solutia"];
    send_body["value"] = entity["costa"];
    send_body["reward"] = entity["costa"] * 2;
    console.warn('added', {send_body});
    //trimit request ul
    let response = fetch(url,{
        method : 'POST',
        mode:'no-cors',
        headers:{
            'Content-Type': 'application/json;charset=utf-8'
        },
        body : JSON.stringify(send_body)
    }).then(r => {
        document.getElementById("solutia").value = '';
        document.getElementById("enunt").value = ''
    });
    let result = response.catch();
    console.warn(result);
}

document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("submit").addEventListener('click', data);
});
