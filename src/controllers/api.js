const Router = require('./../routing');
const api = require('./../binder/api');
const queryApi = require('./../binder/queryHandler');

const locations = Object.keys(api);

function homeView(req, res) {
    JsonRespone(res, );
}

async function whoIAm(req, res) {
    let data = await api.user.get(req.headers.auth.info.user);
    return JsonRespone(res, data);
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
        let qid = /\/api\/query\/check\/(\d+)/gm.exec(req.url)[1];
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

async function apiController(req, res) {
    let nrout = /\/api\/(\w+)\/(.*)/gm.exec(req.url);
    if(nrout !== null && locations.includes(nrout[1])) {
        return serveApi(req, res, nrout);
    }

    return new Router()
        .path(/\/api\/$/gm, homeView)
        .path(/\/api\/whoIAm$/gm, whoIAm)
        .path("/api/query/check/", checkQuestionAnswer)
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
                return JsonRespone(res, await cttr.add(body));
            }
        }
        switch(req.method) {
            case "GET":
                return JsonRespone(res, await cttr.get(unit[2]));
            case "PUT":
                return JsonRespone(res, await cttr.update(unit[2], body));
            case "DELETE":
                return JsonRespone(res, await cttr.delete(unit[2]));
        }
    });
}

module.exports = apiController
