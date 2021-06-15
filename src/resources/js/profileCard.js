let url_user = "http://localhost:2021/user/";
let url_wuser = "http://localhost:2021/wuser/";
let url_wallet = "http://localhost:2021/wallet/";

let username = null;
let user_coins = null;
let poz_clasament = null;

var classament = [];

function setUsername(data){
    username = data;
    
}

function setUserCoins(data){
    user_coins = data;
}

function setPozClasament(data){
    poz_clasament = data;
}

async function load(ID){
    classament = await getClassament();
    console.log(classament);
    getInfo(parseInt(ID));
    replace();
}

function getInfo(ID){
    for(x in classament){
        if(classament[x].id == ID){
            setUsername(classament[x].nickname);
            setUserCoins(classament[x].coins);
            setPozClasament(x);
        }
    }
}

function replace(){
    let replace1 = document.getElementById("text1");
    let replace2 = document.getElementById("text2");
    let replace3 = document.getElementById("text3");

    let text_1 = document.createTextNode(username);
    let text_2 = document.createTextNode(user_coins);
    let text_3 = document.createTextNode(poz_clasament);

    replace1.replaceWith(text_1);
    replace2.replaceWith(text_2);
    replace3.replaceWith(text_3);
}

document.addEventListener("DOMContentLoaded", () => {load(30); });