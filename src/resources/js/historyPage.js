function getFragment(str){
    return document.createRange().createContextualFragment(str);
}

function text(i, Text){
    return getFragment(
        `
        <h4>${i} -> ${Text}</h4>
        `
    );
}

async function replace(istoric){
    let doc = document.getElementById("content");
    let i = 0, j = 1;
    while(doc.firstChild) doc.firstChild.remove();
    while(i != istoric.length)
        doc.appendChild(text(j++, istoric[i++].action));
}


async function loadHistory(id){
    let list = await getHistory(id);
    replace(list);
}

document.addEventListener("DOMContentLoaded", async() => {
    let current_user_id = await getWhoIsLogged();
    loadHistory(parseInt(current_user_id.id));
})