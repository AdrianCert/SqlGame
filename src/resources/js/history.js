document.addEventListener("DOMContentLoaded", function(event){
    var editor;
    var inUse;
    
    editor = document.getElementsByClassName("history-log");

    if(editor != null){
        for(i = 0; i < editor.length; i++){
            editor[i].addEventListener("click", function(){
                document.getElementsByClassName("question-title")[0].innerHTML = this.cells[0].innerHTML;
                document.getElementsByClassName("question-content")[0].innerHTML = this.cells[4].innerHTML;
            });
        }
    }

    editor = document.getElementsByClassName("history-log-navigator")[0].getElementsByTagName("nav");
    inUse = editor[0];
    
    for(i = 0; i < editor.length; i++){
        editor[i].addEventListener("click", function(){
            if(!this.classList.contains("active")){
                inUse.classList.remove("active");
                document.getElementById("page-" + inUse.innerHTML).classList.add("invisible");

                this.classList.add("active");
                inUse = this;
                document.getElementById("page-" + inUse.innerHTML).classList.remove("invisible");
            }
        });
    }

});