package org.howWeather;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

public class NaverMap2 {

    //기본 상태의 맵 이미지를 설정합니다
    public static void setBasicMap(JLabel label) {

        try {
            String URL_STATICMAP = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?w=700&h=750&center=127.1054221,36.3591614&level=6";

            URL url = new URL(URL_STATICMAP);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", "5umm05laoy");
            con.setRequestProperty("X-NCP-APIGW-API-KEY", "CAWHT8yo1vDh8jod76x468PFiQbL1bBIweNA6Qxk");

            int responseCode = con.getResponseCode();
            BufferedReader br;

            if (responseCode == 200) {
                InputStream is = con.getInputStream();
                int read = 0;
                byte[] bytes = new byte[1024];
                String tempName = Long.valueOf(new Date().getTime()).toString();
                File file = new File(tempName + ".jpg");
                file.createNewFile();
                OutputStream out = new FileOutputStream(file);
                while ((read = is.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                is.close();
                ImageIcon img = new ImageIcon(file.getName());
                label.setIcon(img);
            } else {
                System.out.println(responseCode);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void map_service(Frame naverMap, long courseId) {
        List<CourseData> list = DataBase.getCourseDataList(courseId);
        CourseWeather[][] arr = WeatherApi.getCourseWeatherDoubleArr(courseId);

        for (CourseData cd : list) {
            System.out.println(cd);
            System.out.println();
        }

        if (arr != null && arr.length > 0 && arr[0] != null && arr[0].length > 0) {
            try {
                String URL_STATICMAP = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?";
                URL_STATICMAP += "&w=700&h=750";

                for (CourseData cd : list) {
                    String pos = URLEncoder.encode(cd.getLongitude() + " " + cd.getLatitude(), "UTF-8");
                    URL_STATICMAP += "&markers=type:t|size:mid|pos:" + pos + "|label:" + URLEncoder.encode(cd.getTourismName(), "UTF-8");
                }

                URL url = new URL(URL_STATICMAP);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", "5umm05laoy");
                con.setRequestProperty("X-NCP-APIGW-API-KEY", "CAWHT8yo1vDh8jod76x468PFiQbL1bBIweNA6Qxk");

                int responseCode = con.getResponseCode();
                BufferedReader br;

                if (responseCode == 200) {
                    InputStream is = con.getInputStream();
                    int read = 0;
                    byte[] bytes = new byte[1024];
                    String tempName = Long.valueOf(new Date().getTime()).toString();
                    File file = new File(tempName + ".jpg");
                    file.createNewFile();
                    OutputStream out = new FileOutputStream(file);
                    while ((read = is.read(bytes)) != -1) {
                        out.write(bytes, 0, read);
                    }
                    is.close();
                    ImageIcon img = new ImageIcon(file.getName());
                    naverMap.mapLbl.setIcon(img);
                } else {
                    System.out.println(responseCode);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            System.out.println("Error: arr is null or empty");
        }
    }
}

