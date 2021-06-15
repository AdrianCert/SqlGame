function getFragment(str){
    return document.createRange().createContextualFragment(str);
}

function replaceClassamentCard(classament){
    let counter = 1;
    let container = document.getElementById("classamentCard");
    while(container.firstChild){
        container.firstChild.remove();
    }
    container.appendChild(document.createRange().createContextualFragment("<h2>Clasament</h2>"));
    classament.forEach(u => {
       if(counter < 6){
        container.appendChild(getFragment(
            `<div class="row">
                <p id = "row1">${counter++}. ${u.nickname} (${u.coins})</p>
            </div>`
            ));
       }
    })
}

async function writeClassamentCard(){
    let classament = await getClassament();  
    replaceClassamentCard(classament);
}