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


public abstract class RestClient {
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
		 if(null !=requestHeaders && mandatoryHeaders!=null) {
		 for(Map.Entry<String, String> entry:requestHeaders.entrySet()) {
			 httpPost.addHeader(entry.getKey(), entry.getValue());
			
		 }
		 for(Map.Entry<String, String> entry:mandatoryHeaders.entrySet()) {
			 httpPost.addHeader(entry.getKey(), entry.getValue());
		 }
		 }
		 HttpResponse httpResponse = client.execute(httpPost);
		 
		 
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
		client.close();	
			return response;
	}
	
	public  String doGet(String url, Map<String, String> requestHeaders) {
		 HttpClient client =  HttpClientBuilder.create().build();
		 HttpGet httpGet= new HttpGet(url);
		 StringBuffer result=null;
		 try {
			 if(null !=requestHeaders && mandatoryHeaders!=null) {
				 for(Map.Entry<String, String> entry:requestHeaders.entrySet()) {
					 httpGet.addHeader(entry.getKey(), entry.getValue());
				 }
				 for(Map.Entry<String, String> entry:mandatoryHeaders.entrySet()) {
					 httpGet.addHeader(entry.getKey(), entry.getValue());
				 }
				 }
				HttpResponse response= client.execute(httpGet);
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return result.toString();
	}
	
	
//	public static void main(String[] args) {
//		RestClient client= new RestClient();
//		Map<String, String> requestHeaders= new HashMap<>();
//		requestHeaders.put("Content-Type","application/json");
//		Map<String, Object> requestbody= new HashMap<>();
//		requestbody.put("macAddress", "");
//		try {
//			Response response=client.doPost("http:///auth/register",requestbody ,requestHeaders);
//			System.out.println(response.getStatusLine());
//			System.out.println(response.getEntity());
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
}
