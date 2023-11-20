package org.howWeather;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Filter extends JPanel {

    Frame motherfrm;
    FilterGroup[] filterGroups = new FilterGroup[3];

    public Filter(Frame frm){
        motherfrm = frm;
        VisibleFilter();
    }

    public void VisibleFilter(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.white);
        setPreferredSize(new Dimension(300,750));

        filterGroups[0] = new FilterGroup("테마",new String[]{"문화/예술","쇼핑/놀이","자연/힐링","종교/역사/전통","체험/학습/산업","캠핑/스포츠"});
        filterGroups[1] = new FilterGroup("지역",new String[]{"수도권","강원","충청","경상","전라"});
        filterGroups[2] = new FilterGroup("날씨",new String[]{"맑음","비","흐림"});
        add(filterGroups[0]);
        add(filterGroups[1]);
        add(filterGroups[2]);

        JButton applyBtn = new JButton("필터 적용");
        applyBtn.addActionListener(new FilterEvent());
        add(applyBtn);
    }

    public void unckeckAll(){
        for(FilterGroup filtergroup: filterGroups){
            filtergroup.uncheckAll();
        }
    }

    private class FilterEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){

        }
    }
}
