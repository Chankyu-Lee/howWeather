package org.howWeather;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.text.*;

public class AttractionInfo extends JPanel {
    Search motherPnl;
    JButton closeBtn = new JButton("X");

    AttractionInfo(Search motherPnl, CourseData attraction, CourseWeather[] weathers){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.motherPnl = motherPnl;
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                motherPnl.popinfoPnl();
                motherPnl.clearinfoPnl();
                NaverMap2.setCourseMap(attraction.getCourseId());
            }
        });
        NaverMap2.setAttractionMap(attraction);
        visibleinfo(attraction, weathers);
    }

    private void visibleinfo(CourseData attraction, CourseWeather[] weathers){
        JPanel btnPnl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPnl.setBackground(Color.WHITE);
        closeBtn.setBackground(Color.WHITE);
        btnPnl.add(closeBtn);
        add(btnPnl, BorderLayout.NORTH);

        JTextPane info = new JTextPane();
        info.setEditable(false);
        StyledDocument doc = info.getStyledDocument();

        Style style = info.addStyle("TimeStyle", null);
        StyleConstants.setForeground(style, Color.BLUE);

        Style defaultStyle = info.addStyle("DefaultStyle", null);
        StyleConstants.setForeground(defaultStyle, Color.BLACK);

        try {
            doc.insertString(doc.getLength(), attraction.getTourismName() + "\n",defaultStyle);
            doc.insertString(doc.getLength(), "주소?\n",defaultStyle);
            doc.insertString(doc.getLength(), "테마: " + attraction.getThemeName() + "\t실내외 여부: " + (attraction.getIndoorType().equals("indoor") ? "실내" : "실외") + "\n", defaultStyle);
            doc.insertString(doc.getLength(), "------------ 기후 정보 ------------\n", defaultStyle);

        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        if (weathers != null && weathers.length > 0) {
            for (CourseWeather weather : weathers) {
                if (weather != null) {
                    try {
                        doc.insertString(doc.getLength(), "시간: " + weather.getTm() + "\n", style);
                        doc.insertString(doc.getLength(), "하늘 상태: " + weather.getSkyConditionString() + "\n", defaultStyle);
                        doc.insertString(doc.getLength(), "기온: " + weather.getTh3() + "℃\n", defaultStyle);
                        doc.insertString(doc.getLength(), "습도: " + weather.getRhm() + "%\n", defaultStyle);
                        doc.insertString(doc.getLength(), "강수량: " + weather.getPop() + "mm\n", defaultStyle);
                        doc.insertString(doc.getLength(), "------------------------\n", defaultStyle);
                    } catch (BadLocationException e) {
                        e.printStackTrace();
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

}
