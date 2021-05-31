function path(pack, re, f) {
    var _re =  new RegExp(re,"gm");
    if (pack.request.url.match(_re)) {
        f(pack.request, pack.response);
        return false;
    }
    return true;
}

module.exports = { path };
