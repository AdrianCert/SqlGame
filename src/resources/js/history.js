var url_history = "http://localhost:2021/history/"

async function getHistory(user_id){
    let list = Array();

    list = await fetch(url_history, {
        method : 'GET'
    }).then(r => r.json());

    return list.filter(i => i.user_id == user_id);
}