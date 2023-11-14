package org.howWeather;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Filter extends JPanel implements ActionListener {

    Frame motherfrm;

    public Filter(Frame frm){
        motherfrm = frm;
        VisibleFilter();
    }

    public void VisibleFilter(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.white);
        setPreferredSize(new Dimension(300,750));

        FilterGroup themeGrp = new FilterGroup("테마",new String[]{"문화/예술","쇼핑/놀이","자연/힐링","종교/역사/전통","체험/학습/산업","캠핑/스포츠"});
        FilterGroup locateGrp = new FilterGroup("지역",new String[]{"수도권","강원","충청","경상","전라"});
        FilterGroup weatherGrp = new FilterGroup("날씨",new String[]{"맑음","비","흐림"});
        add(locateGrp);
        add(themeGrp);
        add(weatherGrp);

        JButton applyBtn = new JButton("필터 적용");
        applyBtn.addActionListener(this);
        add(applyBtn);
    }

    public void actionPerformed(ActionEvent e){

    }
}
