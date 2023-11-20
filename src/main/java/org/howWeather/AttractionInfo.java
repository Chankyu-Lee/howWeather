package org.howWeather;

import javax.swing.*;

public class AttractionInfo extends JPanel {
    JLabel attractionname = new JLabel();
    JLabel attractionadress = new JLabel();
    JLabel attractiontheme = new JLabel();
    JLabel attractionindoortype = new JLabel();
    JLabel attractionweather = new JLabel();
    JButton closeBtn = new JButton("X");

    AttractionInfo(CourseData attraction){
        attractionname.setText(attraction.getTourismName());
        attractiontheme.setText(attraction.getThemeCategory());
        attractionindoortype.setText(attraction.getIndoorType());
        visibleinfo();
    }

    private void visibleinfo(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
}
