document.addEventListener("DOMContentLoaded", () => writeQuestions(5));

let __questions__ = [
    {
        "id" : "1",
        "title" : "Facturile de astazi",
        "description" : "Afisati toate facturile din ziua curenta"
    },
    {
        "id" : "2",
        "title" : "Clienti Restanta",
        "description" : "Afisati clienti restanti"
    },
    {
        "id" : "3",
        "description" : "Calculeaza incasarile totale pentru fiecare luna"
    },
    {
        "id" : "4",
        "title" : "Best Furnizori",
        "description" : "Afiseaza furnizori cei cautati"
    },
    {
        "id" : "5",
        "title" : "Produse expirate",
        "description" : "Creaza o lista cu produsele care urmeaza sa expire in urmatoarele 14 zile"
    },
    {
        "id" : "6",
        "title" : "Eroare",
        "description" : "Din depozit a plecat o cutie cu marfa gresit. Incarca sa identifici acea cutie. Pe factura apare ca a fost livrat dar in inventar acea cutie lipseste."
    },
    {
        "id" : "7",
        "description" : "Afiseza stocul actual de napolitane cu valinie"
    }
];

function getQuestions() {
    return __questions__;
}

function getQuestion(id) {
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
        console.log(qid);
        window.location = `/question/${qid}`;
    });
    buttons.appendChild(view_btn);

    let buy_btn = document.createElement('button');
    buy_btn.type = 'button';
    buy_btn.className = btn_classes;
    buy_btn.textContent = `${question_data.id} coins`;
    buy_btn.addEventListener('click' , () => {
        window.location = '/';
    });
    buttons.appendChild(buy_btn);

    if( modal.querySelector(".modal-footer").firstChild == null) {
        modal.querySelector(".modal-footer").appendChild(buttons);
    }
    modal.querySelector(".modal-footer").firstChild = buttons;
}

function writeQuestions(n) {
    let data = getQuestions();
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

    /* dublicate data for layout dev */
    [...Array(100).keys()].forEach( () => {
        [...data].forEach( q => {
            if( curr === n) curr = 0;
            slots[curr++].appendChild(createQuestionBox(q))
        });
    });
    /* end dublicate data */
}