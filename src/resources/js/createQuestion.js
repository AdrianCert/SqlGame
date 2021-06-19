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
            builderHistoryCreate(parseInt(current_user_id.id));
            window.location = '/question/';
        }
    });

}

function getFragment(str){
    return document.createRange().createContextualFragment(str);
}

function valid(){
    return getFragment(
        `
        <h3 style="color:green">VALID</h3>
        `
    );
}

function eroare(){
    return getFragment(
        `
        <h3 style="color:red">EROARE</h3>
        `
    )
}

function checkResult(){
    //verifica querryul trimis de el xd
    return x = {
        'accepted' : 'true'
    }
}

function valideaza(){
    let tof = checkResult();
    let container = document.getElementById("Valideaza");
    while(container.firstChild) container.firstChild.remove();
    if(tof.accepted) container.appendChild(valid());
    else container.appendChild(eroare());
    document.getElementById("Valideaza").style.display = 'block';
    // trebuie descarcat fisierul aici cu raspunsul din querry
}

document.addEventListener("DOMContentLoaded", async() => {
    let current_user_id = await didIGetIt();
    writeProfileCard(current_user_id.id);
    writeClassamentCard();
    document.getElementById("submit").addEventListener('click', data);
    document.getElementById("check").addEventListener('click', valideaza);
});
