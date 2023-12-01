package org.howWeather;

import java.awt.*;
import java.io.File;
import javax.swing.*;

public class Frame extends JFrame {
    static JLabel mapLbl = new JLabel();
    Filter filter = new Filter(this);
    Search search = new Search(this);
    Container frameCnt;

    public void initGUI() {
        setTitle("howWeather");                // 프레임 생성
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     // 프레임의 X 클릭 시 종료.
        frameCnt = getContentPane();                     // JFrame 안쪽 영역.
        frameCnt.setLayout(new BoxLayout(frameCnt, BoxLayout.X_AXIS));

        NaverMap2.setBasicMap();

        frameCnt.add(filter);
        frameCnt.add(search);
        frameCnt.add(mapLbl);

        setVisible(true);
    }

    public void refresh(){
        revalidate();
        repaint();
    }
}
