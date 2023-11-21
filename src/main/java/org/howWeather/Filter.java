package org.howWeather;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static org.howWeather.DataBase.getCourseDataList;

public class Filter extends JPanel implements ActionListener {

    Frame motherfrm;
    FilterGroup themeGrp;
    FilterGroup locateGrp;

    public Filter(Frame frm) {
        motherfrm = frm;
        VisibleFilter();
    }

    public void VisibleFilter() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.white);
        setPreferredSize(new Dimension(300, 750));

        themeGrp = new FilterGroup("테마", new String[]{"문화/예술", "쇼핑/놀이", "자연/힐링", "종교/역사/전통", "체험/학습/산업", "캠핑/스포츠"});
        locateGrp = new FilterGroup("지역", new String[]{"서울 특별시", "경기도", "강원도", "충청도", "경상도", "전라도"});
        FilterGroup weatherGrp = new FilterGroup("날씨", new String[]{"맑음", "비", "흐림"});

        add(locateGrp);
        add(themeGrp);
        add(weatherGrp);

        JButton applyBtn = new JButton("필터 적용");
        applyBtn.addActionListener(this);
        add(applyBtn);
    }

    public void actionPerformed(ActionEvent e) {
        handleSelectedThemes();
        handleSelectedRegions();

    }

    private void handleSelectedThemes() {
        for (JCheckBox checkBox : themeGrp.checkBoxes) {
            if (checkBox.isSelected()) {
                printThemeCourse(checkBox.getText());
            }
        }
    }

    private void handleSelectedRegions() {
        for (JCheckBox checkBox : locateGrp.checkBoxes) {
            if (checkBox.isSelected()) {
                printRegionCourse(checkBox.getText());
            }
        }
    }


    private void printThemeCourse(String theme) {
        // 여기서 CourseData 리스트는 DataBase 클래스에서 가져옵니다.
        List<CourseData> courseList = DataBase.getAllCourseData();
        for (CourseData courseData : courseList) {
            if (courseData.getThemeName().equals(theme)) {
                System.out.println(courseData.toString());
            }
        }
    }



    private void printRegionCourse(String region) {
        // 지역에 따른 코드를 설정
        List<String> regionCodes = new ArrayList<>();
        switch (region) {
            case "서울 특별시":
                regionCodes.add("11");
                break;
            case "경기도":
                regionCodes.add("41");
                break;
            case "강원도":
                regionCodes.add("42");
                break;
            case "충청도":
                regionCodes.add("43");
                regionCodes.add("44");
                break;
            case "전라도":
                regionCodes.add("45");
                regionCodes.add("46");
                break;
            case "경상도":
                regionCodes.add("47");
                regionCodes.add("48");
                break;

        }

        // 지역 코드를 사용하여 데이터베이스에서 정보 조회
        for (String regionCode : regionCodes) {
            List<CourseData> courseList = DataBase.getCourseDataByRegion(regionCode);
            for (CourseData courseData : courseList) {
                System.out.println(courseData.toString());
            }
        }
    }

}