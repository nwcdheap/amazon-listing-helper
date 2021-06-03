package cn.nwcdcloud.samples.listingHelper;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSearch {

	private static final Logger logger = LoggerFactory.getLogger(TestSearch.class);

	public static void main(String[] args) {
		logger.info(search());
	}

	private static String search() {
		String url = "https://sellercentral.amazon.com/fba/profitabilitycalculator/productmatches?searchKey=table&searchType=keyword&language=en_US&profitcalcToken=1235";
//		String url="https://www.nowfox.com/article/6013";
//		String url="http://127.0.0.1/test";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		//浏览器cookie
//		httpGet.setHeader("cookie", "aws-priv=eyJ2IjoxLCJldSI6MCwic3QiOjB9; aws-target-static-id=1563939275876-845324; aws-target-data={\"support\":\"1\"}; aws-ubid-main=508-1850186-6738743; ubid-main=134-0374546-0715727; _mkto_trk=id:112-TZM-766&token:_mch-aws.amazon.com-1563939276539-69083; aws-business-metrics-last-visit=1583890500300; regStatus=registered; aws-session-id=270-1895978-5801660; aws-target-visitor-id=1563939275883-911860.38_0; aws-analysis-id=270-1895978-5801660; s_vn=1628929071659&vn=21; s_invisit=true; s_dslv_s=Less than 7 days; s_depth=203; s_dslv=1608100954805; s_nr=1608100954825-Repeat; ubid-acbus=133-7352487-8203956; __Host_mlang=zh_CN; session-id=130-2443526-2662953; s_pers= s_fid=320126ABC3643D2D-0741BA861A1088A2|1768204605633; s_dl=1|1610440005635; gpv_page=US%3ASD%3ASOA-pricing|1610440005645; s_ev15=%5B%5B%27SDUSSOADirect%27%2C%271610099018405%27%5D%2C%5B%27SCUSWPDirect%27%2C%271610331745885%27%5D%2C%5B%27ELUSSOA-jzby18uol0.feishu.cn%27%2C%271610438205654%27%5D%5D|1768204605654;; csm-hit=tb:F3HQCZBD53FMS0GXTCSE+s-F3HQCZBD53FMS0GXTCSE|1610676435393&t:1610676435393&adb:adblk_no; x-main=\"DMkUzMa9a3O2I3wY@wEZkD1baDh6FEsRujawQ@GDKJ7WXANk5?Q9p?o5ZeBe4ihr\"; at-main=Atza|IwEBICoLSSLfdcA8Jk16f0s0CKs22FSKO64UMlCT0ZI_7RuSsqh0lZAEyCH8zeXiFum0KNt9MgGseNB9TY6EugZcU3zi0q7zD7C2MhrSEZc2T1MyGsTLQ3d2I-Kzrdca0fXVz3cYSpzBuscEeL2eJXrV2aX97feQFo25BEr_6QtTLRuqNtRIdAS9Q5K7t_DFYjaoQz7Vw98Vo_0R5EryuuNcbbpb; sess-at-main=\"ebKnqq1km06dJHUP2L29jIv51/nLmqDnzBXfMC2t0jQ=\"; sst-main=Sst1|PQEqXqOWV4YsYg6EgJbAOBOOCY9M5I0wXvUYTAV5tnW0TH3yApdzTO4OhYvMKNkCmWTcTf_nKw6IdFQJ8Z38nK_QF8FU5lsMjG9vuaMHBpoX4tnGyFgnGYwWHW2Pu7vWLMXgSsT2Atu4mt_cR6O85LA-d7uIgju6QSe1ohacQI-5Dld1qmNHvi9ZGjHEqTx2OTY2-Ou9p89ifHCYBUl2FomqnUreC_RZba8gOb08Jbtt-P4xw6WY3h4laZViF4gmMw07lHMccFYzduP0jXimFr045hlxFyQoehD2Vr7wdBO8Q8Y; session-id-time=2082787201l; s_fid=269BA2C0CC618B80-2CC52C63D0CE9DCA; noflush_locale=zh-CN; aws-session-id-time=1611718687l; sp-cdn=\"L5Z9:CN\"; lc-main=zh_CN; i18n-prefs=USD; awsc-color-theme=light; remember-account=false; aws-account-alias=596014444656; aws-userInfo={\"arn\":\"arn:aws:iam::596014444656:user/nowfox\",\"alias\":\"596014444656\",\"username\":\"nowfox\",\"keybase\":\"laf+0x5rAVQkW9m2GOxzuf5cktAUVJJlBbZibniG3mI\\u003d\",\"issuer\":\"http://signin.aws.amazon.com/signin\",\"signinType\":\"PUBLIC\"}; aws-userInfo-signed=eyJ0eXAiOiJKV1MiLCJrZXlSZWdpb24iOiJ1cy1lYXN0LTEiLCJhbGciOiJFUzM4NCIsImtpZCI6IjkxM2YxMWFjLTVlNGQtNGEzNC1hMmIwLTM1NjcxNTBkMWY1MCJ9.eyJzdWIiOiI1OTYwMTQ0NDQ2NTYiLCJzaWduaW5UeXBlIjoiUFVCTElDIiwiaXNzIjoiaHR0cDpcL1wvc2lnbmluLmF3cy5hbWF6b24uY29tXC9zaWduaW4iLCJrZXliYXNlIjoibGFmKzB4NXJBVlFrVzltMkdPeHp1ZjVja3RBVVZKSmxCYlppYm5pRzNtST0iLCJhcm4iOiJhcm46YXdzOmlhbTo6NTk2MDE0NDQ0NjU2OnVzZXJcL25vd2ZveCIsInVzZXJuYW1lIjoibm93Zm94In0.Z-H6CzeMmlCJLPyemVI8uyaTtodTjocOg8wnHSjMO3-lIjhNjI25p0V5OFkbN6hzTw1PfG4RJfRjgOA1GVA1PM5hyP4ok0EsQQ8cEKpYoNzaGW7M_0khjUsCEhvQMiBx; session-token=L21VEGTxzekN8a+R89Jr3uirBaC9zlRlz01ebyYHKhd92QnY+hkpjY5RzwVOGIwbmAh4K6u94rKz5ziIwDtcXDF9+mQLdW7K/1iCdHx2SSKj5sqvHNNEN9p76KpgAXeVi1MgMEwpGHIh16+93cZalzpkbRfDtypk0UTAyl/QH1IvACpvQ1x/VydMW6sGVPmuGYyfpGzYlraTSQkhBHO6dUsTMcV+yYdW20QAq33Ocyc=");
		//postman Cookie
//		httpGet.setHeader("cookie", "session-id=142-6533695-6274244; session-id-time=2082787201l; i18n-prefs=USD; sp-cdn=\"L5Z9:CN\"; ubid-main=134-8183957-6006445; session-token=uS2aC+OYFMTmx36bg71oEt05JZ5FS/7xc/BVVwxHcAQG6ArmJRtXQcxB9sQbG1iF60f/70pyVYeeTazLOqVxe6tOtAUCDAxCsYyW0yBVMInrYnheE2N3pPeAJBCfK63+YY1rilap/g4hP5y+iBlpmf0/hilfn3vlxoiY4Neku3kkbnvcpLkXa3oozW8GzYemn1CdGw1+XuK26+SZZjzmoh3b5fZzfaah");
//		httpGet.setHeader("postman-token", "c9f17d1b-1605-4036-a35b-1fc7bc2ab152");
		httpGet.setHeader("host", "sellercentral.amazon.com");
//		httpGet.setHeader("User-Agent",
//				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36");
		try {
			// 发送请求，收取响应
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
			String entity = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
			logger.info("状态码:{}", httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() >= HttpServletResponse.SC_OK
					&& httpResponse.getStatusLine().getStatusCode() < HttpServletResponse.SC_MULTIPLE_CHOICES) {
				if (logger.isDebugEnabled()) {
					logger.debug("查询结果:{}", entity);
				}
				return entity;
			} else {
				logger.warn("请求Amazon报错，url:{},结果{}", url, entity);
				return "";
			}
		} catch (IOException e) {
			return "";
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				logger.warn("关闭流报错", e);
			}
		}
	}

}
