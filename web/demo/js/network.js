//for demo
/**
var product_list = {
        "data": [
            {
                "isWhiteGloveRequired": false,
                "weightUnitString": "kilograms",
                "subCategory": "2103030",
                "fnsku": "",
                "dimensionUnit": "cm",
                "link": "http://www.amazon.ca/gp/product/B014Q1XX9S/ref=silver_xx_cont_revecalc",
                "binding": "toy",
                "title": "Czech Games Edition Codenames Party Game - Standard, Multicolor",
                "dimensionUnitString": "centimetres",
                "price": 0,
                "imageUrl": "https://m.media-amazon.com/images/I/51Ldg63AV-L._SL120_.jpg",
                "height": 23,
                "isAfn": false,
                "gl": "gl_toy",
                "length": 5.4,
                "isAsinLimits": false,
                "weight": 0.56,
                "originalUrl": "",
                "productGroup": "",
                "width": 16,
                "thumbStringUrl": "https://m.media-amazon.com/images/I/51Ldg63AV-L._SL120_SL80_.jpg",
                "asin": "B014Q1XX9S",
                "encryptedMarketplaceId": "",
                "weightUnit": "kilograms"
            },
            {
                "isWhiteGloveRequired": false,
                "weightUnitString": "kilograms",
                "subCategory": "",
                "fnsku": "",
                "dimensionUnit": "cm",
                "link": "http://www.amazon.ca/gp/product/B075XK7975/ref=silver_xx_cont_revecalc",
                "binding": "toy",
                "title": "Empowering Questions Cards - 52 Cards for Mindfulness & Meditation, Writing, or Any Other Empowering Process - The Original Deck",
                "dimensionUnitString": "centimetres",
                "price": 0,
                "imageUrl": "https://m.media-amazon.com/images/I/61n6t8Um+BL._SL120_.jpg",
                "height": 9.6012,
                "isAfn": false,
                "gl": "gl_toy",
                "length": 2.2098,
                "isAsinLimits": false,
                "weight": 0.132,
                "originalUrl": "",
                "productGroup": "",
                "width": 9.2964,
                "thumbStringUrl": "https://m.media-amazon.com/images/I/61n6t8Um+BL._SL120_SL80_.jpg",
                "asin": "B075XK7975",
                "encryptedMarketplaceId": "",
                "weightUnit": "kilograms"
            },
            {
                "isWhiteGloveRequired": false,
                "weightUnitString": "kilograms",
                "subCategory": "2104040",
                "fnsku": "",
                "dimensionUnit": "cm",
                "link": "http://www.amazon.ca/gp/product/B01NCNLLWD/ref=silver_xx_cont_revecalc",
                "binding": "toy",
                "title": "Learning Resources Sight Word Swat a Sight Word Game, Homeschool, Visual, Tactile and Auditory Learning, 114 Pieces, Ages 5+",
                "dimensionUnitString": "centimetres",
                "price": 0,
                "imageUrl": "https://m.media-amazon.com/images/I/51rHaSLSWwL._SL120_.jpg",
                "height": 25.654,
                "isAfn": false,
                "gl": "gl_toy",
                "length": 5.588,
                "isAsinLimits": false,
                "weight": 0.59,
                "originalUrl": "",
                "productGroup": "",
                "width": 21.844,
                "thumbStringUrl": "https://m.media-amazon.com/images/I/51rHaSLSWwL._SL120_SL80_.jpg",
                "asin": "B01NCNLLWD",
                "encryptedMarketplaceId": "",
                "weightUnit": "kilograms"
            },
            {
                "isWhiteGloveRequired": false,
                "weightUnitString": "kilograms",
                "subCategory": "20106606",
                "fnsku": "",
                "dimensionUnit": "cm",
                "link": "http://www.amazon.ca/gp/product/B075CSR8D7/ref=silver_xx_cont_revecalc",
                "binding": "kitchen",
                "title": "Cinematic Light Box (A4/P4/US Letter Size) with 100 Letters, Emoji, Smilies and Symbols - Personalize Your own Message - Battery and USB Power",
                "dimensionUnitString": "centimetres",
                "price": 0,
                "imageUrl": "https://m.media-amazon.com/images/I/518q8+GUljL._SL120_.jpg",
                "height": 32.512,
                "isAfn": false,
                "gl": "gl_home",
                "length": 6.604,
                "isAsinLimits": false,
                "weight": 0.726,
                "originalUrl": "",
                "productGroup": "",
                "width": 22.352,
                "thumbStringUrl": "https://m.media-amazon.com/images/I/518q8+GUljL._SL120_SL80_.jpg",
                "asin": "B075CSR8D7",
                "encryptedMarketplaceId": "",
                "weightUnit": "kilograms"
            },
            {
                "isWhiteGloveRequired": false,
                "weightUnitString": "kilograms",
                "subCategory": "",
                "fnsku": "",
                "dimensionUnit": "cm",
                "link": "http://www.amazon.ca/gp/product/1790471699/ref=silver_xx_cont_revecalc",
                "binding": "paperback",
                "title": "Event Planner (Birthdays, anniversaries & Celebrations: Record All Your Important Events & Celebrations for Easy Access",
                "dimensionUnitString": "centimetres",
                "price": 0,
                "imageUrl": "https://m.media-amazon.com/images/I/51cYc6p1I5L._SL120_.jpg",
                "height": 25.4,
                "isAfn": false,
                "gl": "gl_book",
                "length": 20.32,
                "isAsinLimits": false,
                "weight": 0.3039,
                "originalUrl": "",
                "productGroup": "",
                "width": 0.635,
                "thumbStringUrl": "https://m.media-amazon.com/images/I/51cYc6p1I5L._SL120_SL80_.jpg",
                "asin": "1790471699",
                "encryptedMarketplaceId": "",
                "weightUnit": "kilograms"
            }
        ],
        "processedDate": "Wed Jan 13 02:43:58 UTC 2021",
        "succeed": "true"
    }
*/

//https://sellercentral.amazon.com/fba/profitabilitycalculator/productmatches?searchKey=abc&searchType=keyword&language=en_US
//http://127.0.0.1/productmatches?searchKey=pen&searchType=keyword&language=en_US
function request_search_product(keyword, language ){

      console.log("  request_search_product  ", keyword, language)

      var url = "http://127.0.0.1/profit/productmatches?searchKey="+keyword+"&searchType=keyword&language="+language
      $.ajax({
              async:true,
              type:"get",
              contentType : "application/json;charset=UTF-8", //类型必填
              url:url,
              success:function(data){
                  get_product_list(JSON.parse(data)['data'])
              },
              error:function(data){
                  console.log(data);
              }
     })


}


function get_product_list(raw_product_list){

   //TODO:  修改成真实数据
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
//   console.log("")
//   console.log(JSON.stringify(product_info_list))
//   console.log("")

   vue.product_list = product_info_list
   show_search_result_inner()
}