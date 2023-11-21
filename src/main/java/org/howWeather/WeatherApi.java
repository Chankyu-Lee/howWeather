package org.howWeather;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class WeatherApi {

    private WeatherApi() {

    }

    private static Document getWeatherDataToXml(long courseId) throws Exception {


        String apiUrl = "http://apis.data.go.kr/1360000/TourStnInfoService1/getTourStnVilageFcst1";
        String serviceKey = "ppbyLar1zzLOwFF8ifh8Xs05l%2FNQn6gLdVqzPbg%2BPkp%2FwulI2V%2FlN4ReRHok%2FrprkInKVhDh%2FYizMQPQZNJ3wg%3D%3D";
        String pageNo = "1";
        String numOfRows = "1";
        String dataType = "XML"; // 데이터 타입을 XML로 설정
        String CURRENT_DATE = getDate();
        String HOUR = "24";
        String COURSE_ID = String.valueOf(courseId);

        boolean check = false;
        while (true) {
            StringBuilder urlBuilder = new StringBuilder(apiUrl);
            urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey);
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode(numOfRows, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode(dataType, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("CURRENT_DATE", "UTF-8") + "=" + URLEncoder.encode(CURRENT_DATE, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("HOUR", "UTF-8") + "=" + URLEncoder.encode(HOUR, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("COURSE_ID", "UTF-8") + "=" + URLEncoder.encode(COURSE_ID, "UTF-8"));

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode=" + conn.getResponseCode());
            if (responseCode != HttpURLConnection.HTTP_OK) {
                System.out.println("HTTP 응답 코드: " + responseCode);
                // 예외 처리 또는 다른 로직을 추가할 수 있습니다.
                return null;  // 또는 throw new RuntimeException("API 호출 실패: " + responseCode);
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(conn.getInputStream()); // 수정된 부분
            doc.getDocumentElement().normalize();

            if (!check) {
                // body 요소 선택
                Element bodyElement = (Element) doc.getElementsByTagName("body").item(0);
                // totalCount 요소 선택
                Element totalCountElement = (Element) bodyElement.getElementsByTagName("totalCount").item(0);
                numOfRows = totalCountElement.getTextContent();
                check = true;
                continue;
            } else {
                return doc;
            }
        }
    }

    public static CourseWeather[][] getCourseWeatherDoubleArr(long courseId) {
        try {
            Timer t1 = new Timer();
            t1.start();

            Document doc = getWeatherDataToXml(courseId);
            NodeList nodeList = doc.getElementsByTagName("item");

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

            int col = 0;
            int cnt = 0;
            String tmTemp = "";
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node nNode = nodeList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    Long spotAreaId = Long.parseLong(eElement.getElementsByTagName("spotAreaId").item(0).getTextContent());
                    if (map.containsKey(spotAreaId)) {
                        int courseIndex = map.get(spotAreaId);
                        String tm = eElement.getElementsByTagName("tm").item(0).getTextContent();
                        if (!check[courseIndex] && !tm.equals(tmTemp)) {

                            check[courseIndex] = true;
                            cnt++;

                            // XML 데이터에서 필드 추출
                            String thema = eElement.getElementsByTagName("thema").item(0).getTextContent();
                            String course_id = eElement.getElementsByTagName("courseId").item(0).getTextContent();
                            String courseAreaId = eElement.getElementsByTagName("courseAreaId").item(0).getTextContent();
                            String courseAreaName = eElement.getElementsByTagName("courseAreaName").item(0).getTextContent();
                            String courseName = eElement.getElementsByTagName("courseName").item(0).getTextContent();
                            String spotAreaName = eElement.getElementsByTagName("spotAreaName").item(0).getTextContent();
                            String spotName = eElement.getElementsByTagName("spotName").item(0).getTextContent();
                            Long th3 = Long.parseLong(eElement.getElementsByTagName("th3").item(0).getTextContent());
                            Long wd = Long.parseLong(eElement.getElementsByTagName("wd").item(0).getTextContent());
                            Long ws = Long.parseLong(eElement.getElementsByTagName("ws").item(0).getTextContent());
                            Long sky = Long.parseLong(eElement.getElementsByTagName("sky").item(0).getTextContent());
                            Long rhm = Long.parseLong(eElement.getElementsByTagName("rhm").item(0).getTextContent());
                            Long pop = Long.parseLong(eElement.getElementsByTagName("pop").item(0).getTextContent());

                            // CourseWeather 객체 생성
                            CourseWeather courseWeather = new CourseWeather(tm, thema, course_id, courseAreaId, courseAreaName,
                                    courseName, spotAreaId, spotAreaName, spotName, th3, wd, ws, sky, rhm, pop);

                            arr[courseIndex][col] = courseWeather;
                            if (cnt == list.size()) {
                                col++;
                                cnt = 0;
                                Arrays.fill(check, false);
                                tmTemp = tm;

                                if (col == arrCol) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            t1.end();
            t1.printTime();

            return reverseArray(arr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getDate() {
        // 현재 날짜 가져오기
        LocalDate currentDate = LocalDate.now();

        // 원하는 날짜 형식 정의
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // 현재 날짜를 지정된 형식으로 변환
        String formattedDate = currentDate.format(formatter);

        return formattedDate;
    }

    // 2차원 배열을 뒤집는 메서드
    private static CourseWeather[][] reverseArray(CourseWeather[][] array) {
        int rows = array.length;
        int cols = array[0].length;

        CourseWeather[][] reversedArray = new CourseWeather[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                reversedArray[i][j] = array[i][cols - 1 - j];
            }
        }

        return reversedArray;
    }
}