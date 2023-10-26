package org.howWeather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class WeatherApi {

    private WeatherApi() {

    }

    private static String getWeatherDataToJsonString(long courseId) throws IOException, ParseException {
        String apiUrl = "http://apis.data.go.kr/1360000/TourStnInfoService1/getTourStnVilageFcst1";
        String serviceKey = "y%2Bcyi5tgE7yNDv8T8Mzs3A4iSs6cxMDvHoHkyj9Eoj%2FHOY8d7XxsLzSqU5SDHiFsL771Qvxow9bw%2BeXKPSJ0yA%3D%3D";
        String pageNo = "1";	//페이지 번호
        String numOfRows = "1";	//한 페이지 결과 수
        String dataType = "JSON";	//데이터 타입
        String CURRENT_DATE = "20230916";	//조회하고싶은 날짜
        String HOUR = "24";	//조회하고 싶은 날짜의 시간 날짜
        String COURSE_ID = String.valueOf(courseId);	//관광 코스ID

        boolean check = false;
        while (true) {
            StringBuilder urlBuilder = new StringBuilder(apiUrl);
            urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "="+serviceKey);
            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(numOfRows, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode(dataType, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("CURRENT_DATE","UTF-8") + "=" + URLEncoder.encode(CURRENT_DATE, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("HOUR","UTF-8") + "=" + URLEncoder.encode(HOUR, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("COURSE_ID","UTF-8") + "=" + URLEncoder.encode(COURSE_ID, "UTF-8"));

            /*
             * GET방식으로 전송해서 파라미터 받아오기
             */
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            BufferedReader br;
            if(200 <= conn.getResponseCode() && conn.getResponseCode() <= 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            conn.disconnect();
            String result= sb.toString();

            if (!check) {
                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(result);
                JSONObject response = (JSONObject) jsonObject.get("response");
                JSONObject body = (JSONObject) response.get("body");

                numOfRows = String.valueOf(body.get("totalCount"));
                check = true;
                continue;
            } else {
                return result;
            }
        }
    }

    public static CourseWeather[][] getCourseWeatherDoubleArr(long courseId) {
        try {
            Timer t1 = new Timer();
            t1.start();
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(getWeatherDataToJsonString(courseId));
            JSONObject response = (JSONObject) jsonObject.get("response");
            JSONObject body = (JSONObject) response.get("body");
            JSONObject items = (JSONObject) body.get("items");
            JSONArray itemArray = (JSONArray) items.get("item");

            t1.end();
            t1.printTime();

            t1.start();

            List<CourseData> list = DataBase.getCourseDataList(courseId);
            Map<Long, Integer> map = new HashMap<Long, Integer>();
            for (int i = 0; i < list.size(); i++) {
                map.put(list.get(i).getTourismId(), i);
            }

            boolean[] check = new boolean[list.size()];
            Arrays.fill(check, false);

            int arrCol = 16;
            CourseWeather[][] arr = new CourseWeather[list.size()][arrCol];

            int cnt = 0;
            String tmTemp = "";
            for (int i = 0; i < (Long)body.get("totalCount"); i++) {
                // 여러 개의 아이템 중에서 하나를 선택
                JSONObject firstItem = (JSONObject) itemArray.get(i);
                int courseIndex = map.get((Long) firstItem.get("spotAreaId"));
                String tm = (String) firstItem.get("tm");
                if (!check[courseIndex] && !tm.equals(tmTemp)) {

                    check[courseIndex] = true;
                    cnt++;

                    // JSON 데이터에서 필드 추출
                    tm = (String) firstItem.get("tm");
                    String thema = (String) firstItem.get("thema");
                    String course_id = (String) firstItem.get("courseId");
                    String courseAreaId = (String) firstItem.get("courseAreaId");
                    String courseAreaName = (String) firstItem.get("courseAreaName");
                    String courseName = (String) firstItem.get("courseName");
                    Long spotAreaId = (Long) firstItem.get("spotAreaId");
                    String spotAreaName = (String) firstItem.get("spotAreaName");
                    String spotName = (String) firstItem.get("spotName");
                    Long th3 = (Long) firstItem.get("th3");
                    Long wd = (Long) firstItem.get("wd");
                    Long ws = (Long) firstItem.get("ws");
                    Long sky = (Long) firstItem.get("sky");
                    Long rhm = (Long) firstItem.get("rhm");
                    Long pop = (Long) firstItem.get("pop");

                    // CourseWeather 객체 생성
                    CourseWeather courseWeather = new CourseWeather(tm, thema, course_id, courseAreaId, courseAreaName,
                            courseName, spotAreaId, spotAreaName, spotName, th3, wd, ws, sky, rhm, pop);

                    arr[courseIndex][arrCol-1] = courseWeather;
                    if (cnt == list.size()) {
                        arrCol--;
                        cnt = 0;
                        Arrays.fill(check, false);
                        tmTemp = tm;
                    }
                }
            }

            t1.end();
            t1.printTime();

            return arr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

