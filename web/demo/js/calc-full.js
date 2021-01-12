
function doSellCalcFees(revenue,feePercent,transactionFee,minimumFee){
    fee = revenue*feePercent+transactionFee;
    if(fee < minimumFee){
        fee = minimumFee;
    }
    return fee;
}

//TODO 需要完成所有类别
function calcSellFees(type,revenue){
    switch(type){
	case 1://3D 打印商品
        fee = doSellCalcFees(revenue,0.12,0,0.3);
        break;
	case 2://亚马逊设备配件
        fee = doSellCalcFees(revenue,0.45,0,0.3);
        break;
	default://其他
        fee = doSellCalcFees(revenue,0.15,0,0.3);
        break;
    }
    return fee.toFixed(2);
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
    return fba.toFixed(2);
}

/**
  * @param type 类别，https://sell.amazon.com/zh_CN/pricing.html?lang=zh_CN#cost-calculator的分类索引
  * @param size 大小，数值为1-7，https://sell.amazon.com/zh_CN/pricing.html?lang=zh_CN#cost-calculator的尺寸分段行数索引
  * @param weight 单位：磅
*/
function calcAmazon(itemPrice,shipToAmazon,storedUnit,costProduct,type,size,weight){
    var result={};
    result.type=type;
    result.itemPrice=itemPrice;
    result.shipping=0;
    result.totalRevenue=itemPrice+result.shipping;
    result.sellingOnAmazonFees=calcSellFees(type,result.totalRevenue);
    result.fulfillmentByAmazonFees=calcFBAFees(type,size,weight);
    result.shipToAmazon=shipToAmazon;
    result.totalFulfillmentCost=parseFloat(result.fulfillmentByAmazonFees)+shipToAmazon;
    result.monthlyStorageCostPerUnit=0.81;//TODO 费用按类别不同
    result.storedUnit=storedUnit;
    result.storageCost=result.monthlyStorageCostPerUnit*storedUnit;
    result.sellerProceeds=(result.totalRevenue-result.sellingOnAmazonFees-result.totalFulfillmentCost-result.storageCost).toFixed(2);
    result.costProduct=costProduct;
    result.netProfit=(result.sellerProceeds-result.costProduct).toFixed(2);
    result.netMargin=(result.netProfit/result.totalRevenue*100).toFixed(2);
    console.log(result);
    return result;
}