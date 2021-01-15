package cn.nwcdcloud.samples.listingHelper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }


    @org.junit.Test
    public void testJSoup(){

        try {


            //创建HttpClient
            CloseableHttpClient httpClient = HttpClients.createDefault();

            //目标网址
            String url = "https://www.amazon.com/gp/product/B082PMS7TB/ref=silver_xx_cont_revecalc";

            //创建请求方法
            HttpGet httpGet = new HttpGet(url);

            //设置Header模拟浏览器行为
            httpGet.setHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 11_1_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");

//           Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0.2 Safari/605.1.15
//           Mozilla/5.0 (Macintosh; Intel Mac OS X 10.16; rv:84.0) Gecko/20100101 Firefox/84.0
//           Mozilla/5.0 (Macintosh; Intel Mac OS X 11_1_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36


            try {
                //发送请求，收取响应
                CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

                if(httpResponse.getStatusLine().getStatusCode() == 200){
                    //解析响应
                    String entity = EntityUtils.toString(httpResponse.getEntity());
//                    System.out.println(entity);

                    Document document = Jsoup.parse(entity, "utf-8");
//                    Document document = Jsoup.connect("https://www.amazon.com/gp/product/B082PMS7TB/ref=silver_xx_cont_revecalc").get();
                    System.out.println( "价格 ： " +  document.getElementById("price_inside_buybox"));
                    System.out.println( "review ： " +  document.getElementById("acrCustomerReviewText"));

                }

                EntityUtils.consume(httpResponse.getEntity());
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }





//            Document document = Jsoup.parse(new File("d://1.html"), "utf-8");
            Document document = Jsoup.connect("https://www.amazon.com/gp/product/B082PMS7TB/ref=silver_xx_cont_revecalc").get();
//            System.out.println(document);
//            System.out.println( "价格 ： " +  document.getElementById("price_inside_buybox"));


//            System.out.println( "review ： " +  document.getElementById("acrCustomerReviewText"));




        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}