package cn.nwcdcloud.commons.http;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.StringEntity;
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
public class HttpPost {
	private static final Logger logger = LoggerFactory.getLogger(HttpPost.class);
	String URL;
	String postContent;
	int time = 1;

	public HttpPost(String URL, String postContent) {
		this.URL = URL;
		this.postContent = postContent;
	}

	public String execute() {
		CloseableHttpClient httpClient = (CloseableHttpClient) HttpClientUtils.getHttpClient();
		org.apache.http.client.methods.HttpPost httpPost = new org.apache.http.client.methods.HttpPost(URL);
		try {
			httpPost.setEntity(new StringEntity(postContent, "UTF-8"));
			CloseableHttpResponse responseServer = httpClient.execute(httpPost);
			int status = responseServer.getStatusLine().getStatusCode();
			HttpEntity entity = responseServer.getEntity();
			String sResult = EntityUtils.toString(entity, "UTF-8");

			if (logger.isDebugEnabled()) {
				logger.debug("发送HTTP post请求，url:" + URL.toString() + ",postContent:" + postContent + ",status:" + status
						+ ",result:" + sResult);
			}
			if (status >= HttpServletResponse.SC_OK && status < HttpServletResponse.SC_MULTIPLE_CHOICES) {
				return sResult;
			} else {
				return null;
			}
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
