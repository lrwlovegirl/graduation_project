package com.lrw.address;

/*1. 第三方API
ps:下面参数ip:	218.192.3.42 用于测试
	淘宝API：http://ip.taobao.com/service/getIpInfo.php?ip=218.192.3.42
	新浪API：http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=218.192.3.42
	pconline API：http://whois.pconline.com.cn/
	百度API：http://api.map.baidu.com/location/ip?ip=218.192.3.42
2. 工具类
AddressUtils.java
[java] view plain copy */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSONObject;


public class AddressUtils {  

	public static String getAddresses(String ip)  
			throws UnsupportedEncodingException {  
		// 这里调用淘宝API  
		
		String encodingString="utf-8";
		String urlStr = "http://ip.taobao.com/service/getIpInfo.php";  
		// 从http://whois.pconline.com.cn取得IP所在的省市区信息  
		String returnStr = getResult(urlStr, ip, encodingString);  
		if (returnStr != null) {  
			// 处理返回的省市区信息  
			returnStr = decodeUnicode(returnStr);  
			String[] temp = returnStr.split(",");  
			if(temp.length<3){  
				return "0";//无效IP，局域网测试  
			}  
			JSONObject obj = JSONObject.parseObject(returnStr);
			Object object = obj.get("data");
			object = (JSONObject) object;

			String country="";
			String region="";
			String city="";
			String area="";

			country= (String) ((JSONObject) object).get("country");
			region= (String) ((JSONObject) object).get("region");
			city= (String) ((JSONObject) object).get("city");
			area= (String) ((JSONObject) object).get("area");
			String address=country+area+region+city;
			if(area.equals(region)||region.equals(city)) {
				address=country+region;
			}
			return address;  
		}  
		return null;  
	}  

	private static String getResult(String urlStr, String content, String encoding) {  
		URL url = null;  
		HttpURLConnection connection = null;  
		try {  
			url = new URL(urlStr);  
			connection = (HttpURLConnection) url.openConnection();// 新建连接实例  
			connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫秒  
			connection.setReadTimeout(2000);// 设置读取数据超时时间，单位毫秒  
			connection.setDoOutput(true);// 是否打开输出流 true|false  
			connection.setDoInput(true);// 是否打开输入流true|false  
			connection.setRequestMethod("POST");// 提交方法POST|GET  
			connection.setUseCaches(false);// 是否缓存true|false  
			connection.connect();// 打开连接端口  
			DataOutputStream out = new DataOutputStream(connection  
					.getOutputStream());// 打开输出流往对端服务器写数据  
			out.writeBytes(content);// 写数据,也就是提交你的表单 name=xxx&pwd=xxx  
			out.flush();// 刷新  
			out.close();// 关闭输出流  
			BufferedReader reader = new BufferedReader(new InputStreamReader(  
					connection.getInputStream(), encoding));// 往对端写完数据对端服务器返回数据  
			// ,以BufferedReader流来读取  
			StringBuffer buffer = new StringBuffer();  
			String line = "";  
			while ((line = reader.readLine()) != null) {  
				buffer.append(line);  
			}  
			reader.close();  
			return buffer.toString();  
		} catch (IOException e) {  
			e.printStackTrace();  
		} finally {  
			if (connection != null) {  
				connection.disconnect();// 关闭连接  
			}  
		}  
		return null;  
	}  

	public static String decodeUnicode(String theString) {  
		char aChar;  
		int len = theString.length();  
		StringBuffer outBuffer = new StringBuffer(len);  
		for (int x = 0; x < len;) {  
			aChar = theString.charAt(x++);  
			if (aChar == '\\') {  
				aChar = theString.charAt(x++);  
				if (aChar == 'u') {  
					int value = 0;  
					for (int i = 0; i < 4; i++) {  
						aChar = theString.charAt(x++);  
						switch (aChar) {  
						case '0':  
						case '1':  
						case '2':  
						case '3':  
						case '4':  
						case '5':  
						case '6':  
						case '7':  
						case '8':  
						case '9':  
							value = (value << 4) + aChar - '0';  
							break;  
						case 'a':  
						case 'b':  
						case 'c':  
						case 'd':  
						case 'e':  
						case 'f':  
							value = (value << 4) + 10 + aChar - 'a';  
							break;  
						case 'A':  
						case 'B':  
						case 'C':  
						case 'D':  
						case 'E':  
						case 'F':  
							value = (value << 4) + 10 + aChar - 'A';  
							break;  
						default:  
							throw new IllegalArgumentException(  
									"Malformed      encoding.");  
						}  
					}  
					outBuffer.append((char) value);  
				} else {  
					if (aChar == 't') {  
						aChar = '\t';  
					} else if (aChar == 'r') {  
						aChar = '\r';  
					} else if (aChar == 'n') {  
						aChar = '\n';  
					} else if (aChar == 'f') {  
						aChar = '\f';  
					}  
					outBuffer.append(aChar);  
				}  
			} else {  
				outBuffer.append(aChar);  
			}  
		}  
		return outBuffer.toString();  
	}  

	public static void main(String[] args) {  
		AddressUtils addressUtils = new AddressUtils();  
		// 测试ip 219.136.134.157 中国=华南=广东省=广州市=越秀区=电信  
		String ip = "106.13.146.228";  
		String address = "";  
		try {  
			address = addressUtils.getAddresses("ip="+ip);  
		} catch (UnsupportedEncodingException e) {  
			// TODO Auto-generated catch block  
			e.printStackTrace();  
		}  
		// 输出结果为：广东省,广州市,越秀区  
	}  

	//	public void getAddressByIp(String ip) throws Exception {  
	//		// 参数ip  
	//		ip = "106.13.146.228";  
	//		// json_result用于接收返回的json数据  
	//		String json_result = null;  
	//		try {  
	//			json_result = AddressUtils.getAddresses("ip=" + ip, "utf-8");  
	//		} catch (UnsupportedEncodingException e) {  
	//			e.printStackTrace();  
	//		}  
	//		JSONObject json = JSONObject.fromObject(json_result);  
	//		System.out.println("json数据： " + json);  
	//		String country = JSONObject.fromObject(json.get("data")).get("country").toString();  
	//		String region = JSONObject.fromObject(json.get("data")).get("region").toString();  
	//		String city = JSONObject.fromObject(json.get("data")).get("city").toString();  
	//		String county = JSONObject.fromObject(json.get("data")).get("county").toString();  
	//		String isp = JSONObject.fromObject(json.get("data")).get("isp").toString();  
	//		String area = JSONObject.fromObject(json.get("data")).get("area").toString();  
	//
	//	}  
}
