const http = require('http');
const config = require('./config.json');
const Router = require('./routing');
const auth = require('./authenticate');
const cookies = require('./utils/cookies');

const signupController = require('./controllers/signupController');
const pageQuestionController = require('./controllers/pageQuestionController');
const createQuestionController = require('./controllers/createQuestionController');
const profileController = require('./controllers/profile');
const questionController = require('./controllers/question');
const serveController = require('./controllers/serve');

const PORT = config.port || 5000;

var app = new Router();
app.showlog();
app.prefilter(processCookies);
app.prefilter(processAuth);
app.prefilter((i, o) => {
    console.log(i.headers.auth);
    if(!i.headers.auth.is_authenticated) {
        o.writeHead(200, {
            'Set-Cookie': 'sid=UUID;path=/',
            'Content-Type': 'text/plain'
        })
        o.end("ok");
    }
});
app.path("/myProfile/", profileController);
app.path("/signup/", signupController);
app.path("/pageQuestion/", pageQuestionController);
app.path("/createQuestion/", createQuestionController);
app.path("/question/", questionController);
app.path(/\.[^.\W]+$/gm, serveController);
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

http.createServer((i, o) => app.route(i, o)).listen(PORT, () => {
    console.log(`Server up on port ${PORT}`);
});
