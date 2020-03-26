function showPhoto(value,row,index) {
        if (value){
            return "<a href=\""+ value +"\" target=\"_Blank\" >" + "<img src='" + value + "' style='width:100px; height:auto;'/>" + "</a>";
        } else {
            return null;
        }
}

//上传文件框显示
function uploadidalogonline() {
    $('#uploadonlineinfo').window('open').dialog('setTitle','文件上传');

}
function uploadonline() {
    var add="/onlineupload";
    $('#fam').form('submit',{
        url: add,
        onSubmit: function(){

        },
        success: function(result){
            var result = eval('('+result+')');

            if (result.success){
                $('#uploadonlineinfo').window('close');
                $('#dg2').datagrid('reload');
                $.messager.show({
                    title: 'Success',
                    msg: '上传成功'
                });
            } else {
                $.messager.show({
                    title: 'Error',
                    msg: '上传错误，文件不能为空或上传重复文件，请重新上传！'
                });
            }
        }
    });
}
