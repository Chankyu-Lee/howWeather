package org.howWeather;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

import static org.howWeather.DataBase.getCourseDataList;

public class Filter extends JPanel implements ActionListener {

    private Frame motherfrm;
    private FilterGroup themeGrp;
    private FilterGroup regionGrp;
    Map<String, String> regionCodeMap;


    public Filter(Frame frm) {
        motherfrm = frm;
        VisibleFilter();
        initializeRegionCodeMap();
    }

    private void initializeRegionCodeMap() {
        regionCodeMap = new HashMap<>();
        regionCodeMap.put("서울특별시", "11");
        regionCodeMap.put("부산광역시", "26");
        regionCodeMap.put("대구광역시", "27");
        regionCodeMap.put("인천광역시", "28");
        regionCodeMap.put("광주광역시", "29");
        regionCodeMap.put("대전광역시", "30");
        regionCodeMap.put("울산광역시", "31");
        regionCodeMap.put("세종특별자치시", "36");
        regionCodeMap.put("경기도", "41");
        regionCodeMap.put("강원특별자치도", "42");
        regionCodeMap.put("충청북도", "43");
        regionCodeMap.put("충청남도", "44");
        regionCodeMap.put("전라북도", "45");
        regionCodeMap.put("전라남도", "46");
        regionCodeMap.put("경상북도", "47");
        regionCodeMap.put("경상남도", "48");
        regionCodeMap.put("제주특별자치도", "50");
    }

    public void VisibleFilter() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.white);
        setPreferredSize(new Dimension(150, 800));

        themeGrp = new FilterGroup("테마", new String[]{"문화/예술", "쇼핑/놀이", "자연/힐링", "종교/역사/전통", "체험/학습/산업", "캠핑/스포츠"});
        regionGrp = new FilterGroup("지역", 
                new String[]{"서울특별시", "부산광역시", "대구광역시", "인천광역시", "광주광역시", "대전광역시", "울산광역시", "세종특별자치시", "경기도", "강원특별자치도", "충청북도", "충청남도", "전라북도", "전라남도", "경상북도", "경상남도", "제주특별자치도"});

        add(regionGrp);
        add(themeGrp);

        JButton applyBtn = new JButton("필터 적용");
        applyBtn.addActionListener(this);
        applyBtn.setFont(new Font("맑은 고딕",Font.BOLD,13));
        add(applyBtn);
    }

    public void uncheckAll() {
        regionGrp.uncheckAll();
        themeGrp.uncheckAll();
    }


    public void actionPerformed(ActionEvent e) {
        boolean isThemeSelected = isAtLeastOneSelected(themeGrp);
        boolean isRegionSelected = isAtLeastOneSelected(regionGrp);

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
        List<String> selectedRegions = getSelectedItems(regionGrp);

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
        List<List<CourseData>> list = new ArrayList<>();
        for (CourseData courseData : courseList) {
            if (courseData.getThemeName().equals(theme)) {
                list.add(DataBase.getCourseDataList(courseData.getCourseId()));
            }
        }
        if(list.isEmpty()){
            motherfrm.search.noneMassage();
        }
        else{
            motherfrm.search.pushinfoPnl(new CourseInfo(motherfrm.search, list));
        }
    }

    private void handleSelectedThemes() {
        List<String> selectedThemes = getSelectedItems(themeGrp);
        printThemeCourse(selectedThemes);
    }


    private void handleSelectedRegions() {
        for (JCheckBox checkBox : regionGrp.checkBoxes) {
            if (checkBox.isSelected()) {
                printRegionCourse(checkBox.getText());
            }
        }
    }


    private void printThemeCourse(List<String> themes) {
        List<CourseData> courseList = DataBase.getAllCourseData();
        List<List<CourseData>> resultList = new ArrayList<>();
        Map<Long, List<CourseData>> courseDataMap = new HashMap<>();

        for (CourseData courseData : courseList) {
            Long ID = courseData.getCourseId();
            if (!courseDataMap.containsKey(ID)) {
                courseDataMap.put(ID, DataBase.getCourseDataList(ID));
            }
        }
        resultList.addAll(courseDataMap.values());

        for (Iterator<List<CourseData>> iterator = resultList.iterator(); iterator.hasNext(); ) {
            List<CourseData> courseDataList = iterator.next();
            boolean isFiltered = true;

            for (String theme : themes) {
                if (courseDataList.stream().anyMatch(courseData -> courseData.getThemeName().equals(theme))) {
                    isFiltered = false;
                } else {
                    isFiltered = true;
                    break;
                }
            }

            if (isFiltered) {
                iterator.remove();
            }
        }

        if(resultList.isEmpty()){
            motherfrm.search.noneMassage();
        } else{
            motherfrm.search.pushinfoPnl(new CourseInfo(motherfrm.search, resultList));
        }

    }

    private void printRegionCourse(String region) {
        String regionCode = regionCodeMap.get(region);
        if (regionCode != null) {
            List<CourseData> courseList = DataBase.getCourseDataByRegion(regionCode);
            List<List<CourseData>> list = new ArrayList<>();
            for(CourseData data: courseList){
                list.add(DataBase.getCourseDataList(data.getCourseId()));
            }
            if(list.isEmpty()){
                motherfrm.search.noneMassage();
            } else{
                motherfrm.search.pushinfoPnl(new CourseInfo(motherfrm.search, list));
            }

        } else {
            System.out.println("해당 지역의 코드를 찾을 수 없습니다.");
        }
    }

}