package org.howWeather;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Search implements ActionListener {
    Frame search;

    public Search(Frame search){ this.search = search; }

    @Override
    public void actionPerformed(ActionEvent e){ VisibleSearch(); }

    public void VisibleSearch(){
        search.mapLbl.setIcon(new ImageIcon("C:/Users/rudwn/Desktop/Blue Archive/디지털 굳즈/활륜대제 후야제.png"));
    }
}