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
        "add" : (data) => feth_add(url, data),
        "getAll" : () => feth_all(url),
        "get" : (id) => feth_get(`${url}${id}`),
        "update" : (id, data) => feth_update(`${url}${id}`, data),
        "delete" : (id) => feth_delete(`${url}${id}`)
    }
}

function bindControllers() {
    let r = {};
    models.forEach(i => r[i] = bindController(i));
    return r;
}

function add(url, data) {
    return fetch(url, {
        method : 'POST',
        headers:{
            'Content-Type': 'application/json;charset=utf-8'
        },
        body : JSON.stringify(data)
    })
}

function feth_all(url) {
    console.log(url);
    return fetch(url);
}

function feth_get(url) {
    return fetch(url);
}

function feth_update(url, data) {
    return fetch(url, {
        method : 'PUT',
        headers:{
            'Content-Type': 'application/json;charset=utf-8'
        },
        body : JSON.stringify(data)
    })
}

function feth_delete(url) {
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
