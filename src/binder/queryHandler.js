const fetch = require('node-fetch');

const models = [
    "verification",
    "query"
]

const api = bindControllers();

/**
 * USAGE const api = require('./binder/queryHandler');
 * let response = api.verification.post({"sendQuery":"SELECT * FROM studenti WHERE rownum<1","correctQuery":"SELECT * FROM studenti WHERE rownum<1","sgbd" : "Oracle","credentials": {"user" : "STUDENT","pass" : "QWERTY"}}).then(response => response.json()).then(result => console.log(result)).catch(error => console.error(error));
 * and will return the user with id user
 */
module.exports = api;

function bindController(controler) {
    let url = `${getLocation()}/${controler}`
    return {
        "post" : (data) => fetch_add(url, data)
    }
}

function bindControllers() {
    let r = {};
    models.forEach(i => r[i] = bindController(i));
    return r;
}

function fetch_add(url, data) {
    return fetch(url, {
        method : 'POST',
        headers:{
            'Content-Type': 'application/json;charset=utf-8'
        },
        body : JSON.stringify(data)
    })
}

function getLocation() {
    return "http://localhost:8100";
}
