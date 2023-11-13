package org.howWeather;

import java.awt.*;

import javax.swing.*;

public class Frame {
    JTextField searchFld;
    JLabel mapLbl = new JLabel();
    Container frameCnt;

    public void initGUI() {
        JFrame frm = new JFrame("howWeather");                // 프레임 생성
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     // 프레임의 X 클릭 시 종료.
      
        Container c = frm.getContentPane();                     // JFrame 안쪽 영역.


        imageLabel = new JLabel("지도보기");                    // JFrame 안쪽 영역 상단에 들어갈 지도보기
        JPanel pan = new JPanel();
        JLabel addressLbl = new JLabel("코스 번호 입력");             // JFrame 안쪽 영역 상단에 들어갈 주소입력
        address = new JTextField(50);
        JButton btn = new JButton("클릭");                      // JFrame 안쪽 영역에 들어갈 클릭 버튼
        pan.add(addressLbl);
        pan.add(address);
        pan.add(btn);
        btn.addActionListener(new NaverMap2(this));              // pan에 생성한 버튼(btn) 클릭 시 처리하는 이벤트 핸들러.


        c.add(BorderLayout.NORTH, pan);                         // 상단 pan 세팅
        c.add(BorderLayout.CENTER, imageLabel);                 // 센터 imageLabel 세팅
        // c.add(BorderLayout.SOUTH, pan1);                        // 하단 pan1 세팅

        frm.setSize(730, 660);
        frm.setVisible(true);

    }
}
