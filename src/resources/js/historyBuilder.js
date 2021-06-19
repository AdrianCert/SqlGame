let url_history = "http://localhost:2021/history/";

function remove(date){
    let cdate = date.toString();
    return cdate.replace("GMT+0300 (Eastern European Summer Time)", "");
}

async function builderHistoryCreate(id_user){
    var date = Date();
    let action = `Created question - ${remove(date)}`;
    let body = {
        'id' : 0,
        'user_id' : parseInt(id_user),
        'action' : action
    }

    await fetch(url_history, {
        method : 'POST',
        body: JSON.stringify(body)
    });

}

async function builderHistoryComplete(id_user){
    let date = Date();
    let action = `Completed question - ${remove(date)}`;
    let body = {
        'id' : 0,
        'user_id' : parseInt(id_user),
        'action' : action
    }

    await fetch(url_history, {
        method : 'POST',
        body: JSON.stringify(body)
    });
}

async function builderHistoryProfileChange(id_user){
    let date = Date();
    let action = `Changed profile - ${remove(date)}`;
    let body = {
        'id' : 0,
        'user_id' : parseInt(id_user),
        'action' : action
    }

    await fetch(url_history, {
        method : 'POST',
        body: JSON.stringify(body)
    });
}