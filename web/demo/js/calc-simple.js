/**
  * @param type 类别，https://sell.amazon.com/zh_CN/pricing.html?lang=zh_CN#cost-calculator的分类索引
  * @param size 大小，数值为1-7，https://sell.amazon.com/zh_CN/pricing.html?lang=zh_CN#cost-calculator的尺寸分段行数索引
  * @param weight 单位：磅
*/
function calcSimple(revenue,type,size,weight){
    var result = {};
    result.revenue = revenue;
    result.costProduct = (revenue * 0.3).toFixed(2);
    result.firstLogistics = (revenue * 0.1).toFixed(2);
    result.sellingOnAmazonFees = (revenue * 0.15).toFixed(2);
    result.FBA = calcFBAFees(type,size,weight);
    result.costGetCustomers = (revenue * 0.05).toFixed(2);
    result.costStorage = (revenue * 0.02).toFixed(2);
    result.costOther = (revenue * 0.05).toFixed(2);
    result.grossProfit = (revenue - result.costProduct - result.firstLogistics - result.sellingOnAmazonFees - result.FBA - result.costGetCustomers - result.costStorage - result.costOther).toFixed(2);
    result.grossMargin = (result.grossProfit / revenue * 100).toFixed(2);
    console.log(result);
    return result;
}

//TODO size达标，重量超重需要处理，目前仅做了非服装
function calcFBAFees(type,size,weight){
    switch(size){
	case 1://轻小商品
        fba = weight < 4 ? 1.97 : 2.39;
        break;
	case 2://小号标准尺寸
        fba = weight < 10 ? 1.97 : 2.39;
        break;
	case 3://大号标准尺寸
        fba = weight < 10 ? 1.97 : 2.39;
        break;
	case 4://小号大件
        fba = 8.26+ weight*0.38;
        break;
	case 5://中号大件
        fba = 11.37+ weight*0.39;
        break;
	case 6://大号大件
        fba = 75.78+ weight*0.79;
        break;
	case 7://特殊大件
        fba = 137.32+ weight*0.91;
        break;
    }
    return fba.toFixed(2);;
}