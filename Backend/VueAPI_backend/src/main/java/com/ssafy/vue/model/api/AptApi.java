package com.ssafy.vue.model.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.ssafy.vue.config.KeyConfig;
import com.ssafy.vue.model.AptInfoDto;

@Component
public class AptApi{
	
	@Autowired
	private KeyConfig keyConfig;
	
	static String API_KEY = "";
	static final String dong = "2720010300";
	static final String LAWD_CD = dong.substring(0, 5); // 지역 코드 5자리 - 구군 단위
	static final String DONG = dong.substring(5); // 동 코드 10자리 - 뒤 5자리만 끊어서 법정동읍면동코드와 비교 필요
	static final String PAGE_NO = "1";	// 페이지번호
	static final String NUM_OF_ROWS = "30";		// 한 페이지 결과 수
	static final String DEAL_YMD = "202110";		// 계약월
	
	static final String sidoName = "대구광역시";
	static final String gugunName = "서구";
	
	static String GEO_API_KEY = "";
	static String KAKAO_MAP_API_KEY = "";		// rest api key
	
	private static AptApi aptApi = new AptApi();
	
	public static AptApi getAptApiInstance() {
		return aptApi;
	}
	
//	private static void setKey() {
//		API_KEY = keyConfig.getKey();
//	}

	
//	private HouseMapService haHouseMapService = HouseMapServiceImpl.getHouseMapServiceImplInstance();
	

	//	@Autowired
//	private SqlSession sqlSession;
//	
//	public List<SidoGugunCodeDto> getSidoByCode(String sidocode) throws Exception {
//		System.out.println(sidocode);
//		return sqlSession.getMapper(HouseMapMapper.class).getSidoByCode(sidocode);
//	}
//
//	public List<SidoGugunCodeDto> getGugunByCode(String guguncode) throws Exception {
//		return sqlSession.getMapper(HouseMapMapper.class).getGugunByCode(guguncode);
//	}
	public List<AptInfoDto> getAptListApi(String LAWD_CD, String DONG, String NUM_OF_ROWS, String PAGE_NO, String DEAL_YMD) throws Exception {		
		String apiData = getApiData(LAWD_CD, NUM_OF_ROWS, PAGE_NO, DEAL_YMD);
		
		NodeList dongList = xmlParsing(apiData, "//items/item/법정동읍면동코드");
		Queue<Integer> dongAptNum = new LinkedList<Integer>();
		
//		System.out.println(apiData);
		
		for (int i = 0; i < dongList.getLength(); i++) {
			NodeList dong = dongList.item(i).getChildNodes();
			for (int j = 0; j < dong.getLength(); j++) {
				Node node = dong.item(j);
	        	if (DONG.equals(node.getTextContent())) {
	        		dongAptNum.offer(i);
	        	}
			}
		}

		// 동이 일치하는 애들만 파싱
		List<AptInfoDto> aptList = new ArrayList<AptInfoDto>();
//		XPathExpression expr = xpath.compile("//items/item");
		NodeList nodeList = xmlParsing(apiData, "//items/item");
//		System.out.println(dongAptNum.size());
		System.out.println("aptList");
		while (!dongAptNum.isEmpty()) {
			int num = dongAptNum.poll();
			NodeList child = nodeList.item(num).getChildNodes();
			AptInfoDto apt = new AptInfoDto();
			for (int i = 0; i < child.getLength(); i++) {
				Node node = child.item(i);
				
				if("일련번호".equals(node.getNodeName()))
					apt.setAptCode(node.getTextContent().trim());
				else if("아파트".equals(node.getNodeName()))
					apt.setAptName(node.getTextContent().trim());
				else if("법정동읍면동코드".equals(node.getNodeName()))
					apt.setDongCode(node.getTextContent().trim());
				else if("법정동".equals(node.getNodeName()))
					apt.setDongName(node.getTextContent().trim());
				else if("법정동시군구코드".equals(node.getNodeName()))
					apt.setSidogugunCode(node.getTextContent().trim());
				else if("도로명".equals(node.getNodeName()))
					apt.setRoadName(node.getTextContent().trim());
				else if("건축년도".equals(node.getNodeName()))
					apt.setBuildYear(Integer.parseInt(node.getTextContent()));
				else if("거래금액".equals(node.getNodeName()))
					apt.setRecentPrice(node.getTextContent().trim());
				else if("지번".equals(node.getNodeName()))
					apt.setJibun(node.getTextContent().trim());
				else if("층".equals(node.getNodeName()))
					apt.setFloor(Integer.parseInt(node.getTextContent()));
				
			}
			StringBuilder sb = new StringBuilder();
//			System.out.println(apt.getSidogugunCode().substring(0, 2));
//			String sidoName = haHouseMapService.getSidoByCode(apt.getSidogugunCode().substring(0, 2)).get(0).getSidoName();
//			System.out.println(sidoName);
			String address = sb.append(apt.getDongName()).append(" ").append(apt.getJibun()).toString();
			NodeList geoList = xmlParsing(getCoordination(address), "//documents");
			if (geoList.item(0) != null) {
				geoList = geoList.item(0).getChildNodes();
				for (int j = 0; j < geoList.getLength(); j++) {
					Node geo = geoList.item(j);
					
					if ("y".equals(geo.getNodeName()))
						apt.setLat(geo.getTextContent());
					else if ("x".equals(geo.getNodeName()))
						apt.setLng(geo.getTextContent());
				}
			}
			
			aptList.add(apt);
		}
		
		return aptList;
	}
	
