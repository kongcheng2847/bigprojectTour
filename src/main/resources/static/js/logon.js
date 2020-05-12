$(function(){
    $("#birthday").datepicker({
        changeMonth: true,
        changeYear: true
    });
    $.datepicker.setDefaults($.datepicker.regional['zh-CN'] = {
        closeText: '关闭',
        prevText: '上月',
        nextText: '下月',
        currentText: '今天',
        monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
        monthNamesShort: ['一','二','三','四','五','六','七','八','九','十','十一','十二'],
        dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
        dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],
        dayNamesMin: ['日','一','二','三','四','五','六'],
        weekHeader: '周',
        dateFormat: 'yy-mm-dd',//日期格式
        firstDay: 1,
        showWeek: true,//显示周
        isRTL: false,
        showMonthAfterYear: true,
        showOtherMonths: true,//显示其它月份日期
        selectOtherMonths: true,//是否可以选择其它月份日期
        //showButtonPanel: true,//显示按钮栏
        yearSuffix: '年'});
});

$().ready(function() {
    $.validator.setDefaults({
        //debug:true
        submitHandler: function(form) {
            form.submit();
        }
    }),
        // 在键盘按下并释放及提交后验证提交表单
        $("#bpuser").validate({
            errorPlacement: function( error, element ) {
                error.insertAfter( element.parent() );
            },
            rules: {
                userName: {
                    required: true,
                    maxlength: 6,
                    remote: {          //远程ajax验证
                        url: "/user/validate/username", //检查是否存在账号，存在则返回true
                        type: "post",               //数据发送方式
                        dataType: "json",           //接受数据格式
                        data: {                     //要传递的数据
                            userName: function() {
                                return $("#userName").val();
                            },
                            dataFilter: function (data) {  //判断控制器返回的内容
                                var notice = eval("("+ data +")");
                                if( notice ){
                                    return false;
                                }else{
                                    return true;
                                }
                            }
                        }
                    }
                },
                passWord: {
                    required: true,
                    minlength: 6,
                    maxlength: 16
                },
                eMail: {
                    required: true,
                    email: true,
                    remote: {
                        url: "/user/validate/email", //检查是否存在账号，存在则返回true
                        type: "POST",
                        dataType: "json",          //接受数据格式
                        data: {                     //要传递的数据
                            eMail: function() {
                                return $("#eMail").val();
                            },
                            dataFilter: function (data) {  //判断控制器返回的内容
                                var notice = eval("("+ data +")");
                                if( notice ){
                                    return false;
                                }else{
                                    return true;
                                }
                            }
                        }
                    }
                },
                sex: {
                    required: true
                },
                age: {
                    required: true,
                    digits: true
                },
                birthday: {
                    required: true,
                    dateISO:true
                },
                idCrad: {
                    required: true,
                    minlength: 18,
                    remote: {
                        url: "/user/validate/idcard", //检查是否存在账号，存在则返回true
                        type: "POST",
                        dataType: "json",           //接受数据格式
                        data: {                     //要传递的数据
                            idCrad: function() {
                                return $("#idCrad").val();
                            },
                            dataFilter: function (data) {  //判断控制器返回的内容
                                var notice = eval("("+ data +")");
                                if( notice ){
                                    return false;
                                }else{
                                    return true;
                                }
                            }
                        }
                    }
                },
                address: {
                    required: true
                },
                country: {
                    required: true
                },
            },
            messages: {
                userName: {
                    required: "请输入用户名",
                    //minlength: "用户名不能低于 2 个字符",
                    maxLength: "用户名不能超过 6 个字符",
                    remote: "用户名已存在！"
                },
                passWord: {
                    required: "请输入密码",
                    minlength: "密码长度不能小于 6 个字母",
                    maxLength: "用户名不能超过 16 个字符"
                },
                eMail: {
                    required: "请输入邮箱",
                    eMail: "请输入一个正确的邮箱",
                    remote: "邮箱已存在！"
                },
                sex: "请选择性别",
                age: {
                    required: "请输入年龄",
                    digits: "年龄不正确"
                },
                birthday:{
                    required: "请选择出生日期",
                    dateFormat: "yyyy-MM-dd"
                },
                idCrad: {
                    required: "请填写身份证号",
                    minlength: "身份证格式不正确",
                    remote: "身份证已存在！"
                },
                address: "请输入地址",
                country: "请选择所在地"
            }
        });
});


function bpTreaty() {
    var isChecked = $('#treaty').prop('checked');
    if(isChecked){
        $("#commit").attr("disabled",false);
    }else{
        $("#commit").attr("disabled",true);
    }
}