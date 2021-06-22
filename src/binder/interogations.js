const config = require('./../config.json');
const queryApi = require('./queryHandler');
const api = require('./api');

const queries = {
    "classment" : `select u.id as id, u.name || ' ' || u.surname as name , u.mail as email, w.balancing as coins
                from usertable u
                join userwallet uw on u.id = uw.user_id
                join wallet w on uw.wallet_id = w.id
                order by w.balancing desc
                `.split(/\s+/).join(' '),
    "coins" : `select w.balancing as coins
                from usertable u
                join userwallet uw on u.id = uw.user_id
                join wallet w on uw.user_id = w.id
                where u.id = {{id}}
                `.split(/\s+/).join(' '),
    "history" : `select * from history where user_id = {{id}}`,
    "banks" : `select w.id, w.BALANCING from wallet w
                left join userwallet u on w.id = u.wallet_id
                where u.user_id is null
                `.split(/\s+/).join(' '),
    "userWallet" : `select w.id, w.BALANCING from wallet w
                left join userwallet u on w.id = u.wallet_id
                where u.user_id = {{id}}
                `.split(/\s+/).join(' '),
    "questionOwn" : `select * from questionsowned
                where user_id = {{id}} and question_id = {{qid}}
                `.split(/\s+/).join(' ')
}

function execQuery(q) {
    let nfo = getInternBdCreddidentials();
    return queryApi.query(q, nfo.sgbd, nfo.user, nfo.pass)
}

function getInternBdCreddidentials() {
    return config.bd.intern;
}

async function whoIAm(req, res , intern = false) {
    let data = req.headers.hasOwnProperty('auth') && req.headers.auth.is_authenticated ?
        await api.user.get(req.headers.auth.info.user).then( async user => {
            user['balance'] = await execQuery(queries.coins.replace("{{id}}", user.id))
                                        .then( r => r.error ? 0 : r.entity[0].COINS)
                                        .catch(() => 0);
            return user;
        }) : {};
    return intern ? data: JsonRespone(res, data);
}

module.exports = {
    execQuery,
    queries,
    whoIAm
}