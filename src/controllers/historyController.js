const Router = require('./../routing');
const render = require('./../utils/html-response').HtmlRespone;

async function homeView(req, res){
    render(res, "history.html",{
    });
}

async function historyController(req, res) {
    return new Router()
        .path(/\/history\/$/gm, homeView)
        .route(req, res);
}

module.exports = historyController