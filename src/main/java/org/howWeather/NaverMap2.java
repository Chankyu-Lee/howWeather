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
        map_service(Long.parseLong(naverMap.address.getText()));
    }

    public void map_service(long courseId) {
        List<CourseData> list = DataBase.getCourseDataList(courseId);
        CourseWeather[][] arr = WeatherApi.getCourseWeatherDoubleArr(courseId);

        if (arr != null && arr.length > 0 && arr[0] != null && arr[0].length > 0) {
            try {
                String URL_STATICMAP = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?";
                URL_STATICMAP += "&w=700&h=500";

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
                    naverMap.imageLabel.setIcon(img);
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
