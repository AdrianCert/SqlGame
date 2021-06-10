const fetch = require('node-fetch');

const models = [
    "verification",
    "query"
]

const queryApi = bindControllers();

/**
 * USAGE const queryApi = require('./binder/queryHandler');
 * let response = queryApi.verification.post({"sendQuery":"SELECT * FROM studenti WHERE rownum<1","correctQuery":"SELECT * FROM studenti WHERE rownum<1","sgbd" : "Oracle","credentials": {"user" : "STUDENT","pass" : "QWERTY"}}).then(response => response.json()).then(result => console.log(result)).catch(error => console.error(error));
 * and will return the user with id user
 */
module.exports = queryApi;

function bindController(controler) {
    let url = `${getLocation()}/${controler}`
    if(controler == "verification")
    return {
        "post" : (sendQuery, correctQuery, sgbd, user, pass) => fetchDataV(url, sendQuery, correctQuery, sgbd, user, pass)
    }
    else return {
        "post" : (query, sgbd, user, pass) => fetchDataQ(url, query, sgbd, user, pass)
    }
}

function bindControllers() {
    let r = {};
    models.forEach(i => r[i] = bindController(i));
    return r;
}

function fetchDataQ(url, query, sgbd, user, pass) {
    dataToSend = {
        query : query,
        sgbd : sgbd,
        credentials : {
            user : user,
            pass : pass
        }
    }
    return fetch(url, {
        method : 'POST',
        headers:{
            'Content-Type': 'application/json;charset=utf-8'
        },
        body : JSON.stringify(dataToSend)
    })
}

function fetchDataV(url, sendQuery, correctQuery, sgbd, user, pass) {
    dataToSend = {
        sendQuery : sendQuery,
        correctQuery : correctQuery,
        sgbd : sgbd,
        credentials : {
            user : user,
            pass : pass
        }
    }
    return fetch(url, {
        method : 'POST',
        headers:{
            'Content-Type': 'application/json;charset=utf-8'
        },
        body : JSON.stringify(dataToSend)
    })
}

function getLocation() {
    return "http://localhost:8100";
}
