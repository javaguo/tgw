package com.tgw.basic.example.exampleBeanFormVal.model;

import com.tgw.basic.core.model.AbstractBaseBean;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by zhaojg on 2017/08/05.
 */
@Table(name="example_bean_form_val")
public class ExampleBeanFormVal extends AbstractBaseBean {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    private String alpha;
    private String alphanum;
    private String email;
    private String url;
    private String letter;
    private String upperCase;
    private String lowerCase;
    private String letterNum;
    private String letterNumUnderline;
    private String chineseLetterNum;
    private String chineseLetterNumUnderline;
    private String chinese;
    private String character50;
    private String emailPlatform;
    private String mobileNo;
    private String fixedPhoneNo;
    private String phoneNo;
    private String idNumber15;
    private String idNumber18;
    private String idNumber;
    private Integer age;
    private String dateYmd;
    private String qq;
    private String postCode;
    private String ip;
    private String accountNumber;
    private String generalPassword;
    private String strongPassword;
    private String regex;
    private String regexVtype;
    private String valText;
    private String valTextParam;
    private String valPassword;
    private String valPasswordParam;
    private String valTextArea;
    private String valTextAreaParam;
    private Double valNumber;
    private Double valNumberParam;
    private String valTag;
    private String valTagParam;
    private String valDate;
    private String valDateParam;
    private String valComboBox;
    private String valComboBoxParam;
    private String valTree;
    private String valTreeParam;
    private String valFileUrl;
    private String valFileOrigFileName;
    private String valFileParamUrl;
    private String valFileParamOrigFileName;
    private String regexVtypeValidator;
    private String addTime;
    private String updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAlpha() {
        return alpha;
    }

    public void setAlpha(String alpha) {
        this.alpha = alpha;
    }

    public String getAlphanum() {
        return alphanum;
    }

