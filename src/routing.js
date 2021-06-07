class Router {
    constructor() {
        this.paths = [];
        this.prefilters = [];
        this.postfilters = [];
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
            applyFilters(req, res, this.prefilters);
            p.function(req, res);
            applyFilters(req, res, this.postfilters);
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

Router.prototype.prefilter = function (func, order = null) {
    this.prefilters.push({
        "function" : func,
        "order" : order ? order : this.prefilters.length + 1
    });
}

Router.prototype.postfilter = function (func, order = null) {
    this.postfilters.push({
        "function" : func,
        "order" : order ? order : this.prefilters.length + 1
    });
}

Router.prototype.showlog = function (stat = true) {
    this.enabled_log = stat;
    return this;
}

function applyFilters(req, res, filters = []) {
    if( !filters) return;
    for(let filter of filters) {
        filter.function(req, res);
    }
}

module.exports = Router;
