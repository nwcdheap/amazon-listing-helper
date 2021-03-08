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
import cn.nwcdcloud.samples.listingHelper.service.ProductService;

@Controller
@RequestMapping("/image")
@CrossOrigin
public class ImageController {
	@Autowired
	private ProductService productService;

	@PostMapping("/detect")
	@ResponseBody
	public String detect(HttpServletRequest request) throws IOException {
		Result result = productService.detect(IOUtils.toByteArray(request.getInputStream()));
		return result.toString();
	}
}
