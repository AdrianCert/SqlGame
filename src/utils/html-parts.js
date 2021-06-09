const fs = require('fs');
const path = require('path');

const val = {
    "container" : "parts",
    "regx" : /(\w+).part.html$/gm,
    "extension" : ".part.html",
    "dir" : path.join(__dirname, './../partials'),
}

val["partials"] = loadAllPartials()

function setPart(part) {
    return part[0] === '$' ? val.partials[part.slice(1)] : part;
}

function loadPart(filepath) {
    return fs.readFileSync(path.join(val.dir, filepath)).toString();
}

function loadAllPartials() {
    let r = {};
    fs.readdirSync(val.dir).forEach( p => {
        val.regx.lastIndex = 0; // reset index;
        let key = val.regx.exec(p)[1];
        r[key] = loadPart(p);
        
    });
    return r;
}

function buildpartials(dic) {
    let replace_dic = val.container in dic ? dic[val.container] : {};
    dic[val.container] = val.partials;
    for( let key in replace_dic) {
        dic[val.container][key] = setPart(replace_dic[key]);
    }
    return dic;
}

module.exports = buildpartials
