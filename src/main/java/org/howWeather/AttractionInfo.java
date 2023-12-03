package org.howWeather;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.text.*;

public class AttractionInfo extends JPanel {
    Search motherPnl;
    JButton closeBtn = new JButton("X");
    CourseWeather[] weathers;

    AttractionInfo(Search motherPnl, CourseData attraction, CourseWeather[] weathers) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.motherPnl = motherPnl;
        this.weathers = weathers;
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                motherPnl.popinfoPnl();
                motherPnl.clearinfoPnl();
                NaverMap2.setCourseMap(attraction.getCourseId());
            }
        });
        NaverMap2.setAttractionMap(attraction);
        visibleinfo(attraction);
    }

    private void visibleinfo(CourseData attraction){
        JPanel btnPnl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPnl.setBackground(Color.WHITE);
        closeBtn.setBackground(Color.WHITE);
        JButton graphBtn = new JButton("그래프");
        graphBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Graph(weathers);
            }
        });
        graphBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        graphBtn.setBackground(Color.WHITE);
        btnPnl.add(graphBtn);
        btnPnl.add(closeBtn);
        add(btnPnl, BorderLayout.NORTH);

        JTextPane info = new JTextPane();
        info.setEditable(false);
        StyledDocument doc = info.getStyledDocument();

        Style style = info.addStyle("TimeStyle", null);
        StyleConstants.setForeground(style, Color.BLUE);

        Style defaultStyle = info.addStyle("DefaultStyle", null);
        StyleConstants.setForeground(defaultStyle, Color.BLACK);

        Style NameStyle = info.addStyle("NameStyle", null);
        StyleConstants.setFontSize(NameStyle, 15);
        StyleConstants.setBold(NameStyle, true);

        // 기온 정보를 위한 스타일 생성
        Style tempStyleLow = info.addStyle("TempStyleLow", null);
        StyleConstants.setForeground(tempStyleLow, Color.BLUE);

        Style tempStyleCommon = info.addStyle("TempStyleCommon", null);
        StyleConstants.setForeground(tempStyleCommon, Color.BLACK);

        Style tempStyleHigh = info.addStyle("TempStyleHigh", null);
        StyleConstants.setForeground(tempStyleHigh, Color.RED);

        // 습도 정보를 위한 스타일 생성
        Style humidityStyleLow = info.addStyle("HumidityStyleLow", null);
        StyleConstants.setForeground(humidityStyleLow, Color.RED);

        Style humidityStyleCommon = info.addStyle("HumidityStyleLow", null);
        StyleConstants.setForeground(humidityStyleCommon, Color.BLACK);

        Style humidityStyleHigh = info.addStyle("HumidityStyleLow", null);
        StyleConstants.setForeground(humidityStyleHigh, Color.BLUE);

        // 강수 정보를 위한 스타일 생성
        Style rainfallStyle = info.addStyle("rainfallStyle", null);
        StyleConstants.setForeground(rainfallStyle, Color.BLUE);

        try {
            doc.insertString(doc.getLength(), attraction.getTourismName() + "\n",NameStyle);
            doc.insertString(doc.getLength(), "테마: " + attraction.getThemeName() + "\t실내외 여부: " + (attraction.getIndoorType().equals("indoor") ? "실내" : "실외") + "\n", defaultStyle);
            doc.insertString(doc.getLength(), "------------ 기후 정보 ------------\n", defaultStyle);

        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        if (weathers != null && weathers.length > 0) {
            for (CourseWeather weather : weathers) {
                if (weather != null) {
                    try {
                        doc.insertString(doc.getLength(), "시간: " + weather.getTm() + "\n", defaultStyle);
                        doc.insertString(doc.getLength(), "하늘 상태: " + weather.getSkyConditionString() + "\n", defaultStyle);
                        Style tempStyle;
                        if (weather.getTh3() <= 10) {
                            tempStyle = tempStyleLow;
                        } else if (weather.getTh3() >= 20) {
                            tempStyle = tempStyleHigh;
                        } else {
                            tempStyle = defaultStyle;
                        }
                        Style humidityStyle;
                        if (weather.getRhm() <= 40 ) {
                            humidityStyle = humidityStyleLow;
                        } else if (weather.getRhm() >= 70) {
                            humidityStyle = humidityStyleHigh;
                        } else {
                            humidityStyle = humidityStyleCommon;
                        }
                        doc.insertString(doc.getLength(), "기온: " + weather.getTh3() + "℃\n", tempStyle);
                        doc.insertString(doc.getLength(), "습도: " + weather.getRhm() + "%\n", humidityStyle);
                        doc.insertString(doc.getLength(), "강수량: " + weather.getPop() + "mm\n", rainfallStyle);
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
