const crypto = require('crypto');
const { uuidv4, isnn } = require('./utils/algebra');
const api = require('./binder/api');

const sessions = { }

function auth(dic) {
    return authenticator(dic);
}

function authenticator(dic) {
    let nfo = {
        "is_authenticated" : is_authenticated(dic)
    };

    if(nfo['is_authenticated']) {
        nfo["info"] = sessions[dic.sid];
    }

    return nfo;
}

function is_authenticated(dic) {
    return isnn( dic)
        && dic.hasOwnProperty('sid')
        && sessions.hasOwnProperty(dic.sid)
        && isnn( sessions[dic.sid])
        && validsession(dic.sid);
}

function validsession(data) {
    if( data.hasOwnProperty('expr')) {
        if (data.expr < new Date()) {
            delete sessions[data]
            return false;
        }
    }

    return true;
}

async function verify_credentials(data) {
    let user = await api.user.getAll().then( r => r.filter(u => u.mail === data.user || u.user_name === data.user));
    if(user.length === 0) {
        return {
            "stat" : 1,
            "mess" : "no such user"
        };
    }
    user = user[0];
    let user_sec = await api.suser.getAll().then( r => r.filter(u => u.user_id === user.id));
    if(user_sec === 0) {
        return {
            "stat" : 2,
            "mess" : "no user sec associated"
        };
    }
    user_sec = user_sec[0];
    let curr_pass = crypto.createHash("sha256").update(data.pass).digest("hex");
    if( curr_pass !== user_sec.pass) {
        return {
            "stat" : 3,
            "mess" : "pass match fail"
        };
    }
    return {
        "stat" : 0,
        "user" : user.id,
        "mess" : "succes"
    };
}

async function login(data) {
    // check credidentail
    let nfo = {};
    let expr = data.hasOwnProperty('expr') ? data.expr : 3;
    nfo.expr = new Date().setDate(new Date() + expr);

    return verify_credentials(data).then( c => {
        if( c.stat !== 0) {
            return {
                "auth" : false,
                "reason" : c.mess
            }
        }

        nfo.user = c.user;

        // generate uuid
        let key = uuidv4();
        while( sessions.hasOwnProperty(key)) key = uuidv4();

        sessions[key] = nfo;

        return {
            "auth" : true,
            "key" : key
        };
    });
}

// login({
//     "pass" : '123',
//     "user" : 'dragosssw101'
// }).then(console.log);

module.exports = {auth, login };