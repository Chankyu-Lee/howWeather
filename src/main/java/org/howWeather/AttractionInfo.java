package org.howWeather;

import javax.swing.*;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




public class AttractionInfo extends JPanel implements ActionListener {
    Search motherPnl;
    JButton closeBtn = new JButton("X");

    AttractionInfo(Search motherPnl, CourseData attraction){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.motherPnl = motherPnl;
        closeBtn.addActionListener(this);
        visibleinfo(attraction);
    }

    private void visibleinfo(CourseData attraction){
        JPanel btnPnl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPnl.setBackground(Color.WHITE);
        closeBtn.setBackground(Color.WHITE);
        btnPnl.add(closeBtn);
        add(btnPnl, BorderLayout.NORTH);

        long courseId = attraction.getCourseId();
        CourseWeather[][] weatherData = WeatherApi.getCourseWeatherDoubleArr(courseId);

        JTextPane info = new JTextPane();
        info.setEditable(false);
        StyledDocument doc = info.getStyledDocument();

        Style style = info.addStyle("TimeStyle", null);
        StyleConstants.setForeground(style, Color.BLUE);

        Style defaultStyle = info.addStyle("DefaultStyle", null);
        StyleConstants.setForeground(defaultStyle, Color.BLACK);

        try {
            doc.insertString(doc.getLength(), (attraction.getIndoorType().equals("indoor") ? "실내" : "실외") + " ", defaultStyle);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        if (weatherData != null && weatherData.length > 0) {
            for (CourseWeather[] courseWeathers : weatherData) {
                for (CourseWeather courseWeather : courseWeathers) {
                    if (courseWeather != null) {
                        try {
                            doc.insertString(doc.getLength(), "시간: " + courseWeather.getTm() + "\n", style);
                            doc.insertString(doc.getLength(), courseWeather.getSpotName() + (attraction.getIndoorType().equals("indoor") ? "(실내)" : "(실외)") + " "+ "\n", defaultStyle);
                            doc.insertString(doc.getLength(), "하늘 상태: " + courseWeather.getSkyConditionString() + "\n", defaultStyle);
                            doc.insertString(doc.getLength(), "기온: " + courseWeather.getTh3() + "℃\n", defaultStyle);
                            doc.insertString(doc.getLength(), "습도: " + courseWeather.getRhm() + "%\n", defaultStyle);
                            doc.insertString(doc.getLength(), "강수량: " + courseWeather.getPop() + "mm\n", defaultStyle);
                            doc.insertString(doc.getLength(), "------------------------\n", defaultStyle);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else {
            try {
                doc.insertString(doc.getLength(), "날씨 정보를 가져올 수 없습니다.\n", defaultStyle);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }

        JScrollPane scrollPane = new JScrollPane(info);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        info.setCaretPosition(0);
    }






    public void actionPerformed(ActionEvent e) {
        motherPnl.popinfoPnl();
        motherPnl.clearinfoPnl();
    }
}
