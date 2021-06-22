const crypto = require('crypto');
const { uuidv4, isnn } = require('./utils/algebra');
const api = require('./binder/api');

const sessions = { };

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
    if(user_sec.length === 0) {
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
    nfo.expr = new Date().setDate(new Date().getDate() + expr);

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

async function getIdByRole(roleTitle) {
    let role_list = [ ... await api.role.getAll()].filter( r => r.title === roleTitle);
    if(role_list.length > 0 && role_list[0].hasOwnProperty('id')) {
        return role_list[0].id;
    }
    return (await api.role.add({
        "title" : roleTitle,
        "description" : roleTitle
    })).id;
}

async function register(data) {
    let nfo = {};
    let expr = data.hasOwnProperty('expr') ? data.expr : 3;
    nfo.expr = new Date().setDate(new Date().getDate() + expr);

    let user = await api.user.add({
        'name' : data.prenume,
        'surname' : data.nume,
        'user_name': data.username,
        'mail' : data.email,
        'details' : data.descriere
    });

    if (!user.hasOwnProperty('id')) {
        return {
            "auth" : false,
            "reason" : "User not creted"
        };
    }
    
    let user_sec = await api.suser.add({
        'user_id' : user.id,
        'pass' : crypto.createHash("sha256").update(data.parola).digest("hex"),
        'pass_update_at': new Date(),
        'recovery_mail' : user.mail,
        'recovery_code' : Math.random().toString(10).substring(8),
    });
    
    if (!user_sec.hasOwnProperty('id')) {
        await api.user.delete(user.id);
        return {
            "auth" : false,
            "reason" : "User Sec not creted"
        };
    }

    let wallet = await api.wallet.add({
        'balancing' : 100
    });

    if(!wallet.hasOwnProperty('id')) {
        await api.user.delete(user.id);
        await api.suser.delete(user_sec.id);
        return {
            "auth" : false,
            "reason" : "Wallet not created"
        };
    }

    let user_wallet = await api.wuser.add({
        'user_id' : user.id,
        'wallet_id' : wallet.id
    });

    if(!user_wallet.hasOwnProperty('id')) {
        await api.user.delete(user.id);
        await api.suser.delete(user_sec.id);
        await api.wallet.delete(wallet.id);
        return {
            "auth" : false,
            "reason" : "User Wallet not created"
        };
    }

    let user_permision = await getIdByRole("USER")
        .then( role_id => api.puser.add({
            'user_id' : user.id,
            'role_id' : role_id,
    }));

    if(!user_permision.hasOwnProperty('id')) {
        await api.user.delete(user.id);
        await api.suser.delete(user_sec.id);
        await api.wallet.delete(wallet.id);
        await api.wuser.delete(user_wallet.id);
        return {
            "auth" : false,
            "reason" : "User Permision not created"
        };
    }

    nfo.user = user.id;

    // generate uuid
    let key = uuidv4();
    while( sessions.hasOwnProperty(key)) key = uuidv4();

    sessions[key] = nfo;

    return {
        "auth" : true,
        "key" : key
    };
}

module.exports = {auth, login, register }
