const Router = require('../routing');
const render = require('../utils/html-response').HtmlRespone;

async function homeView(req, res){
    render(res, "questionpage.html",{
        "title" : "Page question"
    });
}

async function pageQuestionController(req, res) {
    return new Router()
        .path(/\/pageQuestion\/$/gm, homeView)
        .route(req, res);
}

module.exports = pageQuestionController