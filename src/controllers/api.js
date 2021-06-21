const Router = require('./../routing');
const api = require('./../binder/api');
const queryApi = require('./../binder/queryHandler');
const {createPdfBinary, generatePdfReportClasament} = require('./../utils/pdf-make');

const locations = Object.keys(api);
const queries = {
    "classment" : `select u.id as id, u.name || ' ' || u.surname as name , u.mail as email, w.balancing as coins
    from usertable u
    join userwallet uw on u.id = uw.user_id
    join wallet w on uw.wallet_id = w.id
    order by w.balancing desc
    `.split(/\s+/).join(' '),
    "history" : `w`
}

function homeView(req, res) {
    JsonRespone(res, );
}

async function whoIAm(req, res , intern = false) {
    let data = req.headers.hasOwnProperty('auth') && req.headers.auth.is_authenticated ?
        await api.user.get(req.headers.auth.info.user) : {};
    return intern ? data: JsonRespone(res, data);
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
        let anwser = await queryApi.verificate(body, question.solution, nfo.sgbd, nfo.user, nfo.pass);
        if (anwser.accepted) {
            // make history
            // make payment
            // make update balance
        }
        await api.histo
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
        let jbody = JSON.parse(body);
        let qid = 0;
        let nfo = await getQuestionCredidentials(qid);
        let data = await queryApi.query(jbody.querry, nfo.sgbd, nfo.user, nfo.pass).then( r => r.error ? [] : r.entity).catch(() => []);

        createPdfBinary(generatePdfReportClasament(data), (binary) => {
            res.setHeader("Content-Type", "application/pdf");
            res.setHeader("Content-Disposition", `attachment; filename=clasament_${Math.random().toString(30).substring(2)}.pdf`);
            res.writeHead(200);
            res.end(binary);
        }, (e) =>res.end('ERROR:' + e));
        
        

    });
}

async function apiController(req, res) {
    let nrout = /\/api\/(\w+)\/(.*)/gm.exec(req.url);
    if(nrout !== null && locations.includes(nrout[1])) {
        return serveApi(req, res, nrout);
    }

    return new Router()
        .path(/\/api\/$/gm, homeView)
        .path(/\/api\/whoIAm$/gm, whoIAm)
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
