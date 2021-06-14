let url_user = "http://localhost:2021/user/";
let url_wuser = "http://localhost:2021/wuser/";
let url_wallet = "http://localhost:2021/wallet/";

let question = null;
let username = null;
let user_coins = null;
let poz_clasament = null;

var users = {};
var user_wallet = {};
var wallet = {};
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
    await getUsers();
    await getUWallet();
    await getWallet();
    makeClassament();
    sortClassament();
    getInfo(parseInt(ID));
    repalce();
}

async function getUsers(){
    await fetch(url_user, {
        method : 'GET'
    }).then(r => r.json()).then(r => users = r);
}

async function getUWallet(){
    await fetch(url_wuser, {
        method : 'GET'
    }).then(r => r.json()).then(r => user_wallet = r);
}

async function getWallet(){
    await fetch(url_wallet, {
        method: 'GET'
    }).then(r => r.json()).then(r => wallet = r);
}

function makeClassament(){
    var id_currentUser = 0;
    var user_name = null;
    var currency = 0;
    var wallet_id = 0;
    for(i in users){
        id_currentUser = parseInt(users[i]['id']);
        user_name = users[i]['user_name'];
        currency = 0;
        for(j in user_wallet){
            if(parseInt(user_wallet[j]['user_id']) == id_currentUser){
                wallet_id = user_wallet[j]['wallet_id'];
                for(x in wallet){
                    if(wallet[x]['id'] == wallet_id) currency = wallet[x]['balancing'];
                }
                classament.push({'id' : id_currentUser, 'nickname' : user_name, 'coins' : currency});
            }
        }
    }
}

function sortClassament(){
    classament.sort((a, b) => parseInt(a.coins) - parseInt(b.coins));
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

function repalce(){
    // let doc = document.createElement("body_profil");
    // let row1 = document.createElement("row");
    // let info1 = document.createElement("informatie");
    // let text1_1 = document.createTextNode("* Nume utilizator:");
    // let date1 = document.createElement("date");
    // let text1_2 = document.createTextNode(username);
    // doc.appendChild(row1);
    // row1.appendChild(info1);
    // row1.appendChild(date1);
    // info1.appendChild(text1_1);
    // date1.appendChild(text1_2);
    
    // let row2 = document.createElement("row");
    // let info2 = document.createElement("informatie");
    // let text2_1 = document.createTextNode("* SQLCoins:");
    // let date2 = document.createElement("date");
    // let text2_2 = document.createTextNode(user_coins);
    // doc.appendChild(row2);
    // row2.appendChild(info2);
    // row2.appendChild(date2);
    // info2.appendChild(text2_1);
    // date2.appendChild(text2_2);
    
    // let row3 = document.createElement("row");
    // let info3 = document.createElement("informatie");
    // let text3_1 = document.createTextNode("*Poz. clasament:");
    // let date3 = document.createElement("date");
    // let text3_2 = document.createTextNode(poz_clasament);
    // doc.appendChild(row3);
    // row3.appendChild(info3);
    // row3.appendChild(date3);
    // info3.appendChild(text3_1);
    // date3.appendChild(text3_2);
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

document.addEventListener("DOMContentLoaded", () => {load(33); });