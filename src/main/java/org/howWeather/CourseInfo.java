package org.howWeather;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CourseInfo extends JPanel implements ActionListener {
    private Search motherPnl;
    private JLabel coursenameLbl = new JLabel();
    private JLabel currentorder = new JLabel();
    private List<InfoButton> buttons = new ArrayList<>();

    private int currentcourse = 1;
    private List<List<CourseData>> something;

    private JButton nextBtn = new JButton(">");
    private JButton prevBtn = new JButton("<");
    private JPanel coursePnl = new JPanel();


    CourseInfo(Search motherPnl, List<List <CourseData>> list){
        motherPnl.pushinfoPnl(this);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        coursePnl.setLayout((new BoxLayout(coursePnl, BoxLayout.Y_AXIS)));
        something = list;
        this.motherPnl = motherPnl;
        nextBtn.addActionListener(this);
        prevBtn.addActionListener(this);
        visibleCourseInfo();
    }

    private void visibleCourseInfo(){
        setCoursenameLbl();
        setCurrentorder();
        add(coursenameLbl);
        addCourse();
        add(coursePnl);

        JPanel downside = new JPanel();
        downside.add(prevBtn);
        downside.add(currentorder);
        downside.add(nextBtn);
        add(downside);
    }

    private void setCoursenameLbl(){
        coursenameLbl.setText("Course " + something.get(currentcourse).get(0).getCourseId());
    }

    private void setCurrentorder(){
        currentorder.setText(currentcourse + " / " + something.size());
    }

    private void addCourse(){
        List<CourseData> list = something.get(currentcourse);
        for(CourseData coursedata : list){
            buttons.add(new InfoButton(coursedata.getTourismName(), coursedata.getCourseOrder()));
        }
        for(InfoButton btn : buttons) {
            coursePnl.add(btn);
        }
    }

    private void changeCourse(){
        setCoursenameLbl();
        setCurrentorder();
        coursePnl.removeAll();
        buttons.clear();
        addCourse();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == nextBtn){
            if(currentcourse < something.size()) {
                currentcourse++;
                changeCourse();
            }
        } else if (e.getSource() == prevBtn) {
            if(currentcourse > 0){
                currentcourse--;
                changeCourse();
            }
        } else{

            motherPnl.clearinfoPnl();
            //motherPnl.infoPnl = new AttractionInfo(motherPnl, something.get(currentcourse).get());
        }
    }

    class InfoButton extends JButton implements ActionListener{
        private long order;

        public InfoButton(String text, long order){
            super(text);
            this.order = order;
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            AttractionInfo attractionInfo = new AttractionInfo(motherPnl, something.get(currentcourse).get((int)order));
            motherPnl.pushinfoPnl(attractionInfo);
            motherPnl.drawInfoPnl();
        }
    }
}
