
var continiuePressed = false;

$(document).ready(function(){


    $.ajax({
        type: "GET",
        url: "https://localhost:8672/sellers/paymentTypes",
        contentType: "application/json",
        headers: {
            "Authorization": "Bearer "+getUrlParameter("token")
        },
        success: function(data){
           let html = "";
           for(let type of data){
        	   html = html +"<option value="+type.name+">"+type.name+"</option>";
           }
           $("#paymentTypeSelect").html(html);
        }
        
      });
	
	
    $("#continueButton").click(function(event){
        event.preventDefault();
        continiuePressed = true;

        $.ajax({
            type: "POST",
            url: "https://localhost:8672/"+$("#paymentTypeSelect").val()+"/createPayment",
            contentType: "application/json",
            headers: {
                "Authorization": "Bearer "+getUrlParameter("token")
            },
            success: function(data){
                window.location.replace(data);
            }
            
          });
    })

    function getUrlParameter(sParam) {
        var sPageURL = window.location.search.substring(1),
            sURLVariables = sPageURL.split('&'),
            sParameterName,
            i;
    
        for (i = 0; i < sURLVariables.length; i++) {
            sParameterName = sURLVariables[i].split('=');
    
            if (sParameterName[0] === sParam) {
                return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
            }
        }
    };

})

$(window).on("unload", function(e) {
	
	if(!continiuePressed){
		$.ajax({
            type: "POST",
	        async: false,
            url: "https://localhost:8672/sellers/cancelPayment",
            contentType: "application/json",
            headers: {
                "Authorization": "Bearer "+getUrlParameter("token")
            },
            success: function(data){
            	console.log(data);
            }
            
          });
	}

    function getUrlParameter(sParam) {
        var sPageURL = window.location.search.substring(1),
            sURLVariables = sPageURL.split('&'),
            sParameterName,
            i;
    
        for (i = 0; i < sURLVariables.length; i++) {
            sParameterName = sURLVariables[i].split('=');
    
            if (sParameterName[0] === sParam) {
                return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
            }
        }
    };

});