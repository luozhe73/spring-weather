
$(function () {
    $("#selectId").change(function () {
        var cityId = $("#selectId").val();
        var url='/report/cityId/'+cityId;
        window.location.href=url;
    })
});