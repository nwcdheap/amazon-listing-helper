<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Amazon">
    <title>电商助手</title>

    <!-- Bootstrap core CSS -->
<!--    <link href="./assets/dist/css/bootstrap.css" rel="stylesheet">-->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">



    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script type="text/javascript" src="https://cdn.staticfile.org/vue/2.2.2/vue.min.js" ></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <!-- Custom styles for this template -->
    <link href="/static/css/style.css" rel="stylesheet">
  </head>
  <body>

  <div class="contentDiv"  id="mainDiv">

    <!-- Modal  start -->
    <div class="modal fade  loadingModal" tabindex="-1" role="dialog"  id="errorMessageBar">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">提示</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <p id="errorMessageContent">Modal body text goes here.</p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
          </div>
        </div>
      </div>
    </div>



    <div class="modal fade loadingModal"  id="progressBar" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog"  aria-hidden="true">
      <div class="modal-dialog" style="width:300px">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body" style="height:100px">
            商品信息加载中......
          </div>
        </div>
      </div>
    </div>


    <div class="modal modal-dialog-scrollable fade loadingModal" style="width:1500px;height:800px" id="staticBackdrop" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel" aria-hidden="true">
      <div class="modal-dialog"  style="width:1300px;height:750px">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="staticBackdropLabel">选择您的商品</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body" >


            <table border="0" class="product_table" style="height:600px"  >

              <tr v-if="product_list.length ==10" >
                <td v-for="(product , i ) in  product_list" v-if="i < 5" style="width:20%">
                  <div >
                    <div ><img  v-bind:src="product.thumbStringUrl"> </div>
                    <div ><small v-text="product.title" ></small></div>
                    <div v-if="product.display"><button type="button" v-bind:name="product.asin"
                                                        class="btn btn-default btn-xs" v-on:click="request_product_detail($event)">选择</button></div>
                  </div>
                </td>
              </tr>

              <tr >
                <td v-for="(product , i ) in  product_list" v-if="i >= 5" style="width:20%">
                  <div >
                    <div ><img  v-bind:src="product.thumbStringUrl"></div>
                    <div ><small v-text="product.title" ></small></div>
                    <div v-if="product.display"><button type="button" v-bind:name="product.asin"
                                                        class="btn btn-default btn-xs" v-on:click="request_product_detail($event)">选择</button></div>
                  </div>
                </td>
              </tr>

              <tr v-if="product_list.length !=10"></tr>
              <tr v-if="product_list.length !=10"></tr>


            </table>

          </div>
        </div>
      </div>
    </div>
    <!-- Modal  end -->



    <div class="row "  style="margin-right:20px;margin-left:5px"  >
      <div class="col-sm-2" ><img style="width:200px;height:60px" src="https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=48636859,3759177675&fm=26&gp=0.jpg" /></div>
      <div class="col-sm-8  ">  &nbsp; </div>
      <div class="col-sm-2 table-text " ><a class="btn btn-outline-primary" href="#">登录</a> </div>

    </div>





    <div  style="margin-left:30px;margin-right:30px;margin-top:0px">

    <div style="margin-bottom:20px">
      <h2 style="color:#BF6B27">亚马逊收入计算器</h2>

      <span>提供您的履行成本，并查看您的履行与我们在Amazon.ca上为客户订单提供的服务之间的实时成本比较</span>

      <div>
        <small><b>免责声明</b> 此亚马逊收入计算器的实现应仅用作评估亚马逊物流的指南。Amazon不保证此收入计算器中信息或计算的准确性。应对此收入计算器的输出进行独立分析，以验证结果。请查阅《亚马逊服务业务解决方案协议》以获取最新费用。新亚马逊配送选项反映您当前的亚马逊销售费用和亚马逊物流配送费用。</small>
      </div>
    </div>

      <div  id="search_div"  style="border:1px solid #B7BFDA;background:#F2F9FB;padding-top:15px;padding-left:10px;padding-bottom:35px">
        <div >
          <h5 style="color:#BF6B27;padding-left:20px"> 在Amazon.com上找到您的产品 </h5>
          <form class="form-inline">

            <div class="form-group mx-sm-3 mb-2" style="width:450px">
              <input type="text" class="form-control" id="product_keyword" style="width:450px" placeholder="产品名称">
            </div>
            <button type="button" class="btn btn-primary mb-2"   v-on:click="start_search" >搜索</button>

