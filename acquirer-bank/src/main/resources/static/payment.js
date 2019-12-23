$(document).ready(function(){

    $("#payButton").click(function(event){

        event.preventDefault();

        console.log(/^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/.test($("#pan").val()));

        console.log(/^([0-9]){2}([/]{1})([0-9]){2}$/.test($("#pan").val()));

    });

})