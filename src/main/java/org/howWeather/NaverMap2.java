package org.howWeather;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

public class NaverMap2 {

    public static void setBasicMap() {
        File file = new File("map/Basic.jpg");

        if (file.exists()) {
            Frame.mapLbl.setIcon(new ImageIcon(file.getPath()));
            return;
        }

        try {
            String URL_STATICMAP = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?w=700&h=800&center=127.1054221,36.3591614&level=6";

            createMapImageFile(file, URL_STATICMAP);

            Frame.mapLbl.setIcon(new ImageIcon(file.getPath()));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void setCourseMap(List<CourseData> list) {
        File file = new File("map/Course" + list.get(0).getCourseId() + ".jpg");

        if (file.exists()) {
            Frame.mapLbl.setIcon(new ImageIcon(file.getPath()));
            return;
        }

        try {
            String URL_STATICMAP = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?&w=700&h=800";

            for (CourseData cd : list) {
                String pos = URLEncoder.encode(cd.getLongitude() + " " + cd.getLatitude(), "UTF-8");
                URL_STATICMAP += "&markers=type:t|size:mid|pos:" + pos + "|label:" + URLEncoder.encode(cd.getTourismName(), "UTF-8");
            }

            createMapImageFile(file, URL_STATICMAP);

            Frame.mapLbl.setIcon(new ImageIcon(file.getPath()));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void setCourseMap(long courseID){
        Frame.mapLbl.setIcon(new ImageIcon("map/Course" + courseID + ".jpg"));
    }

    public static void setAttractionMap(CourseData coursedata) {
        File file = new File("map/Attraction" + coursedata.getTourismId() +".jpg");

        if (file.exists()) {
            Frame.mapLbl.setIcon(new ImageIcon(file.getPath()));
            return;
        }

        try {
            String URL_STATICMAP = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?&w=700&h=800";

            String pos = URLEncoder.encode(coursedata.getLongitude() + " " + coursedata.getLatitude(), "UTF-8");
            URL_STATICMAP += "&markers=type:t|size:mid|pos:" + pos + "|label:" + URLEncoder.encode(coursedata.getTourismName(), "UTF-8");

            createMapImageFile(file, URL_STATICMAP);

            Frame.mapLbl.setIcon(new ImageIcon(file.getPath()));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void createMapImageFile(File file, String URL) throws IOException {
        URL url = new URL(URL);
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
            file.createNewFile();
            OutputStream out = new FileOutputStream(file);
            while ((read = is.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            is.close();
            ImageIcon img = new ImageIcon(file.getName());
            Frame.mapLbl.setIcon(img);
        } else {
            System.out.println(responseCode);
        }
    }
}