package com.tgw.basic.common.utils.html;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.common.utils.html.domain.ReplaceBase64Result;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zjg on 2019-1-29.
 */
public class PlatformHTMLUtils {

    /**
     * 将html中的base64位编码的图片转为图片路径
     * @param html
     * @param param
     * @return
     * @throws PlatformException
     */
    public static ReplaceBase64Result replaceHTMLBase64Image(String html,Map<String,Object> param) throws PlatformException {
        ReplaceBase64Result result = new ReplaceBase64Result();
        result.setConvertBase64Img(false);
        if (StringUtils.isBlank(html)){
            result.setHtml(html);
            return result;
        }

        String regexImg = "<\\s*(i|I)(m|M)(g|G)[^>]+(s|S)(r|R)(c|C)\\s*=\\s*(\"|')data:image/[^>]+>";// 匹配img标签正则表达式
        Pattern pattern = Pattern.compile(regexImg);
        Matcher matcher = pattern.matcher(html);
        while(matcher.find()){  //存在包含base64图片的标签
            String imgHtml = matcher.group();// 获取到img标签元素
            String imgHtmlNew = null;// base64字符串替换为路径后的新元素

            String regexBase64 = "(\"data:image/[^'\"]+\")|('data:image/[^'\"]+')";// 匹配src属性，包含开头与结尾的双引号或单引号
            Pattern base64Pattern = Pattern.compile(regexBase64);
            Matcher base64matcher = base64Pattern.matcher(imgHtml);
            while(base64matcher.find()){// 存在base64位字符串
                String base64Str = base64matcher.group();// 获取src的属性，包含开头与结尾的双引号或单引号
                base64Str = base64Str.substring(1,base64Str.length()-1);// 去除两边引号

                Object object = saveBase64Img(base64Str,null,param);// 将base64信息存储到磁盘上
                StringBuilder urlStr = new StringBuilder();// 拼接图片url地址
                urlStr.append("");
                // TODO 拼接图片url待实现

                imgHtmlNew = imgHtml.replaceFirst(regexBase64,"\""+urlStr.toString()+"\"");
            }

            html = html.replaceFirst(regexImg,imgHtmlNew);
            result.setConvertBase64Img(true);
        }

        result.setHtml(html);
        return result;
    }

    /**
     * 将图片base64位字符串转换后保存到磁盘中
     * @param base64Str
     * @param fileName
     * @param param
     * @return
     * @throws PlatformException
     */
    public static Object saveBase64Img(String base64Str,String fileName,Map<String,Object> param) throws PlatformException {
        // TODO 返回结果待实现
        Date createDate = new Date();

        String base64Body = null;
        if ( base64Str.startsWith("data:image/") ){// base64编码开头带有data:image/*;base64   （*代表图片格式后缀）
            String[] base64 = base64Str.split(",");
            String base64Prefix = base64[0];
            base64Body = base64[1];

            if (StringUtils.isBlank(fileName)) {// 文件名为空时，根据base64前缀生成文件名
                String imgSuffix = base64Prefix.replace("data:image/","").replace(";base64","");
                if ("jpeg".equalsIgnoreCase(imgSuffix)){
                    imgSuffix = "jpg";
                }
                fileName = ""+"."+imgSuffix;// 组装文件名   // TODO: 文件名拼接待实现
            }
        }else{
            base64Body = base64Str;
        }

        // base64字符串转为输入流
        InputStream inputStream = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = decoder.decodeBuffer(base64Body);// Base64解码
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            inputStream = new ByteArrayInputStream(b);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PlatformException("base64编码转换出错！");
        }

        // 扩展名验证  TODO

        File destFile = new File("");// TODO 输出的目标文件
        BufferedOutputStream bos = null;
        try {// 保存磁盘
            IOUtils.copy(inputStream, new FileOutputStream(destFile));
        } catch (Exception e) {
            e.printStackTrace();
            throw new PlatformException("保存base64编码图片出错！");
        }finally{
            if(bos!=null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return new Object();
    }
}
