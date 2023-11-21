package org.howWeather;

import java.awt.*;
import javax.swing.*;

public class Frame extends JFrame {
    JTextField searchFld;
    static JLabel mapLbl = new JLabel();
    Container frameCnt;

    public void initGUI() {
        setTitle("howWeather");                // 프레임 생성
        setSize(1400, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     // 프레임의 X 클릭 시 종료.
        frameCnt = getContentPane();                     // JFrame 안쪽 영역.

        NaverMap2.setBasicMap(mapLbl);
        Search search = new Search(this);
        Filter filter = new Filter(this);

        frameCnt.add(BorderLayout.WEST,filter);
        frameCnt.add(BorderLayout.CENTER, search);                         // 상단 searchPan 세팅
        frameCnt.add(BorderLayout.EAST, mapLbl);                          // 센터 mapLbl 세팅

        setVisible(true);
    }
}
