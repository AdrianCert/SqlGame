const http = require('http');
const config = require('./config.json');
const Router = require('./routing');
const questionController = require('./controllers/question');
const serveController = require('./controllers/serve');

const PORT = config.port || 5000;

var app = new Router();
app.path("/question/", questionController);
app.path(/\.[^.\W]+$/gm, serveController);
app.path("/", (inp, out) => {
    console.log(`REDIRECT HTTP/${inp.httpVersion} ${inp.method} ${inp.url}`);
    out.setHeader("Location", "/question/");
    out.writeHead(308);
    out.end();
});

http.createServer((i, o) => app.route(i, o)).listen(PORT, () => {
    console.log(`Server up on port ${PORT}`);
});
