package cn.nwcdcloud.samples.listingHelper.controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.nwcdcloud.samples.listingHelper.service.AmazonService;

@Controller
@RequestMapping("/profit")
@CrossOrigin
public class ProfitController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AmazonService amazonService;

	@GetMapping("/productmatches")
	@ResponseBody
	public String productmatches(HttpServletRequest request) {
		String queryString = request.getQueryString();
		return amazonService.productmatches(queryString);
	}

	@PostMapping("/getafnfee")
	@ResponseBody
	public String getafnfee(HttpServletRequest request) {
		String content = getRequestContent(request);
		return amazonService.getafnfee(content);
	}

	private String getRequestContent(HttpServletRequest request) {
		// 获取POST数据
		BufferedReader bufReader = null;
		try {
			bufReader = request.getReader();
			String line = null;
			StringBuffer sbBody = new StringBuffer();
			while ((line = bufReader.readLine()) != null) {
				sbBody.append(line);
			}
			return sbBody.toString();
		} catch (IOException e) {
			logger.info("获取POST数据时出错", e);
			return null;
		} finally {
			if (bufReader != null) {
				try {
					bufReader.close();
				} catch (IOException e) {
					logger.info("关闭获取POST数据流时出错", e);
				}
			}
		}
	}
}
