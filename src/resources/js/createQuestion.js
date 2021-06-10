let url = "http://localhost:2021/question/";
let send_body = {
    'id': 0,
    'title' : "",
    'description' : "",
    'solution' : "",
    'value' : 20,
    'reward' : 40
}

const data = (ev) => {
    ev.preventDefault();
    let entity = {
        'text' : document.getElementById("enunt").value,
        'valid': document.getElementById("solutia").value
    }
    console.warn('added', {entity});
    //construiesc send_body
    send_body["title"] = entity["text"];
    send_body["solution"] = entity["valid"];  
    console.warn('added', {send_body});
    //trimit request ul
    let response = fetch(url,{
        method : 'POST',
        mode:'no-cors',
        headers:{
            'Content-Type': 'application/json;charset=utf-8'
        },
        body : JSON.stringify(send_body)
    })
    let result = response.catch();
    console.warn(result);
}

document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("submit").addEventListener('click', data);
});