<!--            <input type="file" id="upload" class="btn btn-primary mb-2"    onchange="detect();" />-->

            <div class="file-container" style="display:inline-block;position:relative;overflow: hidden;vertical-align:middle">
              <button class="btn btn-success fileinput-button" type="button">用图搜索</button>
              <input type="file" id="upload" onchange="request_detect()" style="position:absolute;top:0;left:0;font-size:34px; opacity:0">
            </div>
            <span id="filename" style="vertical-align: middle"> </span>


          </form>
        </div>
      </div>


      <div  id="product_content" style="border:1px solid #B7BFDA;margin-top:15px;height:200px;display: none;">
        <div  v-if="current_product != null">
          <div  style="width:140px;float:left;padding:3px;text-align:center" >
              <div ><img style="width:120px;height:120px" v-bind:src="current_product.thumbStringUrl"></div>
            <div  style="margin-top:10px"><a target="_blank" :href="current_product.detail_url"><small>查看产品详情</small>  </a></div>
              <div style="margin-top:15px"><button type="button"  class="btn btn-default btn-xs" v-on:click="init_search_inner">尝试其它商品</button></div>
          </div>
          <div   style="overflow:hidden;">
            <h5 style="width:900px" v-text="current_product.title"> </h5>
            <div class="product_detail_div" ><span><b>ASIN:</b> </span>  <span v-text="current_product.asin"></span></div>
            <div class="product_detail_div" ><span><b>产品尺寸:</b> </span> <span v-text="current_product.volume" ></span> </div>
            <div class="product_detail_div" ><span><b>单位重量:</b> </span>  <span v-text="current_product.weight"></span>   <span v-text="current_product.weightUnit"></span></div>

            <div class="product_detail_div" ><span><b>价&nbsp;&nbsp;格:</b> </span>  <span style="color:#A1331A;font-weight:bold;" v-text="current_product.price"></span> </div>
            <div class="product_detail_div" ><span><b>评&nbsp;&nbsp;分:</b> </span>  <span style="font-weight:bold;" v-text="current_product.star"></span> </div>
            <div class="product_detail_div" ><span><b>评论数:</b> </span>  <span style="font-weight:bold;" v-text="current_product.review"></span> </div>


          </div>
        </div>
      </div>




     <div style="border:1px solid #B7BFDA;margin-top:20px;background:#F3F3F3">

           <div style="height:520px;margin:10px;padding-left:10px">
                <!-- left  content ----------->
                <div id="left_content" style="background:F8F8FF;border:1px solid #B7BFDA;width:430px;float:left;padding:8px;visibility: hidden">

                  <table>
                    <thead>
                    <tr style="height:1px">
                      <th scope="col" style="width:10%"></th>
                      <th scope="col" style="width:55%;text-align:right"></th>
                      <th scope="col" style="width:5%;text-align:right"></th>
                      <th scope="col" style="width:30%;text-align:right"></th>
                    </tr>
                    </thead>
                    <tbody>

                    <tr style="height:12px"><td colspan="4"><div style="background:#000000;height:1px;"></div></td></tr>
                    <tr><td><h5>收入</h5> </td> <td> </td> <td> </td> <td></td> </tr>

                    <tr>
                      <td> </td>
                      <td class="table-text">  销售价 <span class="glyphicon glyphicon-question-sign"></span></td>
                      <td> $ </td>
                      <td> <input type="number" class="form-control table-input" v-model="calculate_item.item_price" > </td>
                    </tr>


                    <tr style="height:12px" ><td colspan="4"><div style="background:#000000;height:1px"></div></td></tr>
                    <tr><td><h5>成本</h5></td> <td> </td> <td> </td> <td></td> </tr>

                    <tr>
                      <td> </td>
                      <td class="table-text"> 产品成本 <span class="glyphicon glyphicon-question-sign"></span> </td>
                      <td> $ </td>
                      <td> <input type="number" class="form-control table-input" v-model="calculate_item.product_cost" > </td>
                    </tr>

                    <tr>
                      <td> </td>
                      <td class="table-text"> 头程物流 <span class="glyphicon glyphicon-question-sign"></span> </td>
                      <td> $ </td>
                      <td> <input type="number" class="form-control table-input"  v-model="calculate_item.deliver_to_amazon" > </td>
                    </tr>


                    <tr>
                      <td> </td>
                      <td class="table-text"> 佣金  <span class="glyphicon glyphicon-question-sign"></span> </td>
                      <td> $ </td>
                      <td> <input type="number" class="form-control table-input"  v-model="calculate_item.brokerage" > </td>
                    </tr>


                    <tr>
                      <td> </td>
                      <td class="table-text"> FBA物流 <span class="glyphicon glyphicon-question-sign"></span> </td>
                      <td> $ </td>
                      <td> <input type="number" class="form-control table-input"  v-model="calculate_item.fba" > </td>
                    </tr>


                    <tr>
                      <td> </td>
                      <td class="table-text"> 获客成本（广告等） <span class="glyphicon glyphicon-question-sign"></span> </td>
                      <td> $ </td>
                      <td> <input type="number" class="form-control table-input"  v-model="calculate_item.marketing" > </td>
                    </tr>


                    <tr>
                      <td> </td>
                      <td class="table-text"> 仓储成本 <span class="glyphicon glyphicon-question-sign"></span> </td>
                      <td> $ </td>
                      <td> <input type="number" class="form-control table-input"  v-model="calculate_item.storage" > </td>
                    </tr>

                    <tr>
                      <td> </td>
                      <td class="table-text"> 其它  <span class="glyphicon glyphicon-question-sign"></span> </td>
                      <td> $ </td>
                      <td> <input type="number" class="form-control table-input"  v-model="calculate_item.others" > </td>
                    </tr>


                    <tr style="height:12px"><td colspan="4"><div style="background:#000000;height:1px"></div></td></tr>
                    <tr ><td><h5>利润</h5></td> <td> </td> <td> </td> <td></td> </tr>

                    <tr>
                      <td> </td>
                      <td class="table-text"> <b>毛利率</b> <span class="glyphicon glyphicon-question-sign"></span> </td>
                      <td> $ </td>
                      <td> <span id="net_profit"  class="table-text-right text_result_bold" ></span> </td>
                    </tr>

                    <tr>
                      <td> </td>
                      <td class="table-text"> <b>毛利润率</b> <span class="glyphicon glyphicon-question-sign"></span> </td>
                      <td> </td>
                      <td> <span  id="net_margin" class="table-text-right text_result_bold"> </span> </td>
                    </tr>


                    <tr style="height:12px"><td colspan="4"><div style="background:#000000;height:1px"></div></td></tr>
                    <tr>
                      <td> </td>
                      <td> </td>
                      <td> </td>
                      <td> <button type="button" class="btn btn-primary mb-2" v-on:click="calculate_revenue">计算</button></td>
                    </tr>



                    </tbody>
                  </table>


                </div>
                <!-- left  content ----------->

                <!-- right  content ----------->
                <div id="right_content" style="overflow:hidden;visibility: hidden;padding-right:15px">

                  <div    style="border:1px solid #B7BFDA;margin-top:15px;height:120px;background:#ffffff;margin-bottom:15px">
