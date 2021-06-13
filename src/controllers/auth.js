const Router = require('../routing');
const render = require('../utils/html-response').HtmlRespone;
const {auth, login } = require('./../authenticate');

const noprocess = {
    "__strategy" : 2
};

function makeLogin(req, res) {
    let body = [];
    req.on('error', (err) => {
        console.error(err);
    }).on('data', (chunk) => {
        body.push(chunk);
    }).on('end', () => {
        body = Buffer.concat(body).toString();
        let m, r = {};
        let re = /(\w+)=(.+)&|(\w+)=(.+)$/gm
        while ( (m = re.exec(body)) !== null) {
            if (m.index === re.lastIndex) {
                re.lastIndex++;
            }
            r[m[1] || m[3]] = m[2] || m[4]
        }

        login(r).then(l => {
            if( l.auth) {
                res.setHeader( 'Set-Cookie', `sid=${l.key};path=/`);
                return render(res, "redirect.html", noprocess);
            } else {
                return render(res, "login.html", noprocess);
            }
        });
    });
}

function makeSignup(req, res) {
    let body = [];
    req.on('error', (err) => {
        console.error(err);
    }).on('data', (chunk) => {
        body.push(chunk);
    }).on('end', () => {
        body = Buffer.concat(body).toString();
        console.log(body);
    });
}

async function loginView(req, res) {
    if(req.method === 'GET') {
        return render(res, "login.html", noprocess);
    }
    if(req.method === 'POST') {
        return makeLogin(req, res);
    }
}

async function registerView(req, res) {
    if(req.method === 'GET') {
        return render(res, "signup.html", noprocess);
    }
    if(req.method === 'POST') {
        return makeSignup(req, res);
    }
}

async function authController(req, res) {
    return new Router()
        .path(/\/auth\/login$/gm, loginView)
        .path(/\/auth\/register/gm, registerView)
        .route(req, res);
}

module.exports = authController
