package com.ssafy.vue.model.mapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

public class CrimeApi {

	static boolean isReady = false;
	static final String serviceKey = "VouVavm1Pgk6asq4X6JmiMl5J8cx72IKSRipR%2BxxctP2C4sZJkLbnyLEdsRcd%2BZEzHn%2FVPl%2Boyo1A2xB76RxgQ%3D%3D";
	static final String PAGE = "1";
	static final String PER_PAGE = "15";	//"47"; 원래는 이게 맞는데 list에 들어가는 최대 idx가 14더라
	static List<Integer> searchCrimeNum = new ArrayList<Integer>();
	static int totalSum = 0;
//	static String[] crimeList = new String[] {"절도", "살인", "강도", "방화", "성폭력", "상해", "주거침입", "약취와 유인"};

	private static CrimeApi crimeApi = new CrimeApi();

	// 객체 생성시 exception 던지면 안된다고 빠꾸먹음
	public static CrimeApi getCrimeApiInstance() /* throws Exception */ {
		return crimeApi;
	}
	
	// test용 main
//	public static void main(String[] args) throws Exception {
//		CrimeApi crimeApi = CrimeApi.getCrimeApiInstance();
//		System.out.println(crimeApi.getCrimeInGugun("인천광역시", "강화군"));
//	}
	
	// 최초 한 번만 실행되는 메소드
	// 1. 범죄 분류만 보고 정보가 필요한 범죄의 인덱스만 리스트에 담는다
	// 2. 전국적으로 해당 범죄들의 총 발생 건수를 구한다
	private static void ready() throws Exception {
		String jsonString = callApi(PAGE, PER_PAGE);

		JSONObject jObject = new JSONObject(jsonString);
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

		isReady = true;
	}

	// 범죄발생지 검색 가능여부, 위험도 단계(1~5 정도)
	public Map<String, String> getCrimeInGugun(String sidoName, String gugunName) throws Exception {

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
		String jsonString = callApi(PAGE, PER_PAGE);
		JSONObject jObject = new JSONObject(jsonString);
		// 배열을 가져옵니다.
		JSONArray jArray = jObject.getJSONArray("data");
		
		Map<String, String> crimeRate = new HashMap<String, String>();
		// key : possible / value : rate
		// 검색 가능한 지역 
		//		key : "possible", value : "true"
		//		key : "rate", value : "1~5"
		
		// 검색 불가능으로 초기화
		crimeRate.put("possible", "false");
		crimeRate.put("rate", "-1");

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
		crimeRate.put("rate", Integer.toString(getRate(crimeSum)));
		
		return crimeRate;
	}
	
	private int getRate(int crimeSum) {
		int rate = 0;
		
		if (crimeSum < totalSum / 1000) rate = 1;
		else if (crimeSum < totalSum / 500) rate = 2;
		else if (crimeSum < totalSum / 200) rate = 3;
		else if (crimeSum < totalSum / 100) rate = 4;
		else rate = 5;
		
		return rate;
	}

	private static String callApi(String page, String perPage) throws Exception {
		String apiUrl = "https://api.odcloud.kr/api/15085726/v1/uddi:66542ecf-c470-4c09-80df-a08348e9e679?page=" + page
				+ "&perPage=" + perPage + "&serviceKey=" + serviceKey; // perpage / "&returnType=XML" / key

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

}
