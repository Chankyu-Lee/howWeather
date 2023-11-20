package org.howWeather;

import javax.swing.*;
import java.awt.*;

public class FilterGroup extends JPanel{
    JLabel groupNameLbl;
    JPanel checkBoxPnl = new JPanel();
    JCheckBox[] checkBoxes;

    public FilterGroup(String groupname,String[] names){
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.setBackground(Color.white);
        this.checkBoxPnl.setBackground(Color.white);
        this.checkBoxPnl.setLayout(new FlowLayout(FlowLayout.LEFT));

        this.groupNameLbl = new JLabel(groupname);
        this.checkBoxes = new JCheckBox[names.length];
        GenerateCheckBoxes(names);
        AddAll();
    }

    private void GenerateCheckBoxes(String[] names){
        for(int i = 0;i< names.length;i++){
            this.checkBoxes[i] = new JCheckBox(names[i]);
            this.checkBoxes[i].setBackground(Color.white);
        }
    }

    private void AddAll(){
        this.add(groupNameLbl);
        for (JCheckBox checkBox : checkBoxes) {
            this.checkBoxPnl.add(checkBox);
        }
        this.add(checkBoxPnl);
    }

    public void uncheckAll(){
        for (JCheckBox checkBox: checkBoxes){
            checkBox.setSelected(false);
        }
    }
}
