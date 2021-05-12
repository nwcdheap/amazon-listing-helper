package cn.nwcdcloud.samples.listingHelper.service.impl;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.nwcdcloud.commons.lang.Result;
import cn.nwcdcloud.samples.listingHelper.service.ImageService;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import software.amazon.awssdk.utils.StringUtils;

@Service
public class EbayImageServiceImpl implements ImageService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Value("${recognitionType}")
	private int recognitionType;
	@Value("${secret.ebay.name}")
	private String secretId;
	@Value("${secret.ebay.clientId}")
	private String clientId;
	@Value("${secret.ebay.clientSecret}")
	private String clientSecret;
	private String authorization;
	private String accessToken;

	@PostConstruct
	public void init() {
		if (recognitionType != 2) {
			return;
		}
		try {
			if (StringUtils.isBlank(clientId) || StringUtils.isBlank(clientSecret)) {
				// 在SecretsManager里创建密钥名称为ebay的密钥(其他类别)，密钥值包含clientId、clientSecret
				SecretsManagerClient secretsManagerClient = SecretsManagerClient.create();
				GetSecretValueRequest valueRequest = GetSecretValueRequest.builder().secretId(secretId).build();
				GetSecretValueResponse valueResponse = secretsManagerClient.getSecretValue(valueRequest);
				String secret = valueResponse.secretString();
				com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(secret);
				clientId = json.getString("clientId");
				clientSecret = json.getString("clientSecret");
			}
			String idAndSecret = clientId + ":" + clientSecret;
			authorization = "Basic " + Base64.encodeBase64String(idAndSecret.getBytes());
			getAccessTokenFromServer();
		} catch (Exception e) {
			logger.warn("配置eBay图片识别报错", e);
		}
	}

	private void getAccessTokenFromServer() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String url = "https://api.ebay.com/identity/v1/oauth2/token";
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		httpPost.addHeader("Authorization", authorization);
		try {
			String postContent = "grant_type=client_credentials&scope=https%3A%2F%2Fapi.ebay.com%2Foauth%2Fapi_scope";
			httpPost.setEntity(new StringEntity(postContent, "UTF-8"));
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
			String entity = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
			logger.debug("请求eBay Access Token结果:{}", entity);
			if (httpResponse.getStatusLine().getStatusCode() == HttpServletResponse.SC_OK) {
				JSONObject jsonResult = JSONObject.parseObject(entity);
				accessToken = jsonResult.getString("access_token");
			}
			httpClient.close();
		} catch (IOException e) {
			logger.warn("获取eBay Access Token出错", e);
		}
	}

	@Override
	public Result detect(byte[] image) {
		Result result = new Result();

		CloseableHttpClient httpClient = HttpClients.createDefault();
		String url = "https://api.ebay.com/buy/browse/v1/item_summary/search_by_image?&limit=3&sort=-price";
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "application/json");
		httpPost.addHeader("Authorization", "Bearer " + accessToken);
		try {
			JSONObject jsonContent = new JSONObject();
			String imageEncode = Base64.encodeBase64String(image);
			jsonContent.put("image", imageEncode);
			String postContent = jsonContent.toString();
			httpPost.setEntity(new StringEntity(postContent, "UTF-8"));
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
			String entity = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
			logger.debug("请求eBay searchByImage结果:{}", entity);
			if (httpResponse.getStatusLine().getStatusCode() == HttpServletResponse.SC_OK) {
				JSONObject jsonResult = JSONObject.parseObject(entity);
				String data = jsonResult.getJSONArray("itemSummaries").getJSONObject(1).getString("title");
				logger.info("获取到eBay 第一个data:{}", data);
				result.setData(data);
			}
			httpClient.close();
		} catch (IOException e) {
			logger.warn("获取eBay Access Token出错", e);
		}
		return result;
	}
}
