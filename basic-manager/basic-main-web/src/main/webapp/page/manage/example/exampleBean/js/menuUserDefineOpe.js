/**
 * Created by zjg on 2017/8/26.
 */
function menuUserDefineOpe(ids){
    Ext.Msg.confirm("提示信息","确定要执行此操作吗？",function (btn) {
        if( btn=="yes" ){
            Ext.Msg.alert('提示', '执行自定义方法！参数ids-->'+ids );
        }
    });

}