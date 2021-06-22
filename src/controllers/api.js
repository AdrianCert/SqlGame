const Router = require('./../routing');
const api = require('./../binder/api');
const queryApi = require('./../binder/queryHandler');
const {createPdfBinary, generatePdfReportClasament, generatePdfReportHistory} = require('./../utils/pdf-make');
const { execQuery, queries } = require('./../binder/interogations');
const { reportDate } = require('./../utils/formats');

const locations = Object.keys(api);


function homeView(req, res) {
    JsonRespone(res, );
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

async function getBanksWallets() {
    return execQuery(queries.banks);
}

async function getQuestionCredidentials(id) {
    let nfo = {
        "sgbd" : "ORACLE",
        "user" : "STUDENT",
        "pass" : "STUDENT"
    };

    if(id === 13456433) {
        console.log("pentru gestiunea mai multor scheme bazelor de date");
    }

    return nfo;
}

function getInternBdCreddidentials() {
    return {
        "sgbd" : "ORACLE",
        "user" : "TW",
        "pass" : "TW"
    };
}

async function checkQuestionAnswer(req, res) {
    if( req.method !== 'POST') {
        res.writeHead(405);
        res.end();
        return;
    }

    let body = [];
    req.on('error', (err) => {
        console.error(err);
    }).on('data', (chunk) => {
        body.push(chunk);
    }).on('end', async () => {
        body = Buffer.concat(body).toString();
        let qid = /\/api\/querry\/check\/(\d+)/gm.exec(req.url)[1];
        let question = await api.question.get(qid);
        let nfo = await getQuestionCredidentials(qid);
        let user = await whoIAm(req, res, true);
        let anwser = await queryApi.verificate(body, question.solution, nfo.sgbd, nfo.user, nfo.pass);
        if (anwser.accepted) {
            let w_bank = await execQuery(queries.banks).then( r => r.error ? {} : r.entity[0]).catch(() => {return {}});
            let w_user = await execQuery(queries.userWallet.replace("{{id}}", user.id)).then( r => r.error ? {} : r.entity[0]).catch(() => {return {}});
            let q_own = await execQuery(queries.questionOwn.replace("{{id}}", user.id).replace("{{qid}}", question.id))
                            .then( r => r.error ? {} : r.entity[0])
                            .catch(() => {return {}});
            if(q_own.hasOwnProperty('SOLVED') && q_own.SOLVED === "true") return JsonRespone(res, anwser);
            let payment = await makePayment(w_bank, w_user, question.value, `User ${user.user_name} user#${user.id} completed question#${question.id}`);
            if (payment.hasOwnProperty('id')) {
                return api.owquestions.update(q_own.ID, {
                    'user_id': q_own.USER_ID,
                    'question_id': q_own.QUESTION_ID,
                    'solution': body,
                    'solved': "true",
                    'payment_buy': q_own.PAYMENT_BUY,
                    'payment_rew': payment.id,
                    'id': q_own.ID
                }).then( r => {
                    api.history.add({
                        "user_id" : user.id,
                        "action" : `solve question#${question.id} ${reportDate(new Date())}`
                    })
                    JsonRespone(res, anwser);
                });
            } else {
                return JsonRespone(res, {
                    "error" : "not enought money"
                });
            }
          
            // make history
            // make payment
            // make update balance
        }
        api.history.add({
            "user_id" : user.id,
            "action" : `Try to solve: ${body} question#${question.id} ${reportDate(new Date())}`
        })
        return JsonRespone(res, anwser);
    });
}

async function downloadCSVResult(req, res) {
    if( req.method !== 'POST') {
        res.writeHead(405);
        res.end();
        return;
    }

    let body = [];
    req.on('error', (err) => {
        console.error(err);
    }).on('data', (chunk) => {
        body.push(chunk);
    }).on('end', async () => {
        body = Buffer.concat(body).toString();
        let jbody = JSON.parse(body);
        let qid = 0;
        let nfo = await getQuestionCredidentials(qid);
        let data = await queryApi.query(jbody.querry, nfo.sgbd, nfo.user, nfo.pass).then( r => r.error ? [] : r.entity).catch(() => []);
        let exportData = "";
        if (data.length > 0) {
            // building csv response
            // https://datatracker.ietf.org/doc/html/rfc4180
            let buff = [];
            buff.push(...Object.keys(data[0]).reduce((a,c) => `${a},${c}`));
            data.forEach( r => buff.push('\n', Object.values(r).reduce((a,c) => `${a},${c}`)));
            exportData = buff.reduce((a,c) => `${a}${c}`);
        }

        res.setHeader("Content-Type", "text/csv");
        res.setHeader("Content-Disposition", `attachment; filename=interogration_${Math.random().toString(30).substring(2)}.csv`);
        res.writeHead(200);
        res.end(exportData);
    });
}


async function downloadPdfClasament(req, res) {
    if( req.method !== 'POST') {
        res.writeHead(405);
        res.end();
        return;
    }

    let body = [];
    req.on('error', (err) => {
        console.error(err);
    }).on('data', (chunk) => {
        body.push(chunk);
    }).on('end', async () => {
        body = Buffer.concat(body).toString();
        let nfo = getInternBdCreddidentials();
        let data = await queryApi.query(queries.classment, nfo.sgbd, nfo.user, nfo.pass).then( r => r.error ? [] : r.entity).catch(() => []);

        createPdfBinary(generatePdfReportClasament(data), (binary) => {
            res.setHeader("Content-Type", "application/pdf");
            res.setHeader("Content-Disposition", `attachment; filename=clasament_${Math.random().toString(30).substring(2)}.pdf`);
            res.writeHead(200);
            res.end(binary);
        }, (e) =>res.end('ERROR:' + e));
    });
}


async function downloadPdfHistory(req, res) {
    if( req.method !== 'POST') {
        res.writeHead(405);
        res.end();
        return;
    }

    let body = [];
    req.on('error', (err) => {
        console.error(err);
    }).on('data', (chunk) => {
        body.push(chunk);
    }).on('end', async () => {
        body = Buffer.concat(body).toString();
        let data = await whoIAm(req, res, true);
        data['history'] = await execQuery(queries.history.replace('{{id}}', req.headers.auth.info.user)).then( r => r.error ? [] : r.entity.map(h => h.ACTION)).catch(() => []);

        createPdfBinary(generatePdfReportHistory(data), (binary) => {
            res.setHeader("Content-Type", "application/pdf");
            res.setHeader("Content-Disposition", `attachment; filename=history_${Math.random().toString(30).substring(2)}.pdf`);
            res.writeHead(200);
            res.end(binary);
        }, (e) =>res.end('ERROR:' + e));

    });
}

function makePayment(buyer, seller, ammount, title) {
    let ini_buy = buyer.BALANCING;
    let ini_sell = seller.BALANCING;
    if(ammount < ini_buy) {
        api.wallet.update( buyer.ID, {
            'id' : buyer.ID,
            'balancing' :  ini_buy - ammount
        });
        api.wallet.update(seller.ID, {
            'id' : seller.ID,
            'balancing' :  ini_sell + ammount
        });

        return api.payment.add({
            "wallet_seller": seller.ID,
            "wallet_buyer": buyer.ID,
            "valoare": ammount,
            "blanta_noua": ini_buy - ammount,
            "title": title,
        });
    }
    return {};
}

async function buyQuestion(req, res) {
    let qid = /\/api\/action\/buyquestion\/(\d+)$/gm.exec(req.url);
    if( qid === null) {
        res.writeHead(400);
        res.end();
        return;
    }
    let question = await api.question.get(qid[1]);
    let user = await whoIAm(req, res, true);
    let w_bank = await execQuery(queries.banks).then( r => r.error ? {} : r.entity[0]).catch(() => {return {}});
    let w_user = await execQuery(queries.userWallet.replace("{{id}}", user.id)).then( r => r.error ? {} : r.entity[0]).catch(() => {return {}});
    let payment = await makePayment(w_user, w_bank, question.value, `User ${user.user_name} user#${user.id} buy question#${question.id}`);
    if (payment.hasOwnProperty('id')) {
        api.owquestions.add({
            "user_id": user.id,
            "question_id": question.id,
            "solved": "false",
            "payment_buy": payment.id,
            "payment_rew": payment.id,
        }).then( r => {
            api.history.add({
                "user_id" : user.id,
                "action" : `buy question#${question.id} ${reportDate(new Date())}`
            })
            JsonRespone(res, r);
        });
    } else {
        JsonRespone(res, {
            "error" : "not enought money"
        });
    }
}

async function apiController(req, res) {
    let nrout = /\/api\/(\w+)\/(.*)/gm.exec(req.url);
    if(nrout !== null && locations.includes(nrout[1])) {
        return serveApi(req, res, nrout);
    }

    return new Router()
        .path(/\/api\/$/gm, homeView)
        .path(/\/api\/whoIAm$/gm, whoIAm)
        .path('api/action/buyquestion', buyQuestion)
        .path("/api/querry/check/", checkQuestionAnswer)
        .path("/api/querry/getcsv/", downloadCSVResult)
        .path("/api/pdf/top/", downloadPdfClasament)
        .path("/api/pdf/history/", downloadPdfHistory)
        .route(req, res);
}

function JsonRespone(stream, data) {
    stream.setHeader("Content-Type", "application/json");
    stream.writeHead(200);
    stream.end(JSON.stringify(data, null, 2));
}

function serveApi(req, res, unit) {
    let body = []
    req.on('error', (err) => {
        console.error(err);
    }).on('data', (chunk) => {
        body.push(chunk);
    }).on('end', async () => {
        body = Buffer.concat(body).toString();
        let cttr = api[unit[1]];
        if(unit[2].length === 0) {
            if(req.method === 'GET') {
                return JsonRespone(res, await cttr.getAll());
            }
            if(req.method === 'POST') {
                return JsonRespone(res, await cttr.add(JSON.parse(body)));
            }
        }
        switch(req.method) {
            case "GET":
                return JsonRespone(res, await cttr.get(unit[2]));
            case "PUT":
                return JsonRespone(res, await cttr.update(unit[2], JSON.parse(body)));
            case "DELETE":
                return JsonRespone(res, await cttr.delete(unit[2]));
        }
    });
}

module.exports = apiController
