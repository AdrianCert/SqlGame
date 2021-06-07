const { uuidv4, isnn } = require('./utils/algebra');

const sessions = {
    "UUID" : "325t5"
}

function auth(dic) {
    return {
        "is_authenticated" : is_authenticated(dic)
    }
}

function is_authenticated(dic) {
    return isnn( dic)
        && isnn( dic.sid)
        && isnn( sessions[dic.sid])
        && validsession(sessions[dic.sid]);
}

function validsession(sid) {
    return true;
}

function login() {
    if(req.query.username == someUsername && req.query.password == somePassword){
        res.writeHead(200, {
            'Set-Cookie': 'sid=UUID', //will need a uuidgen here
            'Content-Type': 'text/plain'
        })
        sessions[UUID] = true //store session on server
        return res.send(200,somePage)
    }
}

module.exports = auth;