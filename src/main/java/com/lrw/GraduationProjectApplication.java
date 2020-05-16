package com.lrw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lrw.mapper")
public class GraduationProjectApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(GraduationProjectApplication.class, args);
//		String ip="106.13.146.228";
//		String ak="eSaNKZQUlXLX95fNjW4vCtHOn0OG67i5";
//		ip=URLEncoder.encode(ip, "UTF-8"); 
//		ak=URLEncoder.encode(ak, "UTF-8"); 
//		
//		//String sn = SnCal.getSN();
//		Map<String,String> map = new HashMap();
//		map.put("ip", ip);
//		map.put("ak", ak);
//		//map.put("sn", sn);
//		String  content = HttpClientUtil.doGet("http://api.map.baidu.com/location/ip?ip="+ip+"&ak="+ak);
//		content = URLEncoder.encode(content, "UTF-8"); 
//		System.err.println(content);
	}
}
	