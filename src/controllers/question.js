const Router = require('./../routing');
const render = require('./../utils/html-response').HtmlRespone;
const api = require('./../binder/api');
const queryApi = require('./../binder/queryHandler');
const { execQuery, queries, whoIAm } = require('./../binder/interogations');

async function homeView(req, res) {
    render(res, "layout.html", {
        "title" : "Question"
    });
}

async function questionView(req, res) {
    let qid  = /\/question\/(\d+)/gm.exec(req.url)[1];
    let question = await api.question.get(qid);
    let user = await whoIAm(req, res, true);
    let q_own = await execQuery(queries.questionOwn.replace("{{id}}", user.id).replace("{{qid}}", question.id))
        .then( r => r.error ? {} : r.entity[0])
        .catch(() => {return {}});
    if(!q_own.hasOwnProperty('ID')){
        res.setHeader("Location", "/question/");
        res.writeHead(307);
        res.end();
        return
    }
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

/**
 * Functie de preluare a informatiilor dupa un anumit ID
 */

 async function get_info(ID){
    let url_ = url_2.replace("{id}", id);
    const response = await fetch(url_, {
        method : 'GET',
        mode : 'cors',
        headers:{
            'Content-Type' : 'application/json'
        }
    });
    return response.json();
}

module.exports = questionController
