const api = require('./binder/api');

async function migrate() {
    if( [...await api.role.getAll()].length === 0) {
        for( let r of [ "USER", "ADMIN", "SYS", "GUEST"]) {
            await api.role.add({
                "title" : r,
                "description" : r
            });
        }
    }
}

module.exports = migrate;