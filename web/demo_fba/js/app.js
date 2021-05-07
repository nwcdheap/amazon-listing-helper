
var vue ;
$(function(){
    vue = new Vue({
            el: '#mainDiv',
            data:{
                amazon_fulfilment_item: {"item_price":0.02},
                product_list:[],
                current_product: {},
                raw_product_list:[],
             },
             methods:{
                 start_search:function(){   // 获取文件 文件保存在S3中
                     start_search_inner()
                 },
                 show_search_result:function(){   // 获取文件 文件保存在S3中
                      show_search_result_inner()
                 },
                 show_left_content:function(e){  //用户选择了指定的商品， 显示计算面板
                    asin = e.currentTarget.name
                    show_left_content_inner(asin)
                 },
                 calculate_revenue:function(){
                    calculate_revenue_inner()
                 },
                 init_search:function(){
                    init_search_inner()
                 }

             }

    })

    initChart('chart01', '收入', '商品价格+运费', 5, 6, '#E0DEDE')
    initChart('chart02', '成本', '配送+亚马逊销售费用+商品成本+仓储成本', 9, 14, '#F1F4F5')
    initChart('chart03', '卖方收益', '收入-亚马逊销售费用-配送成本-仓储成本', 9, 11, '#E0DEDE')
    initChart('chart04', '净利', '收入-费用', 10, 21, '#F1F4F5')

//    show_search_result_inner()
//    show_left_content_inner()
//    start_search_inner()
});




/**
开始查询
*/
function start_search_inner(){

    product_keyword = $("#product_keyword").val()
    console.log("keyword " + product_keyword)
    if (product_keyword == null || product_keyword.trim().length < 1){
        product_keyword = "Enter+your+product+name"
    }
    console.log("keyword " + product_keyword)
    request_search_product( product_keyword, 'en_US' )
    $('#progressBar').modal('show')
    $("#loading-icon").show()

}


function show_search_result_inner(){

    $('#progressBar').modal('hide')
    $('#staticBackdrop').modal('show')
}

function show_left_content_inner(asin) {
    var current_product = find_product_by_asin(asin)
    console.log(JSON.stringify(current_product))
    if(current_product == null){
        return ;
    }
    vue.current_product = current_product;
    $("#search_div").css("display","none");
    $("#product_content").css("display","block");
    $('#staticBackdrop').modal('hide')
    $("#left_content").css("visibility","visible");
    $("#right_content").css("visibility","hidden");

}

function calculate_revenue_inner() {

    //TODO: 计算



    item_price = vue.amazon_fulfilment_item['item_price']
    deliver_to_amazon = vue.amazon_fulfilment_item['deliver_to_amazon']
    average_units_stored = vue.amazon_fulfilment_item['average_units_stored']
    product_cost = vue.amazon_fulfilment_item['product_cost']

    console.log("calcAmazon    ",item_price, deliver_to_amazon, average_units_stored, product_cost )
    var result = calcAmazon(item_price, deliver_to_amazon ,
                    average_units_stored ,product_cost,
                     4,4,24);

    console.log(result)

    //TODO: 包装尺寸

    $("#amazon_net_profit").html(result['netProfit'])
    $("#amazon_net_margin").html(result['netMargin']+'%')
    $("#amazon_shipping").html(result['shipping'])
    $("#amazon_total_revenue").html(result['totalRevenue'])
    $("#amazon_selling_on_amazon_fees").html(result['sellingOnAmazonFees'])
    $("#amazon_fulfillment_by_amazon_fees").html(result['fulfillmentByAmazonFees'])



    $("#amazon_seller_proceeds").html(result['sellerProceeds'])
    $("#amazon_monthly_storage_cost_per_unit").html(result['monthlyStorageCostPerUnit'])
    $("#amazon_storage_cost").html(result['storageCost'])









    var netProfit = parseFloat(result['netProfit'])

    console.log('netProfit = ', netProfit)
    if(netProfit >0 ){
        $("#amazon_net_profit").css("color", 'green');
        $("#amazon_net_margin").css("color", 'green');

    }else {

        $("#amazon_net_profit").css("color", 'red');
        $("#amazon_net_margin").css("color", 'red');

    }


    $("#right_content").css("visibility","visible");

}

function init_search_inner(){
    $("#search_div").css("display","block");
    $("#product_content").css("display","none");
    $("#left_content").css("visibility","hidden");
    $("#right_content").css("visibility","hidden");

}



function find_product_by_asin(asin){
    for(i=0; i< vue.raw_product_list.length; i++){
        item = vue.raw_product_list[i]
        if(item['asin'] == asin){
            return item;
        }
    }
    return null;
}