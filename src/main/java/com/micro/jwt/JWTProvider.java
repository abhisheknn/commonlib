package com.micro.jwt;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.micro.auth.constant.AppConstants;
import com.micro.cert.KeyProvider;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTProvider {
	KeyProvider keyProvider;
	
	public void setKeyProvider(KeyProvider keyProvider) {
		this.keyProvider=keyProvider;
	}
	public String getToken(String userName, Map<String, Object>   claims) {
		String s = null;
		Calendar expires = Calendar.getInstance();
		expires.roll(Calendar.HOUR, 2);
		try {

			Key key = keyProvider.getPrivateKey(AppConstants.CERTALIAS); // Get this from configuration server
			s = Jwts.builder()
					.setClaims(claims)
					.setSubject(userName)	
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
