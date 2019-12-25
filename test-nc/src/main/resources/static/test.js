$(document).ready(function(){

    $("#priceError").hide();

    $("#checkOutButton").click(function(event){

        event.preventDefault();

        if($("#price").val() == ""){
            $("#priceError").show();
            return;
        }else{
            $("#priceError").hide();
        }

        $.ajax({
            type: "POST",
            url: "http://localhost:12000/testPayment",
            data: $("#price").val(),
            contentType: "application/json",
            success: function(data){
                $("#price").val("");
                window.open(data,"_blank");
            }
            
          });

    })

})