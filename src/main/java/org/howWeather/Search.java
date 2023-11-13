package org.howWeather;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Search implements ActionListener {
    Frame search;

    public Search(Frame search){ this.search = search; }

    @Override
    public void actionPerformed(ActionEvent e){ VisibleSearch(); }

    public void VisibleSearch(){

    }
}