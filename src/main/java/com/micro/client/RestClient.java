package com.micro.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;


public abstract class RestClient {

	public static String doPost(String url,Map<String, Object> requestBody,Map<String, String> requestHeaders) {
		
		return null;
	}
	
	public static String doGet(String url, Map<String, String> requestHeaders) {
		 HttpClient client =  HttpClientBuilder.create().build();
		 HttpGet req= new HttpGet(url);
		 StringBuffer result=null;
		 try {
				HttpResponse response= client.execute(req);
				int responsecode=response.getStatusLine().getStatusCode();
				if( responsecode>=200 && responsecode<400) {
					BufferedReader rd = new BufferedReader(
							new InputStreamReader(response.getEntity().getContent()));
					
					result = new StringBuffer();
					String line = "";
					while ((line = rd.readLine()) != null) {
						result.append(line);
					}
				}
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
//	
//		// All this code should be part of its own repo
//		
//		String s= doGet("http://localhost:2375/v1.24/containers/json", null);
//		Gson gson= new Gson();
//		Type typeOfT=new TypeToken<List<Map<String,Object>>>(){}.getType();
//		List<Map <String,Object>> values =  gson.fromJson(s, typeOfT);
//		System.out.println(values);
//		values.stream().parallel().forEach((container)->{
//			String id=(String) container.get("Id");
//			getStatsForContainer(id);
//			});
//	}
//
//	private static void getStatsForContainer(String id) {
//		String stat= doGet("http://localhost:2375/v1.24/containers/"+id+"/stats?stream=0", null);
//		Gson gson= new Gson();
//		Type typeOfT=new TypeToken<Map<String,Object>>(){}.getType();
//		Map <String,Object> values =  gson.fromJson(stat, typeOfT);
//		
//	}

}
