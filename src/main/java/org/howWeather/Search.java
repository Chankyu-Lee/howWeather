package org.howWeather;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class Search extends JPanel implements ActionListener {

    private Frame motherFrm;
    private JTextField searchFld = new JTextField(20);
    private JButton searchBtn = new JButton("검색");
    private JPanel infoPnl;
    private Stack<JPanel> infoPnlStack = new Stack<>();
    private JButton resetBtn = new JButton("초기화"); // 초기화 버튼 추가

    public Search(Frame frm){
        motherFrm = frm;
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        searchBtn.addActionListener(this);
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        VisibleSearch();
    }

    public void VisibleSearch(){
        JPanel searchPnl = new JPanel();

        searchPnl.add(searchFld);
        searchPnl.add(searchBtn);
        searchPnl.add(resetBtn); // searchPnl에 초기화 버튼 추가

        add(searchPnl);

        setVisible(true);
    }


    public void pushinfoPnl(JPanel pnl){
        infoPnlStack.push(pnl);
        drawInfoPnl();
    }

    public void popinfoPnl(){
        infoPnlStack.pop();
    }

    public void drawInfoPnl(){
        if(infoPnl != null){
            clearinfoPnl();
        }
        infoPnl = infoPnlStack.peek();
        add(infoPnl);
        motherFrm.refresh();
    }

    public void clearinfoPnl(){
        remove(infoPnl);
        infoPnl = null;
        motherFrm.refresh();
        if(!(infoPnlStack.empty())){
            drawInfoPnl();
        }
    }

    public void reset() {
        // 필터 초기화
        motherFrm.filter.unckeckAll();

        // 결과 초기화
        infoPnlStack.clear();
        clearinfoPnl();

        // 지도 초기화
        NaverMap2.setBasicMap(motherFrm.mapLbl);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        String text = searchFld.getText();
        if(!(text.isEmpty())){
            motherFrm.filter.unckeckAll();
            infoPnlStack.clear();
            NaverMap2.map_service(motherFrm, Long.parseLong(text));
            AttractionInfo attractionInfo = new AttractionInfo(this,DataBase.getCourseDataList(Long.parseLong(text)).get(1));
            pushinfoPnl(attractionInfo);
        }
    }

}
