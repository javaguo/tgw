/**
 * Created by zjg on 2017/8/24.
 */
function exampleBeanUserDefineOpe1(url,id,formText,formNumberInteger){
    Ext.Msg.confirm("提示信息","确定要操作此条数据吗？",function (btn) {
        if( btn=="yes" ){
            Ext.Ajax.request({
                url: url,
                params: {
                    id: id,formText:formText,formNumberInteger:formNumberInteger
                },
                method:'POST',
                success: function(response){
                    var responseStr = response.responseText;
                    var responseJsonObj = Ext.JSON.decode( responseStr );

                    if( responseJsonObj.success ){
                        Ext.Msg.alert('提示', '操作成功！'+responseJsonObj.msg,function(){
                            /**
                             * 操作数据成功后，应该刷新列表数据。
                             * refreshList_ExampleBeanList()为框架生成的固定格式的方法。
                             * refreshList_为固定名称。
                             * ExampleBeanList为每个controller的Identifier名称。
                             */
                            refreshList_ExampleBeanList();
                        });
                    }else{
                        Ext.Msg.alert('提示', '操作失败！'+responseJsonObj.msg );
                    }

                },
                failure:function(response){
                    Ext.Msg.alert('提示', '抱歉，操作失败，出错了！' );
                }
            });
        }
    });

}

function exampleBeanUserDefineOpe2(){
    Ext.Msg.confirm("提示信息","确定要操作此条数据吗？",function (btn) {
        if( btn=="yes" ){
            Ext.Msg.alert('提示', '执行了不带参数的自定义方法！' );
        }
    });

}