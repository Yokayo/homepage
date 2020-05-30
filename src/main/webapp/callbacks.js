$(main);

function main(){
    currentSection = 0; // main page
    alert(1);
    var d = new XMLHttpRequest();
    d.open('GET', '/info/persons?type=brief');
    d.send();
    d.onreadystatechange = function(){
        var response = JSON.parse(this.responseText);
        if(response.Message){
            alert(response.Message);
            return;
        }
        $(".main_section").html(response.Description).fadein(250, function(){});
    }
}