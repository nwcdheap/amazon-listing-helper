package cn.nwcdcloud.samples.listingHelper.service.impl;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baidu.aip.imageclassify.AipImageClassify;

import cn.nwcdcloud.commons.lang.Result;
import cn.nwcdcloud.samples.listingHelper.service.ImageService;
import cn.nwcdcloud.samples.listingHelper.service.TranslateService;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import software.amazon.awssdk.utils.StringUtils;

@Service
public class BaiduImageServiceImpl implements ImageService {
	@Value("${recognitionType}")
	private int recognitionType;
	@Value("${secret.baiduimage.name}")
	private String secretId;
	@Value("${secret.baiduimage.appId}")
	private String appId;
	@Value("${secret.baiduimage.apiKey}")
	private String apiKey;
	@Value("${secret.baiduimage.secretKey}")
	private String secretKey;
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private AipImageClassify client;
	@Autowired
	private TranslateService translateService;

	@PostConstruct
	public void init() {
		if (recognitionType != 3) {
			return;
		}
		try {
			if (StringUtils.isBlank(appId) || StringUtils.isBlank(apiKey) || StringUtils.isBlank(secretKey)) {
				// 在SecretsManager里创建密钥名称为baiduimage的密钥(其他类别)，密钥值包含appId、apiKey、secretKey
				SecretsManagerClient secretsManagerClient = SecretsManagerClient.create();
				GetSecretValueRequest valueRequest = GetSecretValueRequest.builder().secretId(secretId).build();
				GetSecretValueResponse valueResponse = secretsManagerClient.getSecretValue(valueRequest);
				String secret = valueResponse.secretString();
				com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(secret);
				appId = json.getString("appId");
				apiKey = json.getString("apiKey");
				secretKey = json.getString("secretKey");
			}
			// 初始化一个AipImageClassify
			client = new AipImageClassify(appId, apiKey, secretKey);
			// 可选：设置网络连接参数
			client.setConnectionTimeoutInMillis(2000);
			client.setSocketTimeoutInMillis(60000);
		} catch (Exception e) {
			logger.warn("配置百度图片识别报错", e);
		}

	}

	@Override
	public Result detect(byte[] image) {
		Result result = new Result();
		JSONObject res = client.advancedGeneral(image, new HashMap<String, String>());
		if (res.has("result")) {
			String data = res.getJSONArray("result").getJSONObject(0).getString("keyword");
			logger.debug("识别结果：{}", data);
			// 百度的需要翻译为英文，否则效果不好
			data = translateService.toEn(data);
			result.setData(data);
		} else {
			result.setCode(2);
			result.setMsg("未识别成功");
		}
		return result;
	}
}
