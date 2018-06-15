package com.mrbt.lingmoney.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;


/**
 * 作用：Https 发送POST 请求
 * 
 * @author sumylu@globex.cn
 * @date 2016年6月22日
 */
public class HttpsRequest{
	
	public static void main(String[] args) {
		try {
			new HttpsRequest().sendPost("https://223.223.179.205:8443/bank/accountRechargeCallBack/aaaaaaa", "aaaaaaaaaaaaaaa");
		} catch (UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException | KeyStoreException
				| IOException e) {
			e.printStackTrace();
		}
	}


    private Logger logger = LoggerFactory.getLogger(HttpsRequest.class);

    private static final String CERT_PATH = "D:/Maven/ghb.ssl.dep.jks";

    private String charset_gbk = "GBK";
    /**
     * 表示请求器是否已经做了初始化工作
     */
    private boolean hasInit = false;

    /**
     * 连接超时时间，默认10秒
     */
    private int socketTimeout = 10000;

    /**
     * 传输超时时间，默认30秒
     */
    private int connectTimeout = 30000;

    // 请求器的配置
    private RequestConfig requestConfig;

    // HTTP请求器
    private CloseableHttpClient httpClient;

    public HttpsRequest() throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException,
            KeyStoreException, IOException {
        init();
    }

    private void init() throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException,
            KeyManagementException {

        // Init keyStore
        KeyStore keyStore = KeyStore.getInstance("jks");// PKCS12、jks 等
        FileInputStream instream = new FileInputStream(new File(CERT_PATH));// 加载本地的证书进行https加密传输
        try {
            keyStore.load(instream, "wdzg2017".toCharArray());// 设置证书密码
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            instream.close();
        }

        // Init key manager factory
        /*
         * KeyManagerFactory kmf =
         * KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
         * kmf.init(keyStore, "12345678".toCharArray()); KeyManager[] km = kmf.getKeyManagers();
         * 
         * // Init trust manager TrustManagerFactory tmf =
         * TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
         * tmf.init(keyStore); TrustManager[] tms = tmf.getTrustManagers();
         */
        // Trust own CA and all self-signed certs, SSL protocol
        SSLContext sslcontext =
                SSLContexts.custom().useSSL().loadKeyMaterial(keyStore, "wdzg2017".toCharArray())
                        .loadTrustMaterial(keyStore).build();

        // sslcontext.init(km, tms, null);

        SSLConnectionSocketFactory sslsf =
                new SSLConnectionSocketFactory(sslcontext, null, null,
                        SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

        httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

        // 根据默认超时限制初始化requestConfig
        requestConfig =
                RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();

        hasInit = true;
    }

    public String sendPost(String url, Object xmlObj) throws UnrecoverableKeyException, KeyManagementException,
            NoSuchAlgorithmException, KeyStoreException, IOException {
        if (!hasInit) {
            init();
        }

        String result = null;

        HttpPost httpPost = new HttpPost(url);

        // 解决XStream对出现双下划线的bug
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));

        // 将要提交给API的数据对象转换成XML格式数据Post给API
        String postDataXML = xStreamForRequestPostData.toXML(xmlObj);

        logger.info("API,POST请求参数：{}", postDataXML);

        // 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(postDataXML, charset_gbk);
        // httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);

        // 设置请求器的配置
        httpPost.setConfig(requestConfig);

        logger.info("Request line:{}", httpPost.getRequestLine());

        try {
            HttpResponse response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();

            result = EntityUtils.toString(entity, charset_gbk);

        } catch (ConnectionPoolTimeoutException e) {
            logger.error("http get throw ConnectionPoolTimeoutException(wait time out)");

        } catch (ConnectTimeoutException e) {
            logger.error("http get throw ConnectTimeoutException");

        } catch (SocketTimeoutException e) {
            logger.error("http get throw SocketTimeoutException");

        } catch (Exception e) {
            logger.error("http get throw Exception");

        } finally {
            httpPost.abort();
        }

        return result;
    }

    private void resetRequestConfig() {
        requestConfig =
                RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
    }

    /**
     * @return the hasInit
     */
    public boolean isHasInit() {
        return hasInit;
    }

    /**
     * @return the socketTimeout
     */
    public int getSocketTimeout() {
        return socketTimeout;
    }

    /**
     * @return the connectTimeout
     */
    public int getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * @param hasInit the hasInit to set
     */
    public void setHasInit(boolean hasInit) {
        this.hasInit = hasInit;
    }

    /**
     * @param socketTimeout the socketTimeout to set
     */
    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
        resetRequestConfig();
    }

    /**
     * @param connectTimeout the connectTimeout to set
     */
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        resetRequestConfig();
    }

    /**
     * @return the requestConfig
     */
    public RequestConfig getRequestConfig() {
        return requestConfig;
    }

    /**
     * @param requestConfig the requestConfig to set
     */
    public void setRequestConfig(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
    }

}

