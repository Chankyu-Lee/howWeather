package org.howWeather;

import javax.swing.*;
import java.awt.*;

public class FilterGroup extends JPanel {
    JLabel groupNameLbl;
    JPanel checkBoxPnl = new JPanel();
    JCheckBox[] checkBoxes;

    public FilterGroup(String groupname,String[] names){
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setBackground(Color.white);
        checkBoxPnl.setBackground(Color.white);
        checkBoxPnl.setLayout(new BoxLayout(checkBoxPnl,BoxLayout.Y_AXIS));

        groupNameLbl = new JLabel(groupname);
        groupNameLbl.setFont(new Font("맑은 고딕",Font.BOLD,20));
        checkBoxes = new JCheckBox[names.length];
        GenerateCheckBoxes(names);
        AddAll();
    }

    private void GenerateCheckBoxes(String[] names){
        for(int i = 0;i< names.length;i++){
            checkBoxes[i] = new JCheckBox(names[i]);
            checkBoxes[i].setBackground(Color.white);
            checkBoxes[i].setFont(new Font("맑은 고딕",Font.PLAIN,15));

        }
    }

    private void AddAll(){
        this.add(groupNameLbl);
        for (JCheckBox checkBox : checkBoxes) {
            checkBoxPnl.add(checkBox);
        }
        this.add(checkBoxPnl);
    }

    public void uncheckAll(){
        for (JCheckBox checkBox: checkBoxes){
            checkBox.setSelected(false);
        }
    }

}
