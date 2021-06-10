const Router = require('./../routing');
const render = require('./../utils/html-response').HtmlRespone;

async function homeView(req, res){
    render(res, "addquestion.html",{
        "title" : "Create question"
    });
}

async function createQuestionController(req, res) {
    return new Router()
        .path(/\/createQuestion\/$/gm, homeView)
        .route(req, res);
}

module.exports = createQuestionController