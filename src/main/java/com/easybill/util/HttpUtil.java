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
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.security.access.AccessDeniedException;

import com.easybill.pojo.ChangePasswordForm;
import com.easybill.pojo.LoginRequest;
import com.easybill.pojo.UserVO;
import com.google.gson.Gson;

public class HttpUtil {

	public static void main(String[] args) throws IOException {
		String response = Constants.EMPTY_STRING;
		UserVO userVO = new UserVO();
		userVO.setName("Abhay");
		userVO.setPhoneNumber("7987987811");
		userVO.setAddress("KR Puram");
		userVO.setPassword("Abcd@001");
		userVO.setEmail("abcde@gmail.com");
		userVO.setUserType("DISTRIBUTOR");

		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername("abcd@gmail.com");
		loginRequest.setPassword("Abcd@001");
		JSONObject json = null;
		try {
			response = doPost("http://localhost:8090/auth/login", new Gson().toJson(loginRequest), null);
			System.out.println(response);
			json = (JSONObject) JSONValue.parse(response);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			response = doPost("http://localhost:8090/user/add", new Gson().toJson(userVO), null);
			System.out.println(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		response = doPut("http://localhost:8090/user/inactivate/13", json.get("accessToken").toString());
		System.out.println(response);

		try {
			String url = "http://localhost:8090/user/get/14";
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
		
		ChangePasswordForm changePasswordForm = new ChangePasswordForm();
		changePasswordForm.setId(1);
		changePasswordForm.setCurrentPassword("Abcd@001");
		changePasswordForm.setNewPassword("Abcd@001");
		
		try {
			response = doPost("http://localhost:8090/user/changePassword", new Gson().toJson(changePasswordForm), json.get("accessToken").toString());
			System.out.println(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JSONObject j = new JSONObject();
		j.put("emailId", "abcde@gmail.com");
		String url = "http://localhost:8090/user/sendResetPasswordEmail";
		response = doPost(url, j.toJSONString(), null);
		System.out.println(response);
		
		/*EditUserForm userForm = new EditUserForm();
		userForm.setId(5);
		userForm.setName("abcd");
		userForm.setAddress("abcde");
		userForm.setPhoneNumber("9879879798");
		userForm.setEmail("abcdefgh@gmail.com");
		
		try {
			response = doPost("http://localhost:8090/user/edit", new Gson().toJson(userForm), json.get("accessToken").toString());
			System.out.println(response);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

	private static String doPut(String url, String authorizationHeader) {
		CloseableHttpClient client = getHTTPClient();
		CloseableHttpResponse response = null;
		try {
			HttpPut put = new HttpPut(url);
			put.addHeader("Authorization", authorizationHeader);
			response = client.execute(put);
			return EntityUtils.toString(response.getEntity());
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
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

	public static String doPost(String url, String data, String authorizationHeader) throws IOException {
		CloseableHttpClient client = getHTTPClient();
		CloseableHttpResponse response = null;
		try {
			HttpPost post = new HttpPost(url);
			HttpEntity entity = new StringEntity(data, ContentType.APPLICATION_JSON);
			post.setEntity(entity);
			post.addHeader("Authorization", authorizationHeader);
			response = client.execute(post);
			return EntityUtils.toString(response.getEntity(), Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
