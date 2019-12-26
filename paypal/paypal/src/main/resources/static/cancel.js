$(document).ready(function(){

	$.ajax({
        type: "POST",
        url: "https://localhost:8672/paypal/cancel",
        data: getUrlParameter("id"),
        contentType: "application/json",
        success: function(data){
        }         
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