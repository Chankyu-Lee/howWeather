package org.howWeather;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CourseInfo extends JPanel implements ActionListener {
    private Search motherPnl;
    private JLabel coursenameLbl = new JLabel();
    private JLabel currentorderLbl = new JLabel();
    private List<InfoButton> buttons = new ArrayList<>();

    private int currentcourse = 1;
    private List<List<CourseData>> courseList;
    private JButton nextBtn = new JButton(">");
    private JButton prevBtn = new JButton("<");
    private JPanel coursePnl = new JPanel();
    private JPanel downsidePnl = new JPanel();

    private CourseWeather[][] weatherData;

    CourseInfo(Search motherPnl, List<List<CourseData>> list){
        motherPnl.pushinfoPnl(this);
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        coursePnl.setLayout(new GridLayout(0,1));
        courseList = list;
        this.motherPnl = motherPnl;
        nextBtn.addActionListener(this);
        prevBtn.addActionListener(this);
        getCourseWeatherData();
        visibleCourseInfo();
    }

    private void visibleCourseInfo(){
        coursenameLbl.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        currentorderLbl.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        nextBtn.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        prevBtn.setFont(new Font("맑은 고딕", Font.BOLD, 20));

        setCoursenameLbl();
        setcurrentorderLbl();
        add(coursenameLbl);
        addCourse();
        add(coursePnl);

        prevBtn.setBackground(Color.WHITE);
        downsidePnl.add(prevBtn);

        downsidePnl.add(currentorderLbl);

        nextBtn.setBackground(Color.WHITE);
        downsidePnl.add(nextBtn);

        add(downsidePnl);
        setMap();
    }

    private void setCoursenameLbl(){
        coursenameLbl.setText("Course " + courseList.get(currentcourse-1).get(0).getCourseId());
    }

    private void setcurrentorderLbl(){
        currentorderLbl.setText(currentcourse + " / " + courseList.size());
    }

    private void addCourse(){
        List<CourseData> list = courseList.get(currentcourse-1);
        for(CourseData coursedata : list){
            InfoButton btn = new InfoButton(coursedata);
            btn.setBackground(Color.WHITE);
            btn.setFont(new Font("맑은 고딕",Font.PLAIN,17));
            btn.setMaximumSize(new Dimension(motherPnl.getWidth(),20));
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            buttons.add(btn);
        }
        for(InfoButton btn : buttons) {
            coursePnl.add(btn);
        }

    }

    private void changeCourse(){
        System.out.println("Start");
        setCoursenameLbl();
        setcurrentorderLbl();
        coursePnl.removeAll();
        buttons.clear();
        addCourse();
        setMap();
        System.out.println("End");
        getCourseWeatherData();
    }

    private void setMap(){
        NaverMap2.setCourseMap(courseList.get(currentcourse-1));
    }

    private void getCourseWeatherData(){
        weatherData = WeatherApi.getCourseWeatherDoubleArr(courseList.get(currentcourse-1).get(0).getCourseId());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == nextBtn){
            if(currentcourse < courseList.size()) {
                currentcourse++;
                changeCourse();
            }
        } else if (e.getSource() == prevBtn) {
            if(currentcourse > 1){
                currentcourse--;
                changeCourse();
            }
        }
    }

    class InfoButton extends JButton implements ActionListener {
        private long order;

        public InfoButton(CourseData coursedata) {
            super(coursedata.getTourismName());
            this.order = coursedata.getCourseOrder();
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            AttractionInfo attractionInfo = new AttractionInfo(motherPnl, courseList.get(currentcourse-1).get((int)order-1), weatherData[(int)order-1]);
            motherPnl.pushinfoPnl(attractionInfo);
            motherPnl.drawInfoPnl();
        }
    }

}