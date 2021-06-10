const Router = require('./../routing');
const render = require('./../utils/html-response').HtmlRespone;

async function homeView(req, res){
    render(res, "profile.html",{
        "title" : "My Profile"
    });
}

async function profileController(req, res) {
    return new Router()
        .path(/\/myProfile\/$/gm, homeView)
        .route(req, res);
}

module.exports = profileController