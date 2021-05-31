const {path} = require('./../routing');
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
    const pack = { 
        "request" : req,
        "response" : res
    };
    return path(pack, "/question/", homeView);
}

module.exports = questionController
