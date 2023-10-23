package org.howWeather;

public class Main {

    public static void main(String[] args) {

        try {
            CourseWeather[][] arr = WeatherApi.getCourseWeatherDoubleArr(316);

            for (int j = 0; j < arr[0].length; j++) {
                for (int i = 0; i < arr.length; i++) {
                    System.out.println(i + "-" + j + " " + arr[i][j].getTm() + " " + arr[i][j].getSpotName() + "\t");
                }
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}