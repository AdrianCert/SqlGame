
const url_update_user = "/api/user/{id}";

function loadDetails(user){
    document.getElementById("name").value = user.name;
    document.getElementById("surname").value  = user.surname;
    document.getElementById("username").value  = user.user_name;
    document.getElementById("email").value  = user.mail;
}

function loadSpecificDetails(ID, classament){
    let line = classament.filter(i => i.id == ID);
    document.getElementById("sqlid").value = line[0].coins;
    document.getElementById("rank").value = line[0].poz_clasament;
}

function loadProfile(user, classament){
    loadDetails(user);
    loadSpecificDetails(parseInt(user.id), classament);
}

let updateUser = async(ev) =>{
    let curret_user = await didIGetIt();
    curret_user.name = document.getElementById("name").value;
    curret_user.surname = document.getElementById("surname").value;
    curret_user.user_name = document.getElementById("username").value;
    curret_user.mail = document.getElementById("email").value;
    let x = await tryUpdate(curret_user, parseInt(curret_user.id));
    console.log(x);
}

async function tryUpdate(data, ID){
    let url_ = url_update_user.replace("{id}", ID);
    return await fetch(url_, {
        method : 'PUT',
        body : JSON.stringify(data)
    }).then(r => r.json());
}

function loadIstoric(istoric){
    //dau load
}


document.addEventListener("DOMContentLoaded", async() => {
    var current_user = await didIGetIt();
    var classament = await getClassament();
    var istoric = await getHistory(parseInt(current_user.id));
    console.log(istoric);
    loadProfile(current_user, classament);
    loadIstoric(istoric);
    document.getElementById("edit_button").addEventListener("click", updateUser);
});