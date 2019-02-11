package com.kdDemo.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.kdDemo.utils.Base64;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Nan 2015-11
 */
public class HttpClientUtil {
	private static PoolingHttpClientConnectionManager cm;
	private static String EMPTY_STR = "";
	private static String UTF_8 = "UTF-8";

	private static void init() {
		if (cm == null) {
			cm = new PoolingHttpClientConnectionManager();
			cm.setMaxTotal(50);// 整个连接池最大连接数
			cm.setDefaultMaxPerRoute(5);// 每路由最大连接数，默认�?�是2
		}
	}

	/**
	 * 通过连接池获取HttpClient
	 *
	 * @return
	 */
	private static CloseableHttpClient getHttpClient() {
		init();
		return HttpClients.custom().setConnectionManager(cm).build();
	}

	/**
	 *
	 * @param url
	 * @return
	 */
	public static String httpGetRequest(String url) {
		HttpGet httpGet = new HttpGet(url);
		return getResult(httpGet);
	}

	public static String httpGetRequest(String url, Map<String, Object> params) throws URISyntaxException {
		URIBuilder ub = new URIBuilder();
		ub.setPath(url);

		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		ub.setParameters(pairs);

		HttpGet httpGet = new HttpGet(ub.build());
		return getResult(httpGet);
	}

	public static String httpGetRequest(String url, Map<String, Object> headers, Map<String, Object> params)
			throws URISyntaxException {
		URIBuilder ub = new URIBuilder();
		ub.setPath(url);

		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		ub.setParameters(pairs);

		HttpGet httpGet = new HttpGet(ub.build());
		for (Map.Entry<String, Object> param : headers.entrySet()) {
			httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
		}
		return getResult(httpGet);
	}

	public static String httpPostRequest(String url) {
		HttpPost httpPost = new HttpPost(url);
		return getResult(httpPost);
	}

	public static String httpPostRequest(String url, Map<String, Object> params,String refer) throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(url);
		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		httpPost.setHeader("Referer", refer);
		httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));
		return getResult(httpPost);
	}

	public static String httpPostRequestArray(String url, List<BasicNameValuePair> params)
			throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new UrlEncodedFormEntity(params, UTF_8));
		return getResult(httpPost);
	}

	public static String httpPostRequest(String url, Map<String, Object> headers, Map<String, Object> params)
			throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(url);

		for (Map.Entry<String, Object> param : headers.entrySet()) {
			httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
		}

		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));

		return getResult(httpPost);
	}

	private static ArrayList<NameValuePair> covertParams2NVPS(Map<String, Object> params) {
		ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
		for (Map.Entry<String, Object> param : params.entrySet()) {
			pairs.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
		}

		return pairs;
	}

	/**
	 * 处理Http请求
	 *
	 * @param request
	 * @return
	 */
	private static String getResult(HttpRequestBase request) {
		CloseableHttpClient httpClient = getHttpClient();
		try {
			CloseableHttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity);
				response.close();
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}

		return EMPTY_STR;
	}

	public static Map<String, Object> doPost(Map<String, String> paramMap, String refer,String url) throws IOException {
		CloseableHttpResponse response = null;
		CloseableHttpClient httpclient = null;
		try {
			httpclient = HttpClients.createDefault();
			Builder builder = RequestConfig.custom();
			builder.setSocketTimeout(60000);// 设置请求超时时间
			builder.setConnectTimeout(60000);// 设置传输超时时间
			RequestConfig requestConfig = builder.build();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);
			System.out.println("===================Referer:");
			httpPost.setHeader("Referer", refer);
			List<NameValuePair> params = generateParam(paramMap);
			System.out.println("===================params " + Base64.decode(params.toString()));
			if (params.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
				httpPost.setEntity(entity);
				response = httpclient.execute(httpPost);
				System.out.println("response:" + response);
				if (null != response) {
					Map<String, Object> map = new HashMap<String, Object>();
					if (response.getLastHeader("Location") != null) {
						map.put("Location", response.getLastHeader("Location").getValue());
					}
					map.put("headers", response.getAllHeaders());
					return map;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				response.close();
			}
			if (httpclient != null) {
				httpclient.close();
			}
		}
		return null;
	}

	public static List<NameValuePair> generateParam(Map<String, String> paramMap) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (String key : paramMap.keySet()) {
			params.add(new BasicNameValuePair(key, paramMap.get(key)));
		}
		return params;
	}
}