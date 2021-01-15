package cn.nwcdcloud.samples.listingHelper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.nwcdcloud.commons.lang.Result;
import cn.nwcdcloud.samples.listingHelper.service.ProductService;

@Controller
@RequestMapping("/product")
@CrossOrigin
public class ProductController {
	@Autowired
	private ProductService productService;

	@GetMapping("/{id}")
	@ResponseBody
	public String productmatches(@PathVariable("id") String id) {
		Result result = productService.getProduct(id);
		return result.toString();
	}
}
