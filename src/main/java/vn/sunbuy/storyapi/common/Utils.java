//package vn.sunbuy.storyapi.common;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//import java.text.DecimalFormat;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Base64;
//import java.util.Date;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import javax.crypto.spec.SecretKeySpec;
//import javax.servlet.http.HttpServletRequest;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicNameValuePair;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.BeanWrapper;
//import org.springframework.beans.BeanWrapperImpl;
//import org.springframework.beans.InvalidPropertyException;
//import org.springframework.core.env.Environment;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.jsonwebtoken.JwtBuilder;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//
//public class Utils {
//	protected static Logger log = LoggerFactory.getLogger(Utils.class.getName());
//	public static List<NameValuePair> buildFormParam(Map<String, Object> requestMap) {
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
//			Object value = entry.getValue();
//			if (isNotEmpty(value)) {
//				params.add(new BasicNameValuePair(entry.getKey(), String.valueOf(value)));
//			}
//		}
//		return params;
//	}
//
//	public static Map<String, Object> buildFormParamURLEncoder(Map<String, Object> requestMap)
//			throws UnsupportedEncodingException {
//		Map<String, Object> params = new HashMap<String, Object>();
//		for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
//			Object value = entry.getValue();
//			if (isNotEmpty(value)) {
//				params.put(entry.getKey(), URLEncoder.encode(String.valueOf(String.valueOf(value)), "UTF-8"));
//			}
//		}
//		return params;
//	}
//
//	public static Map<String, String> buildMap(String propery, Object object, Map<String, String> param) {
//		if (Utils.isEmpty(object)) {
//			return param;
//		}
//
//		Map<String, Object> requestMap = new ObjectMapper().convertValue(object, Map.class);
//		for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
//			Object value = entry.getValue();
//			if (isNotEmpty(value) && !(value instanceof LinkedHashMap)) {
//				param.put(propery + "." + entry.getKey(), String.valueOf(String.valueOf(value)));
//			}
//		}
//		return param;
//
//	}
//	public static String buildQueryObject(Object object) throws UnsupportedEncodingException {
//		Map<String, Object> requestMap = new ObjectMapper().convertValue(object, Map.class);
//		return buildQueryParam(requestMap);
//	}
//	public static String buildQueryParam(Map<String, Object> requestMap) throws UnsupportedEncodingException {
//		List<NameValuePair> params = buildFormParam(requestMap);
//		StringBuilder postData = new StringBuilder();
//		for (NameValuePair nameValuePair : params) {
//			if (postData.length() != 0)
//				postData.append('&');
//			postData.append(nameValuePair.getName());
//			postData.append('=');
//			postData.append(URLEncoder.encode(String.valueOf(nameValuePair.getValue()), "UTF-8"));
//		}
//		return postData.toString();
//	}
//	public static int random(int pow) {
//		int limit = (int) Math.pow(10, pow - 1);
//		SecureRandom secureRandom;
//		try {
//			secureRandom = SecureRandom.getInstance("SHA1PRNG");
//			int secureNumber = secureRandom.nextInt(9 * limit) + limit;
//			return secureNumber;
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//			return 999;
//		}
//	}
//
//	public static <T> String doPost(String url, Map<String, String> headers, T body) {
//		CloseableHttpClient httpClient = null;
//		HttpPost httpPost = null;
//		CloseableHttpResponse response = null;
//		StringBuffer responseString = new StringBuffer();
//
//		try {
//
//			httpClient = HttpClients.createDefault();
//			httpPost = new HttpPost(url);
//
//			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//			if (headers != null) {
//				headers.forEach((k, v) -> {
//					nvps.add(new BasicNameValuePair(k, v));
//				});
//			}
//			if (body != null) {
//				ObjectMapper objectMapper = new ObjectMapper();
//				String bodyAsString = objectMapper.writeValueAsString(body);
//
//				StringEntity input = new StringEntity(bodyAsString);
//				input.setContentType("application/json");
//				httpPost.setEntity(input);
//			}
//
//			for (NameValuePair h : nvps) {
//				httpPost.addHeader(h.getName(), h.getValue());
//			}
//
//			response = httpClient.execute(httpPost);
//			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
//
//			String output;
//			while ((output = br.readLine()) != null) {
//				responseString.append(output);
//			}
//
//			if (response.getStatusLine().getStatusCode() != 200) {
//				throw new RuntimeException(
//						url + " Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (response != null) {
//					response.close();
//				}
//				if (httpClient != null) {
//					httpClient.close();
//				}
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
//		}
//		return responseString.toString();
//	}
//	public static boolean isLocal(String env) {
//		return env.contains(JConstants.Env.local.name());
//	}
//
//	public static boolean isDev(String env) {
//		return env.contains(JConstants.Env.dev.name());
//
//	}
//
//	public static boolean isEmpty(Object str) {
//		return !isNotEmpty(str);
//	}
//
//	public static boolean isNotEmpty(Object str) {
//		if (str == null || str.toString()==null || str.toString().trim().length() == 0 || str.toString().trim().equalsIgnoreCase("null")) {
//			return false;
//		} else {
//			return true;
//		}
//	}
//	public static String sortByKeyData(Map<String, Object> map) {
//
//		LinkedHashMap<String, Object> sortedMap = new LinkedHashMap<>();
//		map.entrySet().stream().sorted(Map.Entry.comparingByKey())
//				.forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
//		String data = "";
//
//		for (Map.Entry<String, Object> entry : sortedMap.entrySet()) {
//			Object value = entry.getValue();
//			if (isNotEmpty(value)) {
//				data = data + value;
//			}
//		}
//
//		return data;
//	}
//	public static void copyNonNullProperties(Object source, Object destination) {
//		// getNullPropertyNames(Object source) {
//		final BeanWrapper src = new BeanWrapperImpl(source);
//		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
//		Set<String> emptyNames = new HashSet<String>();
//		for (java.beans.PropertyDescriptor pd : pds) {
//			// check if value of this property is null add fullName
//			String name = pd.getName();
//			if (name.equals("request") || name.equals("response")) {
//				continue;
//			}
//			try {
//				Object srcValue = src.getPropertyValue(name);
//				if (Utils.isEmpty(srcValue) || srcValue.toString().contains("***")) {
//					emptyNames.add(name);
//				}
//			} catch (InvalidPropertyException e) {
//				emptyNames.add(name);
//				continue;
//			}
//		}
//		String[] result = new String[emptyNames.size()];
//		String[] ignoreProperties = emptyNames.toArray(result);
//		BeanUtils.copyProperties(source, destination, ignoreProperties);
//	}
//	public static boolean isTest(Environment env) {
//		return isLocal(env);
//	}
//
//	public static boolean isLocal(Environment env) {
//		return isLocal(env.getActiveProfiles()[0]);
//	}
//	public static boolean isDev(Environment env) {
//		return isDev(env.getActiveProfiles()[0]);
//	}
//	public static String generateId(String userName) {
//		SimpleDateFormat dateFomrat = new SimpleDateFormat("yyMMddHHmmss");
//		String floor = new DecimalFormat("#0000").format(Math.floor(Math.random() * 1000));
//		return dateFomrat.format(new Date()) + floor;
//	}
//	public static String readAsText(String resourceFile) {
//		try {
//			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//			BufferedReader reader = new BufferedReader(
//					new InputStreamReader(classLoader.getResourceAsStream(resourceFile)));
//			StringBuffer fileContentBuffer = new StringBuffer();
//			String line;
//			while ((line = reader.readLine()) != null) {
//				fileContentBuffer.append(line);
//				fileContentBuffer.append('\n');
//			}
//			reader.close();
//			return fileContentBuffer.toString();
//		} catch (IOException e) {
//			e.printStackTrace();
//			throw new RuntimeException(e.getMessage());
//		}
//	}
//	public static String getIpAddress(HttpServletRequest request) {
//		String ipAddress = request.getHeader("X-FORWARDED-FOR");
//		if (Utils.isEmpty(ipAddress)) {
//			ipAddress = request.getRemoteAddr();
//		}
//		if (Utils.isEmpty(ipAddress)) {
//			ipAddress = "0.0.0.0";
//		}
//		return ipAddress;
//	}
//	public static Map<String, Object> convertParammeter2Map(HttpServletRequest request) {
//		log.info("request.getQueryString()" + request.getQueryString());
//		Enumeration<String> paramNames = request.getParameterNames();
//		Map<String, Object> data = new HashMap<String, Object>();
//		while (paramNames.hasMoreElements()) {
//			String key = (String) paramNames.nextElement();
//			String value = request.getParameter(key);
//			log.info("Param [" + key + " = " + value + "]");
//			data.put(key, value);
//		}
//		return data;
//	}
//	public static boolean checkContainSpecialCharacters(String input) {
//		Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
//		Matcher matcher = pattern.matcher(input);
//		boolean isStringContainsSpecialCharacter = matcher.find();
//		if (isStringContainsSpecialCharacter) {
//			return true;
//		}
//		return false;
//	}
//
//	public static boolean isFullname(String input) {
//		Pattern pattern = Pattern.compile("[^a-zA-Z]");
//		Matcher matcher = pattern.matcher(input.replaceAll(" ", ""));
//		boolean isStringContainsSpecialCharacter = matcher.find();
//		if (isStringContainsSpecialCharacter) {
//			return true;
//		}
//		return false;
//	}
//
//	public static HttpHeaders encodeHeader(String jwtClientId, String jwtClientSecret) {
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		String basicToken = jwtClientId + ":" + jwtClientSecret;
//		headers.add("Authorization", "Basic " + Base64.getEncoder().encodeToString(basicToken.getBytes()));
//		return headers;
//	}
//	
//	public static HttpHeaders setHeaderWithBearer(String accessToken) {
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.add("Authorization", "Bearer " + accessToken);
//		return headers;
//	}
//
//
//
//	public static String getString(String string) {
//		if(isEmpty(string)) {
//			return "";
//		}
//		return string;
//	}
//	
//	public static String generateJwt(String signalRServiceKey,String audience, String userId) {
//        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
//
//        long nowMillis = System.currentTimeMillis();
//        Date now = new Date(nowMillis);
//
//        long expMillis = nowMillis + (30 * 30 * 1000);
//        Date exp = new Date(expMillis);
//
//        byte[] apiKeySecretBytes = signalRServiceKey.getBytes(StandardCharsets.UTF_8);
//        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
//
//        JwtBuilder builder = Jwts.builder()
//            .setAudience(audience)
//            .setIssuedAt(now)
//            .setExpiration(exp)
//            .signWith(signatureAlgorithm, signingKey);
//
//        if (userId != null) {
//            builder.claim("nameid", userId);
//        }
//        
//        return builder.compact();
//    }
//
//}