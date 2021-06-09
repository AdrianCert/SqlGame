const re = /(\w+)\=(.*);|(\w+)\=(.*)/gm;

function cookies(inp) {
    let m, r = {};
    while ( (m = re.exec(inp)) !== null){
        if (m.index === re.lastIndex) {
            re.lastIndex++;
        }
        r[m[1] || m[3]] = m[2] || m[4]
    }
    return r;
}

module.exports = cookies;