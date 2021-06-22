const api = require('./binder/api');
const { execQuery, queries } = require('./binder/interogations');

function addBank() {
    return api.wallet.add({
        "balancing": 1000000000,
    });
}

async function migrate() {
    if( [...await api.role.getAll()].length === 0) {
        for( let r of [ "USER", "ADMIN", "SYS", "GUEST"]) {
            await api.role.add({
                "title" : r,
                "description" : r
            });
        }
    }

    // add a bank wallet
    execQuery(queries.banks).then( r => r.length === 0 ? addBank() : []).catch( addBank );
}

module.exports = migrate;