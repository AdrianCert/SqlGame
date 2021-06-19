const url_whoIAm = "/api/whoIAm"

async function getWhoIsLogged(){
    return await fetch(url_whoIAm, {
        method : 'GET'
    }).then(r => r.json());
}

async function didIGetIt(){
    let x = await getWhoIsLogged();
    return x;
}