<!--                    <div><span><a href="">Price your FBA listings right </a></span></div>-->
<!--                    <div><span><a href="">These prices compete equally on Amazon </a></span></div>-->
<!--                    <div><span><a href="">Have you accounted for all of your costs? </a></span></div>-->

                  </div>


                  <div></div>
                </div>
                <!-- right  end ----------->
    </div>
     </div>




</div>


    <footer class="pt-4 border-top" style="margin-left:30px;margin-top:10px;margin-bottom:30px" >
      <div >
      <span>现在开始销售：</span>
      </div>

      <div><span><a href="https://www.amazon.ca/b/?node=13653457011&ref=RevCalRegAmazon">在亚马逊上注册</a></span></div>
      <div><span><a href="https://www.amazon.ca/b?ie=UTF8&node=13718755011&ref=RevCalStartFBA">亚马逊物流入门</a></span></div>
      <div><span><a href="https://sellercentral.amazon.ca/productsearch?ref=RevCalAddProd">将产品添加到您的列表中</a></span></div>

    </footer>

  </div>



</body>
  <script type="text/javascript" src="/static/js/calc-full.js" ></script>
  <script type="text/javascript" src="/static/js/network.js" ></script>
  <script type="text/javascript" src="/static/js/app.js" ></script>

</html>
