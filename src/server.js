const http = require('http');
const config = require('./config.json');
const Router = require('./routing');
const {auth, login } = require('./authenticate');
const cookies = require('./utils/cookies');
const migrate = require('./migrate');

const historyController = require('./controllers/historyController')
const landingController = require('./controllers/landingController');
const signupController = require('./controllers/signupController');
const pageQuestionController = require('./controllers/pageQuestionController');
const createQuestionController = require('./controllers/createQuestionController');
const profileController = require('./controllers/profile');
const questionController = require('./controllers/question');
const serveController = require('./controllers/serve');
const authController = require('./controllers/auth');
const apiController = require('./controllers/api');

const PORT = config.port || 5000;

var app = new Router();
app.showlog();
app.prefilter(staticChain)
app.prefilter(processCookies);
app.prefilter(processAuth);
app.prefilter(authWall);

app.path("/api/", apiController);
app.path("/landing/", landingController);
app.path("/signup/", signupController);
app.path("/pageQuestion/", pageQuestionController);
app.path("/createQuestion/", createQuestionController);
app.path("/myProfile/", profileController);
app.path("/question/", questionController);
app.path("/auth/", authController);
app.path("/history/", historyController);
app.path("/", (inp, out) => {
    console.log(`REDIRECT HTTP/${inp.httpVersion} ${inp.method} ${inp.url}`);
    out.setHeader("Location", "/question/");
    out.writeHead(308);
    out.end();
});

function processCookies(i, o) {
    i.headers.cookies = cookies(i.headers.cookie);
}

function processAuth(i, o) {
    i.headers.auth = auth(i.headers.cookies);
}

async function staticChain(req, res) {
    if( req.url.match(/\.[^.\W]+$/gm)) {
        await serveController(req, res);
    }
}

function authWall(req, res) {
    let allow_r = [
        "/",
        "/landing/",
        "/auth/login",
        "/auth/register"
    ];

    if( !req.headers.auth.is_authenticated) {
        for(let i of allow_r) {
            if (i === req.url) {
                return;
            }
        }
        res.setHeader("Location", "/landing/");
        res.writeHead(307);
        res.end();
    }
}

http.createServer((i, o) => app.route(i, o)).listen(PORT, () => {
    console.log(`Server up on port ${PORT}`);
    migrate();
});
