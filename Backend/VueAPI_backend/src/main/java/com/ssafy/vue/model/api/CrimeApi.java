package com.ssafy.vue.model.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ssafy.vue.config.KeyConfig;

@Component
public class CrimeApi {
	
	@Autowired
	private KeyConfig keyConfig;

	/** 범죄용 **/
	static boolean isReady = false;
	static String serviceKey = "";
	static final String PAGE = "1";
	static final String PER_PAGE = "15";	//"47"; 원래는 이게 맞는데 list에 들어가는 최대 idx가 14더라
	static List<Integer> searchCrimeNum = new ArrayList<Integer>();
	static int totalSum = 0;
	static String apiUrl = ""; // perpage / "&returnType=XML" / key

//	static String[] crimeList = new String[] {"절도", "살인", "강도", "방화", "성폭력", "상해", "주거침입", "약취와 유인"};
	
	/** 인구조사용 **/
	static String consumer_key = "";
	static String consumer_secret = "";
	static String accessToken;
	static final String YEAR = "2020";
	static int totalPopulation;
	static double totalCrimePerPopulation;
	
	private static CrimeApi crimeApi = new CrimeApi();

	// 객체 생성시 exception 던지면 안된다고 빠꾸먹음
	public static CrimeApi getCrimeApiInstance() /* throws Exception */ {
//		setKey();
		return crimeApi;
	}
	
//	public CrimeApi(KeyConfig keyConfig) {
//		super();
//		this.keyConfig = keyConfig;
//	}
//
//	void setKey() {
//		serviceKey = keyConfig.getKey();
//		consumer_key = keyConfig.getConsumer_key();
//		consumer_secret = keyConfig.getConsumer_secret();
//		apiUrl = "https://api.odcloud.kr/api/15085726/v1/uddi:66542ecf-c470-4c09-80df-a08348e9e679?page=" + PAGE
//				+ "&perPage=" + PER_PAGE + "&serviceKey=" + serviceKey;
//	}
	
	// test용 main
//	public static void main(String[] args) throws Exception {
//		CrimeApi crimeApi = CrimeApi.getCrimeApiInstance();
//		ready();
//		// 35.2089568        | 126.5076215
//		// 37.34860159999999 | 126.7271088 경기도 시흥시
//		//  37.5327384        | 127.1134887 서울특별시 송파구
//		// 35.4473437        | 128.2489677 경상남도 의령군
////		System.out.println(getCorrectCode("35.2089568", "126.5076215"));
//		System.out.println("송파  " + crimeApi.getCrimeInGugun("서울특별시", "송파구", "37.5327384", "127.1134887"));
//		System.out.println("시흥 " + crimeApi.getCrimeInGugun("경기도", "시흥시", "37.34860159999999", "126.7271088"));
//		// 35.2310561        | 128.914185
//		System.out.println("김해  " + crimeApi.getCrimeInGugun("경상남도", "김해시", "35.2310561", "128.914185"));
//		// 인천광역시 | 강화군    | 강화읍   | 2871025000 | 37.7483209        | 126.4835663
//		System.out.println("강화군  " + crimeApi.getCrimeInGugun("인천광역시", "강화군", "37.7483209", "126.4835663"));
//	}
//	
	// 최초 한 번만 실행되는 메소드
	// 1. 범죄 분류만 보고 정보가 필요한 범죄의 인덱스만 리스트에 담는다
	// 2. 전국적으로 해당 범죄들의 총 발생 건수를 구한다
	// 3. 인구수 api를 위한 토큰을 발급받는다
	private static void ready() throws Exception {
		
		String jsonString = callApi(apiUrl);

		JSONObject jObject = new JSONObject(jsonString);
//		System.out.println(jObject.toString());

		JSONArray jArray = jObject.getJSONArray("data");
		
		totalSum = 0;
		for (int i = 0; i < jArray.length(); i++) {
			JSONObject obj = jArray.getJSONObject(i);
			String crime = obj.getString("범죄분류");
			if ("절도".equals(crime) || "살인".equals(crime) || "강도".equals(crime) || "방화".equals(crime) || "성폭력".equals(crime) || "상해".equals(crime) || "주거침입".equals(crime) || "약취와 유인".equals(crime)) {
				searchCrimeNum.add(i);
				Map<String, Object> jsonMap = obj.toMap();
				for (String mapkey : jsonMap.keySet()){ 
					if ("범죄분류".equals(mapkey)) continue;
					totalSum += (int) jsonMap.get(mapkey);
				}
			}
		}
		
		// 토큰 발급받기
		String apiTokenUrl = "https://sgisapi.kostat.go.kr/OpenAPI3/auth/authentication.json?consumer_key=" + consumer_key + "&consumer_secret=" + consumer_secret;
		String tokenStr = callApi(apiTokenUrl);
		
		JSONObject tokenObj = new JSONObject(tokenStr).getJSONObject("result");
		accessToken = tokenObj.getString("accessToken");
		
		
		String totalPopUrl = "https://sgisapi.kostat.go.kr/OpenAPI3/stats/population.json?accessToken=" + accessToken
				+ "&year=" + YEAR + "&low_search=0";
		String totalPopStr = callApi(totalPopUrl);
		JSONArray jsonArr = new JSONObject(totalPopStr).getJSONArray("result");
		JSONObject obj = jsonArr.getJSONObject(0);
		totalPopulation = Integer.parseInt(obj.getString("tot_ppltn"));
		totalCrimePerPopulation = (double)totalSum / (double)totalPopulation;
		
		isReady = true;
	}

