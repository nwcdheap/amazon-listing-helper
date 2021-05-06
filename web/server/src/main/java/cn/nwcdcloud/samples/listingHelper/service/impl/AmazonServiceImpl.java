package cn.nwcdcloud.samples.listingHelper.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.nwcdcloud.commons.http.HttpPost;
import cn.nwcdcloud.commons.lang.Result;
import cn.nwcdcloud.samples.listingHelper.service.AmazonService;

@Service
public class AmazonServiceImpl implements AmazonService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Value("${amazon.product.uri}")
	private String productUri;
	@Value("${amazon.afn.uri}")
	private String afnUri;

	@Override
	public Result getProduct(String id) {
		Result result = new Result();
		// 创建HttpClient
		CloseableHttpClient httpClient = HttpClients.createDefault();

		// 目标网址
		String url = String.format("https://www.amazon.com/gp/product/%s/ref=silver_xx_cont_revecalc", id);

		// 创建请求方法
		HttpGet httpGet = new HttpGet(url);

		// 设置Header模拟浏览器行为
		httpGet.setHeader("User-Agent",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 11_1_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");

		httpGet.addHeader("Cookie",
				"ubid-main=134-0374546-0715727;  x-main=\"DMkUzMa9a3O2I3wY@wEZkD1baDh6FEsRujawQ@GDKJ7WXANk5?Q9p?o5ZeBe4ihr\"; ");
		try {
			// 发送请求，收取响应
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == HttpServletResponse.SC_OK) {
				Map<String, String> mapData = new HashMap<>();
				String entity = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
				Document document = Jsoup.parse(entity, "utf-8");
				String price = "";
				if (document.getElementById("priceblock_ourprice") != null) {
					price = document.getElementById("priceblock_ourprice").text();
				}
				mapData.put("price", price);
				String review = "";
				if (document.getElementById("acrCustomerReviewText") != null) {
					review = document.getElementById("acrCustomerReviewText").text();
				}
				mapData.put("review", review);
				mapData.put("star", document.getElementsByClass("a-icon-alt").first().text());
				mapData.put("asin", id);
				result.setData(mapData);
			} else {
				result.setCode(2);
				result.setMsg("查询商品信息出错");
			}
			httpClient.close();
		} catch (IOException e) {
			logger.warn("查询商品信息出错", e);
			result.setCode(10);
			result.setMsg("查询商品信息出错");
		}
		return result;
	}

	@Override
	public String productmatches(String content) {
		StringBuffer sbUrl = new StringBuffer();
		sbUrl.append(productUri).append("?").append(content).append("&profitcalcToken=123");
		cn.nwcdcloud.commons.http.HttpGet httpGet = new cn.nwcdcloud.commons.http.HttpGet(sbUrl.toString());
		String result = httpGet.execute();
		logger.debug(result);
		return result;
	}

	@Override
	public String getafnfee(String content) {
		StringBuffer sbUrl = new StringBuffer();
		sbUrl.append(afnUri).append("?profitcalcToken=123");
		HttpPost httpPost = new HttpPost(sbUrl.toString(), content);
		String result = httpPost.execute();
		logger.debug(result);
		return result;
	}
}
