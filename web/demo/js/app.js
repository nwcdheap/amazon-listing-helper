var vue ;
$(function(){
    vue = new Vue({
            el: '#mainDiv',
            data:{
                pictures: []
             },
             methods:{
                 start_search:function(){   // 获取文件 文件保存在S3中
                     start_search_inner()
                 },
                 show_search_result:function(){   // 获取文件 文件保存在S3中
                      show_search_result_inner()
                 },
             }

    })
    getData()
    initChart('chart01', '收入', '商品价格+运费', 5, 6, '#E0DEDE')
    initChart('chart02', '成本', '配送+亚马逊销售费用+商品成本+仓储成本', 9, 14, '#F1F4F5')
    initChart('chart03', '卖方收益', '收入-亚马逊销售费用-配送成本-仓储成本', 9, 11, '#E0DEDE')
    initChart('chart04', '净利', '收入-费用', 10, 21, '#F1F4F5')

//    start_search_inner()
});

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
    setTimeout("show_search_result_inner()",2000)
}


function show_search_result_inner(){
    $('#progressBar').modal('hide')
    $('#staticBackdrop').modal('show')

}