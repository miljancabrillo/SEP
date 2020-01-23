var confirmPressed = false;

$(document).ready(function(){

    var paymentId = getUrlParameter("paymentId");
    var payerId = getUrlParameter("PayerID");

    $.ajax({
        type: "POST",
        url: "https://localhost:8672/paypal/subscriptionDetails/"+getUrlParameter("token"),
        contentType: "application/json",
        success: function(data){
            $("#name").val(data.name);
            $("#description").val(data.description);

        }
      });

      $("#cancelButton").click(function(event){
          event.preventDefault();     
          window.location.replace("https://localhost:8672/paypal/cancelSubscription.html?token="+getUrlParameter("token"));
      })

      $("#confirmButton").click(function(event){
          event.preventDefault();
          confirmPressed = true;
          $.ajax({
            type: "POST",                   
            url: "https://localhost:8672/paypal/executeSubscription/"+getUrlParameter("token")+"/"+getUrlParameter("sellerId"),
            contentType: "application/json",
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
	
	if(!confirmPressed){
		$.ajax({
	        type: "POST",
	        url: "https://localhost:8672/paypal/cancelSubscription/"+getUrlParameter("token"),
	        async: false,
	        contentType: "application/json",
	        success: function(data){
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
