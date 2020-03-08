package com.tgw.basic.common.utils.file;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.common.utils.math.PlatformMathUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zjg on 2017/7/1.
 */
public class PlatformFileUtils {

    /**
     * 重命名文件名。
     * 文件名由年月日时分秒毫秒+3位随即数组成
     * @param orginFileName
     * @return
     */
    public static String renameFileNameByTimeRandom(String orginFileName){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

        String random = PlatformMathUtils.createRandString999();

        if(StringUtils.isBlank( orginFileName )){
            throw new PlatformException("文件名参数错误！");
        }

        int startIndex = orginFileName.lastIndexOf(".");
        String suffix = orginFileName.substring( startIndex );

        return sdf.format( new Date() )+random+suffix;
    }

    /**
     * 下载文件公用方法。
     * @param request
     * @param response
     * @param path 文件绝对路径
     * @param downloadFielName 要下载的文件名称
     * @return
     */
    public static HttpServletResponse download(HttpServletRequest request, HttpServletResponse response,String path,String downloadFielName) {
        InputStream fis=null;
        OutputStream toClient=null;
        try {
            path = path.replace("\\",File.separator);
            path.replace("/",File.separator);

            // path是指下载的文件的路径。
            File file = new File(path);
            if( !file.exists() ){
                path = File.separator+"resource"+File.separator+"other"+File.separator+"fileNotExistsTip.txt";
                path = request.getSession().getServletContext().getRealPath( path );
                file = new File(path);

                downloadFielName = "文件不存在提示.txt";
            }

            // 取得文件名。
            if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
                downloadFielName = URLEncoder.encode(downloadFielName, "UTF-8");
            } else {
                downloadFielName = new String(downloadFielName.getBytes("UTF-8"), "ISO8859-1");
            }
            // 取得文件的后缀名。
            //String ext = downloadFielName.substring(downloadFielName.lastIndexOf(".") + 1).toLowerCase();

            // 以流的形式下载文件。
            fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);

            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + downloadFielName );
            response.addHeader("Content-Length", "" + file.length());
            response.setContentType("application/octet-stream");

            toClient = new BufferedOutputStream(response.getOutputStream());
            toClient.write(buffer);
            toClient.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }finally {
            if( fis!=null ){
                try {
                    fis.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            if( toClient!=null ){
                try {
                    toClient.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return response;
    }
}
