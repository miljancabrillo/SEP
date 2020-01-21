$(document).ready(function(){
	
    $("#registrationSuccess").hide();


    $("#register").click(function(){

        let errorMessage = "";

        $(".required-field").each(function(){
            if($(this).val() == "") errorMessage = "Field " + $(this).attr("displayName")+ " is required!";
        });

        if(errorMessage != ""){
            $("#registrationMessage").text(errorMessage);
            return;
        }
        $("#registrationMessage").text("");


        let data = new Object();
        data.email = $("#email").val();
        data.accessToken = $("#accessToken").val();
        console.log(data);

        $.ajax({
            type: "POST",
            url: "https://localhost:8672/bitcoin/register",
            data: JSON.stringify(data),
            contentType: "application/json",
            headers: {
                "Authorization": "Bearer "+getUrlParameter("token")
            },
            success: function(data){
                $.ajax({
                    type: "GET",
                    url: "https://localhost:8672/sellers/confirmRegistration/"+selectedPaymentType,
                    data: JSON.stringify(data),
                    contentType: "application/json",
                    headers: {
                        "Authorization": "Bearer "+getUrlParameter("token")
                    },
                    success: function(data){
                       
                    },
                });
                $("#registrationForm").hide();
                $("#registrationSuccess").show();
            },
            error: function(data){
                $("#registrationMessage").text(data.responseText);
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

//# sourceURL=registrationFormBitcoin.js