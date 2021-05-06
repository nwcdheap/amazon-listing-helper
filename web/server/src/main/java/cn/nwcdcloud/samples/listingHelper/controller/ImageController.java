package cn.nwcdcloud.samples.listingHelper.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.nwcdcloud.commons.lang.Result;
import cn.nwcdcloud.samples.listingHelper.service.AmazonService;
import cn.nwcdcloud.samples.listingHelper.service.ImageService;

@Controller
@RequestMapping("/image")
@CrossOrigin
public class ImageController {
	@Autowired
	private ImageService rekognitionServiceImpl;
//	@Autowired
//	private TranslateService translateService;
	@Autowired
	private AmazonService amazonService;

	@PostMapping("/detect")
	@ResponseBody
	public String detect(HttpServletRequest request) throws IOException {
		Result result = rekognitionServiceImpl.detect(IOUtils.toByteArray(request.getInputStream()));
		String data = (String) result.getData();
//		String dataEn = translateService.toEn(data);
		String queryString = String.format("searchKey=%s&searchType=keyword&language=en_US", data);
		return amazonService.productmatches(queryString);
	}
}
