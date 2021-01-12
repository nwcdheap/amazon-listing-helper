var vue ;
$(function(){
    vue = new Vue({
            el: '#mainDiv',
            data:{
                amazon_fulfilment_item: {"item_price":0.02}
             },
             methods:{
                 start_search:function(){   // 获取文件 文件保存在S3中
                     start_search_inner()
                 },
                 show_search_result:function(){   // 获取文件 文件保存在S3中
                      show_search_result_inner()
                 },
                 show_left_content:function(){
                    show_left_content_inner()
                 },
                 calculate_revenue:function(){
                    calculate_revenue_inner()
                 },
                 init_search:function(){
                    init_search_inner()
                 }

             }

    })
//    getData()
    initChart('chart01', '收入', '商品价格+运费', 5, 6, '#E0DEDE')
    initChart('chart02', '成本', '配送+亚马逊销售费用+商品成本+仓储成本', 9, 14, '#F1F4F5')
    initChart('chart03', '卖方收益', '收入-亚马逊销售费用-配送成本-仓储成本', 9, 11, '#E0DEDE')
    initChart('chart04', '净利', '收入-费用', 10, 21, '#F1F4F5')

//    show_search_result_inner()
    show_left_content_inner()
    init_data()
//    start_search_inner()
});


function init_data(){

    vue.amazon_fulfilment_item['item_price']    = 20
    vue.amazon_fulfilment_item['shipping']      = 1
    vue.amazon_fulfilment_item['deliver_to_amazon']      = 2
    vue.amazon_fulfilment_item['average_units_stored']      = 3
    vue.amazon_fulfilment_item['product_cost']      = 5
    vue.amazon_fulfilment_item['net_profit']      = '0.00'
    vue.amazon_fulfilment_item['net_margin']      = '0.00'
    vue.amazon_fulfilment_item['is_increase']      = true
    vue.amazon_fulfilment_item['net_profit_value']      =  0



}

function getData(){

//
//
//      $.ajax({
//              async:true,
//              type:"get",
//              contentType : "application/json;charset=UTF-8", //类型必填
//              url:"/pictures",
//              dataType:"json",
//              success:function(data){
//                   console.log("New length: "+ data.length +" Old  length: "+ vue.pictures.length);
//
//                   vue.pictures = data;
//
//              },
//              error:function(data){
//                  console.log(data.result);
//              }
//     })

}

function start_search_inner(){
    $('#progressBar').modal('show')
    $("#loading-icon").show()
    setTimeout("show_search_result_inner()",2000)
}


function show_search_result_inner(){
    $('#progressBar').modal('hide')
    $('#staticBackdrop').modal('show')
}

function show_left_content_inner() {


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