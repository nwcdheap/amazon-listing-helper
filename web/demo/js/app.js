var vue ;
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

    init_data()
});


function init_data(){



    vue.current_product = {"isWhiteGloveRequired":false,"weightUnitString":"pounds","subCategory":"","fnsku":"","dimensionUnit":"inches","link":"http://www.amazon.com/gp/product/B083LFWL2F/ref=silver_xx_cont_revecalc","binding":"apparel","title":"To list your products after your brand is enrolled, enter the brand name exactly as you submitted it for brand approval, and specify a unique value for the Key Attribute that you selected in the brand","dimensionUnitString":"inches","price":0,"imageUrl":"https://m.media-amazon.com/images/I/41n-+eWgRoL._SL120_.jpg","height":14.2126,"isAfn":false,"gl":"gl_apparel","length":0.5906,"isAsinLimits":true,"weight":0.2712,"originalUrl":"","productGroup":"","width":11.4961,"thumbStringUrl":"https://m.media-amazon.com/images/I/41n-+eWgRoL._SL120_SL80_.jpg","asin":"B083LFWL2F","encryptedMarketplaceId":"","weightUnit":"pounds"}
    show_left_content_inner()


    vue.calculate_item['item_price']        =  0.1   // 销售价格
    vue.calculate_item['product_cost']      =  0.2   //  成本
    vue.calculate_item['deliver_to_amazon'] =  0.3   //  头程物流
    vue.calculate_item['brokerage']         =  0.4   //  佣金

    vue.calculate_item['fba']               =  0.5   // FBA 费用
    vue.calculate_item['marketing']         =  0.6   // 获客成本 广告等
    vue.calculate_item['storage']           =  0.7   // 仓储成本
    vue.calculate_item['others']            =  0.8   // 其他
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
    $('#progressBar').modal('show')
    $("#loading-icon").show()

}


function show_search_result_inner(){

    $('#progressBar').modal('hide')
    $('#staticBackdrop').modal('show')
}

function show_left_content_inner(asin) {
//    var current_product = find_product_by_asin(asin)
//    console.log(JSON.stringify(current_product))
//    if(current_product == null){
//        return ;
//    }
//    vue.current_product = current_product;



    vue.current_product['detail_url'] = 'http://www.amazon.ca/gp/product/' +vue.current_product['asin']
    $("#search_div").css("display","none");
    $("#product_content").css("display","block");
    $('#staticBackdrop').modal('hide')
    $("#left_content").css("visibility","visible");
    $("#right_content").css("visibility","hidden");

}

function calculate_revenue_inner() {
//    vue.calculate_item['item_price']        =  0.1   // 销售价格
//    vue.calculate_item['product_cost']      =  0.2   //  成本
//    vue.calculate_item['deliver_to_amazon'] =  0.3   //  头程物流
//    vue.calculate_item['brokerage']         =  0.4   //  佣金
//
//    vue.calculate_item['fba']               =  0.5   // FBA 费用
//    vue.calculate_item['marketing']         =  0.6   // 获客成本 广告等
//    vue.calculate_item['storage']           =  0.7   // 仓储成本
//    vue.calculate_item['others']            =  0.8   // 其他


   console.log("")
   console.log(JSON.stringify(vue.calculate_item))
   console.log("")

    var netProfit   = -20;
    var netMargin = 13.56 ;

    //TODO: 计算
    $("#net_profit").html(netProfit)
    $("#net_margin").html(netMargin+'%')


    console.log('netProfit = ', netProfit)
    if(netProfit >0 ){
        $("#net_profit").css("color", 'green');
        $("#net_margin").css("color", 'green');

    }else {

        $("#net_profit").css("color", 'red');
        $("#net_margin").css("color", 'red');
    }


//    $("#right_content").css("visibility","visible");

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