$(function(){

});

/**
 * 文件上传
 */
function importFile() {
    var fileData = new FormData();
    fileData.append("file",$("#selectfile")[0].files[0]);
    $.ajax({
        type: "post",
        url: "/file/importfile",
        async: false,
        data: fileData,
        contentType: false,
        processData: false,
        //dataType: "json",
        success: function (data) {
            alert("文件上传成功！");
            $("#selectfile").val("");
            location.reload();
        },
        error: function (msg) {
            alert("文件上传失败！");
        }
    });
}

/**
 * 删除文件
 * @param e
 */
function doDeleteFile(e) {
    var fileId = e.name;
    var data = {
        'fileId' : fileId
    };
    $.ajax({
        type: "GET",
        url: "/file/doDeleteFile?fileId="+fileId,
        async: false,
        contentType: false,
        processData: false,
        //dataType: "json",
        success: function (data) {
            if (data == "ok"){
                alert("文件删除成功！");
                location.reload();
            }else{
                alert("文件删除失败！");
            }
        },
        error: function (msg) {
            alert("文件删除失败！");
        }
    });
}


function uploadFiles() {
    var files = new FormData($("#form")[0]);
    $.ajax({
        type: "post",
        url: "/file/importfiles",
        async: false,
        data: files,
        contentType: false,
        processData: false,
        //dataType: "json",
        success: function (data) {
            alert("文件上传成功！");
            $("#form").val("");
            location.reload();
        },
        error: function (msg) {
            alert("文件上传失败！");
        }
    });
}

/**
 * 文件下载
 * @param e
 */
function dowloadFile(e) {
    var fileId = $(e).text();
    window.location.href = "/file/dowloadFile?fileId="+fileId;
}

/**
 * 多个文件下载
 */
function dowloadFiles() {
    var fileIds = "";
    var table = $("#left_table"); //获得整个表格对象
    var tables = table[0];
    var tableRows = tables.rows;
    for (var i = 0; i < tableRows.length; i++) {
        var cells = tableRows[i].cells;
        if(i > 0){
            for (var j = 0; j < cells.length; j++) {
                if(j == 0){
                    fileIds += cells[0].innerText+",";
                }
            }
        }

    }
    fileIds = fileIds.substr(0,fileIds.length-1);
    window.location.href = "/file/dowloadFiles?fileIds="+fileIds;
}

/**
 * 首页
 */
function first() {

}

/**
 * 上一页
 */
function previous(e){

}

/**
 * 下一页
 */
function next(e){

}

/**
 * 末页
 */
function lastNext(e){
    var pageNum = 2;
    var url = "/file/viewFiles?pageNum="+pageNum;
    $.get(url);
    location.reload();
}
