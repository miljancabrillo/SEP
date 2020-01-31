var payPressed = false;


$(document).ready(function(){

    $(".form-text").each(function(){
        $(this).hide();
    })

    $("#payButton").click(function(event){

        event.preventDefault();

        if(/^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/.test($("#pan").val()) == false){
            $("#panError").show();
            return;
        }else{
            $("#panError").hide();
        }

        if($("#securityCode").val().length != 3 && $("#securityCode").val().length != 4){
            $("#securityCodeError").show();
            return;
        }else{
            $("#securityCodeError").hide();
        }

        if($("#cardHolderName").val()==""){
            $("#cardHolderNameError").show();
            return;
        }else{
            $("#cardHolderNameError").hide();
        }
        
        if(/^([0-9]){2}([/]){1}([0-9]){2}?$/.test($("#expirationDate").val()) == false){
            $("#expirationDateError").show();
            return;
        }else{
            $("#expirationDateError").hide();
        }

        var cardData = new Object();
        cardData.paymentId = getUrlParameter("id");
        cardData.payerPan = $("#pan").val();
        cardData.securityCode = $("#securityCode").val();
        cardData.cardHolderName = $("#cardHolderName").val();
        cardData.expirationDate = $("#expirationDate").val();

        payPressed = true;
        
        $.ajax({
            type: "POST",
            url: "https://localhost:11000/executePayment",
            data: JSON.stringify(cardData),
            contentType: "application/json",
            success: function(data){
                $.ajax({
                    type: "GET",
                    url: data,
                    contentType: "application/json",
                    success: function(data){
                    	window.location.href =  data;
                    }
                    
                  });
        
            }
            
          });

    });

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
	
	if(!payPressed){
		$.ajax({
            type: "GET",
	        async: false,
            url: "https://localhost:11000/cancelPayment/"+getUrlParameter("id"),
            contentType: "application/json",
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

