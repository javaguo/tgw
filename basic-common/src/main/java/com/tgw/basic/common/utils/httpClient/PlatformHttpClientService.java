package com.tgw.basic.common.utils.httpClient;

import com.tgw.basic.common.utils.json.PlatformJsonUtils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PlatformHttpClientService {
    private static Logger log= Logger.getLogger(PlatformHttpClientService.class);

    @Autowired
    private CloseableHttpClient httpClient;
    @Autowired
    private RequestConfig requestConfig;

    /**
     * 通过给的url地址，获取服务器数据
     * @param url 服务器地址
     * @param urlParamMap 封装用户参数
     * @param charset 设定字符编码
     * @return
     */
    public String doGet(String url, Map<String,String> urlParamMap, String charset) {
        if (StringUtils.isEmpty(charset)) {
            charset = "utf-8";
        }

        url = joinUrlParam(url,urlParamMap);

        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        String result = "";
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity(),charset);
            }else {
                log.warn("get request fail! StatusCode: "+response.getStatusLine().getStatusCode()+" ,url: "+url+" ,urlParamMap: "+ PlatformJsonUtils.toJsonString(urlParamMap));
            }
        } catch (Exception e) {
            log.error("get request error! url: "+url+" ,urlParamMap: "+ PlatformJsonUtils.toJsonString(urlParamMap),e);
        }
        return result;
    }

    public String doGet(String url, Map<String,String> params) {
            return doGet(url,params,null);
    }

    public String doGet(String url) {
        return doGet(url,null,null);
    }

    /**
     * 带参数的post请求
     *
     * @param url
     * @param urlParamMap
     * @param bodyData
     * @return
     * @throws Exception
     */
    public String doPostBody(String url, Map<String,String> urlParamMap, String bodyData, String charset) {
        if (StringUtils.isEmpty(charset)) {
            charset = "utf-8";
        }

        url = joinUrlParam(url,urlParamMap);

        String result = "";
        try {
            // 声明httpPost请求
            HttpPost httpPost = new HttpPost(url);
            // 加入配置信息
            httpPost.setConfig(requestConfig);

            httpPost.setEntity(new StringEntity(bodyData, Consts.UTF_8));

            // 发起请求
            CloseableHttpResponse response = this.httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity(),charset);
            }else {
                log.warn("post body fail! StatusCode: "+response.getStatusLine().getStatusCode()+" ,url: "+url+" ,urlParamMap: "+ PlatformJsonUtils.toJsonString(urlParamMap)+" ,bodyData: "+bodyData);
            }
        } catch (Exception e) {
            log.error("post body error! url: "+url+" ,urlParamMap: "+ PlatformJsonUtils.toJsonString(urlParamMap)+" ,bodyData: "+bodyData,e);
            throw new RuntimeException(e);
        }
        return result;
    }

    public String doPostBody(String url, Map<String,String> urlParamMap, String bodyData) {
        return this.doPostBody(url,urlParamMap, bodyData,null);
    }

    /**
     * 带参数的post请求
     *
     * @param url
     * @param formParamMap
     * @return
     * @throws Exception
     */
    public String doPostForm(String url, Map<String,String> urlParamMap, Map<String,Object> formParamMap, String charset) {
        if (StringUtils.isEmpty(charset)) {
            charset = "utf-8";
        }

        url = joinUrlParam(url,urlParamMap);

        String result = "";
        try {
            // 声明httpPost请求
            HttpPost httpPost = new HttpPost(url);
            // 加入配置信息
            httpPost.setConfig(requestConfig);

            // 判断map是否为空，不为空则进行遍历，封装from表单对象
            if (formParamMap != null) {
                List<NameValuePair> list = new ArrayList<NameValuePair>();
                for (Map.Entry<String, Object> entry : formParamMap.entrySet()) {
                    list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                }
                // 构造from表单对象
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, charset);

                // 把表单放到post里
                httpPost.setEntity(urlEncodedFormEntity);
            }

            // 发起请求
            CloseableHttpResponse response = this.httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity(),charset);
            }else {
                log.warn("post form fail! StatusCode: "+response.getStatusLine().getStatusCode()+" ,url: "+url+" ,urlParamMap: "+ PlatformJsonUtils.toJsonString(urlParamMap)+" ,formParamMap: "+ PlatformJsonUtils.toJsonString(formParamMap));
            }
        } catch (Exception e) {
            log.error("post form error! url: "+url+" ,urlParamMap: "+ PlatformJsonUtils.toJsonString(urlParamMap)+" ,formParamMap: "+ PlatformJsonUtils.toJsonString(formParamMap),e);
            throw new RuntimeException(e);
        }
        return result;
    }

    private String joinUrlParam (String url ,Map<String,String> urlParamMap){
        if (!CollectionUtils.isEmpty(urlParamMap)) {
            StringBuilder newUrl = new StringBuilder();
            newUrl.append(url).append("?");
            for (Map.Entry<String,String> entry: urlParamMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                newUrl.append(key).append("=").append(value).append("&");
            }

            url = newUrl.substring(0,newUrl.length()-1);
        }

        return url;
    }
}
