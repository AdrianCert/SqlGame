class Router {
    constructor() {
        this.paths = [];
        return this;
    }
}

Router.prototype.path = function (url, f) {
    this.paths.push({
        'url': url,
        'function': f
    });
    return this;
};

Router.prototype.route = async function (req, res) {
    let found = false;
    for (let p of this.paths) {
        if (req.url.match(new RegExp(p.url))) {
            p.function(req, res);
            found = true;
            break;
        }
    }

    if(!found) {
        res.writeHead(404);
        res.end();
    }

    console.log(`${res.statusCode} HTTP/${req.httpVersion} ${req.method} ${req.url}`);
};

module.exports = Router;
