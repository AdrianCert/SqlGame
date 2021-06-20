const fetch = require('node-fetch');

const queryApi = {
    "verificate" : (sendQuery, correctQuery, sgbd, user, pass) => {
        return fetchData(`${getLocation()}/verification`, {
            "sendQuery" : sendQuery,
            "correctQuery" : correctQuery,
            "sgbd" : sgbd,
            "credentials" : {
                "user" : user,
                "pass" : pass
            }
        });
    },

    "query" : (query, sgbd, user, pass) => {
        return fetchData(`${getLocation()}/query`, {
            "query" : query,
            "sgbd" : sgbd,
            "credentials" : {
                "user" : user,
                "pass" : pass
            }
        });
    }
}

module.exports = queryApi;

function fetchData(url, data) {
    return fetch(url, {
        method : 'POST',
        body : JSON.stringify(data)
    }).then(r => r.json());
}

function getLocation() {
    return "http://localhost:8100";
}
