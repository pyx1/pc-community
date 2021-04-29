document.addEventListener("DOMContentLoaded", function(){
    astars();
})

function astars(){
    var holder = document.getElementsByName('stars-holder');
    for(let i = 0; i<holder.length; i++){
        var number = holder[i].getAttribute('stars-number');
        if(number > 0){
            switch(number){
                case '1':
                    holder[i].innerHTML = "&#9733; &#9734; &#9734; &#9734; &#9734;"; 
                    break;
                case '2':
                    holder[i].innerHTML = "&#9733; &#9733; &#9734; &#9734; &#9734;"; 
                    break;
                case '3':
                    holder[i].innerHTML = "&#9733; &#9733; &#9733; &#9734; &#9734;"; 
                    break;
                case '4':
                    holder[i].innerHTML = "&#9733; &#9733; &#9733; &#9733; &#9734;"; 
                    break;
                case '5':
                    holder[i].innerHTML = "&#9733; &#9733; &#9733; &#9733; &#9733;"; 
                    break;
                default:
                    break;
            }
        }
        else{
            holder[i].innerHTML = "No reviews";
        }
    }
    
}