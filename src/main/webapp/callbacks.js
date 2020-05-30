$(main);

function main(){ // инициализация
    var params = new URLSearchParams(window.location.search);
    if(params.has('userid')){ // id пользователя, чью страничку смотрим (в нашем случае всегда один и тот же)
        currentID = parseInt(params.get('userid'));
        if(isNaN(currentID))
            currentID = 0;
    }
    else
        currentID = 0;
    loadModule('base'); // зарузка модуля "кратко обо мне"
    $(".bg_link").click(function(){ // коллбэки для кнопок
        if(currentSection == 'bg')
            return;
        $(".main_section").fadeOut(50, function(){});
        loadModule('bg');
    });
    $(".base_link").click(function(){
        if(currentSection == 'base')
            return;
        $(".main_section").fadeOut(50, function(){});
        loadModule('base');
    });
    $(".skills_link").click(function(){
        if(currentSection == 'skills')
            return;
        $(".main_section").fadeOut(50, function(){});
        loadModule('skills');});
}
    
function loadModule(name){ // функция загрузки модулей
    var d = new XMLHttpRequest();
    switch(name){
        case 'base':
            d.open('GET', '/info/persons?type=brief&userid=' + currentID);
            d.send();
            d.onreadystatechange = function(){
                if(!checkReady(this)) // готов ли ответ
                    return;
                currentSection = 'base'; // чтобы не загружать два раза подряд одно и то же
                var response = JSON.parse(this.responseText);
                $(".main_section").fadeIn(250, function(){}).css({display: 'inline-block'})
                .html('<img class="image"></img><span class="main_text"></span>');
                $(".main_text").html(response.Description);
                $(".image").attr('src', '/res/' + (currentID + 1) + '.jpeg');
            }
            break;
        case 'bg':
            currentSection = 'bg';
            d.open('GET', '/info/persons?type=bg&userid=' + currentID);
            d.send();
            d.onreadystatechange = function(){
                if(!checkReady(this))
                    return;
                var response = JSON.parse(this.responseText);
                $(".main_section").html(response.BG).fadeIn(250, function(){}).css({display: 'inline-block'});
            }
            break;
        case 'skills':
            currentSection = 'skills';
            d.open('GET', '/info/persons?type=skills&userid=' + currentID);
            d.send();
            d.onreadystatechange = function(){
                if(!checkReady(this))
                    return;
                var response = JSON.parse(this.responseText);
                var text = '';
                var arrays = []; // всё делаем перебором
                for(var a = 0; a < 3; a++) // таким образом сохраняется возможность добавить новую категорию навыков
                    arrays.push(new Array());
                for(var a = 0; a < response.Skills.length; a++){ // идём наполнять массивы категорий навыков
                    switch(response.Skills[a].type){
                        case 'back':
                            if(arrays[0].length == 0){
                                arrays[0].push('<b>Бэк</b>: ');
                            }
                            arrays[0].push(response.Skills[a].name + ', ');
                            break;
                        case 'front':
                            if(arrays[1].length == 0){
                                arrays[1].push('<b>Фронт</b>: ');
                            }
                            arrays[1].push(response.Skills[a].name + ', ');
                            break;
                        case 'other':
                            if(arrays[2] == 0){
                                arrays[2].push('<b>Другое</b>: ');
                            }
                            arrays[2].push(response.Skills[a].name + ', ');
                    }
                }
                for(var a = 0; a < arrays.length; a++){ // теперь формируем текст на основе массивов
                    if(arrays[a].length == 0)
                        continue;
                    var str = arrays[a][arrays[a].length-1]; // две одинаковые переменные, сделал просто для удобочитаемости
                    arrays[a][arrays[a].length-1] = str.substring(0, str.length-2);
                    for(var b = 0; b < arrays[a].length; b++){
                        text += arrays[a][b];
                    }
                    if(a != arrays.length - 1)
                        text += '<br/>';
                }
                $(".main_section").html(text).fadeIn(250, function(){}).css({display: 'inline-block'});
            }
            break;
    }
}

function checkReady(response){ // проверка готовности ответа и обработка ошибок
        if(response.readyState != 4)
            return false;
        if(response.status == 404){
            alert('Пользователь с таким ID не найден');
            return false;
        }
        if(response.status == 400){
            alert('Ошибка запроса (возможно, отсутствует один из параметров)');
            return false;
        }
        return true;
}