package com.zjy.phoenix.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt工具类
 * 
 * @author zxl
 * @date 2018年10月15日
 */
public class JwtUtil {

	private JwtUtil(){};  //私有化构造方法
	private static final String SECRET = Const.JWT_SECRET;
	private static final String ISSUER = Const.JWT_ISSUER;
	
	/**
	 * 创建jwt
	 *
	 * @param ttlMillis 过期的时间长度
	 * @return
	 * @throws Exception
	 */
	public static String createJWT(Map<String, Object> claims, long ttlMillis) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
		long nowMillis = System.currentTimeMillis();// 生成JWT的时间
		Date now = new Date(nowMillis);
		JwtBuilder builder = Jwts.builder() // 这里其实就是new一个JwtBuilder，设置jwt的body
				.setClaims(claims) // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
				.setIssuer(ISSUER)
				// .setId(id) //设置jti(JWTID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
				.setIssuedAt(now) // iat: jwt的签发时间
				// .compressWith(CompressionCodecs.GZIP)
				//.setSubject(subject) // sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
				.signWith(signatureAlgorithm, SECRET);// 设置签名使用的签名算法和签名使用的秘钥
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp); // 设置过期时间
		}
		return builder.compact(); // 就开始压缩为xxxxxxxxxxxxxx.xxxxxxxxxxxxxxx.xxxxxxxxxxxxx这样的jwt
		// 打印了一哈哈确实是下面的这个样子
		// eyJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiJEU1NGQVdEV0FEQVMuLi4iLCJzdWIiOiIiLCJ1c2VyX25hbWUiOiJhZG1pbiIsIm5pY2tfbmFtZSI6IkRBU0RBMTIxIiwiZXhwIjoxNTE3ODI4MDE4LCJpYXQiOjE1MTc4Mjc5NTgsImp0aSI6Imp3dCJ9.xjIvBbdPbEMBMurmwW6IzBkS3MPwicbqQa2Y5hjHSyo
	}

	/**
	 * 解密jwt
	 * 
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	public static Claims parseJWT(String jwt) {

		Claims claims = Jwts.parser().requireIssuer(ISSUER).setSigningKey(SECRET).parseClaimsJws(jwt).getBody();// 设置需要解析的jwt


		return claims;
	}

	// 测试
	public static void main(String[] args) throws InterruptedException {
		Map<String, Object> data = new HashMap();
		data.put("uid", "12");
		data.put("schoolId", "12");
		data.put("role", "12");
		data.put("userName", "12");
		data.put("schoolName", "12");
		data.put("roleName", "12");
		String jwt = JwtUtil.createJWT(data, 2000);
		//Thread.sleep(3000);
		Claims claims = JwtUtil.parseJWT(jwt);
		System.out.println(jwt);
		System.out.println(claims);
	}

}