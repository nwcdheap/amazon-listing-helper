package cn.nwcdcloud.samples.listingHelper.service;

import cn.nwcdcloud.commons.lang.Result;

public interface ProductService {
	Result getProduct(String id);
	
	Result detect(byte[] image);
}
