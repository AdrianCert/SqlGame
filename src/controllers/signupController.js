const Router = require('./../routing');
const render = require('./../utils/html-response').HtmlRespone;

async function homeView(req, res){
    render(res, "signup.html",{
        "title" : "Create question"
    });
}

async function signupController(req, res) {
    return new Router()
        .path(/\/signup\/$/gm, homeView)
        .route(req, res);
}

module.exports = signupController