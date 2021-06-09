let container = [];

let send = {
    "enunt" : 0
}

send.id = 1;
console.log(send);

const data = (ev) => {
    ev.preventDefault();
    let entity = {
        'text' : document.getElementById("enunt").value,
        'valid': document.getElementById("solutia").value
    }
    container.push(entity);
    console.warn('added', {container});
}

document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("submit").addEventListener('click', data);
});

