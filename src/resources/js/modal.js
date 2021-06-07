function loadModalResurces() {
    if(document.getElementById("modal_css_file") === null) {
        var link = document.createElement('link');
        link.rel = 'stylesheet'; 
        link.type = 'text/css';
        link.href = '/resources/css/modal.min.css';
        link.id = "modal_css_file";
        document.getElementsByTagName('HEAD')[0].appendChild(link); 
    }
}

function getContextModal(ind) {
    let modal = document.getElementById(ind);
    if(modal !== null) return modal;
    modal = document.createElement('div');
    modal.setAttribute('id', ind);
    modal.classList.add('modal');

    let modal_container = document.createElement('div');
    modal_container.classList.add('modal-content');
    modal.appendChild(modal_container);

    let modal_head = document.createElement('div');
    modal_head.classList.add('modal-header');
    modal_container.appendChild(modal_head);

    let modal_body = document.createElement('div');
    modal_body.classList.add('modal-body');
    modal_container.appendChild(modal_body);

    let modal_footer = document.createElement('div');
    modal_footer.classList.add('modal-footer');
    modal_container.appendChild(modal_footer);

    let modal_title = document.createElement('h2');
    modal_title.innerHTML = "Title";
    modal_head.appendChild(modal_title);

    let modal_close = document.createElement('span');
    modal_close.innerHTML = "&times;";
    modal_close.classList.add('close');
    modal_head.appendChild(modal_close);

    modal_close.onclick = () => {
        console.log(modal);
        modal.style.display = 'none';
    };

    modal.onclick = (e) => {
        if( e.target === modal) {
            modal.style.display = 'none';
        }
    };

    window.onkeydown = (e) => {
        if (e.defaultPrevented) {
          return;
        }
        if( e.key === 'Escape') {
            modal.style.display = 'none';
        }
    };

    loadModalResurces();
    document.getElementsByTagName('main')[0].appendChild(modal);

    return modal;
}
