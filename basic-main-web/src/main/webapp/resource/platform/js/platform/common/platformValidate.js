/**
 * 校验ext上传附件的文件格式
 * @param value  文件名
 * @param allowSuffix  允许的后缀
 * @returns {*}
 */
function validateSuffix(value,allowSuffix){
	if( value.length>0 ){
		if( value.indexOf('.')!=-1 ){
			var arr = value.split('.');
			var suffix = arr[arr.length-1];

			if( suffix.length>0 ){
				suffix = suffix.toLowerCase();
			}else{
				return "文件格式错误！";
			}

			if( allowSuffix.length>0 ){
				var allowSuffixArra = allowSuffix.split(',');

				var resFlag = false;
				for( var i=0;i<allowSuffixArra.length;i++ ){
					if( allowSuffixArra[i].toLowerCase()==suffix ){
						resFlag = true;
						break;
					}
				}

				if( !resFlag ){
					return '文件格式错误！允许的文件类型（'+allowSuffix+'）';
				}else{
					return true;
				}

			}else{
				return "文件格式校验配置错误！";
			}

		}else{
			return "文件格式错误！";
		}

	}else{
		return true;
	}
}
