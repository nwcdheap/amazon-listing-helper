package cn.nwcdcloud.samples.listingHelper.service;

import cn.nwcdcloud.commons.lang.Result;

public interface AmazonService {
	Result getProduct(String id);

	String productmatches(String content);

	String getafnfee(String content);
}
