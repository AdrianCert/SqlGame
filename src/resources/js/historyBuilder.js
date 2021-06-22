let url_history_Builder = "http://localhost:2021/history/";

function getDate(){
    var d = new Date();
    return `${d.getDate()}/${d.getMonth() + 1}/${d.getFullYear()} - ${d.getHours()}:${d.getMinutes()}`;
}

async function builderHistoryCreate(id_user, question_id){
    console.log(getDate());
    let action = `Created question ${question_id}  ${getDate()}`;
    let body = {
        'id' : 0,
        'user_id' : parseInt(id_user),
        'action' : action
    }

    let response = await fetch(url_history_Builder, {
        method : 'POST',
        body: JSON.stringify(body)
    });
    console.log(response);
}

async function builderHistoryComplete(id_user){
    let action = `Completed question  ${getDate()}`;
    let body = {
        'id' : 0,
        'user_id' : parseInt(id_user),
        'action' : action
    }

    await fetch(url_history_Builder, {
        method : 'POST',
        body: JSON.stringify(body)
    });
}

async function builderHistoryProfileChange(id_user){
    let action = `Changed profile  ${getDate()}`;
    let body = {
        'id' : 0,
        'user_id' : parseInt(id_user),
        'action' : action
    }

    await fetch(url_history_Builder, {
        method : 'POST',
        body: JSON.stringify(body)
    });
}