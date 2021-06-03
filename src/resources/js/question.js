document.addEventListener("DOMContentLoaded", () => writeQuestions(5));

let __questions__ = [
    {
        "title" : "Facturile de astazi",
        "description" : "Afisati toate facturile din ziua curenta"
    },
    {
        "title" : "Clienti Restanta",
        "description" : "Afisati clienti restanti"
    },
    {
        "description" : "Calculeaza incasarile totale pentru fiecare luna"
    },
    {
        "title" : "Best Furnizori",
        "description" : "Afiseaza furnizori cei cautati"
    },
    {
        "title" : "Produse expirate",
        "description" : "Creaza o lista cu produsele care urmeaza sa expire in urmatoarele 14 zile"
    },
    {
        "title" : "Eroare",
        "description" : "Din depozit a plecat o cutie cu marfa gresit. Incarca sa identifici acea cutie. Pe factura apare ca a fost livrat dar in inventar acea cutie lipseste."
    },
    {
        "description" : "Afiseza stocul actual de napolitane cu valinie"
    }
];

function getQuestions() {
    return __questions__;
}

function createQuestionBox(data) {

    let doc = document.createElement("div");
    doc.classList.add("question-box");

    let title = document.createElement('h4');
    title.textContent = data.title;
    doc.appendChild(title);

    let description = document.createElement('p');
    description.innerHTML = data.description;
    doc.appendChild(description);

    doc.addEventListener("click", showQuestionModal);

    return doc;
}

function getQuestionModal() {
    let modal = document.getElementById("question_modal_id");
    console.log(modal);
    if(modal !== null) return doc;
    modal = document.createElement('div');
    // create modal
    return modal;
}

function showQuestionModal(e) {
    console.log(e);
    let modal = getQuestionModal();
    console.log(modal);
    // fill with the details
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