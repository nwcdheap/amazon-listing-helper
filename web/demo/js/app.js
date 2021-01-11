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
//    show_left_content_inner()
    init_data()
    start_search_inner()
});


function init_data(){

    vue.amazon_fulfilment_item['item_price']    = 0.1
    vue.amazon_fulfilment_item['shipping']      = 0.0
    vue.amazon_fulfilment_item['deliver_to_amazon']      = 0.3
    vue.amazon_fulfilment_item['average_units_stored']      = 0
    vue.amazon_fulfilment_item['product_cost']      = 0.4
    vue.amazon_fulfilment_item['net_profit']      = 'a'
    vue.amazon_fulfilment_item['net_margin']      = 'b'
    vue.amazon_fulfilment_item['is_increase']      = false



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

    $("#amazon_net_profit").html('12')
    $("#amazon_net_margin").html('120%')
    vue.amazon_fulfilment_item['is_increase']      = true
    $("#right_content").css("visibility","visible");

}

function init_search_inner(){
    $("#search_div").css("display","block");
    $("#product_content").css("display","none");
    $("#left_content").css("visibility","hidden");
    $("#right_content").css("visibility","hidden");

}