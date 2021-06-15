const Router = require('./../routing');
const api = require('./../binder/api');

const locations = Object.keys(api);

function homeView(req, res) {
    JsonRespone(res, );
}

async function apiController(req, res) {
    let nrout = /\/api\/(\w+)\/(.*)/gm.exec(req.url);
    if(locations.includes(nrout[1])) {
        return serveApi(req, res, nrout);
    }

    return new Router()
        .path(/\/api\/$/gm, homeView)
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
