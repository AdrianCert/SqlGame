const Router = require('../routing');
const render = require('../utils/html-response').HtmlRespone;

async function homeView(req, res){
    render(res, "landing.html",{
        "title" : "Page question"
    });
}

async function landingController(req, res) {
    return new Router()
        .path(/\/landing\/$/gm, homeView)
        .route(req, res);
}

module.exports = landingController