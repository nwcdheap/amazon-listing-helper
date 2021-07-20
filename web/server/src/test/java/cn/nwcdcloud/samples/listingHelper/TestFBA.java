package cn.nwcdcloud.samples.listingHelper;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFBA {

	private static final Logger logger = LoggerFactory.getLogger(TestFBA.class);

	public static void main(String[] args) {
		logger.info(search());
	}
	protected static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
	private static String search() {
		String url = "https://sellercentral.amazon.com/fba/profitabilitycalculator/getafnfee?profitcalcToken="+getUUID();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		//浏览器cookie
		httpPost.setHeader("cookie", "");
		httpPost.setHeader("host", "sellercentral.amazon.com");
		httpPost.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36");
		try {
			httpPost.setEntity(new StringEntity("{\"afnPriceStr\":\"40.2\",\"currency\":\"USD\",\"futureFeeDate\":\"2021-06-30 15:49:13\",\"hasFutureFee\":false,\"hasTaxPage\":true,\"marketPlaceId\":\"ATVPDKIKX0DER\",\"mfnPriceStr\":0,\"mfnShippingPriceStr\":0,\"productInfoMapping\":{\"isWhiteGloveRequired\":false,\"weightUnitString\":\"pounds\",\"subCategory\":\"\",\"fnsku\":\"\",\"dimensionUnit\":\"inches\",\"link\":\"http://www.amazon.com/gp/product/B08RLMQ74C/ref=silver_xx_cont_revecalc\",\"binding\":\"sports\",\"title\":\"YETI Rambler 14 oz Mug, Vacuum Insulated, Stainless Steel with MagSlider Lid, Navy\",\"dimensionUnitString\":\"inches\",\"price\":0,\"imageUrl\":\"https://m.media-amazon.com/images/I/41zD7BJljAL._SL120_.jpg\",\"height\":5.39,\"isAfn\":false,\"gl\":\"gl_outdoors\",\"TRexId\":\"\",\"length\":4.37,\"isAsinLimits\":true,\"weight\":1.0406,\"originalUrl\":\"\",\"productGroup\":\"\",\"width\":4.61,\"thumbStringUrl\":\"https://m.media-amazon.com/images/I/41zD7BJljAL._SL120_SL80_.jpg\",\"asin\":\"B08RLMQ74C\",\"encryptedMarketplaceId\":\"\",\"weightUnit\":\"pounds\"}}"));
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
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
