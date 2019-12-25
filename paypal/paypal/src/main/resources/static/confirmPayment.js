$(document).ready(function(){

    var paymentId = getUrlParameter("paymentId");
    var payerId = getUrlParameter("PayerID");

    $.ajax({
        type: "POST",
        url: "https://localhost:8672/paypal/paymentOrderAmount",
        data: paymentId,
        contentType: "application/json",
        success: function(data){
            $("#price").val(data);
        }
      });

      $("#confirmButton").click(function(event){
          event.preventDefault();

          $.ajax({
            type: "GET",
            url: "https://localhost:8672/paypal/success?paymentId="+paymentId+"&PayerID="+payerId,
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