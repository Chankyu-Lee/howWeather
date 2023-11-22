package org.howWeather;

import javax.swing.*;
import java.awt.*;
import java.awt.desktop.SystemEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Filter extends JPanel implements ActionListener {

    Frame motherfrm;
    FilterGroup themeGrp;
    FilterGroup locateGrp;
    FilterGroup weatherGrp;

    public Filter(Frame frm){
        motherfrm = frm;
        VisibleFilter();
    }

    public void VisibleFilter(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.white);
        setPreferredSize(new Dimension(300,750));

        locateGrp = new FilterGroup("지역",new String[]{"수도권","강원","충청","경상","전라"});
        themeGrp = new FilterGroup("테마",new String[]{"문화/예술","쇼핑/놀이","자연/힐링","종교/역사/전통","체험/학습/산업","캠핑/스포츠"});
        weatherGrp = new FilterGroup("날씨",new String[]{"맑음","비","흐림"});

        add(locateGrp);
        add(themeGrp);
        add(weatherGrp);

        JButton applyBtn = new JButton("필터 적용");
        applyBtn.addActionListener(this);
        add(applyBtn);
    }

    public void unckeckAll(){
       locateGrp.uncheckAll();
       themeGrp.uncheckAll();
       weatherGrp.uncheckAll();
    }

    private void printThemeCourse(String theme) {
        // 여기서 CourseData 리스트는 DataBase 클래스에서 가져옵니다.
        List<CourseData> courseList = DataBase.getAllCourseData();
        List<List <CourseData>> resultList = new ArrayList<>();
        for (CourseData courseData : courseList) {
            if (courseData.getThemeName().equals(theme)) {
                Long ID = courseData.getCourseId();
                List<CourseData> temp = DataBase.getCourseDataList(ID);
                if(!(resultList.contains(temp))){
                    resultList.add(temp);
                }
            }
        }
        motherfrm.search.pushinfoPnl(new CourseInfo(motherfrm.search, resultList));
    }

    @Override
    public void actionPerformed(ActionEvent e){
        for (JCheckBox checkBox : themeGrp.checkBoxes) {
            if (checkBox.isSelected()) {
                printThemeCourse(checkBox.getText());
            }
        }
    }
}
