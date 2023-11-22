package org.howWeather;

import javax.swing.*;
import javax.swing.text.StyledEditorKit;
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
        JPanel btnPnl = new JPanel();
        btnPnl.setLayout(new FlowLayout(FlowLayout.RIGHT));
        btnPnl.setBackground(Color.WHITE);
        closeBtn.setBackground(Color.WHITE);
        btnPnl.add(closeBtn);
        add(btnPnl);

        JTextArea name = new JTextArea(attraction.getTourismName());
        add(name);

        JTextArea info = new JTextArea();
        info.append("fsnkdnasdsal\n");
        info.append(attraction.getThemeName() + "\t" + attraction.getIndoorType() + "\n");
        info.append("맑음\n기온: 20 C\t습도: 46%\n강수량: 0ml\n");

        add(info);
    }

    public void actionPerformed(ActionEvent e) {
        motherPnl.popinfoPnl();
        motherPnl.clearinfoPnl();
    }
}
