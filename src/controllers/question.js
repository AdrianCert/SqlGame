const Router = require('./../routing');
const render = require('./../utils/html-response').HtmlRespone;

async function homeView(req, res) {
    render(res, "layout.html", {
        "title" : "Question"
    });
}

async function questionView(req, res) {
    render(res, "questionpage.html", {
        "title" : "Question"
    });
}

async function questionController(req, res) {
    return new Router()
        .path(/\/question\/$/gm, homeView)
        .path(/\/question\/(\d+)/gm, questionView)
        .route(req, res);
}

module.exports = questionController
