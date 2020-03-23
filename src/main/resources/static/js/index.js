$(function(){

});

/**
 * 退出登录
 */
function doLogout() {
    $.get("/user/logout",function (data) {
        window.location.href = "/bigproject/login.do";
    });
}

/**
 * 查看地图
 */
function viewMaps() {
    window.location.href = "/bigproject/viewMap";
}

/**
 * 查看日程
 */
function viewCalendar() {
    window.location.href = "/bigproject/viewCalendar";
}
