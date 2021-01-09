var vue ;
$(function(){
    vue = new Vue({
            el: '#main',
            data:{
                pictures: []
             }
    })
    getData()
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


