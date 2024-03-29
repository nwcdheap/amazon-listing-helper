package cn.nwcdcloud.samples.listingHelper;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		assertTrue(true);
	}

	@org.junit.Test
	public void testJSoup() {

		// 创建HttpClient
		CloseableHttpClient httpClient = HttpClients.createDefault();

		// 目标网址
		String url = "https://www.amazon.com/gp/product/B082PMS7TB/ref=silver_xx_cont_revecalc";

		// 创建请求方法
		HttpGet httpGet = new HttpGet(url);

		// 设置Header模拟浏览器行为
		httpGet.setHeader("User-Agent",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 11_1_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");

		httpGet.addHeader("Cookie",
				"ubid-main=134-0374546-0715727;  x-main=\"DMkUzMa9a3O2I3wY@wEZkD1baDh6FEsRujawQ@GDKJ7WXANk5?Q9p?o5ZeBe4ihr\"; ");
		try {
			// 发送请求，收取响应
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == HttpServletResponse.SC_OK) {
				String entity = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
//                    System.out.println(entity);
				Document document = Jsoup.parse(entity, "utf-8");
				System.out.println("价格 ： " + document.getElementById("priceblock_ourprice").text());
				System.out.println("review ： " + document.getElementById("acrCustomerReviewText"));
				System.out.println("star ： " + document.getElementsByClass("a-icon-alt").first());
			}
			httpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}