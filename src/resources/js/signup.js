function intitiateWizard() {
    [...document.querySelectorAll("button[data-wizrole]")].forEach( el => {
        if( el.dataset.wizrole == 'next_page') {
            el.addEventListener('click', (e) => {
                let element = e.target;
                while( !element.parentElement.classList.contains('wizzard')) {
                    element = element.parentElement;
                }
                element.classList.add('offline');;
                element.nextElementSibling.classList.remove('offline');
            });
        }
        if( el.dataset.wizrole == 'last_page') {
            el.addEventListener('click', (e) => {
                let element = e.target;
                while( !element.parentElement.classList.contains('wizzard')) {
                    element = element.parentElement;
                }
                element.classList.add('offline');;
                element.previousElementSibling.classList.remove('offline');
            });
        }
        if( el.dataset.wizrole == 'submit') {

        }
    });
}

document.addEventListener("DOMContentLoaded", function(event) {
    intitiateWizard();
});
