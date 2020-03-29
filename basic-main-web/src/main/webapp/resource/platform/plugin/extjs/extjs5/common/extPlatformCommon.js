function closeWindowPlatform(){
	/**
	 添加编辑都用到此方法
	 * 关闭窗口事件，空方法即可
	 * window窗口需要通过实现closeAction来关闭，
	 * 否则有其他问题，具体原理暂不清楚
	 * */
}

/**
 * 修改控件的left值。左侧位置偏移量
 */
function resetComLeft( resetLeftComboArray,leftMargin ){
	if( resetLeftComboArray.length>0 ){
		for( var i=0;i<resetLeftComboArray.length;i++ ){
			var tempCom = resetLeftComboArray[i];
			var orgLeftTop = tempCom.getPosition( true );
			var objLeft = orgLeftTop[0]-leftMargin;
			tempCom.setStyle({left:objLeft+'px'});
		}
	}
}

/**
 * 判断checkbox是否选中。在初始化值的时候调用
 * @param checkedVal  所有选中的值，逗号分隔
 * @param currenVal 要判断的值
 * @returns {boolean}
 */
function isCheckedCheckbox( checkedVal,currenVal){
	var checkedVal_arr;
	if( checkedVal ){
		checkedVal_arr = checkedVal.split(",");
	}else{
		return false;
	}

	if( checkedVal_arr ){
		for( i=0;i<checkedVal_arr.length;i++ ){
			if( checkedVal_arr[i]+""==currenVal+"" ){
				return true;
			}
		}
	}

	return false;
}

