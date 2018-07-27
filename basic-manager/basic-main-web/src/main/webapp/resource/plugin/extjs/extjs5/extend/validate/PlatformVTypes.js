/**
 * Created by zjg on 2017/7/31.
 */
Ext.define('Platform.form.field.VTypes', {
    override: 'Ext.form.field.VTypes',

    /**
     * 自定义的正则验证
     */
    //正则验证函数，以time举例说明
    time: function(value) {
        return this.timeRe.test(value);
    },
    //正则表达式
    timeRe: /^([1-9]|1[0-9]):([0-5][0-9])(\s[a|p]m)$/i,
    //验证不通过的提示信息
    timeText: 'Not a valid time.  Must be in the format "12:34 PM".',
    //输入框过滤字符（限制输入框允许输入的字符）,列举出正则表达式中所有可能出现的字符
    timeMask: /[\d\s:amp]/i,

    //26个英文字母
    letter: function(value) {
        return this.letterRe.test(value);
    },
    letterRe:/^[A-Za-z]*$/,
    letterText:'只能输入英文字母',
    letterMask:/[A-Za-z]/i,

    //26个大写英文字母
    upperCase: function(value) {
        return this.upperCaseRe.test(value);
    },
    upperCaseRe:/^[A-Z]*$/,
    upperCaseText:'只能输入大写英文字母',
    upperCaseMask:/[A-Z]/,

    //26个小写英文字母
    lowerCase: function(value) {
        return this.lowerCaseRe.test(value)
    },
    lowerCaseRe:/^[a-z]*$/,
    lowerCaseText:'只能输入小写英文字母',
    lowerCaseMask:/[a-z]/,

    //26个英文字母、数字
    letterNum: function(value) {
        return this.letterNumRe.test(value);
    },
    letterNumRe:/^[A-Za-z0-9]*$/,
    letterNumText:'只能输入英文字母、数字',
    letterNumMask:/[A-Za-z0-9]/,

    //26个英文字母、数字、下划线，与ext自带的alpha正则验证相同
    letterNumUnderline: function(value) {
        return this.letterNumUnderlineRe.test(value);
    },
    letterNumUnderlineRe:/^\w*$/,
    letterNumUnderlineText:'只能输入英文字母、数字、下划线',
    letterNumUnderlineMask:/\w/i,

    //中文、26个英文字母、数字
    chineseLetterNum: function(value) {
        return this.chineseLetterNumRe.test(value);
    },
    chineseLetterNumRe:/^[\u4E00-\u9FA5A-Za-z0-9]*$/,
    chineseLetterNumText:'只能输入汉字、英文字母、数字',
    chineseLetterNumMask:/[\u4E00-\u9FA5A-Za-z0-9]/,

    //中文、26个英文字母、数字、下划线
    chineseLetterNumUnderline: function(value) {
        return this.chineseLetterNumUnderlineRe.test(value);
    },
    chineseLetterNumUnderlineRe:/^[\u4E00-\u9FA5A-Za-z0-9_]*$/,
    chineseLetterNumUnderlineText:'只能输入汉字、英文字母、数字、下划线',
    chineseLetterNumUnderlineMask:/[\u4E00-\u9FA5A-Za-z0-9_]/,

    //纯汉字
    chinese: function(value) {
        return this.chineseRe.test(value);
    },
    chineseRe:/^[\u4e00-\u9fa5]*$/,
    chineseText:'只能输入汉字',
    chineseMask:/[\u4E00-\u9FA5]/,

    //长度为3-20的所有字符
    character50: function(value) {
        return this.character50Re.test(value);
    },
    character50Re:/^.{1,50}$/,
    character50Text:'只能输入长度为1-50的字符',
    character50Mask:/./,


    //Email邮箱地址
    emailPlatform: function(value) {
        return this.emailPlatformRe.test(value);
    },
    emailPlatformRe:/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
    emailPlatformText:'email地址不合法',
    emailPlatformMask:/[\w.\-@'"!#$%&'*+/=?^_`{|}~]/i,

    //手机号码
    mobileNo: function(value) {
        return this.mobileNoRe.test(value);
    },
    mobileNoRe:/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/,
    mobileNoText:'手机号码不合法，只能输入13、14、15、17、18开头的手机号',
    mobileNoMask:/\d/,


    //固定电话号码
    fixedPhoneNo: function(value) {
        return this.fixedPhoneNoRe.test(value);
    },
    fixedPhoneNoRe:/^(\d{3,4}-|\d{3,4} )?\d{7,8}$/,
    fixedPhoneNoText:'电话号码不合法，正确格式如下：电话号或区号-电话号',
    fixedPhoneNoMask:/\d|[-]/,

    //联系电话（手机号或电话号码）
    phoneNo: function(value) {
        return this.phoneNoRe.test(value);
    },
    phoneNoRe:/(^(\d{3,4}-|\d{3,4}-)?\d{7,8}$)|(^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$)/,
    phoneNoText:'手机号或电话号不合法',
    phoneNoMask:/\d|[-]/,

    //15位身份证
    IDNumber15: function(value) {
        return this.IDNumber15Re.test(value);
    },
    IDNumber15Re:/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/,
    IDNumber15Text:'不合法的15位身份证号',
    IDNumber15Mask:/\d/,

    //18位身份证
    IDNumber18: function(value) {
        return this.IDNumber18Re.test(value);
    },
    IDNumber18Re:/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/,
    IDNumber18Text:'不合法的18位身份证号',
    IDNumber18Mask:/\d/,

    //身份证（简易验证）
    IDNumber: function(value) {
        return this.IDNumberRe.test(value);
    },
    IDNumberRe:/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/,
    IDNumberText:'不合法的身份证号',
    IDNumberMask:/\d|[X]/,

	//年龄
    age: function(value) {
        return this.ageRe.test(value);
    },
    ageRe:/^[1-9]{1}$|^[1-9]{1}[0-9]{1}$|^[1]{1}[0]{1}[0]{1}$/,
    ageText:'不合法的年龄，正确的年龄范围：1~100',
    ageMask:/\d/,
	
    //日期格式年月日
    dateYMD: function(value) {
        return this.dateYMDRe.test(value);
    },
    dateYMDRe:/^\d{4}-\d{1,2}-\d{1,2}$/,
    dateYMDText:'不合法的日期，正确格式如下：2000-01-01',
    dateYMDMask:/\d|[-]/,

    //腾讯QQ号
    QQ: function(value) {
        return this.QQRe.test(value);
    },
    QQRe:/^[1-9][0-9]{4,}$/,
    QQText:'不合法的QQ号码',
    QQMask:/\d/,

    //中国邮政编码
    postCode: function(value) {
        return this.postCodeRe.test(value);
    },
    postCodeRe:/^[1-9]\d{5}(?!\d)$/,
    postCodeText:'不合法的邮编，正确邮编为6位数字',
    postCodeMask:/\d/,

    //IP地址
    IP: function(value) {
        return this.IPRe.test(value);
    },
    IPRe:/^\d+\.\d+\.\d+\.\d+$/,
    IPText:'不合法的ip地址',
    IPMask:/\d|\./,

    //帐号是否合法(字母开头，允许5~16字节，允许字母数字下划线)
    accountNumber: function(value) {
        return this.accountNumberRe.test(value);
    },
    accountNumberRe:/^[a-zA-Z][a-zA-Z0-9_]{4,15}$/,
    accountNumberText:'不合法的账号，正确格式为：字母开头，长度在5~16之间，允许字母数字下划线',
    accountNumberMask:/[a-zA-Z0-9_]/,

    //密码(以字母开头，长度在6~18之间，只能包含字母、数字和下划线)
    generalPassword: function(value) {
        return this.generalPasswordRe.test(value);
    },
    generalPasswordRe:/^[a-zA-Z]\w{5,17}$/,
    generalPasswordText:'不合法的密码，必须以字母开头，长度在6~18之间，只能包含字母、数字和下划线',
    generalPasswordMask:/[a-zA-Z]|\w/,

    //强密码(必须包含大小写字母和数字的组合，不能使用特殊字符，长度在8-10之间)
    strongPassword: function(value) {
        return this.strongPasswordRe.test(value);
    },
    strongPasswordRe:/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$/,
    strongPasswordText:'不合法的密码，必须包含大小写字母和数字的组合，不能使用特殊字符，长度在8~10之间',
    strongPasswordMask:/[a-zA-Z0-9]/
});