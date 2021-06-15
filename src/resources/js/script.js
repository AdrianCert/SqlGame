document.addEventListener("DOMContentLoaded", () => {
    updateNavigation();
});

function updateNavigation() {
    document.querySelectorAll("nav a").forEach( i => {
        if(i.href == window.location) {
            if(!i.classList.contains('active')) {
                document.querySelector("nav a.active").classList.remove('active');
            }
            i.classList.add('active');
        }
    });
}

function whoIAm() {
    
}