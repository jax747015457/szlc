package com.juhe;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
*身份证实名认证调用示例代码 － 聚合数据
*在线接口文档：http://www.juhe.cn/docs/103
**/
 public class JuheApi {
    private final static Logger log = LoggerFactory.getLogger(JuheApi.class);

    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
    public static final String strUrl = "http://op.juhe.cn/idcard/query";
    //配置您申请的KEY
    public static final String APPKEY ="97dedbaeea6b1920c35d59c225b1805c";


    /**
     * 身份证验证(通过/验证失败)
     */
    public static boolean idCard(String realname, String idcard) {
//        http://op.juhe.cn/idcard/query?key=您申请的KEY&idcard=420104198905015713&realname=%E7%8E%8B%E9%9D%9E%E5%90%9F
//        {"reason":"请求身份证号不标准：身份证号为空或者不符合身份证校验规范","result":null,"error_code":210304}
//        {"reason":"库中无此身份证记录","result":null,"error_code":210301}
//        {"resultcode":"101","reason":"错误的请求KEY!","result":null,"error_code":10001}
//        {"reason":"成功 ","result":{"realname":"XXXX","idcard":"XXXXXXXXXXXX","res":1},"error_code":0}
//        {reason=成功, result={realname=XXXXX, idcard=XXXXXXXXXXXXXX, res=1.0}, error_code=0.0}
        try {
            // 封装请求参数
            Map<String, String> map = new HashMap<>();
            map.put("key", APPKEY);
            map.put("realname", realname);
            map.put("idcard", idcard);

            Map<String, Object> res = new Gson().fromJson(net(strUrl, map, "GET"), Map.class);
            if (res != null && (res.get("reason")+"").indexOf("成功") != -1) {
                return true;
            } else {
                log.error("身份验证失败！" + res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
 
    /**
     *
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return  网络请求字符串
     * @throws Exception
     */
    public static String net(String strUrl, Map params,String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null || method.equals("GET")){
                strUrl = strUrl+"?"+urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params!= null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                        out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }
 
    //将map型转为请求参数型
    public static String urlencode(Map<String,Object>data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}