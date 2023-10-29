package org.howWeather;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

public class NaverMap2 implements ActionListener {

    Frame naverMap;
    public NaverMap2(Frame naverMap) {
        this.naverMap = naverMap;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        map_service2(Long.parseLong(naverMap.searchFld.getText()));
    }

    public void map_service2(long courseId) {
        List<CourseData> list = DataBase.getCourseDataList(courseId);
        CourseWeather[][] arr = WeatherApi.getCourseWeatherDoubleArr(316);

        try {
            String URL_STATICMAP = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?";
            URL_STATICMAP += "&w=700&h=500";

            for (int i = 0; i < list.size(); i++) {
                CourseData cd = list.get(i);
                String pos = URLEncoder.encode(cd.getLongitude() + " " + cd.getLatitude(), "UTF-8");
                URL_STATICMAP += "&markers=type:t|size:mid|pos:" + pos + "|label:" + URLEncoder.encode(String.valueOf(i + 1) + " - " + cd.getTourismName() + " 날씨 : " + arr[i][0].getSkyConditionString(), "UTF-8");
            }

            URL url = new URL(URL_STATICMAP);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", "5umm05laoy");
            con.setRequestProperty("X-NCP-APIGW-API-KEY","CAWHT8yo1vDh8jod76x468PFiQbL1bBIweNA6Qxk");

            int responseCode = con.getResponseCode();
            BufferedReader br;

            // 정상호출인 경우.
            if (responseCode == 200) {
                InputStream is = con.getInputStream();

                int read = 0;
                byte[] bytes = new byte[1024];

                // 랜덤 파일명으로 파일 생성
                String tempName = Long.valueOf(new Date().getTime()).toString();
                File file = new File(tempName + ".jpg");	// 파일 생성.

                file.createNewFile();

                OutputStream out = new FileOutputStream(file);

                while ((read = is.read(bytes)) != -1) {
                    out.write(bytes, 0, read);	// 파일 작성
                }

                is.close();
                ImageIcon img = new ImageIcon(file.getName());
               // naverMap.mapLbl.setIcon(img);

            } else {
                System.out.println(responseCode);
            }

        } catch(Exception e) {
            System.out.println(e);
        }

    }
}
