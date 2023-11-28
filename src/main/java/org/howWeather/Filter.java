package org.howWeather;

import javax.swing.*;
import java.awt.*;
import java.awt.desktop.SystemEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.ArrayList;

public class Filter extends JPanel implements ActionListener {

    Frame motherfrm;
    FilterGroup themeGrp;
    FilterGroup locateGrp;
    FilterGroup weatherGrp;

    public Filter(Frame frm) {
        motherfrm = frm;
        VisibleFilter();
    }

    public void VisibleFilter() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.white);
        setPreferredSize(new Dimension(300, 750));

        locateGrp = new FilterGroup("지역", new String[]{"수도권", "강원", "충청", "경상", "전라"});
        themeGrp = new FilterGroup("테마", new String[]{"문화/예술", "쇼핑/놀이", "자연/힐링", "종교/역사/전통", "체험/학습/산업", "캠핑/스포츠"});
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

        motherfrm.search.pushinfoPnl(new CourseInfo(motherfrm.search, resultList));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        List<String> selectedThemes = new ArrayList<>();
        for (JCheckBox checkBox : themeGrp.checkBoxes) {
            if (checkBox.isSelected()) {
                selectedThemes.add(checkBox.getText());
            }
        }
        printThemeCourse(selectedThemes);
    }
}

