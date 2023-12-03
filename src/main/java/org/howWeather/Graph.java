package org.howWeather;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Graph extends JFrame implements ActionListener {
    int[] x;
    int[] ytemp;
    int[] ypop;
    String[] time = new String[2];

    JPanel graphPnl;
    public Graph(CourseWeather[] weathers){
        setLayout(new BorderLayout());
        Container content = getContentPane();
        setTitle("기상 정보");
        setSize(900,600);
        ytemp = new int[weathers.length];
        ypop = new int[weathers.length];
        time = new String[weathers.length];
        this.x = new int[weathers.length];

        int x = 100;

        for(int i = 0; i < weathers.length; i++){
            this.x[i] = x;
            ytemp[i] = (int)weathers[i].getTh3() * (-5) + 300;
            ypop[i] = (int)weathers[i].getPop() * (-2) + 450;
            x += 50;
        }
        time[0] = weathers[0].getTm();
        time[1] = weathers[8].getTm();

        JPanel downPnl = new JPanel();

        JButton tempBtn = new JButton("기온");
        tempBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        tempBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(graphPnl != null){
                    content.remove(graphPnl);
                    graphPnl = null;
                }
                graphPnl = new TempGraphPanel();
                content.add(graphPnl, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });

        JButton popBtn = new JButton("강수량");
        popBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        popBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(graphPnl != null){
                    content.remove(graphPnl);
                    graphPnl = null;
                }
                graphPnl = new PopGraphPanel();
                content.add(graphPnl, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });

        downPnl.add(tempBtn);
        downPnl.add(popBtn);

        content.add(downPnl,BorderLayout.SOUTH);

        setVisible(true);
    }

    class TempGraphPanel extends JPanel{
        public void paint(Graphics g){
            super.paintComponent(g);

            g.clearRect(0,0,getWidth(),getHeight());
            g.drawLine(100,300,x[15],300);
            g.drawString("0℃", 70, 300);

            g.drawString("-10℃", 70, 350);
            g.drawString("-20℃", 70, 400);
            g.drawString("-30℃", 70, 450);

            g.setColor(Color.GRAY);
            g.drawLine(100,350,x[15],350);
            g.drawLine(100,400,x[15],400);
            g.drawLine(100,450,x[15],450);


            for(int i = 1; i < 5; i++){
                g.setColor(Color.BLACK);
                g.drawString(i*10 + "℃", 70, 300-(i*50));
                g.setColor(Color.GRAY);
                g.drawLine(100,300-(50*i),x[15],300-(50*i));
            }

            int t = 0;
            for(int i = 0; i < 16; i++){
                g.setColor(Color.BLACK);
                if ( t == 24){
                    t = 0;
                }
                if(i == 0){
                    g.drawString(time[0], x[i], 480);
                } else if (i == 8) {
                    g.drawString(time[1], x[i], 480);
                } else{
                    g.drawString(t+":00", x[i], 470);
                }

                g.setColor(Color.GRAY);
                g.drawLine(x[i],460,x[i],50);
                t += 3;
            }

            g.setColor(Color.BLUE);
            g.drawPolyline(x,ytemp,16);
        }
    }

    class PopGraphPanel extends JPanel{
        public void paint(Graphics g){
            super.paintComponent(g);

            g.clearRect(0,0,getWidth(),getHeight());
            g.drawLine(70,450,x[15],450);
            g.drawString("0mm", 70, 450);
            g.drawLine(100,450,100,150);

            int mm = 15;
            for(int i = 1; i < 11; i++){
                g.setColor(Color.BLACK);
                g.drawString( mm + "mm", 60, 450-(i*30));
                g.setColor(Color.GRAY);
                g.drawLine(100,450-(30*i),x[15],450-(30*i));
                mm += 15;
            }

            int t = 0;
            for(int i = 0; i < 16; i++){
                g.setColor(Color.BLACK);
                if ( t == 24){
                    t = 0;
                }
                if(i == 0){
                    g.drawString(time[0], x[i], 480);
                } else if (i == 8) {
                    g.drawString(time[1], x[i], 480);
                } else{
                    g.drawString(t+":00", x[i], 470);
                }

                g.setColor(Color.GRAY);
                g.drawLine(x[i],460,x[i],150);
                t += 3;
            }

            g.setColor(Color.BLUE);
            g.drawPolyline(x,ypop,16);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
    }
}
