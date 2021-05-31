const http = require('http');
const config = require('./config.json');
const { path } = require('./routing');
const questionController = require('./controllers/question');
const serveController = require('./controllers/serve');
const { HtmlRender } = require('./utils/template')

const FL404 = true;
const PORT = config.port || 5000;

const server = http.createServer((req, res) => {
    const pack = { 
        "request" : req,
        "response" : res
    };
    
    try {
        if( FL404
            && path(pack, "/question/", questionController)
            && path(pack, "\\.[^.\W]+$", serveController)
            && path(pack, "/", (inp, out) => {
                console.log(`REDIRECT HTTP/${inp.httpVersion} ${inp.method} ${inp.url}`);
                out.setHeader("Location", "/question/");
                out.writeHead(308);
                out.end();
            })
            ) {
                console.log("404");
            }
    } catch {
        
    }
    console.log(`${res.statusCode} HTTP/${req.httpVersion} ${req.method} ${req.url}`);
});

server.listen(PORT, () => {
    console.log(`Server up on port ${PORT}`);
});

module.exports = server;
