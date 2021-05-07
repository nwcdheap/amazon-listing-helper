//https://sellercentral.amazon.com/fba/profitabilitycalculator/productmatches?searchKey=abc&searchType=keyword&language=en_US
//http://127.0.0.1/productmatches?searchKey=pen&searchType=keyword&language=en_US

function request_search_product(keyword, language ){

      console.log("  request_search_product  ", keyword, language)
      $('#progressBar').modal('show')
      var url = server_url +"profit/productmatches?searchKey="+keyword+"&searchType=keyword&language="+language
      $.ajax({
              async:true,
              type:"get",
              contentType : "application/json;charset=UTF-8", //类型必填
              url:url,
              success:function(data){
                  var result = JSON.parse(data)
                  $('#progressBar').modal('hide')
//                  console.log('productmatches:  ', JSON.stringify(result))
                  if(result['succeed']){
                    get_product_list(result['data'])
                  }else {
                    $("#errorMessageContent").html("没有查询到 ["+keyword+"] 相关商品")
                    $('#errorMessageBar').modal('show')
                  }


              },
              error:function(data){
                  console.log(data);
                  $('#progressBar').modal('hide')
              }
     })
}

function request_product_detail_inner(asin){

      $('#progressBar').modal('show')
      $('#staticBackdrop').modal('hide')
      var url = server_url + "product/"+asin
      $.ajax({
              async:true,
              type:"get",
              contentType : "application/json;charset=UTF-8", //类型必填
              url:url,
              success:function(data){

                 $('#progressBar').modal('hide')
                 var result = JSON.parse(data)
//                 console.log(JSON.stringify(result))
                 if(result['code'] == 1){
                    show_left_content_inner(result['data'])
                 }else {
                    alert("未查询到商品信息")
                 }
              },
              error:function(data){
                  $('#progressBar').modal('hide')
                  console.log(data);
                  $("#errorMessageContent").html("未查询到相关商品信息")
                  $('#errorMessageBar').modal('show')
              }
     })
}



function request_detect(){
    var file = upload.files[0];
    $("#filename").html(file.name);
    console.log("  request_detect  "  ,file.name )
    $('#progressBar').modal('show')

	$.ajax({
		url : "/image/detect",
		type : 'POST',
		data : file,
		processData : false,
		contentType : "image/jpeg",
		beforeSend:function(){
			console.log("正在进行，请稍候");
		},
		success : function(data) {

          var result = JSON.parse(data)
          $('#progressBar').modal('hide')
          console.log('request_detect result:  ', JSON.stringify(result))
          if(result['succeed']){
            get_product_list(result['data'])
          }else {
            $("#errorMessageContent").html("没有查询到 ["+keyword+"] 相关商品")
            $('#errorMessageBar').modal('show')
          }
		},
		error : function(data) {
			console.log(data);
            $('#progressBar').modal('hide')
		}
	});
}



function get_product_list(raw_product_list){

   console.log('product_list  length= ' , raw_product_list.length)
   vue.raw_product_list = raw_product_list

   var product_info_list = new Array();
   var product_count = 10;
   for (i =0 ; i<10; i++){
        var product_info = {}
        if(i < raw_product_list.length){
            product_info['display'] = true
            product_info['title'] = raw_product_list[i]['title']
            product_info['thumbStringUrl'] = raw_product_list[i]['thumbStringUrl']
            product_info['asin'] = raw_product_list[i]['asin']
        }else {
            product_info['display'] = false
        }
        product_info_list.push(product_info)
   }
   console.log("")
   console.log(JSON.stringify(product_info_list))
   console.log("")

   vue.product_list = product_info_list

    $('#progressBar').modal('hide')
    $('#staticBackdrop').modal('show')


}