    public void setAlphanum(String alphanum) {
        this.alphanum = alphanum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getUpperCase() {
        return upperCase;
    }

    public void setUpperCase(String upperCase) {
        this.upperCase = upperCase;
    }

    public String getLowerCase() {
        return lowerCase;
    }

    public void setLowerCase(String lowerCase) {
        this.lowerCase = lowerCase;
    }

    public String getLetterNum() {
        return letterNum;
    }

    public void setLetterNum(String letterNum) {
        this.letterNum = letterNum;
    }

    public String getLetterNumUnderline() {
        return letterNumUnderline;
    }

    public void setLetterNumUnderline(String letterNumUnderline) {
        this.letterNumUnderline = letterNumUnderline;
    }

    public String getChineseLetterNum() {
        return chineseLetterNum;
    }

    public void setChineseLetterNum(String chineseLetterNum) {
        this.chineseLetterNum = chineseLetterNum;
    }

    public String getChineseLetterNumUnderline() {
        return chineseLetterNumUnderline;
    }

    public void setChineseLetterNumUnderline(String chineseLetterNumUnderline) {
        this.chineseLetterNumUnderline = chineseLetterNumUnderline;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public String getCharacter50() {
        return character50;
    }

    public void setCharacter50(String character50) {
        this.character50 = character50;
    }

    public String getEmailPlatform() {
        return emailPlatform;
    }

    public void setEmailPlatform(String emailPlatform) {
        this.emailPlatform = emailPlatform;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getFixedPhoneNo() {
        return fixedPhoneNo;
    }

    public void setFixedPhoneNo(String fixedPhoneNo) {
        this.fixedPhoneNo = fixedPhoneNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getIdNumber15() {
        return idNumber15;
    }

    public void setIdNumber15(String idNumber15) {
        this.idNumber15 = idNumber15;
    }

    public String getIdNumber18() {
        return idNumber18;
    }

    public void setIdNumber18(String idNumber18) {
        this.idNumber18 = idNumber18;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDateYmd() {
        return dateYmd;
    }

    public void setDateYmd(String dateYmd) {
        this.dateYmd = dateYmd;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getGeneralPassword() {
        return generalPassword;
    }

    public void setGeneralPassword(String generalPassword) {
        this.generalPassword = generalPassword;
    }

    public String getStrongPassword() {
        return strongPassword;
    }

    public void setStrongPassword(String strongPassword) {
        this.strongPassword = strongPassword;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getRegexVtype() {
        return regexVtype;
    }

    public void setRegexVtype(String regexVtype) {
        this.regexVtype = regexVtype;
    }

    public String getValText() {
        return valText;
    }

    public void setValText(String valText) {
        this.valText = valText;
    }

    public String getValTextParam() {
        return valTextParam;
    }

    public void setValTextParam(String valTextParam) {
        this.valTextParam = valTextParam;
    }

    public String getValPassword() {
        return valPassword;
    }

    public void setValPassword(String valPassword) {
        this.valPassword = valPassword;
    }

    public String getValPasswordParam() {
        return valPasswordParam;
    }

    public void setValPasswordParam(String valPasswordParam) {
        this.valPasswordParam = valPasswordParam;
    }

    public String getValTextArea() {
        return valTextArea;
    }

    public void setValTextArea(String valTextArea) {
        this.valTextArea = valTextArea;
    }

    public String getValTextAreaParam() {
        return valTextAreaParam;
    }

    public void setValTextAreaParam(String valTextAreaParam) {
        this.valTextAreaParam = valTextAreaParam;
    }

    public Double getValNumber() {
        return valNumber;
    }

    public void setValNumber(Double valNumber) {
        this.valNumber = valNumber;
    }

    public Double getValNumberParam() {
        return valNumberParam;
    }

    public void setValNumberParam(Double valNumberParam) {
        this.valNumberParam = valNumberParam;
    }

    public String getValTag() {
        return valTag;
    }

    public void setValTag(String valTag) {
        this.valTag = valTag;
    }

    public String getValTagParam() {
        return valTagParam;
    }

    public void setValTagParam(String valTagParam) {
        this.valTagParam = valTagParam;
    }

    public String getValDate() {
        return valDate;
    }

    public void setValDate(String valDate) {
        this.valDate = valDate;
    }

    public String getValDateParam() {
        return valDateParam;
    }

    public void setValDateParam(String valDateParam) {
        this.valDateParam = valDateParam;
    }

    public String getValComboBox() {
        return valComboBox;
    }

    public void setValComboBox(String valComboBox) {
        this.valComboBox = valComboBox;
    }

    public String getValComboBoxParam() {
        return valComboBoxParam;
    }

    public void setValComboBoxParam(String valComboBoxParam) {
        this.valComboBoxParam = valComboBoxParam;
    }

    public String getValTree() {
        return valTree;
    }

    public void setValTree(String valTree) {
        this.valTree = valTree;
    }

    public String getValTreeParam() {
        return valTreeParam;
    }

    public void setValTreeParam(String valTreeParam) {
        this.valTreeParam = valTreeParam;
    }

    public String getValFileUrl() {
        return valFileUrl;
    }

    public void setValFileUrl(String valFileUrl) {
        this.valFileUrl = valFileUrl;
    }

    public String getValFileOrigFileName() {
        return valFileOrigFileName;
    }

    public void setValFileOrigFileName(String valFileOrigFileName) {
        this.valFileOrigFileName = valFileOrigFileName;
    }

    public String getValFileParamUrl() {
        return valFileParamUrl;
    }

    public void setValFileParamUrl(String valFileParamUrl) {
        this.valFileParamUrl = valFileParamUrl;
    }

    public String getValFileParamOrigFileName() {
        return valFileParamOrigFileName;
    }

    public void setValFileParamOrigFileName(String valFileParamOrigFileName) {
        this.valFileParamOrigFileName = valFileParamOrigFileName;
    }

    public String getRegexVtypeValidator() {
        return regexVtypeValidator;
    }

    public void setRegexVtypeValidator(String regexVtypeValidator) {
        this.regexVtypeValidator = regexVtypeValidator;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
