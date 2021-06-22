document.addEventListener("DOMContentLoaded", () => {
    updateNavigation();
});

function updateNavigation() {
    whoIAm().then( r => {
        document.querySelector('nav a[href="/myProfile/"]').innerHTML = `${r.name} (${r.balance})`
    });

    document.querySelectorAll("nav a").forEach( i => {
        if(i.href == window.location) {
            if(!i.classList.contains('active')) {
                document.querySelector("nav a.active").classList.remove('active');
            }
            i.classList.add('active');
        }
    });
}

async function fetchDownload(r) {
    r = {
        "data" : await r.blob(),
        "headers" : r.headers
    }
    let a = document.createElement("a");
    document.body.appendChild(a);
    a.style.display = "none";
    let reqxfile = /filename=(.+);|filename=(.+)$/gm.exec(r.headers.get('content-disposition'));
    let filename = reqxfile[1] || reqxfile[2];
    a.download = filename;
    a.href = window.URL.createObjectURL(new Blob([r.data],{ type : r.headers.get('content-type')}));
    a.click();
}

/**
 * Get user details response
 */
async function whoIAm() {
    return fetch("/api/whoIAm").then(r => r.json());
}