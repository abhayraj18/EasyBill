package com.bill.management.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.bill.management.pojo.DistributorVO;
import com.google.gson.Gson;

public class HttpUtil {

	public static void main(String[] args) throws IOException {
		Response response = null;
		try {
			response = doGet("http://localhost:8090/BillManagement/distributor/get/1");
			System.out.println(response.getResponse());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		DistributorVO distributorVO = new DistributorVO();
		distributorVO.setName("Samyak Agencies");
		distributorVO.setPhoneNumber("97879879899");
		distributorVO.setAddress("KR Puram");
		try {
			response = doPost("http://localhost:8090/BillManagement/distributor/add", new Gson().toJson(distributorVO));
			System.out.println(response.getResponse());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static CloseableHttpClient getHTTPClient() {
		return HttpClientBuilder.create().build();
	}
	
	public static Response doGet(String url) throws IOException {
		CloseableHttpClient client = getHTTPClient();
		CloseableHttpResponse response = null;
		try {
			HttpGet get = new HttpGet(url);
			response = client.execute(get);
			return ResponseUtil.getResponse(EntityUtils.toString(response.getEntity()), response.getStatusLine().getStatusCode());
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseUtil.getResponse(e.getMessage(), Constants.FAIL.value());
		}
	}
	
	public static Response doPost(String url, String data) throws IOException {
		CloseableHttpClient client = getHTTPClient();
		CloseableHttpResponse response = null;
		try {
			HttpPost post = new HttpPost(url);
			HttpEntity entity = new StringEntity(data, ContentType.APPLICATION_JSON);
			post.setEntity(entity);
			response = client.execute(post);
			return ResponseUtil.getResponse(EntityUtils.toString(response.getEntity()), response.getStatusLine().getStatusCode());
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseUtil.getResponse(e.getMessage(), Constants.FAIL.value());
		}
	}
}
