document.addEventListener("DOMContentLoaded", () => {
    let error_box = document.querySelector(".error");
    console.log(error_box.innerHTML);
    if( error_box.innerHTML !== "{{mess}}"
        && error_box.innerHTML !== "undefined") {
        error_box.classList.add("active");
    }
});

function goregist() {
    window.location = '/auth/register';
}