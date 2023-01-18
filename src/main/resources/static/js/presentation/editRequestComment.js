window.onload = function () {
 
    document.getElementById("editRequestCommentBtn").onclick = function(){
        const target = document.querySelectorAll(".editRequestCommentMore");
        
        target.forEach(function (t){
            console.log(t.style.display);
            if (t.style.display == "block") {
                t.style.display ="none";
                console.log("none!");
            }else{
                t.style.display ="block";
                console.log("block!!");
            }
        });
    
        
    }
    
}