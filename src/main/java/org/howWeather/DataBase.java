package org.howWeather;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
// 438개의 코스가 있습니다
public class DataBase {
    private static Connection conn;

    private static final String csvFilePath = "기상청_관광코스별 관광지 상세날씨 조회 지점 정보_20200110.csv"; // CSV 파일 경로
    private static final String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl"; // Oracle 데이터베이스 주소
    private static final String username = "system"; // 여기에 사용자 이름 추가
    private static final String password = "4208"; // 여기에 패스워드 추가
    private static final String tableName = "course_data";

    static {
        try {
            // 데이터베이스 연결
            conn = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DataBase() {
    }

    private static boolean tableExists() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM user_tables WHERE table_name = '" + tableName + "'");
        rs.next();
        int count = rs.getInt(1);
        rs.close();
        stmt.close();
        return count > 0;
    }

    public static void createTable() {
        try {
            if (!tableExists()) {
                // 테이블 생성
                Statement stmt = conn.createStatement();
                stmt.execute(getCreateSQL());
                System.out.println("테이블이 생성 되었습니다.");

                // CSV 파일을 읽어서 데이터베이스에 삽입
                insertCsvData();
                System.out.println("데이터가 삽입 되었습니다.");
            }
            // 데이터베이스 연결 닫기
            conn.close();
        } catch (SQLException e) {
            System.out.println("테이블이 이미 존재합니다.");
            e.printStackTrace();
        }
    }

    // 데이터베이스에 테이블 생성
    private static String getCreateSQL() throws SQLException {
        String createTableSQL = "CREATE TABLE " + tableName + "(\r\n"
                + "        	    theme_category VARCHAR2(255),\r\n"
                + "        	    course_id NUMBER(10),\r\n"
                + "        	    tourism_id NUMBER(10),\r\n"
                + "        	    region_id NUMBER(10),\r\n"
                + "        	    tourism_name VARCHAR2(255),\r\n"
                + "        	    longitude VARCHAR2(255),\r\n"
                + "        	    latitude VARCHAR2(255),\r\n"
                + "        	    course_order NUMBER(10),\r\n"
                + "        	    travel_time NUMBER(10),\r\n"
                + "        	    indoor_type VARCHAR2(255),\r\n"
                + "        	    theme_name VARCHAR2(255)\r\n"
                + "        	)";

        return createTableSQL;
    }

    // CSV 파일을 읽어서 데이터베이스에 삽입
    private static void insertCsvData() throws SQLException {
        String insertSQL = "INSERT INTO " + tableName + " (theme_category, course_id, tourism_id, region_id, tourism_name, longitude, latitude, course_order, travel_time, indoor_type, theme_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 11) {
                    for (int i = 0; i < 11; i++) {
                        if (i == 0 || i == 4 || i == 5 || i == 6 || i == 9 || i == 10) {
                            pstmt.setString(i + 1, data[i]);
                        }
                        else {
                            pstmt.setLong(i + 1, Long.parseLong(data[i]));
                        }
                    }
                    pstmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static List<CourseData> getAllCourseData() {
        List<CourseData> list = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM course_data ORDER BY COURSE_ORDER ASC");
            while (rs.next()) {
                CourseData cd = new CourseData();

                cd.setThemeCategory(rs.getString("theme_category"));
                cd.setCourseId(rs.getLong("course_id"));
                cd.setTourismId(rs.getLong("tourism_id"));
                cd.setRegionId(rs.getLong("region_id"));
                cd.setTourismName(rs.getString("tourism_name"));
                cd.setLongitude(rs.getString("longitude"));
                cd.setLatitude(rs.getString("latitude"));
                cd.setCourseOrder(rs.getLong("course_order"));
                cd.setTravelTime(rs.getLong("travel_time"));
                cd.setIndoorType(rs.getString("indoor_type"));
                cd.setThemeName(rs.getString("theme_name"));

                list.add(cd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    public static List<CourseData> getCourseDataList(long courseId) {
        List<CourseData> list = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM course_data WHERE COURSE_ID = " + courseId + " ORDER BY COURSE_ORDER ASC");) {

            while (rs.next()) {
                CourseData cd = new CourseData();

                cd.setThemeCategory(rs.getString("theme_category"));
                cd.setCourseId(rs.getLong("course_id"));
                cd.setTourismId(rs.getLong("tourism_id"));
                cd.setRegionId(rs.getLong("region_id"));
                cd.setTourismName(rs.getString("tourism_name"));
                cd.setLongitude(rs.getString("longitude"));
                cd.setLatitude(rs.getString("latitude"));
                cd.setCourseOrder(rs.getLong("course_order"));
                cd.setTravelTime(rs.getLong("travel_time"));
                cd.setIndoorType(rs.getString("indoor_type"));
                cd.setThemeName(rs.getString("theme_name"));

                list.add(cd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void writeToExcelFile(String tableName, String fileName) {
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);
            FileWriter writer = new FileWriter(fileName + ".csv"); {

                // Write header
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    writer.append(resultSet.getMetaData().getColumnName(i));
                    if (i < resultSet.getMetaData().getColumnCount()) {
                        writer.append(",");
                    }
                }
                writer.append("\n");

                // Write data
                while (resultSet.next()) {
                    for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                        writer.append(resultSet.getString(i));
                        if (i < resultSet.getMetaData().getColumnCount()) {
                            writer.append(",");
                        }
                    }
                    writer.append("\n");
                }

                System.out.println("CSV file has been generated successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<CourseData> getCourseDataByRegion(String regionCode) {
        List<CourseData> list = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            // region_id의 앞 두 자리가 지역 코드와 일치하는 데이터 조회
            ResultSet rs = stmt.executeQuery("SELECT * FROM course_data WHERE substr(region_id, 1, 2) = '" + regionCode + "' ORDER BY COURSE_ORDER ASC");
            while (rs.next()) {
                CourseData cd = new CourseData();

                cd.setThemeCategory(rs.getString("theme_category"));
                cd.setCourseId(rs.getLong("course_id"));
                cd.setTourismId(rs.getLong("tourism_id"));
                cd.setRegionId(rs.getLong("region_id"));
                cd.setTourismName(rs.getString("tourism_name"));
                cd.setLongitude(rs.getString("longitude"));
                cd.setLatitude(rs.getString("latitude"));
                cd.setCourseOrder(rs.getLong("course_order"));
                cd.setTravelTime(rs.getLong("travel_time"));
                cd.setIndoorType(rs.getString("indoor_type"));
                cd.setThemeName(rs.getString("theme_name"));


                list.add(cd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return list;
    }
}