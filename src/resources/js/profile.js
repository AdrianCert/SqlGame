let url = "http://localhost:2021/myProfile/";
let send_body = {
    'id': 0,
    'name' : "",
    'surname' : "",
    'username' : "",
    'mail' : "",
    'sqlizi' : 0
}
var edit = false;

const data = (ev) => {
    if(document.getElementById("edit_button").classList.contains("enabled_button")){
        ev.preventDefault();
        let entity = {
            'name' : document.getElementById("name").value,
            'surname': document.getElementById("surname").value,
            'username' : document.getElementById("username").value,
            'mail' : document.getElementById("email").value
        }
        console.warn('added', {entity});
        //construiesc send_body
        send_body["name"] = entity["name"];
        send_body["surname"] = entity["surname"];
        send_body["username"] = entity["username"];
        send_body["mail"] = entity["mail"];
        
        console.warn('added', {send_body});
        //trimit request ul
        let response = fetch(url,{
            method : 'POST',
            mode:'no-cors',
            headers:{
                'Content-Type': 'application/json;charset=utf-8'
            },
            body : JSON.stringify(send_body)
        }).then(r => {
            
        });
        let result = response.catch();
        console.warn(result);
        changeProfileInfo();
    }
}

var profile_email, profile_name, profile_surname, profile_username, profile_id;

function setProfileInfo(json){
    var jsonDoc = JSON.parse(json.responseText);
    
    document.getElementById("edit_button").classList.add("disabled_button");
    document.getElementById("edit_button").classList.remove("enabled_button");

    document.getElementById("name").value = jsonDoc.name;
    document.getElementById("name").placeholder = jsonDoc.name;

    document.getElementById("surname").value = jsonDoc.surname;
    document.getElementById("surname").placeholder = jsonDoc.surname;

    document.getElementById("username").value = jsonDoc.username;
    document.getElementById("username").placeholder = jsonDoc.username;

    document.getElementById("email").value = jsonDoc.email;
    document.getElementById("email").placeholder = jsonDoc.email;

    document.getElementById("profile_picture").src = "../resources/img/" + jsonDoc.id + ".png";
    document.getElementById("profile_picture").alt = document.getElementById("profile_picture").src;

    document.getElementById("sqlid").placeholder = jsonDoc.sqlid;
    document.getElementById("sqlid").value = jsonDoc.sqlid;

    document.getElementById("rank").placeholder = "#" + jsonDoc.rank;
    document.getElementById("rank").value = "#" + jsonDoc.rank;
}

function changeProfileInfo(){
    document.getElementById("username").placeholder = document.getElementById("username").value;
    document.getElementById("email").placeholder = document.getElementById("email").value;

    if(!(document.getElementById("email").value == "" || document.getElementById("email").value == document.getElementById("email").placeholder)){
        
    }

    if(!(document.getElementById("name").value == "" || document.getElementById("name").value == document.getElementById("name").placeholder)){
        document.getElementById("name").placeholder = document.getElementById("name").value;
    }

    if(!(document.getElementById("surname").value == "" || document.getElementById("surname").value == document.getElementById("surname").placeholder)){
        document.getElementById("surname").placeholder = document.getElementById("surname").value;
    }

    /*if(!(document.getElementById("profile_picture").src == document.getElementById("profile_picture").alt)){
        document.getElementById("profile_picture").alt = document.getElementById("profile_picture").src;
    }*/

    document.getElementById("edit_button").classList.add("disabled_button");
    document.getElementById("edit_button").classList.remove("enabled_button");
}

function edited_username(){
    if(document.getElementById("username").value == "" || document.getElementById("username").value == document.getElementById("username").placeholder){
        return false;
    }
    return true;
}

function edited_email(){
    if(document.getElementById("email").value == "" || document.getElementById("email").value == document.getElementById("email").placeholder){
        return false;
    }
    return true;
}

function edited_name(){
    if(document.getElementById("name").value == "" || document.getElementById("name").value == document.getElementById("name").placeholder){
        return false;
    }
    return true;
}

function edited_surname(){
    if(document.getElementById("surname").value == "" || document.getElementById("surname").value == document.getElementById("surname").placeholder){
        return false;
    }
    return true;
}

function edited_photo(){
    if(document.getElementById("profile_picture").src == document.getElementById("profile_picture").alt){
        return false;
    }
    return true;
}

function edited(){
    return edited_username() | edited_email() | edited_name() | edited_surname() /*| edited_photo()*/;
}

document.addEventListener("DOMContentLoaded", function(event) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            setProfileInfo(this);
        }
    };
    xhttp.open("GET", "../resources/database/localuser.json", true);
    xhttp.send();

    var username_input = document.getElementById("username");

    if(username_input != null){
        username_input.addEventListener("input", function(){            
            if(edited() == false){
                document.getElementById("edit_button").classList.add("disabled_button");
                document.getElementById("edit_button").classList.remove("enabled_button");
            }else{
                document.getElementById("edit_button").classList.add("enabled_button");
                document.getElementById("edit_button").classList.remove("disabled_button");
            }
        })    
    }

    var email_input = document.getElementById("email");

    if(email_input != null){
        email_input.addEventListener("input", function(){
            if(edited() == false){
                document.getElementById("edit_button").classList.add("disabled_button");
                document.getElementById("edit_button").classList.remove("enabled_button");
            }else{
                document.getElementById("edit_button").classList.add("enabled_button");
                document.getElementById("edit_button").classList.remove("disabled_button");
            }
        })    
    }

    var name_input = document.getElementById("name");

    if(name_input != null){
        name_input.addEventListener("input", function(){
            if(edited() == false){
                document.getElementById("edit_button").classList.add("disabled_button");
                document.getElementById("edit_button").classList.remove("enabled_button");
            }else{
                document.getElementById("edit_button").classList.add("enabled_button");
                document.getElementById("edit_button").classList.remove("disabled_button");
            }
        })    
    }

    var surname_input = document.getElementById("surname");

    if(surname_input != null){
        surname_input.addEventListener("input", function(){
            if(edited() == false){
                document.getElementById("edit_button").classList.add("disabled_button");
                document.getElementById("edit_button").classList.remove("enabled_button");
            }else{
                document.getElementById("edit_button").classList.add("enabled_button");
                document.getElementById("edit_button").classList.remove("disabled_button");
            }
        })    
    }

    var photo_input = document.getElementById("profile_picture");

    if(photo_input != null){
        photo_input.addEventListener("click", function(){
            document.getElementById("change_profile_picture").click();
        })
    }

    document.getElementById("edit_button").addEventListener("click", data)
});