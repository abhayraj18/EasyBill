package com.easybill.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.security.access.AccessDeniedException;

import com.easybill.pojo.LoginRequest;
import com.easybill.pojo.UserVO;
import com.google.gson.Gson;

public class HttpUtil {

	public static void main(String[] args) throws IOException {
		String response = Constants.EMPTY_STRING;
		UserVO userVO = new UserVO();
		userVO.setName("ahjhjk");
		userVO.setPhoneNumber("7987987891");
		userVO.setAddress("KR Puram");
		userVO.setPassword("Abh@yabcdefghijklmnop001");
		userVO.setEmail("a@gmail.com");
		userVO.setUserType("WHOLESALER");

		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername("abcdefgh@gmail.com");
		loginRequest.setPassword("Abh@y001");
		JSONObject json = null;
		try {
			response = doPost("http://localhost:8090/auth/login", new Gson().toJson(loginRequest));
			System.out.println(response);
			json = (JSONObject) JSONValue.parse(response);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			response = doPost("http://localhost:8090/user/add", new Gson().toJson(userVO));
			System.out.println(response);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			String url = "http://localhost:8090/distributor/get/1";
			List<NameValuePair> params = new LinkedList<NameValuePair>();

			params.add(new BasicNameValuePair("email", "abcd@gmail.com"));

			// String paramString = URLEncodedUtils.format(params, "utf-8");

			// url += paramString;
			response = doGet(url, json.get("accessToken").toString());
			System.out.println(response);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		}
	}

	public static CloseableHttpClient getHTTPClient() {
		return HttpClientBuilder.create().build();
	}

	public static String doGet(String url, String authorizationHeader) throws IOException {
		CloseableHttpClient client = getHTTPClient();
		CloseableHttpResponse response = null;
		try {
			HttpGet get = new HttpGet(url);
			get.addHeader("Authorization", authorizationHeader);
			response = client.execute(get);
			return EntityUtils.toString(response.getEntity());
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public static String doPost(String url, String data) throws IOException {
		CloseableHttpClient client = getHTTPClient();
		CloseableHttpResponse response = null;
		try {
			HttpPost post = new HttpPost(url);
			HttpEntity entity = new StringEntity(data, ContentType.APPLICATION_JSON);
			post.setEntity(entity);
			response = client.execute(post);
			return EntityUtils.toString(response.getEntity(), Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
