package cn.nwcdcloud.samples.listingHelper.service.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.nwcdcloud.samples.listingHelper.service.TranslateService;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import software.amazon.awssdk.services.translate.TranslateClient;
import software.amazon.awssdk.services.translate.model.TranslateTextRequest;
import software.amazon.awssdk.services.translate.model.TranslateTextResponse;
import software.amazon.awssdk.utils.StringUtils;

@Service
public class TranslateServiceImpl implements TranslateService {
	private TranslateClient translateClient;
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Value("${recognitionType}")
	private int recognitionType;
	@Value("${secret.global.name}")
	private String secretId;
	@Value("${secret.global.GlobalAK}")
	private String globalAK;
	@Value("${secret.global.GlobalSK}")
	private String globalSK;

	@PostConstruct
	public void init() {
		// 百度方式才需要翻译
		if (recognitionType != 3) {
			return;
		}
		try {
			if (StringUtils.isBlank(globalAK) || StringUtils.isBlank(globalSK)) {
				// 需要在SecretsManager里创建密钥名称为GlobalAKSK的密钥(其他类别)，密钥值包含GlobalAK、GlobalSK
				SecretsManagerClient client = SecretsManagerClient.create();
				GetSecretValueRequest valueRequest = GetSecretValueRequest.builder().secretId(secretId).build();

				GetSecretValueResponse valueResponse = client.getSecretValue(valueRequest);
				String secret = valueResponse.secretString();
				JSONObject json = JSONObject.parseObject(secret);
				globalAK = json.getString("GlobalAK");
				globalSK = json.getString("GlobalSK");
			}
			Region region = Region.US_WEST_2;
			translateClient = TranslateClient.builder()
					.credentialsProvider(
							StaticCredentialsProvider.create(AwsBasicCredentials.create(globalAK, globalSK)))
					.region(region).build();
		} catch (Exception e) {
			logger.warn("配置Translate报错", e);
		}
	}

	@Override
	public String toEn(String chinese) {
		TranslateTextRequest textRequest = TranslateTextRequest.builder().sourceLanguageCode("zh")
				.targetLanguageCode("en").text(chinese).build();
		TranslateTextResponse textResponse = translateClient.translateText(textRequest);
		String result = textResponse.translatedText();
		logger.debug("翻译原文:{}，结果:{}", chinese, result);
		return result;
	}
}