	public List<AptInfoDto> getAptListApi(String sidoName, String gugunName, String LAWD_CD, String DONG, String NUM_OF_ROWS, String PAGE_NO, String DEAL_YMD) throws Exception {
		API_KEY = keyConfig.getKey();
		GEO_API_KEY = keyConfig.getGEO_API_KEY();
		KAKAO_MAP_API_KEY = keyConfig.getKAKAO_MAP_API_KEY();
		
		String apiData = getApiData(LAWD_CD, NUM_OF_ROWS, PAGE_NO, DEAL_YMD);
		
		NodeList dongList = xmlParsing(apiData, "//items/item/법정동읍면동코드");
		Queue<Integer> dongAptNum = new LinkedList<Integer>();

		for (int i = 0; i < dongList.getLength(); i++) {
			NodeList dong = dongList.item(i).getChildNodes();
			for (int j = 0; j < dong.getLength(); j++) {
				Node node = dong.item(j);
	        	if (DONG.equals(node.getTextContent())) {
	        		dongAptNum.offer(i);
	        	}
			}
		}

		// 동이 일치하는 애들만 파싱
		List<AptInfoDto> aptList = new ArrayList<AptInfoDto>();
//		XPathExpression expr = xpath.compile("//items/item");
		NodeList nodeList = xmlParsing(apiData, "//items/item");
//		System.out.println(dongAptNum.size());
		while (!dongAptNum.isEmpty()) {
			int num = dongAptNum.poll();
			NodeList child = nodeList.item(num).getChildNodes();
			AptInfoDto apt = new AptInfoDto();
			for (int i = 0; i < child.getLength(); i++) {
				Node node = child.item(i);
				
				if("일련번호".equals(node.getNodeName()))
					apt.setAptCode(node.getTextContent().trim());
				else if("아파트".equals(node.getNodeName()))
					apt.setAptName(node.getTextContent().trim());
				else if("법정동읍면동코드".equals(node.getNodeName()))
					apt.setDongCode(node.getTextContent().trim());
				else if("법정동".equals(node.getNodeName()))
					apt.setDongName(node.getTextContent().trim());
				else if("법정동시군구코드".equals(node.getNodeName()))
					apt.setSidogugunCode(node.getTextContent().trim());
				else if("도로명".equals(node.getNodeName()))
					apt.setRoadName(node.getTextContent().trim());
				else if("건축년도".equals(node.getNodeName()))
					apt.setBuildYear(Integer.parseInt(node.getTextContent()));
				else if("거래금액".equals(node.getNodeName()))
					apt.setRecentPrice(node.getTextContent().trim());
				else if("지번".equals(node.getNodeName()))
					apt.setJibun(node.getTextContent().trim());
				else if("층".equals(node.getNodeName()))
					apt.setFloor(Integer.parseInt(node.getTextContent()));
				
			}
			StringBuilder sb = new StringBuilder();
//			System.out.println(apt.getSidogugunCode().substring(0, 2));
//			String sidoName = haHouseMapService.getSidoByCode(apt.getSidogugunCode().substring(0, 2)).get(0).getSidoName();
//			System.out.println(sidoName);
			String address = sb.append(sidoName).append(" ").append(gugunName).append(" ").append(apt.getDongName()).append(" ").append(apt.getJibun()).toString();
			NodeList geoList = xmlParsing(getCoordination(address), "//documents");
			if (geoList.item(0) != null) {
				geoList = geoList.item(0).getChildNodes();
				for (int j = 0; j < geoList.getLength(); j++) {
					Node geo = geoList.item(j);
					
					if ("y".equals(geo.getNodeName()))
						apt.setLat(geo.getTextContent());
					else if ("x".equals(geo.getNodeName()))
						apt.setLng(geo.getTextContent());
				}
			}
			
			aptList.add(apt);
		}
		
		return aptList;
	}
	
