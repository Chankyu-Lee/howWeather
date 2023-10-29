package org.howWeather;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Filter implements ActionListener {
    Frame filter;

    public Filter(Frame filter){ this.filter = filter; }

    public JCheckBox mkCheckBox(JPanel p, String s){
        JCheckBox c = new JCheckBox(s);
        c.setBackground(Color.white);

        p.add(c);

        return c;
    }

    public void actionPerformed(ActionEvent e){ VisibleFilter(); }

    public void VisibleFilter(){
        JPanel filterPan = new JPanel();
        filterPan.setLayout(new BoxLayout(filterPan, BoxLayout.Y_AXIS));
        filterPan.setBackground(Color.white);

        JPanel themeChecks = new JPanel();
        themeChecks.setBackground(Color.white);
        themeChecks.setLayout(new FlowLayout(FlowLayout.LEFT));

        JCheckBox themeCheck_culture = mkCheckBox(themeChecks,"문화/예술");
        JCheckBox themeCheck_shopping = mkCheckBox(themeChecks,"쇼핑/놀이");
        JCheckBox themeCheck_nature = mkCheckBox(themeChecks,"자연/힐링");
        JCheckBox themeCheck_tradition = mkCheckBox(themeChecks,"종교/역사/전통");
        JCheckBox themeCheck_practice = mkCheckBox(themeChecks,"체험/학습/산업");
        JCheckBox themeCheck_camping = mkCheckBox(themeChecks,"캠핑/스포츠");

        //JButton closeBtn = new JButton("X");
        //filterPan.add(closeBtn);
        filterPan.add(new JLabel("테마"));
        filterPan.add(themeChecks);

        filter.frameCnt.add(BorderLayout.CENTER,filterPan);

        filter.frameCnt.validate();
        filter.frameCnt.repaint();
    }
}
