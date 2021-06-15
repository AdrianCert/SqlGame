var users = {};
var user_wallet = {};
var wallet = {};
var classament = new Array();

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
    let classament = [];
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
    return classament;
}

function sortClassament(){
    classament.sort((a, b) => parseInt(a.coins) - parseInt(b.coins));
}

async function getClassament(){
    await getUsers();
    await getUWallet();
    await getWallet();
    classament = makeClassament();
    sortClassament();
    return classament;
}