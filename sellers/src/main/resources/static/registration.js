var selectedPaymentType = 0;

$(document).ready(function(){
	
    $.ajax({
        type: "GET",
        url: "https://localhost:8672/sellers/allPaymentTypes",
        contentType: "application/json",
        headers: {
            "Authorization": "Bearer "+getUrlParameter("token")
        },
        success: function(data){
            var html = ""
            for (index = 0; index < data.length; ++index) {
                html += '<li class="list-group-item row py-2"><label class="col-8">'+data[index].name+'</label><button class="btn btn-link btn-payment mb-2 col-4" id='+data[index].id+' name='+data[index].name+'>Register</button></li>';
            }
            $("#paymentList").html(html);
        }  
    });

    $("#paymentList").on('click', '.btn-payment', function() {
        let paymentType = $(this).attr("name");
        selectedPaymentType = $(this).attr("id");
        $("#formContainer").load("https://localhost:8672/"+paymentType+"/getRegistrationForm");
        }
    )

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