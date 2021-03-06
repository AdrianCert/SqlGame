document.addEventListener("DOMContentLoaded", () => {
    writeQuestions(3);
    document.getElementById("dificultate").addEventListener("change", selector);
    document.getElementById("text").addEventListener("change", inputText);
    document.getElementById("status").addEventListener("change", dif);
});

let __questions__ = null;

async function getQuestions() {
    __questions__ = __questions__ === null ? await binder_api.question.getAll().then(r => r.json()) : __questions__;
    return [...__questions__];
}

function getQuestion(id) {
    id = parseInt(id);
    return __questions__.filter( q => q.id === id)[0];
}

function createQuestionBox(data) {

    let doc = document.createElement("div");
    doc.classList.add("question-box");
    doc.dataset.entity_id = data.id;

    let title = document.createElement('h4');
    title.textContent = data.title;
    doc.appendChild(title);

    let description = document.createElement('p');
    description.innerHTML = data.description;
    doc.appendChild(description);

    let nfobox = document.createElement('div');
    nfobox.classList.add('nfo');
    doc.appendChild(nfobox);

    nfobox.appendChild(document.createRange().createContextualFragment(`<p>#${data.id}</p><p>${data.value}/${data.reward}</p>`));

    doc.addEventListener("click", showQuestionModal);

    return doc;
}

function showQuestionModal(e) {
    let question_box = e.target;
    let qid = 0;
    while(qid < e.path.length - 2) {
        question_box = e.path[qid++];
        if( question_box.classList.contains('question-box')) break;
        question_box = null;
    }
    if(question_box === null) return;
    qid = question_box.dataset.entity_id;
    let question_data = getQuestion(qid);

    let modal = getContextModal("question_modal_id");
    modal.style.display = "block";
    modal.querySelector(".modal-header h2").textContent = question_data.title;
    modal.querySelector(".modal-body").textContent = question_data.description;

    // <button type="button" class="">default</button>
    let btn_classes = "btn btn-round btn-default";
    let buttons = document.createElement('div');
    buttons.classList.add('space-around');

    let view_btn = document.createElement('button');
    view_btn.type = 'button';
    view_btn.className = btn_classes;
    view_btn.textContent = "view";
    view_btn.addEventListener('click' , () => {
        window.location = `/question/${qid}`;
    });
    buttons.appendChild(view_btn);

    let buy_btn = document.createElement('button');
    buy_btn.type = 'button';
    buy_btn.id = "buy_question_btn";
    buy_btn.className = btn_classes;
    buy_btn.textContent = `${question_data.value} coins`;
    buy_btn.addEventListener('click' , () => {
        fetch(`/api/action/buyquestion/${qid}`)
        .then( r => r.json()).then(r => {
            console.log(r);
            if(r.hasOwnProperty('id')){
                window.location = `/question/${qid}`;
            } else {
                document.getElementById('buy_question_btn').classList.add('error');
            }
        });
    });
    buttons.appendChild(buy_btn);

    if( modal.querySelector(".modal-footer").firstChild !== null){
        modal.querySelector(".modal-footer").firstChild.remove();
    }
    modal.querySelector(".modal-footer").appendChild(buttons);
}

let selector = async(ev) => {
    let doc = document.getElementById("dificultate");
    let listQuestions = await getQuestions();
    let sortedList = listQuestions.filter(r => r.value == doc.value);
    if(doc.value == 0) writeQuestions(3, listQuestions);
    else writeFitredQuestion(sortedList);
}

let inputText = async(ev) =>{
    let doc = document.getElementById("text");
    let listQuestions = await getQuestions();
    let sortedList = listQuestions.filter(r => r.title.includes(doc.value));
    if(sortedList.length != 0) writeFitredQuestion(sortedList);
}

async function getOwned(){
    let url = "http://localhost:2021/owquestions/";
    return fetch(url, {
        method : 'GET'
    }).then(r => r.json());
}

let dif = async(ev) =>{
    var doc = document.getElementById("status");
    var current_user = await didIGetIt();
    var listQ = await getQuestions();
    var showList = Array();
    var listaOwned = await getOwned();
    var listaFiltrata = listaOwned.filter(q => q.user_id == parseInt(current_user.id));
    var ok = 0, poz;

    if(doc.value == 0){
        for(let i = 0; i < listaFiltrata.length ; ++i)
            for(let j = 0; j < listQ.length; ++j)
                if(listaFiltrata[i].question_id == listQ[j].id) showList.push(listQ[j]);
        
        writeFitredQuestion(showList);
    }
    else if(doc.value == 1){
        for(let i = 0; i < listQ.length; ++i){
            ok = 1;
            for(let j = 0; j < listaFiltrata.length; ++j)
                if(listQ[i].id == listaFiltrata[j].question_id) ok = 0;
            if(ok) showList.push(listQ[i]);
        }
        writeFitredQuestion(showList);
    }
    else{
        for(let i = 0; i < listaFiltrata.length ; ++i)
            for(let j = 0; j < listQ.length; ++j)
                if(listaOwned[i].question_id == listQ[j].id && listaFiltrata[i].solved == "false") showList.push(listQ[j]);
        
        writeFitredQuestion(showList);
    }
}

async function writeFitredQuestion(list) {
    let doc = document.getElementById("main");
    while(doc.firstChild) doc.firstChild.remove();  
    writeQuestions(3, list);
}

async function writeQuestions( n, data = null) {
    data = data === null ? await getQuestions() : data;
    let container = document.getElementById('main');
    let curr = 0;

    // create slots and add recusing each outher
    let slots = [...Array(n).keys()].map( s => {
        let c = document.createElement('div');
        c.setAttribute('id',`qestion_box_slot_id_${s}`);
        c.classList.add('question-slot');
        c.style.width = `calc(${100 / n}% - 16px)`;
        container.appendChild(c);
        return c;
    });
    
    [...data].forEach( q => {
        if( curr === n) curr = 0;
        slots[curr++].appendChild(createQuestionBox(q))
    });
}