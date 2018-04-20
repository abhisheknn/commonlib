package com.nfactorial.jwt;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWT {

	public boolean verify(String jwt,String  stringKey) {
		byte[] decodedKey = Base64.getDecoder().decode(stringKey);
		PublicKey publicKey =null;
		try {
			KeyFactory kf = KeyFactory.getInstance("RSA");
			 publicKey = kf.generatePublic(new X509EncodedKeySpec(decodedKey));
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		//String token="eyJhbGciOiJSUzI1NiJ9.eyJhY2Nlc3NUeXBlIjoiZGVmYXVsdCIsIiBpYXQiOiIxNTIzMDgyNDAxIiwiZXhwIjoxNTIzMDk4MDg5LCIgZXhwIjoiMTUyMzA0NjQwMSIsImlhdCI6MTUyMzA5MDg4OX0.IFTEyBfnGYK1yKicUH991fo51f3tr0XqNf8ByrFDJjiZZyUmT6icskIbPFTAGytmIfekyXUjUiqGdjem0AvgGpee2-84_4mVLP7fIioU69DfYA--wrPp971-SWkTwWM9797M5R_oWbqDcIxUabbwETq7O3_zq2OlReByr_xGdSOaQ7sPIAxComWFGWq2Au2rUn4QxmBFNp_TPwNSZK7YScGUNJ8y7qerebi8kKEm5dbSo3gJJGtvfIzXRsEvYDl6zJWUh-CjPhTMFoT4Yt-QAgE2KF6x_MC9ntBjxASpjfF0C7EjA0x4VEyUj5xntYsgwroONArknQ4XATNXf4bj5JxBSOl8jeo8JTETY0_x_0OAt4AlfVZO1J1lInLaihsAkQWda61c19TaUzUcG1XsnNxmrKRUfNB1QIwzdIfXuvvhZUUbhI_OjFMs7TqmhCYbDup21ujaIIfT4PGOCSxon1Mslzhr6cGAAFaf4ejBVy0Wx4scZ9XUa2UOYbPq7taFo5y-DCPVrqNLZwdSg36kGkooGnXKXb9X2Vy7T3b8Ilzh1-AkiRzJx5K1N85hjb6C5nPCDk4zmVKavNMBOxVyvnaKhWCEJS_5bEcr3XJk1Gice1si7TP6H2j4-8PRBLUydzlKyZflOPgwcBHn-mwCf5waMbJ321eC3ujiLLaZsEI";
		Claims claims=Jwts.parser()         
			       .setSigningKey(publicKey)
			       .parseClaimsJws(jwt).getBody();
		return true;
	}
}
