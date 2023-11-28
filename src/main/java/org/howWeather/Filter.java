package org.howWeather;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.howWeather.DataBase.getCourseDataList;

public class Filter extends JPanel implements ActionListener {

    Frame motherfrm;
    FilterGroup themeGrp;
    FilterGroup locateGrp;
    FilterGroup weatherGrp;

    Map<String, String> regionCodeMap;


    public Filter(Frame frm) {
        motherfrm = frm;
        VisibleFilter();
        initializeRegionCodeMap();
    }

    private void initializeRegionCodeMap() {
        regionCodeMap = new HashMap<>();
        regionCodeMap.put("서울 특별시", "11");
        regionCodeMap.put("경기도", "41");
        regionCodeMap.put("강원도", "42");
        regionCodeMap.put("충청도", "43");
        regionCodeMap.put("전라도", "45");
        regionCodeMap.put("경상도", "47");
    }

    public void VisibleFilter() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.white);
        setPreferredSize(new Dimension(300, 750));

        themeGrp = new FilterGroup("테마", new String[]{"문화/예술", "쇼핑/놀이", "자연/힐링", "종교/역사/전통", "체험/학습/산업", "캠핑/스포츠"});
        locateGrp = new FilterGroup("지역", new String[]{"서울 특별시", "경기도", "강원도", "충청도", "경상도", "전라도"});
        weatherGrp = new FilterGroup("날씨", new String[]{"맑음", "비", "흐림"});

        add(locateGrp);
        add(themeGrp);
        add(weatherGrp);

        JButton applyBtn = new JButton("필터 적용");
        applyBtn.addActionListener(this);
        add(applyBtn);
    }

    public void uncheckAll() {
        locateGrp.uncheckAll();
        themeGrp.uncheckAll();
        weatherGrp.uncheckAll();
    }


    public void actionPerformed(ActionEvent e) {
        boolean isThemeSelected = isAtLeastOneSelected(themeGrp);
        boolean isRegionSelected = isAtLeastOneSelected(locateGrp);

        // '지역', '테마' 선택 여부에 따른 필터링
        if (isThemeSelected && isRegionSelected) {
            handleSelectedThemesAndRegions();
        } else if (!isThemeSelected && isRegionSelected) {
            handleSelectedRegions();
        } else if (isThemeSelected && !isRegionSelected) {
            handleSelectedThemes();
        } else {
            System.out.println("테마 또는 지역을 선택해주세요.");
        }
    }

    private boolean isAtLeastOneSelected(FilterGroup group) {
        for (JCheckBox checkBox : group.checkBoxes) {
            if (checkBox.isSelected()) {
                return true;
            }
        }
        return false;
    }

    private void handleSelectedThemesAndRegions() {
        List<String> selectedThemes = getSelectedItems(themeGrp);
        List<String> selectedRegions = getSelectedItems(locateGrp);

        for (String theme : selectedThemes) {
            for (String region : selectedRegions) {
                printCourseByRegionAndTheme(region, theme);
            }
        }
    }

    private List<String> getSelectedItems(FilterGroup group) {
        List<String> selectedItems = new ArrayList<>();
        for (JCheckBox checkBox : group.checkBoxes) {
            if (checkBox.isSelected()) {
                selectedItems.add(checkBox.getText());
            }
        }
        return selectedItems;
    }
    private void printCourseByRegionAndTheme(String region, String theme) {
        List<CourseData> courseList = DataBase.getCourseDataByRegion(regionCodeMap.get(region));

        for (CourseData courseData : courseList) {
            if (courseData.getThemeName().equals(theme)) {
                System.out.println(courseData.toString());
            }
        }
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