/**
 * Created by zjg on 2017/8/5.
 */
function valText(value){

    if(value.length>0){
        if( value>=90 && value<=110 ){
            return true;
        }else{
            return "不在指定的年龄范围内，年龄范围：90-110，包括90和110";
        }
    }else{
        return true;
    }

}


function valTextParam(value,alpha,chinese){
    if(value.length>0){
        if( value>=90 && value<=110 ){
            if( alpha ){
                if( alpha.getValue()=="abc" ){

                    if( chinese ){
                        if( chinese.getValue()=="中国" ){
                            return true;
                        }else{
                            return "chinese的值应该为'中国'";
                        }
                    }else{
                        return "chinese字段不存在。必须有chinese字段";
                    }

                }else{
                    return "alpha的值应该为abc";
                }
            }else{
                return "alpha字段不存在。必须有alpha字段";
            }


        }else{
            return "不在指定的年龄范围内，年龄范围：90-110，包括90和110";
        }
    }else{
        return true;
    }

}

function valPassword(value){

    if(value.length>0){
        if( value.length>0 ){
            return true;
        }else{
            return "输入的值非法";
        }
    }else{
        return true;
    }

}


function valPasswordParam(value,alpha){
    if(value.length>0){
        /*if( value.length>0 ){
            if( alpha ){
                if( alpha.getValue()=="abc" ){
                    return true;
                }else{
                    return "alpha的值应该为abc";
                }
            }else{
                return "alpha字段不存在。必须有alpha字段";
            }

        }else{
            return "输入的值非法";
        }*/
        return true;
    }else{
        return true;
    }

}

function valTextArea(value){

    if(value.length>0){
        if( value.length>0 ){
            return true;
        }else{
            return "输入的值非法";
        }
    }else{
        return true;
    }

}


function valTextAreaParam(value,alpha){
    if(value.length>0){
        /*if( value.length>0 ){
            if( alpha ){
                if( alpha.getValue()=="abc" ){
                    return true;
                }else{
                    return "alpha的值应该为abc";
                }
            }else{
                return "alpha字段不存在。必须有alpha字段";
            }

        }else{
            return "输入的值非法";
        }*/
        return true;
    }else{
        return true;
    }

}

function valNumber(value){

    if(value.length>0){
        if( value.length>0 ){
            return true;
        }else{
            return "输入的值非法";
        }
    }else{
        return true;
    }

}


function valNumberParam(value,alpha){
    if(value.length>0){
        /*if( value.length>0 ){
            if( alpha ){
                if( alpha.getValue()=="abc" ){
                    return true;
                }else{
                    return "alpha的值应该为abc";
                }
            }else{
                return "alpha字段不存在。必须有alpha字段";
            }

        }else{
            return "输入的值非法";
        }*/
        return true;
    }else{
        return true;
    }

}

function valTag(value){

    if(value.length>0){
        if( value.length>0 ){
            return true;
        }else{
            return "输入的值非法";
        }
    }else{
        return true;
    }

}


function valTagParam(value,alpha){
    if(value.length>0){
        /*if( value.length>0 ){
            if( alpha ){
                if( alpha.getValue()=="abc" ){
                    return true;
                }else{
                    return "alpha的值应该为abc";
                }
            }else{
                return "alpha字段不存在。必须有alpha字段";
            }

        }else{
            return "输入的值非法";
        }*/
        return true;
    }else{
        return true;
    }

}



function valDate(value){

    if(value.length>0){
        if( value.length>0 ){
            return true;
        }else{
            return "输入的值非法";
        }
    }else{
        return true;
    }

}


function valDateParam(value,alpha){
    if(value.length>0){
        /*if( value.length>0 ){
            if( alpha ){
                if( alpha.getValue()=="abc" ){
                    return true;
                }else{
                    return "alpha的值应该为abc";
                }
            }else{
                return "alpha字段不存在。必须有alpha字段";
            }

        }else{
            return "输入的值非法";
        }*/
        return true;
    }else{
        return true;
    }

}

function valComboBox(value){

    if(value.length>0){
        if( value.length>0 ){
            return true;
        }else{
            return "输入的值非法";
        }
    }else{
        return true;
    }

}


function valComboBoxParam(value,alpha){
    if(value.length>0){
        /*if( value.length>0 ){
            if( alpha ){
                if( alpha.getValue()=="abc" ){
                    return true;
                }else{
                    return "alpha的值应该为abc";
                }
            }else{
                return "alpha字段不存在。必须有alpha字段";
            }

        }else{
            return "输入的值非法";
        }*/
        return true;
    }else{
        return true;
    }

}

function valTree(value){

    if(value.length>0){
        if( value.length>0 ){
            return true;
        }else{
            return "输入的值非法";
        }
    }else{
        return true;
    }

}


function valTreeParam(value,alpha){
    if(value.length>0){
        /*if( value.length>0 ){
            if( alpha ){
                if( alpha.getValue()=="abc" ){
                    return true;
                }else{
                    return "alpha的值应该为abc";
                }
            }else{
                return "alpha字段不存在。必须有alpha字段";
            }

        }else{
            return "输入的值非法";
        }*/
        return true;
    }else{
        return true;
    }

}


function valFile(value){

    if(value.length>0){
        if( value.length>0 ){
            return true;
        }else{
            return "输入的值非法";
        }
    }else{
        return true;
    }

}


function valFileParam(value,alpha){
    if(value.length>0){
        /*if( value.length>0 ){
            if( alpha ){
                if( alpha.getValue()=="abc" ){
                    return true;
                }else{
                    return "alpha的值应该为abc";
                }
            }else{
                return "alpha字段不存在。必须有alpha字段";
            }

        }else{
            return "输入的值非法";
        }*/
        return true;
    }else{
        return true;
    }

}