const fetch = require('node-fetch');

const models = [
    "board",
    "bmembership",
    "bpublish",
    "history",
    "instance",
    "payment",
    "post",
    "aquestion",
    "question",
    "squestion",
    "role",
    "schemaL",
    "schemaT",
    "puser",
    "suser",
    "user",
    "wuser",
    "wallet"
]

const api = bindControllers();

/**
 * USAGE const api = require('./api');
 * let user = api.user.get(5);
 * and will return the user with id user
 */
module.exports = api;

function bindController(controler) {
    let url = `${getLocation()}/${controler}/`
    return {
        "add" : (data) => fetch_add(url, data),
        "getAll" : () => fetch_all(url),
        "get" : (id) => fetch_get(`${url}${id}`),
        "update" : (id, data) => fetch_update(`${url}${id}`, data),
        "delete" : (id) => fetch_delete(`${url}${id}`)
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

function fetch_all(url) {
    console.log(url);
    return fetch(url);
}

function fetch_get(url) {
    return fetch(url);
}

function fetch_update(url, data) {
    return fetch(url, {
        method : 'PUT',
        headers:{
            'Content-Type': 'application/json;charset=utf-8'
        },
        body : JSON.stringify(data)
    })
}

function fetch_delete(url) {
    return fetch(url, {
        method : 'DELETE',
        headers:{
            'Content-Type': 'application/json;charset=utf-8'
        },
        body : JSON.stringify(data)
    })
}

function getLocation() {
    return "http://localhost:2021";
}
