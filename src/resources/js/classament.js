let url_user = "http://localhost:2021/user/";
let url_wuser = "http://localhost:2021/wuser/";
let url_wallet = "http://localhost:2021/wallet/";

function getUsers(){
    return fetch(url_user, {
        method : 'GET'
    }).then(r => r.json());
}

function getUWallet(){
    return fetch(url_wuser, {
        method : 'GET'
    }).then(r => r.json());
}

function getWallet(){
    return fetch(url_wallet, {
        method: 'GET'
    }).then(r => r.json());
}

function makeClassament(users, user_wallet, wallet){
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

function sortClassament(classament){
    classament.sort((a, b) => parseInt(b.coins) - parseInt(a.coins));
}

async function getClassament(){
    let user = await getUsers();
    let uwallet =  await getUWallet();
    let wallet = await getWallet();
    let classament = makeClassament(user, uwallet, wallet);
    sortClassament(classament);
    let i = 0;
    classament.forEach(e => {
        e.poz_clasament = ++i;
    })
    return classament;
}