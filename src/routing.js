class Router {
    constructor() {
        this.paths = [];
        this.log = false;
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

Router.prototype.route = function (req, res) {
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

    if( this.enabled_log) {
        console.log(`${res.statusCode} HTTP/${req.httpVersion} ${req.method} ${req.url}`);
    }

    return this;
};

Router.prototype.showlog = function (stat = true) {
    this.enabled_log = stat;
    return this;
}

module.exports = Router;
