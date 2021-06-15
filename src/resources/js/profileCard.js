async function writeProfileCard(ID){
    replaceProfileCard( await getInfo(parseInt(ID)));
}

async function getInfo(ID){
    let classament = await getClassament();
    let x = classament.filter(i => i.id == ID); 
    return x.length > 0 ? x[0] : null;
}

function replaceProfileCard(info){
    console.log(info);
    document.getElementById("text1").innerHTML = info.nickname;
    document.getElementById("text2").innerHTML = info.coins;
    document.getElementById("text3").innerHTML = info.poz_clasament;
}