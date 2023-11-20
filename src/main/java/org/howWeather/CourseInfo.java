package org.howWeather;

import javax.swing.*;

public class CourseInfo extends JPanel {
    JLabel coursenameLbl = new JLabel();
    AttractionInfo[] attractionInfos;

    CourseInfo(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    private void visibleCourseInfo(){
        coursenameLbl.setText("");

    }

    private void addAttraction(){

    }
}
