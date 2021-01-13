package cn.nwcdcloud.commons.http;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 封装了下HttpClient<br>
 * 正常返回内容，失败返回null
 * 
 * @author zhangjzh
 * 
 */
public class HttpGet {
	private static final Logger logger = LoggerFactory.getLogger(HttpGet.class);
	String URL;
	int time = 1;

	public HttpGet(String URL) {
		this.URL = URL;
	}

	public String execute() {
		CloseableHttpClient httpClient = (CloseableHttpClient) HttpClientUtils.getHttpClient();
		HttpRequestBase httpRequest = new org.apache.http.client.methods.HttpGet(URL);
		// httpRequest.setConfig(HttpClientUtils.getRequestConfig());
		try {
			CloseableHttpResponse responseServer;
			responseServer = httpClient.execute(httpRequest);
			int status = responseServer.getStatusLine().getStatusCode();
			HttpEntity entity = responseServer.getEntity();
			String sResult = EntityUtils.toString(entity, "UTF-8");
			if (logger.isDebugEnabled()) {
				logger.debug("发送HTTP get请求，url:" + URL.toString() + ",status:" + status + ",result:" + sResult);
			}
			if (status >= HttpServletResponse.SC_OK && status < HttpServletResponse.SC_MULTIPLE_CHOICES) {
				return sResult;
			} else
				return null;
		} catch (NoHttpResponseException e) {
			if (time > 3) {
				logger.warn("请求URL时NoHttpResponseException：{},time:{}", URL, time);
				return null;
			}
			logger.info("请求URL时NoHttpResponseException：{},time:{}", URL, time);
			time++;
			return execute();
		} catch (Exception e) {
			logger.warn("请求URL时失败：" + URL, e);
			return null;
		} finally {
			// 使用连接池后，这里不能关闭
			// httpClient.close();
		}
	}
}
