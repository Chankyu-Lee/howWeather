package org.howWeather;

import javax.swing.*;
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

    private CourseWeather[][] weatherData;

    CourseInfo(Search motherPnl, List<List<CourseData>> list){
        motherPnl.pushinfoPnl(this);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        coursePnl.setLayout((new BoxLayout(coursePnl, BoxLayout.Y_AXIS)));
        courseList = list;
        this.motherPnl = motherPnl;
        nextBtn.addActionListener(this);
        prevBtn.addActionListener(this);
        getCourseWeatherData();
        visibleCourseInfo();
    }

    private void visibleCourseInfo(){
        setCoursenameLbl();
        setcurrentorderLbl();
        add(coursenameLbl);
        addCourse();
        add(coursePnl);

        JPanel downside = new JPanel();
        downside.add(prevBtn);
        downside.add(currentorderLbl);
        downside.add(nextBtn);
        add(downside);
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
            buttons.add(new InfoButton(coursedata));
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
        //weatherData = WeatherApi.getCourseWeatherDoubleArr(courseList.get(currentcourse-1).get(0).getCourseId());

        weatherData = new CourseWeather[2][7];

        weatherData[0][0] = new CourseWeather("1", "dsa","3","3","3","3",3,"3","3",3,3,3,3,3,3);
        weatherData[0][1] = new CourseWeather("1", "dsa","3","3","3","3",3,"3","3",3,3,3,3,3,3);
        weatherData[0][2] = new CourseWeather("1", "dsa","3","3","3","3",3,"3","3",3,3,3,3,3,3);
        weatherData[0][3] = new CourseWeather("1", "dsa","3","3","3","3",3,"3","3",3,3,3,3,3,3);
        weatherData[0][4] = new CourseWeather("1", "dsa","3","3","3","3",3,"3","3",3,3,3,3,3,3);
        weatherData[0][5] = new CourseWeather("1", "dsa","3","3","3","3",3,"3","3",3,3,3,3,3,3);
        weatherData[0][6] = new CourseWeather("1", "dsa","3","3","3","3",3,"3","3",3,3,3,3,3,3);
        weatherData[1][0] = new CourseWeather("1", "dsa","3","3","3","3",3,"3","3",3,3,3,3,3,3);
        weatherData[1][1] = new CourseWeather("1", "dsa","3","3","3","3",3,"3","3",3,3,3,3,3,3);
        weatherData[1][2] = new CourseWeather("1", "dsa","3","3","3","3",3,"3","3",3,3,3,3,3,3);
        weatherData[1][3] = new CourseWeather("1", "dsa","3","3","3","3",3,"3","3",3,3,3,3,3,3);
        weatherData[1][4] = new CourseWeather("1", "dsa","3","3","3","3",3,"3","3",3,3,3,3,3,3);
        weatherData[1][5] = new CourseWeather("1", "dsa","3","3","3","3",3,"3","3",3,3,3,3,3,3);
        weatherData[1][6] = new CourseWeather("1", "dsa","3","3","3","3",3,"3","3",3,3,3,3,3,3);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == nextBtn){
            if(currentcourse < courseList.size()) {
                currentcourse++;
                changeCourse();
            }
        } else if (e.getSource() == prevBtn) {
            if(currentcourse > 0){
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
