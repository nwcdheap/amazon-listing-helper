package cn.nwcdcloud.samples.listingHelper.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.nwcdcloud.commons.lang.Result;
import cn.nwcdcloud.samples.listingHelper.service.AmazonService;

@Service
public class AmazonServiceImpl implements AmazonService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Value("${amazon.product.uri}")
	private String productUri;
	@Value("${amazon.afn.uri}")
	private String afnUri;
	@Value("${cookie}")
	private String cookie;
	@Value("${user.agent}")
	private String userAgent;

	@Override
	public Result getProduct(String id) {
		Result result = new Result();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String url = String.format("https://www.amazon.com/gp/product/%s/ref=silver_xx_cont_revecalc", id);
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("User-Agent", userAgent);
		httpGet.setHeader("Cookie", cookie);
		try {
			// 发送请求，收取响应
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == HttpServletResponse.SC_OK) {
				Map<String, String> mapData = new HashMap<>();
				String entity = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
				Document document = Jsoup.parse(entity, "utf-8");
				String price = "";
				if (document.getElementById("priceblock_ourprice") != null) {
					price = document.getElementById("priceblock_ourprice").text().replace("US$", "");
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

	protected String getUUID() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

	@Override
	public String productmatches(String content) {
		StringBuffer sbUrl = new StringBuffer();
		sbUrl.append(productUri).append("?").append(content).append("&profitcalcToken=").append(getUUID());

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(sbUrl.toString());
		httpGet.setHeader("User-Agent", userAgent);
		httpGet.setHeader("host", "sellercentral.amazon.com");
		httpGet.setHeader("cookie", cookie);
		try {
			// 发送请求，收取响应
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
			String entity = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
			int status = httpResponse.getStatusLine().getStatusCode();
			if (status >= HttpServletResponse.SC_OK && status < HttpServletResponse.SC_MULTIPLE_CHOICES) {
				if (logger.isDebugEnabled()) {
					logger.debug("查询结果:{}", entity);
				}
				return entity;
			} else {
				logger.warn("请求Amazon查询商品报错，url:{}", sbUrl.toString());
//				logger.debug("请求Amazon报错，结果{}", entity);
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

	@Override
	public String getafnfee(String content) {
		StringBuffer sbUrl = new StringBuffer();
		sbUrl.append(afnUri).append("?profitcalcToken=123");

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(sbUrl.toString());
		httpPost.setHeader("User-Agent", userAgent);
		httpPost.setHeader("host", "sellercentral.amazon.com");
		httpPost.setHeader("cookie", cookie);
		try {
			httpPost.setEntity(new StringEntity(content));
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
			String entity = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
			int status = httpResponse.getStatusLine().getStatusCode();
			if (status >= HttpServletResponse.SC_OK && status < HttpServletResponse.SC_MULTIPLE_CHOICES) {
				if (logger.isDebugEnabled()) {
					logger.debug("查询结果:{}", entity);
				}
				return entity;
			} else {
				logger.warn("请求Amazon FBA报错，url:{}", sbUrl.toString());
				logger.warn("请求Amazon FBA报错，", entity);
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
