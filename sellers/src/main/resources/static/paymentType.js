$(document).ready(function(){


    $("#continueButton").click(function(event){
        event.preventDefault();

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