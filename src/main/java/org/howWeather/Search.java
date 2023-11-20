package org.howWeather;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Search extends JPanel {

    Frame motherFrm;
    JTextField searchFld = new JTextField(20);
    JButton searchBtn = new JButton("검색");                      // JFrame 안쪽 영역에 들어갈 클릭 버튼

    public Search(Frame frm){
        motherFrm = frm;
        searchBtn.addActionListener(new SearchEvent());
        VisibleSearch();
    }

    public void VisibleSearch(){
        add(searchFld);
        add(searchBtn);

        setVisible(true);
    }

    private class SearchEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            if(!(searchFld.getText().isEmpty())){
                motherFrm.filter.unckeckAll();
                //NaverMap2.map_service(motherFrm, Long.parseLong(searchFld.getText()));
            }
        }
    }
}