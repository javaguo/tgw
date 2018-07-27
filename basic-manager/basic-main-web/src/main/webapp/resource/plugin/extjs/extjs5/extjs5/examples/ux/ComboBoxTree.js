/**
 * Created by zjg on 2017/6/12.
 */
Ext.define('Ext.ux.ComboBoxTree',{
    extend : 'Ext.form.field.Picker',
    requires : ['Ext.tree.Panel'],
    alias : ['widget.comboboxtree'],
    //submitValue  是否作为表单字段提交，默认为true
    //submitValue:true,
    pathValue:'',
    //multiSelect属性不写默认是false，是单选;设定为true时是多选;
    //multiSelect :true,
    //multiCascade属性不写默认是true,代表多选时是否级联选择;设定为false时不级联选择;
    //multiCascade:true,
	/**
	* 扩展的属性：存储选中的节点id值。
	* 初始化选中节点，将节点id赋值给selectedIds即可，多个id逗号隔开。
	*/
    selectedIds:'',
	/**
	* 扩展的属性：加载树节点的请求地址。
	* url地址返回满足树节点的json格式数据即可
	*/
	//loadTreeDataUrl:'',
    initComponent : function(){
        var self = this;
        Ext.apply(self,{
            fieldLabel : self.fieldLabel,
            labelWidth : self.labelWidth
        });
        self.callParent();
    },
    createPicker:function(){
        var self = this;
        self.picker = Ext.create('Ext.tree.Panel',{
            height : self.treeHeight==null?300:self.treeHeight,
            autoScroll : true,
            floating : true,
            focusOnToFront : false,
            shadow : true,
            ownerCt : this.ownerCt,
            useArrows : false,
            //store : self.store,
            store : Ext.create('Ext.data.TreeStore',{
                autoLoad:true,
                proxy:{
                    type:'ajax',
                    //json路径要对
                    url:self.loadTreeDataUrl,
                    reader: {
                        type: 'json'
                    }
                },
                listeners: {
                    load: function(store, records, successful, operation, node, eOpts ) {
                        var displayName = "";
                        var displayValue = "";

                        var ids = self.selectedIds;

                        if( ids ){
                            ids = ids+"";//强制转为字符串类型
                            var idArr = ids.split( ",");

                            for( var i=0;i< idArr.length;i++ ){
                                var temp = store.getNodeById( idArr[i] );
                                if( temp ){
                                    /**
                                     * 多选的时候才设置checked属性。
                                     * 设置checked属性，树节点前面会出现勾选框，无论值是true或false.
                                     * true:选中  false：不选中
                                     */
                                    if( self.multiSelect ){
                                        temp.set("checked",true);
                                    }

                                    if( displayValue.length>0 ){
                                        displayName = displayName+"、"+temp.get("text");
                                        displayValue = displayValue+","+temp.get("id");
                                    }else{
                                        displayName = temp.get("text");
                                        displayValue = temp.get("id");
                                    }

                                }else{

                                }

                            }
                        }

                        self.selectedIds = displayValue ;
                        self.setValue( displayName );
                    }
                }
            }),
            rootVisible : false,
            //lines :true,
            viewConfig: {
                onCheckboxChange: function(e,t) {
                    if (self.multiSelect) {
                        var item = e.getTarget(this.getItemSelector(),this.getTargetEl()), record;
                        if (item) {
                            record = this.getRecord(item);
                            var check = !record.get('checked');
                            record.set('checked',check);

                            if(self.multiCascade){
                                if (check) {
                                    record.bubble(function(parentNode) {
                                        if ('Root' != parentNode.get('text')) {
                                            parentNode.set('checked',true);
                                        }
                                    });
                                    record.cascadeBy(function(node) {
                                        node.set('checked',true);
                                        node.expand(true);
                                    });
                                } else {
                                    record.cascadeBy(function(node) {
                                        node.set('checked',false);
                                    });
                                    record.bubble(function(parentNode) {
                                        if ('Root' != parentNode.get('text')) {
                                            var flag = true;
                                            for (var i = 0; i < parentNode.childNodes.length; i++) {
                                                var child = parentNode.childNodes[i];
                                                if(child.get('checked')){
                                                    flag = false;
                                                    continue;
                                                }
                                            }
                                            if(flag){
                                                parentNode.set('checked',false);
                                            }
                                        }
                                    });
                                }
                            }
                        }
                        self.expand();//此行代码解决节点可复选框多选时，360极速模式、谷歌浏览器中选中或取消一个节点树自动关闭问题。

                        var records = self.picker.getView().getChecked(), names = [], values = [];
                        Ext.Array.each(records, function(rec) {
                            names.push(rec.get('text'));
                            values.push(rec.get('id'));
                        });

                        self.selectedIds = values.join(',');
                        self.setValue(names.join('、'));
                    }
                }
            }
            ,dockedItems: [{
                xtype: 'toolbar',
                dock: 'top',
                rtl:false,
                items: [
                    '->',//箭头之前的在toolbar左边，箭头后面的排在toolbar右边
                    {
                    xtype: 'button', text: '清空'
                    ,listeners: {
                        click: function () {
                            self.clearValue();
                        }
                    }
                }]
            }]
        });

        self.picker.on({
            itemclick: function (view,recore,item,index,e,object) {
                if (!self.multiSelect) {
                    self.selectedIds = recore.get('id');
                    self.setValue(recore.get('text'));
                    self.eleJson = Ext.encode(recore.raw);
                    self.collapse();
                }
            }
        });
        return self.picker;
    },
    getSubmitValue: function() {
		//submitValue为true时，提交表单时才会调用此方法
        var self = this;
		return self.selectedIds;
    },
    clearValue: function() {
        var self = this;
        var ids = self.selectedIds;
        if( ids ){
            ids = ids+"";//强制转为字符串类型
            var idArr = ids.split( ",");

            for( var i=0;i< idArr.length;i++ ){
                var temp = self.picker.store.getNodeById( idArr[i] );
                if( temp ){
                    /**
                     * 多选的时候才设置checked属性。
                     * 设置checked属性，树节点前面会出现勾选框，无论值是true或false.
                     * true:选中  false：不选中
                     */
                    if( self.multiSelect ){
                        temp.set("checked",false);
                    }
                }
            }
        }

        self.selectedIds = '';
        self.setValue(null);
    },
    listeners:{
        afterrender:function(picker,opt){
            /**
             * 作用：初始化ComboBoxTree的value 值(也可以在added监听事件中调用getPicker方法)
             *
             * 调用getPicker方法，触发createPicker方法
             * createPicker方法会请求加载数据，store加载数据触发store的load事件，load事件中初始化value
             */
             this.getPicker();
        }
        /*added:function( picker, container, pos, eOpts ){
            this.getPicker();
        }*/

    },
    alignPicker:function(){
        var me = this, picker, isAbove, aboveSfx = '-above';
        if (this.isExpanded){

            picker = me.getPicker();
            if (me.matchFieldWidth){picker.setWidth(me.bodyEl.getWidth());}
            if (picker.isFloating()){

                picker.alignTo(me.inputEl, "", me.pickerOffset);// ""->tl
                isAbove = picker.el.getY() < me.inputEl.getY();
                me.bodyEl[isAbove ? 'addCls' : 'removeCls'](me.openCls+aboveSfx);
                picker.el[isAbove ? 'addCls' : 'removeCls'](picker.baseCls+aboveSfx);
            }
        }
    }
});