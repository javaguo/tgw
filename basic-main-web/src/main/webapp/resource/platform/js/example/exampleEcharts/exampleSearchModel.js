function exampleSearchModelCallback( freeAreaEleId,searForm ){

    //使用静态数据展示echart示例
    var myChart = echarts.init(document.getElementById(freeAreaEleId));
    // 指定图表的配置项和数据
    var option = {
        title: {
            text: 'ECharts 入门示例'
        },
        tooltip: {},
        legend: {
            data:['销量']
        },
        xAxis: {
            data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
        },
        yAxis: {},
        series: [{
            name: '销量',
            type: 'bar',
            data: [5, 20, 36, 10, 10, 20]
        }]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

    //查看表单参数信息
    /*var formValues = searForm.getValues();
     var formJsonStr = Ext.JSON.encode(formValues);
     console.log("formJsonStr-->"+formJsonStr);
     var formJsonObj = Ext.JSON.decode( formJsonStr );
     console.log("formJsonObj-->"+formJsonObj);*/

    //submit方法测试成功，可以正常提交表单参数。
    /*searForm.submit({
        submitEmptyText :false,
        waitMsg :'正在查询，请耐心等待......',
        success: function(form, action) {
            Ext.Msg.show({
                title:"提示信息",
                message:action.result.msg,
                buttons:Ext.Msg.OK,
                icon: Ext.Msg.INFO,
                fn: function(btn) {

                }
            });
        },
        failure: function(form, action) {
            Ext.Msg.alert('错误提示', "抱歉，出错了！"+action.result.msg);
        }
    });*/

    //以下方法没有测试成功，尚不知道怎么提交表单参数
    /*Ext.Ajax.request({
        url: globalBasePath+"exampleSearchModel/ajaxReq.do",
        extraParams:formValues,
        method:'POST',
        success: function(response){
            var responseStr = response.responseText;
            var responseJsonObj = Ext.JSON.decode( responseStr );

            if( responseJsonObj.success ){
                Ext.Msg.alert('提示', '操作成功！'+responseJsonObj.msg,function(){

                });
            }else{
                Ext.Msg.alert('提示', '操作失败！'+responseJsonObj.msg );
            }

        },
        failure:function(response){
            Ext.Msg.alert('提示', '抱歉，操作失败，出错了！' );
        }
    });*/
}