package cn.nwcdcloud.samples.listingHelper.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.nwcdcloud.commons.lang.Result;
import cn.nwcdcloud.samples.listingHelper.service.ImageService;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.DetectLabelsRequest;
import software.amazon.awssdk.services.rekognition.model.DetectLabelsResponse;
import software.amazon.awssdk.services.rekognition.model.Image;
import software.amazon.awssdk.services.rekognition.model.Label;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@Service
public class RekognitionServiceImpl implements ImageService {
	@Value("${secret.global.name}")
	private String secretId;
	private final Logger logger = LoggerFactory.getLogger(getClass());
	RekognitionClient rekClient;

	@PostConstruct
	public void init() {
		// 需要在SecretsManager里创建密钥名称为GlobalAKSK的密钥(其他类别)，密钥值包含GlobalAK、GlobalSK
		SecretsManagerClient client = SecretsManagerClient.create();
		GetSecretValueRequest valueRequest = GetSecretValueRequest.builder().secretId(secretId).build();

		GetSecretValueResponse valueResponse = client.getSecretValue(valueRequest);
		String secret = valueResponse.secretString();
		JSONObject json = JSONObject.parseObject(secret);
		Region region = Region.US_EAST_1;
		rekClient = RekognitionClient.builder()
				.credentialsProvider(StaticCredentialsProvider
						.create(AwsBasicCredentials.create(json.getString("GlobalAK"), json.getString("GlobalSK"))))
				.region(region).build();
	}

	@Override
	public Result detect(byte[] image) {
		Result result = new Result();
		SdkBytes sourceBytes = SdkBytes.fromByteArray(image);
		Image souImage = Image.builder().bytes(sourceBytes).build();
		DetectLabelsRequest detectLabelsRequest = DetectLabelsRequest.builder().image(souImage).maxLabels(10).build();
		DetectLabelsResponse labelsResponse = rekClient.detectLabels(detectLabelsRequest);
		List<Label> labels = labelsResponse.labels();
		for (Label label : labels) {
			logger.debug("检测到第一个：{}", label.name());
			result.setData(label.name());
			break;
		}
		return result;
	}
}
