/**
 * Created by zjg on 2017/12/24.
 */

function downloadFile( url ){
    /*下载附件在新窗口中打开：
        1、避免当前窗口地址变化，下载附件出错，页面报错。
        2、*/
    //window.location.href = url;
    window.open(url);
}

/**
 * 删除附件。添加页面使用。
 * @param filefield
 */
function deleteFileLocal(filefield){
    var component = Ext.getCmp( filefield );
    if( component ){
        component.reset();
    }else{
        Ext.Msg.alert('提示', '抱歉，删除失败！' );
    }
}

/**
 * 删除附件。编辑页面使用。
 * @param filefield
 * @param id
 * @param fieldName
 * @param url
 * @param identifier
 */
function deleteFile(filefield,id,fieldName,url,identifier){
    Ext.Msg.confirm("提示信息","确定要删除此附件吗？",function (btn) {
        if( btn=="yes" ){
            var component = Ext.getCmp( filefield );
            if( component ){
                Ext.Msg.wait("正在删除，请耐心等待......");
                Ext.Ajax.request({
                    url: url,
                    params: {
                        beanId: id,fieldName:fieldName
                    },
                    method:'POST',
                    success: function(response){
                        Ext.Msg.hide();
                        var responseStr = response.responseText;
                        var responseJsonObj = Ext.JSON.decode( responseStr );
                        if( responseJsonObj.success ){
                            component.reset();
                            Ext.Msg.alert('提示', responseJsonObj.msg );
                            eval("searSubmit"+identifier+"();");//重新加载列表
                        }else{
                            Ext.Msg.alert('提示', responseJsonObj.msg );
                        }
                    },
                    failure:function(response){
                        Ext.Msg.hide();
                        Ext.Msg.alert('提示', '抱歉，删除附件失败，出错了！' );
                    }
                });

            }else{
                Ext.Msg.alert('提示', '抱歉，删除附件失败！' );
            }
        }
    });
}