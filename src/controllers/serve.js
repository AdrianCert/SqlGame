const fs = require('fs').promises;

const mime_type = require('./../utils/mime-type.json');

function getMimeTypeByExtension(file) {
    let extension = file.match(/\.[^.\W]+$/gm)
    if( extension) {
        extension = extension[0];
        if( extension in mime_type) {
            return mime_type[extension];
        }
    }
    return 'text/plain';
}

async function serveController(req, res) {
    return await fs.readFile(`${__dirname}./../${req.url}`)
        .then(contents => {
            res.setHeader("Content-Type", getMimeTypeByExtension(req.url));
            res.writeHead(200);
            res.end(contents);
        }).catch( r => {
            res.writeHead(404);
            res.end();
        })
}

module.exports = serveController
