const Router = require('./../routing');
const fs = require('fs').promises;

async function homeView(req, res) {
    fs.readFile(__dirname + ".\\..\\templates/questionpage.html")
        .then(contents => {
            res.setHeader("Content-Type", "text/html");
            res.writeHead(200);
            res.end(contents);
        })
}


async function questionController(req, res) {
    return new Router()
        .path("/question/", homeView)
        .route(req, res);
}

module.exports = questionController
