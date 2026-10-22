/**
 * @title UrlUtils.java
 * @package commons.tools
 * @description TODO
 * @author junqing.cao
 * @date 2014-10-20 下午1:55:49
 * @version 1.0.0
 * @company Live By Touch
 */
package com.suomei.payment.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.suomei.payment.common.constant.ConstantParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {
    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    public static HttpUtils httpUtils = new HttpUtils();

    /**
     * 普通请求超时配置(60秒)
     */
    private static RequestConfig config;

    /**
     * 星云请求超时配置(20秒)
     */
    private static RequestConfig nebulaConfig;


    /**
     * 百度API请求超时配置(30秒)
     */
    private static RequestConfig baiDuApiConfig;


    static {
        // == 将请求超时时间60s 修改为20s
        config = RequestConfig.custom().setConnectTimeout(20000)
                .setSocketTimeout(20000).build();

        baiDuApiConfig = RequestConfig.custom().setConnectTimeout(30000)
                .setSocketTimeout(30000).build();

        nebulaConfig = RequestConfig.custom().setConnectTimeout(20000)
                .setSocketTimeout(20000).build();
    }

    /**
     *
     * @Title get
     * @Description GET方式获取远程数据
     * @param url
     * @param params 以&拼接的参数
     * @return
     */
    public static String getNebula(String url, String params) {
        return getNebula(url, params, ConstantParams.CHARSET);
    }


    /**
     *
     * @Title get
     * @Description GET方式获取远程数据
     * @param url
     * @param params 以&拼接的参数
     * @param charset
     * @return
     */
    public static String getNebula(String url, String params, String charset) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String result = "";

        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(nebulaConfig).build();

        String toUrl = url + (url.indexOf("?") > 0 ? "&" : "?") + params;

        log.info("GET请求:{}",toUrl);

        HttpGet httpget = new HttpGet(toUrl);


        try {
            response = httpClient.execute(httpget);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpget.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            entity = response.getEntity();

            if (entity != null) {
                result = EntityUtils.toString(entity, charset);
            }
            EntityUtils.consume(entity);
        } catch (ClientProtocolException e) {
            log.error("请求错误",e);
        } catch (IOException e) {
            log.error("读写错误",e);
        } finally {
            if (response != null) {
                log.debug("关闭CloseableHttpResponse");
                try {
                    response.close();
                } catch (IOException e) {
                    log.error("关闭CloseableHttpResponse错误", e);
                }
            }

            if (httpClient != null) {
                log.debug("关闭CloseableHttpClient");
                try {
                    httpClient.close();
                } catch (IOException e) {
                    log.error("关闭CloseableHttpClient错误", e);
                }
            }
        }

        return result;
    }


    /**
     *
     * @Title get
     * @Description GET方式获取远程数据
     * @param url
     * @param params 以&拼接的参数
     * @return
     */
    public static String get2(String url, String params) {
    	try {
			return get2(url, params, ConstantParams.CHARSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    		return null;
    }


    /**
     * 百度定位API专用
     * @Title get
     * @Description GET方式获取远程数据
     * @param url
     * @param params 以&拼接的参数
     * @return
     */
    public static String get3(String url, String params) {
    		return get3(url, params, ConstantParams.CHARSET);
    }


    /**
     *
     * @Title get
     * @Description GET方式获取远程数据
     * @param url
     * @param params 以&拼接的参数
     * @return
     */
    public static String get(String url, String params) {
        return get(url, params, ConstantParams.CHARSET);
    }

    /**
     *
     * @Title get
     * @Description GET方式获取远程数据
     * @param url
     * @param params 以&拼接的参数
     * @param charset
     * @return
     */
    public static String get(String url, String params, String charset) {
        //埋点参数
        long startTime = System.currentTimeMillis();
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String result = "";

        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

        String toUrl = url + (url.indexOf("?") > 0 ? "&" : "?") + params;

        log.info("GET请求:{}",toUrl);

        HttpGet httpget = new HttpGet(toUrl);


        try {
            response = httpClient.execute(httpget);
            log.info("response:{}",JSON.toJSONString(response));
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpget.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            entity = response.getEntity();

            if (entity != null) {
                result = EntityUtils.toString(entity, charset);
            }
            EntityUtils.consume(entity);
        } catch (ClientProtocolException e) {
            log.error("请求错误",e);
        } catch (IOException e) {
            log.error("读写错误",e);
        } finally {
            if (response != null) {
                log.debug("关闭CloseableHttpResponse");
                try {
                    response.close();
                } catch (IOException e) {
                    log.error("关闭CloseableHttpResponse错误", e);
                }
            }

            if (httpClient != null) {
                log.debug("关闭CloseableHttpClient");
                try {
                    httpClient.close();
                } catch (IOException e) {
                    log.error("关闭CloseableHttpClient错误", e);
                }
            }
        }

        return result;
    }

    /**
     *
     * @Title get
     * @Description GET方式获取远程数据
     * @param url
     * @param params 以&拼接的参数
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String get2(String url, String params, String charset) throws UnsupportedEncodingException {
        //埋点参数
        long startTime = System.currentTimeMillis();
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String result = "";

        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        params = new String(params.getBytes(), "utf-8");
        log.debug("params:"+params);
        String toUrl = url + (url.indexOf("?") > 0 ? "&" : "?") + params;
        log.info("toUrl:"+toUrl);

        HttpGet httpget = new HttpGet(toUrl);
        httpget.addHeader("Content-Type", "text/html;charset=utf-8");

        try {
            response = httpClient.execute(httpget);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpget.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            entity = response.getEntity();

            if (entity != null) {
                result = EntityUtils.toString(entity, charset);

            }
            EntityUtils.consume(entity);
        } catch (ClientProtocolException e) {
            log.error("请求错误",e);
        } catch (IOException e) {
            log.error("读写错误",e);
        } finally {

            if (response != null) {
                log.debug("关闭CloseableHttpResponse");
                try {
                    response.close();
                } catch (IOException e) {
                    log.error("关闭CloseableHttpResponse错误", e);
                }
            }

            if (httpClient != null) {
                log.debug("关闭CloseableHttpClient");
                try {
                    httpClient.close();
                } catch (IOException e) {
                    log.error("关闭CloseableHttpClient错误", e);
                }
            }
        }

        return result;
    }



    /**
     *
     * @Title get
     * @Description GET方式获取远程数据
     * @param url
     * @param params 以&拼接的参数
     * @param charset
     * @return
     */
    public static String get3(String url, String params, String charset) {
        //埋点参数
        long startTime = System.currentTimeMillis();
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String result = "";

        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(baiDuApiConfig).build();

        String toUrl = url + (url.indexOf("?") > 0 ? "&" : "?") + params;

        log.info("GET请求:{}",toUrl);

        HttpGet httpget = new HttpGet(toUrl);


        try {
            response = httpClient.execute(httpget);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpget.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            entity = response.getEntity();

            if (entity != null) {
                result = EntityUtils.toString(entity, charset);
            }
            EntityUtils.consume(entity);
        } catch (ClientProtocolException e) {
            log.error("请求错误",e);
        } catch (IOException e) {
            log.error("读写错误",e);
        } finally {

            if (response != null) {
                log.debug("关闭CloseableHttpResponse");
                try {
                    response.close();
                } catch (IOException e) {
                    log.error("关闭CloseableHttpResponse错误", e);
                }
            }

            if (httpClient != null) {
                log.debug("关闭CloseableHttpClient");
                try {
                    httpClient.close();
                } catch (IOException e) {
                    log.warn("关闭CloseableHttpClient错误", e);
                }
            }
        }
        return result;
    }


    /**
     * POST方式提交数据
     * @param url url地址
     * @param ps 参数MAP
     * @return
     *
     * @author Joakim
     * @date 2014-10-30
     * @version 1.0.0
     */
    public static String post(String url, Map<String, String> ps) {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(8000).setSocketTimeout(5000).build();
        //埋点参数
        long startTime = System.currentTimeMillis();
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        List<NameValuePair> nvps = null;
        HttpPost httpPost = null;
        HttpEntity entity = null;
        String result = "";

      try {
          httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

          httpPost = new HttpPost(url);

          if (ps != null) {
              nvps = new ArrayList<NameValuePair>();
              for (String key : ps.keySet()) {
                  nvps.add(new BasicNameValuePair(key, ps.get(key)));
              }

//              UrlEncodedFormEntity ue = new UrlEncodedFormEntity(nvps, CHARSET);
//              readInputStream(ue.getContent());

              httpPost.setEntity(new UrlEncodedFormEntity(nvps, ConstantParams.CHARSET));

          }

          response = httpClient.execute(httpPost);

          int statusCode = response.getStatusLine().getStatusCode();
          if (statusCode != 200) {
              httpPost.abort();
              throw new RuntimeException("HttpClient,error status code :" + statusCode);
          }
          entity = response.getEntity();

          if (entity != null) {
              result = EntityUtils.toString(entity, ConstantParams.CHARSET);
          }
          EntityUtils.consume(entity);

      } catch (Exception e) {
          log.error("请求错误",e);
      } finally {

          if (response != null) {
              try {
                  response.close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }

          if (httpClient != null) {
              try {
                  httpClient.close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return result;
    }


    public static JSONObject tencentPost(String url, JSONObject jsonParam) {
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(8000).setConnectTimeout(5000).build();
        //埋点参数
        long startTime = System.currentTimeMillis();
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        CloseableHttpResponse result = null;
        HttpPost httpPost = new HttpPost(url);
        // 设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        try
        {
            if (null != jsonParam)
            {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }
            result = httpClient.execute(httpPost);
            // 请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                String str = "";
                try
                {
                    // 读取服务器返回过来的json字符串数据
                    str = EntityUtils.toString(result.getEntity(), "utf-8");
                    // 把json字符串转换成json对象
                    jsonResult = JSONObject.parseObject(str);
                }
                catch (Exception e)
                {
                    log.error("post请求提交失败:{}",e.getMessage());
                }
            }
        }
        catch (IOException e)
        {
            log.error("post请求提交失败:{}",e.getMessage());
        }
        finally
        {
            httpPost.releaseConnection();
        }
        return jsonResult;
    }


    @SuppressWarnings("unused")
	private static void readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len = inStream.read(buffer)) !=-1 ){
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();//网页的二进制数据
        outStream.close();
        inStream.close();
        log.info("PARAMS:" + new String(data));
    }

    public static String urlPostData1(String url, Map<String, String> ps) {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(8000).setSocketTimeout(5000).build();
        //埋点参数
        long startTime = System.currentTimeMillis();
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpPost httpPost = null;
        HttpEntity entity = null;
        String result = "";

      try {
          httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

          httpPost = new HttpPost(url);

          if (ps != null) {
              MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
              // 设置为浏览器兼容模式
              entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
              // 设置请求的编码格式
              entityBuilder.setCharset(Charset.forName(ConstantParams.CHARSET));

              for (String key : ps.keySet()) {
                  entityBuilder.addTextBody(key, ps.get(key));
              }

              HttpEntity httpEntity = entityBuilder.build();

              httpPost.setEntity(httpEntity);
          }

          response = httpClient.execute(httpPost);

          int statusCode = response.getStatusLine().getStatusCode();
          if (statusCode != 200) {
              httpPost.abort();
              throw new RuntimeException("HttpClient,error status code :" + statusCode);
          }
          entity = response.getEntity();

          if (entity != null) {
              result = EntityUtils.toString(entity, "UTF-8");
          }
          EntityUtils.consume(entity);

      } catch (Exception e) {
          log.error("异常", e);
      } finally {

          if (response != null) {
              try {
                  response.close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }

          if (httpClient != null) {
              try {
                  httpClient.close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return result;
    }

    /**
     * POST请求
     * @param url 请求地址
     * @param params 参数
     * @return
     * @throws IOException
     *
     * @author Joakim
     * @date 2014-12-12
     * @version 1.0.0
     */
    public static String post(String url, String params) throws IOException {
        return post(url, params, ConstantParams.CHARSET);
    }

    /**
     * POST请求
     * @param url
     * @param params
     * @param charset
     * @return
     * @throws IOException
     *
     * @author Joakim
     * @date 2015-1-29
     * @version 1.0.0
     */
    public static String post(String url, String params, String charset) throws IOException {
        //埋点参数
        long startTime = System.currentTimeMillis();
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String returnVal = "";

        HttpPost method = new HttpPost(url);
//        method.addHeader("Connection", "Keep-Alive");

        method.setEntity(new ByteArrayEntity(params.getBytes(charset)));
        method.addHeader("Content-Type","application/x-www-form-urlencoded;charset=" + charset);

        try {
            httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

            response = httpClient.execute(method);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                return null;
            }
            returnVal = EntityUtils.toString(response.getEntity(), charset);

            EntityUtils.consume(method.getEntity());
        }catch (Exception e) {
        	log.error("post请求报错",e.toString());
            log.error("异常", e);
        } finally {

            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return returnVal;
    }


    /**
     * pos请求，发送json数据
     *
     * @param httpUrl  请求地址
     * @param postJson json参数
     */
    public static String httpPostJson(String httpUrl, String postJson) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        String result = "";
        // 创建httpClient实例
        httpClient = HttpClients.createDefault();
        // 创建httpPost远程连接实例
        HttpPost httpPost = new HttpPost(httpUrl);
        // 为httpPost实例设置配置
        httpPost.setConfig(config);
        // 设置请求头
        httpPost.addHeader("Content-Type", "application/json");
        // 封装post请求参数
        httpPost.setEntity(new StringEntity(postJson, ContentType.create("application/json", "utf-8")));
        try {
            // httpClient对象执行post请求,并返回响应参数对象
            httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容
            if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                result = EntityUtils.toString(httpResponse.getEntity(),Charset.forName("UTF-8"));
//                result = EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 请求发送
     *
     * @param callURL
     * @param postData
     * @return
     */
    public static String send(String callURL, String postData) {
        InputStream in = null;
        HttpURLConnection connection = null;
        BufferedReader data = null;
        try {
            URL url = new URL(callURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);
            connection.setRequestProperty("Content-Type","application/json;charset=UTF-8");
            connection.connect();
            DataOutputStream out = new DataOutputStream( connection.getOutputStream());
            out.write(postData.getBytes("UTF-8"));
            out.flush();
            out.close();
            int rc = connection.getResponseCode();
            // 响应成功
            if (rc == 200) {
                String temp;

                in = connection.getInputStream();
                data = new BufferedReader(new InputStreamReader(
                        in, "utf-8"));
                StringBuffer result = new StringBuffer();
                while ((temp = data.readLine()) != null) {
                    result.append(temp);
                    temp = null;
                }
                return result.toString();
            }else{
                log.warn("http response code : {}",rc);
            }
        }
        catch (Exception e) {
            log.error("http request error!",e);
        }finally {
            if(data != null){
                try {
                    data.close();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                connection.disconnect();
            }
        }
        return null;
    }


}
