package cn.nwcdcloud.samples.listingHelper.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

	@GetMapping("/")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		return mv;
	}

	@GetMapping("/test")
	@ResponseBody
	public String test(HttpServletRequest request) {
		Enumeration<String> headers = request.getHeaderNames();
		while (headers.hasMoreElements()) {
			String header = headers.nextElement();
			System.out.println("header:" + header + "=" + request.getHeader(header));
		}
		return "OK";
	}
}
