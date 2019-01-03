package com.micro.jwt;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.micro.constant.AppConstants;
import com.micro.cert.KeyProvider;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTProvider {
	
	
	public String getToken(String entityName, Map<String, Object> claims,KeyProvider keyProvider) {
		String s = null;
		Calendar expires = Calendar.getInstance();
		expires.roll(Calendar.HOUR, 2);
		try {

			Key key = keyProvider.getPrivateKey(AppConstants.CERTALIAS); // Get this from configuration server
			s = Jwts.builder()
					.setClaims(claims)
					.setSubject(entityName)	
					.setIssuedAt(new Date())
					.setExpiration(expires.getTime())
					.signWith(SignatureAlgorithm.RS256, key).compact();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
}
