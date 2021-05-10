package cn.nwcdcloud.samples.listingHelper.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.nwcdcloud.commons.lang.Result;
import cn.nwcdcloud.commons.util.ApplicationUtils;
import cn.nwcdcloud.samples.listingHelper.service.AmazonService;
import cn.nwcdcloud.samples.listingHelper.service.ImageService;

@Controller
@RequestMapping("/image")
@CrossOrigin
public class ImageController {
	private ImageService imageService;
	@Value("${recognitionType}")
	private int recognitionType;
	@Autowired
	private AmazonService amazonService;
	private final static int MAX_COUNT = 5;

	@PostConstruct
	public void init() {
		if (recognitionType == 3) {
			imageService = ApplicationUtils.getBean("baiduImageServiceImpl");
		} else if (recognitionType == 2) {
			imageService = ApplicationUtils.getBean("ebayImageServiceImpl");
		} else {
			imageService = ApplicationUtils.getBean("rekognitionServiceImpl");
		}
	}

	@PostMapping("/detect")
	@ResponseBody
	public String detect(HttpServletRequest request) throws IOException {
		Result result = imageService.detect(IOUtils.toByteArray(request.getInputStream()));
		String data = (String) result.getData();
		String[] array = data.split(" ");
		int size = array.length;
		if (size > MAX_COUNT) {
			size = MAX_COUNT;
		}
		StringBuffer sbData = new StringBuffer();
		for (int i = 0; i < size; i++) {
			if (i == 0) {
				sbData.append(array[i]);
			} else {
				sbData.append(" ").append(array[i]);
			}
		}
		String searchKey = URLEncoder.encode(sbData.toString(), "UTF-8");
		String queryString = String.format("searchKey=%s&searchType=keyword&language=en_US", searchKey);
		return amazonService.productmatches(queryString);
	}
}
