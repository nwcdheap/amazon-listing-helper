var vue ;
var server_url = "/" ;
$(function(){
    vue = new Vue({
            el: '#mainDiv',
            data:{
                calculate_item: {"item_price":0.02},
                product_list:[],  // 用于界面显示 大小为10个
                current_product: {},
                raw_product_list:[],  // 保存了查询到的商品列表
             },
             methods:{
                 start_search:function(){   // 搜索商品列表
                     start_search_inner()
                 },
                 request_product_detail:function(e){  //用户选择了指定的商品， 显示计算面板
                    asin = e.currentTarget.name
                    request_product_detail_inner(asin)
                 },
                 calculate_revenue:function(){   // 计算商品价格
                    calculate_revenue_inner()
                 },
                 init_search:function(){   // 重新查询商品
                    init_search_inner()
                 }
             }

    })

    init_data()
});


function init_data(){
     //    show_left_content_inner()
}


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

}


function show_left_content_inner(result) {

    var current_product = find_product_by_asin(asin)
//    current_product = {"isWhiteGloveRequired":false,"weightUnitString":"pounds","subCategory":"","fnsku":"","dimensionUnit":"inches","link":"http://www.amazon.com/gp/product/B083LFWL2F/ref=silver_xx_cont_revecalc","binding":"apparel","title":"To list your products after your brand is enrolled, enter the brand name exactly as you submitted it for brand approval, and specify a unique value for the Key Attribute that you selected in the brand","dimensionUnitString":"inches","price":0,"imageUrl":"https://m.media-amazon.com/images/I/41n-+eWgRoL._SL120_.jpg","height":14.2126,"isAfn":false,"gl":"gl_apparel","length":0.5906,"isAsinLimits":true,"weight":0.2712,"originalUrl":"","productGroup":"","width":11.4961,"thumbStringUrl":"https://m.media-amazon.com/images/I/41n-+eWgRoL._SL120_SL80_.jpg","asin":"B083LFWL2F","encryptedMarketplaceId":"","weightUnit":"pounds"}

    vue.current_product = current_product;
    if(current_product == null){
        return ;
    }

    console.log("result: " , JSON.stringify(result))
    vue.current_product['volume']            =  current_product['length'] + ' x ' + current_product['width'] +' x ' + current_product['height'] + ' centimetres'      // 评论数
    vue.current_product['price']             =  result['price']   // 价格
    vue.current_product['star']              =  result['star'].split(" ")[0]   // 评分
    vue.current_product['review']            =  result['review'].split(" ")[0]   // 评论数

    var itemPrice = result['price'].split("-")[0]
    itemPrice = itemPrice.replace("$", "")
    itemPrice = parseFloat(itemPrice)
    console.log(" -------price----------:     ", itemPrice)

    vue.calculate_item['item_price']        =  itemPrice   // 销售价格
    vue.calculate_item['product_cost']      =  (itemPrice*0.3).toFixed(2);   //  成本
    vue.calculate_item['deliver_to_amazon'] =  (itemPrice*0.01).toFixed(2);   //  头程物流
    vue.calculate_item['brokerage']         =  (itemPrice*0.15).toFixed(2);   //  佣金

    vue.calculate_item['fba']               =  24.6   // FBA 费用
    vue.calculate_item['marketing']         =  (itemPrice*0.05).toFixed(2);   // 获客成本 广告等
    vue.calculate_item['storage']           =  (itemPrice*0.02).toFixed(2);   // 仓储成本
    vue.calculate_item['others']            =  (itemPrice*0.05).toFixed(2);   // 其他


    vue.current_product['detail_url'] = 'http://www.amazon.com/gp/product/' +vue.current_product['asin']
    $("#search_div").css("display","none");
    $("#product_content").css("display","block");
    $('#staticBackdrop').modal('hide')
    $("#left_content").css("visibility","visible");
    $("#right_content").css("visibility","hidden");

}

function calculate_revenue_inner() {

    var netProfit   = (vue.calculate_item['item_price']
                    - vue.calculate_item['product_cost']
                    - vue.calculate_item['deliver_to_amazon']
                    - vue.calculate_item['brokerage']
                    - vue.calculate_item['fba']
                    - vue.calculate_item['marketing']
                    - vue.calculate_item['storage']-vue.calculate_item['others']).toFixed(2);
    var netMargin = (netProfit / vue.calculate_item['item_price'] * 100).toFixed(2);

    $("#net_profit").html(netProfit)
    $("#net_margin").html(netMargin+'%')

    if(netProfit >0 ){
        $("#net_profit").css("color", 'green');
        $("#net_margin").css("color", 'green');
    }else {

        $("#net_profit").css("color", 'red');
        $("#net_margin").css("color", 'red');
    }

}

function init_search_inner(){
    $("#search_div").css("display","block");
    $("#product_content").css("display","none");
    $("#left_content").css("visibility","hidden");
    $("#right_content").css("visibility","hidden");

    $("#net_profit").html("")
    $("#net_margin").html("")
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
