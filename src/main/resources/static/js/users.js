$(function(){

});

/**
 *删除用户
 */
function doDeleteUser(e) {
    var userId = e.name;

    $.ajax({
        type: "GET",
        url: "/user/deleteUser?userId="+userId,
        async: false,
        contentType: false,
        processData: false,
        //dataType: "json",
        success: function (data) {
            if (data == "ok"){
                alert("用户删除成功！");
                location.reload();
            }else{
                alert("用户删除失败！");
            }
        },
        error: function (msg) {
            alert("用户删除失败！");
        }
    });
}