	// 범죄발생지 검색 가능여부, 위험도 단계(1~5 정도)	
	public Map<String, String> getCrimeInGugun(String sidoName, String gugunName, String lat, String lng) throws Exception {
		
//		serviceKey = KEY;
		serviceKey = keyConfig.getKey();
		consumer_key = keyConfig.getConsumer_key();
		consumer_secret = keyConfig.getConsumer_secret();
//		System.out.println(keyConfig.getKey());
		apiUrl = "https://api.odcloud.kr/api/15085726/v1/uddi:66542ecf-c470-4c09-80df-a08348e9e679?page=" + PAGE
				+ "&perPage=" + PER_PAGE + "&serviceKey=" + serviceKey;
				
		if (!isReady) {
			ready();
			//return null; // 검색할 때 부를거면 지우고, 웹페이지 접속할 때 부를거면 놔두기
		}
			
		String sido = sidoName.substring(0, 2);
		// 시도 이름이 의미가 없으면 "_"로 바꿔놓음
		if (!("광주".equals(sido) || "대구".equals(sido) || "대전".equals(sido) || "부산".equals(sido) || "서울".equals(sido)
				|| "울산".equals(sido) || "인천".equals(sido))) sido = "_";
		String gugun = gugunName.substring(0, gugunName.length() - 1);

		/** json parsing **/
		String jsonString = callApi(apiUrl);
		JSONObject jObject = new JSONObject(jsonString);
		// 배열을 가져옵니다.
		JSONArray jArray = jObject.getJSONArray("data");
		
		Map<String, String> crimeRate = new HashMap<String, String>();
		// key : possible / value : rate
		// 검색 가능한 지역 
		//		key : "possible", value : "true"
		//		key : "ratio", value : "비율"
		
		// 검색 불가능으로 초기화
		crimeRate.put("possible", "false");
		crimeRate.put("ratio", "-1");

		int crimeSum = 0;
		boolean flag = false;
		
		// 필요한 범죄 이름 번호만 출력
		for (int i = 0; i < searchCrimeNum.size(); i++) {
			JSONObject obj = jArray.getJSONObject(searchCrimeNum.get(i));
			
			Map<String, Object> jsonMap = obj.toMap();
			for (String mapkey : jsonMap.keySet()){ 
				// 일단 해당하는 구군 정보가 있고
				if (mapkey.contains(gugun)) {
					// 시도 정보가 불필요하거나, 입력된 시도와 일치한다면
					if ("_".equals(sido) || mapkey.contains(sido)) {
						crimeSum += (int) jsonMap.get(mapkey);
						flag = true;
					}
				}
			}
			if (!flag) return crimeRate;
		}
		
		crimeRate.put("possible", "true");
		
		// 인구 수 호출해서 rate 변경
		int population = Integer.parseInt(getPopulation(lat, lng));
		
		crimeRate.put("ratio", Integer.toString(getRate(crimeSum, population)));
		
		return crimeRate;
	}
	
	private int getRate(int crimeSum, int population) {
//		int rate = 0;
		
		double crimePerPopulation = (double) crimeSum / (double) population;

		totalCrimePerPopulation = (double)totalSum / (double)totalPopulation;
		
		double ratio = crimePerPopulation / totalCrimePerPopulation;
		
//		System.out.println("ratio : " + ratio);
//		
//		if (ratio < 0.85) rate = 1;
//		else if (ratio < 0.95) rate = 2;
//		else if (ratio < 1.05) rate = 3;
//		else if (ratio < 1.15) rate = 4;
//		else rate = 5;
		
		return (int) (ratio * 100);
	}

	private static String callApi(String apiUrl) throws Exception {

		URL url = new URL(apiUrl);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setRequestMethod("GET");

		BufferedReader br;

		int responseCode = conn.getResponseCode();
		if (responseCode == 200) { // 호출 OK
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		} else { // 에러
			br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
		}

		String jsonString = new String();
		String stringLine;
		while ((stringLine = br.readLine()) != null) {
			jsonString += stringLine;
		}
		return jsonString;
	}
	
	private static String getPopulation(String lat, String lng) throws Exception {
		
		String gugunCode = getCorrectCode(lat, lng);
		String apiUrl = "https://sgisapi.kostat.go.kr/OpenAPI3/stats/population.json?accessToken=" + accessToken
				+ "&year=" + YEAR + "&low_search=0&adm_cd=" + gugunCode;
		String jsonString = callApi(apiUrl);
		JSONArray jsonArr = new JSONObject(jsonString).getJSONArray("result");
		JSONObject obj = jsonArr.getJSONObject(0);
		String population = obj.getString("tot_ppltn");
		
		return population;
	}
	
	private static String getCorrectCode(String lat, String lng) throws Exception {
		// 좌표계가 달라서 변환해야 한다
		String coordinateUrl = "https://sgisapi.kostat.go.kr/OpenAPI3/transformation/transcoord.json?accessToken=" + accessToken + "&src=4326&dst=5179&posX=" + lng + "&posY=" + lat;
		String coordStr = callApi(coordinateUrl);
		JSONObject coorObj = new JSONObject(coordStr).getJSONObject("result");
		String x_coor = coorObj.get("posX").toString();
		String y_coor = coorObj.get("posY").toString();
		
		// 바뀐 좌표계로 통계청에서 사용하는 코드 얻어오기
		String codeUrl = "https://sgisapi.kostat.go.kr/OpenAPI3/personal/findcodeinsmallarea.json?accessToken=" + accessToken + "&x_coor=" + x_coor + "&y_coor=" + y_coor;
		String codeStr = callApi(codeUrl);
		
		JSONObject codeObj = new JSONObject(codeStr).getJSONObject("result");
		String tot_reg_cd = codeObj.getString("tot_reg_cd");
		
		return tot_reg_cd.substring(0, 5);
	}
}

