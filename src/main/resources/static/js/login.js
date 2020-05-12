/**
 * 创建用户
 */
function createUser() {
    var userName = $("#userName").val();
    var passWord = $("#passWord").val();
    var e_mail = $("#e-mail").val();
    var sex = $("#sex").val();
    var age = $("#age").val();
    var birthday = $("#birthday").val();
    var country = $("#country").val();
    var data = {
        "userName": userName,
        "passWord": passWord,
        "e_mail": e_mail,
        "sex": sex,
        "age": age,
        "birthday": birthday,
        "country": country,
    };
    $.ajax({
        url: "/user/createuser",
        type: "POST",
        async: false,
        contentType: 'application/json',
        //dataType: 'json',
        data: JSON.stringify(data),
        success: function (data) {
            //debugger;
            if (data == "ok") {
                alert("用户创建成功！");
                $("#light").css("display","none");
                $("#fade").css("display","none");
            } else {
                alert("用户创建失败！");
            }
        },
        error: function (response, ajaxOptions, thrownError) {
            //debugger;
            alert("用户创建失败！");
        }
    });

}

/**
 * 用户登录
 */
function doUserLogin() {
    var loginName = $("#loginName").val();
    var loginPassWord = $("#loginPassWord").val();
    var data = {
        "userName":loginName,
        "passWord":loginPassWord
    };
    $.ajax({
        url: "/user/checklogin",
        type: "POST",
        async: false,
        contentType: 'application/json',
        //dataType: 'json',
        data: JSON.stringify(data),
        success: function (data) {
            if (data == "ok") {
                window.location.href = "/bigproject/index.do";
            } else {
                $("#tips").val("用户名或密码错误！");
            }
        },
        error: function (response, ajaxOptions, thrownError) {
            alert("登录失败！");
        }
    });
}
