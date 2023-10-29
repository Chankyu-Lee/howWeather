package org.howWeather;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Filter implements ActionListener {
    Frame filter;

    public Filter(Frame filter){ this.filter = filter; }

    @Override
    public void actionPerformed(ActionEvent e){ VisibleFilter(); }

    public void VisibleFilter(){
        JPanel filterPan = new JPanel();
        filterPan.setLayout(new BoxLayout(filterPan, BoxLayout.Y_AXIS));

        filterPan.add(new JLabel("지역"));
        filterPan.add(new JLabel("지역"));
        filterPan.add(new JLabel("지역"));
        filterPan.add(new JLabel("지역"));
        filter.frameCnt.add(BorderLayout.CENTER,filterPan);
    }
}
