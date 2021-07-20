package cn.nwcdcloud.samples.listingHelper;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSearch {

	private static final Logger logger = LoggerFactory.getLogger(TestSearch.class);

	public static void main(String[] args) {
		logger.info(search());
	}
	protected static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
	private static String search() {
		String url = "https://sellercentral.amazon.com/fba/profitabilitycalculator/productmatches?searchKey=table&searchType=keyword&language=en_US&profitcalcToken="+getUUID();
//		String url="https://www.nowfox.com/article/6013";
//		String url="http://127.0.0.1/test";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		//浏览器cookie
		httpGet.setHeader("cookie", "");
		httpGet.setHeader("host", "sellercentral.amazon.com");
		httpGet.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36");
		try {
			// 发送请求，收取响应
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
			String entity = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
			logger.info("状态码:{}", httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() >= HttpServletResponse.SC_OK
					&& httpResponse.getStatusLine().getStatusCode() < HttpServletResponse.SC_MULTIPLE_CHOICES) {
				if (logger.isDebugEnabled()) {
					logger.debug("查询结果:{}", entity);
				}
				return entity;
			} else {
				logger.warn("请求Amazon报错，url:{},结果{}", url, entity);
				return "";
			}
		} catch (IOException e) {
			return "";
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				logger.warn("关闭流报错", e);
			}
		}
	}

}
