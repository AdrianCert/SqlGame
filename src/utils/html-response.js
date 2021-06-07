const fs = require('fs').promises;
const hdp = require("./html-render").HtmlRender;

async function HtmlRespone(res, template, dic = {}) {
    await fs.readFile(__dirname + ".\\..\\templates\\" + template)
        .then(contents => {
            res.setHeader("Content-Type", "text/html");
            res.writeHead(200);
            res.end(hdp(contents, dic));
        });
}

module.exports = { HtmlRespone };
