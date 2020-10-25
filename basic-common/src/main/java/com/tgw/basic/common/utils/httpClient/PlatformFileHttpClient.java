package com.tgw.basic.common.utils.httpClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
public class PlatformFileHttpClient {
    
    private static Logger log=LoggerFactory.getLogger(PlatformFileHttpClient.class);
    private final static String REQ_TYPE_POST = "POST";
    private final static String REQ_TYPE_GET = "GET";
    private final static String REQ_PROTOCOL_HTTP = "http";
    private final static String REQ_PROTOCOL_HTTPS = "https";
    private String url;
    private ContentType contentType;
    private String RequestType;
    private static HttpClientBuilder httpclientbuilder;
    private HttpClient httpclient;
    static{
        httpclientbuilder=HttpClientBuilder.create();
    }
    
    public static void main(String[] args) {
    	// 测试示例
        String url = "";
        Map<Object, Object> params=new HashMap<Object, Object>();
        File file=new File("F:\\testFile\\abc.jpg");
        params.put("patchFile", file);
        params.put("userName", "zhang");
        
        PlatformFileHttpClient.uploadFile(url, params);
    }
    
    /**
     * 通过httpClient上传文件
     * @param url
     * @param params
     * @return
     */
    public static String uploadFile(String url,Map<Object, Object> params) {
        PlatformFileHttpClient client = PlatformFileHttpClient.createHttpClient(url, ContentType.MULTIPART_FORM_DATA, PlatformFileHttpClient.REQ_TYPE_POST);
        if (url.startsWith(PlatformFileHttpClient.REQ_PROTOCOL_HTTPS)){
        	client.ssLInit();
        }else{
        	client.initHttp();
        }

        MultipartEntityBuilder multipartBuilder= client.SetForm(params);
        return client.requestRun(multipartBuilder);
    }
    
    /**
     * @param url 上传文件地址
     * @param contentType   MIME type 表单类型
     * @param RequestType 请求类型post,get
     * @return
     */
    public static PlatformFileHttpClient createHttpClient(String url, ContentType contentType, String RequestType){
        PlatformFileHttpClient client=new PlatformFileHttpClient();
        
        if (contentType==null || contentType.equals("")){
            contentType=ContentType.MULTIPART_FORM_DATA;
        }
        contentType=contentType.withCharset(Charset.forName("UTF-8"));
        
        if (RequestType==null || RequestType.equalsIgnoreCase(PlatformFileHttpClient.REQ_TYPE_POST)){
            RequestType = PlatformFileHttpClient.REQ_TYPE_POST;
        }else{
            RequestType = PlatformFileHttpClient.REQ_TYPE_GET;
        }
        
        client.url=url;
        client.contentType=contentType;
        client.RequestType=RequestType;
        return client; 
    }
    
    /**
     * 执行请求，提交表单
     * @return
     */
    private  String requestRun(MultipartEntityBuilder    MultipartBuilder){
    	HttpEntity reqEntity = MultipartBuilder.build();
    	
        HttpPost httpPost = new HttpPost(url);  
        httpPost.addHeader( "Connection", "close");
        httpPost.setEntity(reqEntity); 
        HttpResponse response = null;
        try {
            response = this.httpclient.execute(httpPost); 
            int statusCode = response.getStatusLine().getStatusCode();  
            String responseText="";
            if (statusCode == HttpStatus.SC_OK){     
                HttpEntity resEntity = response.getEntity();   
                responseText = EntityUtils.toString(resEntity);
                EntityUtils.consume(resEntity);  
                return responseText;
            }
        } catch (Exception e) {
        	log.error("httpClient上传文件异常！", e);
            return null;
        }finally{
            httpPost.releaseConnection();
        }
        return null;
    }
    
    /**
     * http请求初始化
     */
    private  void initHttp(){
        this.httpclient = httpclientbuilder.build();
    }
    
    /**
     * https请求初始化
     * 如果是ssl文件上传必须要调用这个方法
     */
    private void ssLInit(){
        try {
            SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial( new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {//信任所有
                    return true;
                }
            }).build();  
            SSLConnectionSocketFactory sf = new SSLConnectionSocketFactory(sslcontext,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            this.httpclient= HttpClients.custom().setSSLSocketFactory(sf).build();
        } catch (Exception e) {
        	log.error("初始化ssl异常！", e);
        }
    }
    
    /**
     * 设置需要传输的参数
     * @param params
     */
    private MultipartEntityBuilder SetForm(Map<Object,Object> params){
        MultipartEntityBuilder multipartBuilder = MultipartEntityBuilder.create();
        if (params==null){
        	return multipartBuilder;
        }
        
        Iterator<Object> it = params.keySet().iterator();
        while (it.hasNext()){
            Object key = it.next();
            Object item = params.get(key);
            
            if (item instanceof File){
                File file = (File) item;
                FileBody filebody = new FileBody(file);
                multipartBuilder.addPart(key+"", filebody);
            }else if (item instanceof List){
                for (Object obj:(List)item){
                    if (obj instanceof File){
                        File file = (File) obj;
                        FileBody filebody = new FileBody(file);
                        multipartBuilder.addPart(key+"", filebody);
                    }else{
                        ContentBody comment = new StringBody(obj+"",contentType);
                        multipartBuilder.addPart(key+"",comment);
                    }
                }
            }else{
                ContentBody comment = new StringBody(item+"",contentType);
                multipartBuilder.addPart(key+"",comment);
            }
        }
        
        return multipartBuilder;
    }
    
}