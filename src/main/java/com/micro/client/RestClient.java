package com.micro.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.BasicHeader;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;


public  class RestClient {
private  Gson gson= new Gson();
private  Map<String, String> mandatoryHeaders=null;

public void setMandatoryHeaders(Map<String, String> mandatoryHeaders) {
	this.mandatoryHeaders = mandatoryHeaders;
}

	public  Response doPost(String url,Map<String, Object> requestBody,Map<String, String> requestHeaders) throws ClientProtocolException, IOException,UnknownHostException {
		 Header header = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");
		 List<Header> headers = Lists.newArrayList(header);
		 CloseableHttpClient client =  HttpClientBuilder.create().setDefaultHeaders(headers).build();
		 HttpPost httpPost= new HttpPost(url);
		 String json=gson.toJson(requestBody);
		 StringEntity entity = new StringEntity(json);
		 httpPost.setEntity(entity);
		 setHeaders(requestHeaders, httpPost);
		 HttpResponse httpResponse = client.execute(httpPost);
		 Response response = getReponse(httpResponse);
		client.close();	
			return response;
	}
	
	public  Response doPost(String url,String requestBody,Map<String, String> requestHeaders) throws ClientProtocolException, IOException,UnknownHostException {
		 Header header = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");
		 List<Header> headers = Lists.newArrayList(header);
		 CloseableHttpClient client =  HttpClientBuilder.create().setDefaultHeaders(headers).build();
		 HttpPost httpPost= new HttpPost(url);
		 StringEntity entity = new StringEntity(requestBody);
		 httpPost.setEntity(entity);
		 setHeaders(requestHeaders, httpPost);
		 HttpResponse httpResponse = client.execute(httpPost);
		 Response response = getReponse(httpResponse);
		 client.close();	
		 return response;
	}

	private void setHeaders(Map<String, String> requestHeaders, AbstractHttpMessage http) {
		if(null !=requestHeaders) {
		 for(Map.Entry<String, String> entry:requestHeaders.entrySet()) {
			 http.addHeader(entry.getKey(), entry.getValue());
			
		 }
		 }
		 if(null !=mandatoryHeaders) {
		 for(Map.Entry<String, String> entry:mandatoryHeaders.entrySet()) {
			 http.addHeader(entry.getKey(), entry.getValue());
		 }
		 }
	}

	private Response getReponse(HttpResponse httpResponse) throws IOException {
		Response response = new Response();
		 response.setStatusLine(gson.toJson(httpResponse.getStatusLine()));
		 int responsecode=httpResponse.getStatusLine().getStatusCode();
			if( responsecode>=200 && responsecode<400) {
				BufferedReader rd = new BufferedReader(
						new InputStreamReader(httpResponse.getEntity().getContent()));
				
				StringBuffer result = new StringBuffer();
				String line = "";
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
			response.setEntity(result.toString());
			}
		return response;
	}
	
	public Response doGet(String url, Map<String, String> requestHeaders) {
		 HttpClient client =  HttpClientBuilder.create().build();
		 HttpGet httpGet= new HttpGet(url);
		 Response response=null;
		 try {
			 	setHeaders(requestHeaders, httpGet);
				HttpResponse httpResponse= client.execute(httpGet);
				response =getReponse(httpResponse);
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return response;
	}
	
	
//	public static void main(String[] args) {
//		RestClient client= new RestClient();
//		Map<String, String> requestHeaders= new HashMap<>();
//		requestHeaders.put("Content-Type","application/json");
//		try {
//			Response response=client.doGet("http:///auth/getPublicKey",requestHeaders);
//			System.out.println(response.getEntity());
//			System.out.println(response);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//	}
//		}
}