	private String getApiData(String LAWD_CD, String NUM_OF_ROWS, String PAGE_NO, String DEAL_YMD) throws Exception {
		/***** api *****/
		StringBuilder urlBuilder = new StringBuilder(
				"http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev"); /*URL*/
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + API_KEY); /* Service Key */
//        urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("-", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(PAGE_NO, "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(NUM_OF_ROWS, "UTF-8")); /*한 페이지 결과 수*/
		urlBuilder.append(
				"&" + URLEncoder.encode("LAWD_CD", "UTF-8") + "=" + URLEncoder.encode(LAWD_CD, "UTF-8")); /* 지역코드 */
		urlBuilder.append(
				"&" + URLEncoder.encode("DEAL_YMD", "UTF-8") + "=" + URLEncoder.encode(DEAL_YMD, "UTF-8")); /* 계약월 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
//		System.out.println("Response code: " + conn.getResponseCode());
		
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();

		return sb.toString();
	}
	
	private String getGeoCode(String address) throws Exception {
		/***** api *****/
		StringBuilder urlBuilder = new StringBuilder(
				"https://maps.googleapis.com/maps/api/geocode/xml?address="); /*URL*/
		urlBuilder.append(address);
		urlBuilder.append("&key=").append(GEO_API_KEY);
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		
//		System.out.println(sb.toString());
		
		return sb.toString();
	}
	
	
	/***** xml parsing *****/
	private NodeList xmlParsing(String apiData, String compilePath) throws Exception {
		InputSource is = new InputSource(new StringReader(apiData));
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(is);

		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		
		// null check - 굳이 status 찾을 필요는 없음...
//		XPathExpression expr = xpath.compile("//status");
		
		XPathExpression expr = xpath.compile(compilePath);
		Object temp = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		if (temp == null) return null;
		NodeList nodeList = (NodeList) temp;
		
		
		return nodeList;
	}
	
	private String getCoordination( String address ) throws Exception {
		
		String encodeAddress = "";  // 한글 주소는 encoding 해서 날려야 함
		try { encodeAddress = URLEncoder.encode( address, "UTF-8" ); } 
		catch ( Exception e ) { e.printStackTrace(); }
		
		String apiUrl = "https://dapi.kakao.com/v2/local/search/address.xml?query=" 
	                                                                     + encodeAddress;
		String auth = "KakaoAK " + KAKAO_MAP_API_KEY;
		
		URL url = new URL( apiUrl );
	    HttpsURLConnection conn = ( HttpsURLConnection ) url.openConnection();
		conn.setRequestMethod( "GET" );
	    conn.setRequestProperty( "Authorization", auth );
	    
	    BufferedReader br;

	    int responseCode = conn.getResponseCode();
	    if( responseCode == 200 ) {  // 호출 OK
	    	br = new BufferedReader( new InputStreamReader(conn.getInputStream(), "UTF-8") );
	    } else {  // 에러
	    	br = new BufferedReader( new InputStreamReader(conn.getErrorStream(), "UTF-8") );
	    }
	    
	    String xmlString = new String();
	    String stringLine;
	    while ( ( stringLine= br.readLine()) != null ) {
	        xmlString += stringLine;
	    }
//	    System.out.println(xmlString);
	    return xmlString;
	}

	
//	public static void main(String[] args) throws Exception {
//		AptApi api = AptApi.getAptApiInstance();
//		List<AptInfoDto> aptlist = api.getAptListApi(sidoName, gugunName, LAWD_CD, DONG, NUM_OF_ROWS, PAGE_NO, DEAL_YMD);
//		
//		for (AptInfoDto aptInfoDto : aptlist) {
//			System.out.println(aptInfoDto);
//		}
//	}
}


/** 코드창고 **/
//System.out.println(sb.toString());
//JSONObject jsonObject = XML.toJSONObject(sb.toString());
//System.out.println("Convert result : "+jsonObject.toString());
//return jsonObject;
//System.out.println("Key : test   Value : " + jsonObject.getString("test"));
//System.out.println("Key : test2  Value : " + jsonObject.getString("test2"));

//int n = items.getLength();
//for (int i = 0; i < n; i++) {
//  Node item = items.item(i);
//  Node text = item.getFirstChild();
//  String itemValue = text.getNodeValue();
//  System.out.println(itemValue);
//
//}
//
//        for (int i = 0; i < nodeList.getLength(); i++) {
//    		NodeList child = nodeList.item(i).getChildNodes();
//	      
//    		for (int j = 0; j < child.getLength(); j++) {
//    			Node node = child.item(j);
//    			System.out.println(node.getNodeName() + " : " + node.getTextContent());
//    			System.out.println("현재 노드 이름 : " + node.getNodeName());
//    			System.out.println("현재 노드 타입 : " + node.getNodeType());
//    			System.out.println("현재 노드 값 : " + node.getTextContent());
//    			System.out.println("현재 노드 네임스페이스 : " + node.getPrefix());
//    			System.out.println("현재 노드의 다음 노드 : " + node.getNextSibling());
//			}
//    		System.out.println("");
//		}