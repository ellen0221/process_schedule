import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OS_save extends JFrame {

    private JPanel gui;
    private JButton FF_button;
    private JButton NF_button;
    private JButton BF_button;
    private JButton WF_button;
    private JButton add;
    private JLabel FF_11;
    private JLabel FF_21;
    private JLabel FF_12;
    private JLabel FF_13;
    private JLabel FF_14;
    private JLabel FF_15;
    private JLabel FF_22;
    private JLabel FF_23;
    private JLabel FF_24;
    private JLabel FF_25;
    private JLabel NF_11;
    private JLabel NF_21;
    private JLabel NF_12;
    private JLabel NF_22;
    private JLabel NF_13;
    private JLabel NF_23;
    private JLabel NF_14;
    private JLabel NF_24;
    private JLabel NF_15;
    private JLabel NF_25;
    private JLabel BF_11;
    private JLabel BF_12;
    private JLabel BF_13;
    private JLabel BF_14;
    private JLabel BF_15;
    private JLabel BF_21;
    private JLabel BF_22;
    private JLabel BF_23;
    private JLabel BF_24;
    private JLabel BF_25;
    private JLabel WF_11;
    private JLabel WF_12;
    private JLabel WF_13;
    private JLabel WF_14;
    private JLabel WF_15;
    private JLabel WF_21;
    private JLabel WF_22;
    private JLabel WF_23;
    private JLabel WF_24;
    private JLabel WF_25;
    private JLabel p_1;
    private JTextField size_a;
    private JTextField ntime_a;
    private JTextField ntime_b;
    private JTextField ntime_c;
    private JTextField ntime_d;
    private JTextField ntime_e;
    private JTextField size_b;
    private JTextField size_c;
    private JTextField size_d;
    private JTextField size_e;

    private int size = 100;   // 内存大小
    private int lastpoint = 0;  // 上次分配的空闲区位置
    private List<Point> points;   // 空闲内存分区

    public int time_a = 5;
    public int time_b = 2;
    public int time_c = 8;
    public int time_d = 3;
    public int time_e = 4;
    public int s_a = 50;
    public int s_b = 60;
    public int s_c = 30;
    public int s_d = 10;
    public int s_e = 40;

    // 进程
    public static Process a;
    public static Process b;
    public static Process c;
    public static Process d;
    public static Process e1;

    public int nowtime = 0;

    public List<Process> list = new ArrayList<>();
    public Arithmetic ar = new Arithmetic(nowtime);

    public OS_save() {
        super("存储管理");
        this.add(gui);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        this.points = new ArrayList<>();
        points.add(new Point(0, size));

        ntime_a.setText(String.valueOf(time_a));
        ntime_b.setText(String.valueOf(time_b));
        ntime_c.setText(String.valueOf(time_c));
        ntime_d.setText(String.valueOf(time_d));
        ntime_e.setText(String.valueOf(time_e));
        size_a.setText(String.valueOf(s_a));
        size_b.setText(String.valueOf(s_b));
        size_c.setText(String.valueOf(s_c));
        size_d.setText(String.valueOf(s_d));
        size_e.setText(String.valueOf(s_e));

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                time_a = Integer.parseInt(ntime_a.getText());
                time_b = Integer.parseInt(ntime_b.getText());
                time_c = Integer.parseInt(ntime_c.getText());
                time_d = Integer.parseInt(ntime_d.getText());
                time_e = Integer.parseInt(ntime_e.getText());
                s_a = Integer.parseInt(size_a.getText());
                s_b = Integer.parseInt(size_b.getText());
                s_c = Integer.parseInt(size_c.getText());
                s_d = Integer.parseInt(size_d.getText());
                s_e = Integer.parseInt(size_e.getText());
            }
        });
        FF_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                a = new Process("A",time_a,s_a,time_a);
                b = new Process("B",time_b,s_b,time_b);
                c = new Process("C",time_c,s_c,time_c);
                d = new Process("D",time_d,s_d,time_d);
                e1 = new Process("E",time_e,s_e,time_e);
                list.clear();
                list.add(e1);
                list.add(d);
                list.add(c);
                list.add(b);
                list.add(a);
                List<Process> finish = ar.FF(list, points);
                for (int i = 0; i < finish.size(); i++) {
                    Process p = finish.get(i);
                    switch (p.getName()) {
                        case "A":
                            FF_11.setText(Integer.toString(p.getFirstpoint()));
                            FF_21.setText(Integer.toString(p.getGtime()));
                            break;
                        case "B":
                            FF_12.setText(Integer.toString(p.getFirstpoint()));
                            FF_22.setText(Integer.toString(p.getGtime()));
                            break;
                        case "C":
                            FF_13.setText(Integer.toString(p.getFirstpoint()));
                            FF_23.setText(Integer.toString(p.getGtime()));
                            break;
                        case "D":
                            FF_14.setText(Integer.toString(p.getFirstpoint()));
                            FF_24.setText(Integer.toString(p.getGtime()));
                            break;
                        case "E":
                            FF_15.setText(Integer.toString(p.getFirstpoint()));
                            FF_25.setText(Integer.toString(p.getGtime()));
                            break;
                    }
                }
            }
        });
        NF_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                a = new Process("A",time_a,s_a,time_a);
                b = new Process("B",time_b,s_b,time_b);
                c = new Process("C",time_c,s_c,time_c);
                d = new Process("D",time_d,s_d,time_d);
                e1 = new Process("E",time_e,s_e,time_e);
                list.clear();
                list.add(e1);
                list.add(d);
                list.add(c);
                list.add(b);
                list.add(a);
                List<Process> finish = ar.NF(list, points);
                for (int i = 0; i < finish.size(); i++) {
                    Process p = finish.get(i);
                    switch (p.getName()) {
                        case "A":
                            NF_11.setText(Integer.toString(p.getFirstpoint()));
                            NF_21.setText(Integer.toString(p.getGtime()));
                            break;
                        case "B":
                            NF_12.setText(Integer.toString(p.getFirstpoint()));
                            NF_22.setText(Integer.toString(p.getGtime()));
                            break;
                        case "C":
                            NF_13.setText(Integer.toString(p.getFirstpoint()));
                            NF_23.setText(Integer.toString(p.getGtime()));
                            break;
                        case "D":
                            NF_14.setText(Integer.toString(p.getFirstpoint()));
                            NF_24.setText(Integer.toString(p.getGtime()));
                            break;
                        case "E":
                            NF_15.setText(Integer.toString(p.getFirstpoint()));
                            NF_25.setText(Integer.toString(p.getGtime()));
                            break;
                    }
                }
            }
        });
        BF_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                a = new Process("A",time_a,s_a,time_a);
                b = new Process("B",time_b,s_b,time_b);
                c = new Process("C",time_c,s_c,time_c);
                d = new Process("D",time_d,s_d,time_d);
                e1 = new Process("E",time_e,s_e,time_e);
                list.clear();
                list.add(e1);
                list.add(d);
                list.add(c);
                list.add(b);
                list.add(a);
                List<Process> finish = ar.BF(list, points);
                for (int i = 0; i < finish.size(); i++) {
                    Process p = finish.get(i);
                    switch (p.getName()) {
                        case "A":
                            BF_11.setText(Integer.toString(p.getFirstpoint()));
                            BF_21.setText(Integer.toString(p.getGtime()));
                            break;
                        case "B":
                            BF_12.setText(Integer.toString(p.getFirstpoint()));
                            BF_22.setText(Integer.toString(p.getGtime()));
                            break;
                        case "C":
                            BF_13.setText(Integer.toString(p.getFirstpoint()));
                            BF_23.setText(Integer.toString(p.getGtime()));
                            break;
                        case "D":
                            BF_14.setText(Integer.toString(p.getFirstpoint()));
                            BF_24.setText(Integer.toString(p.getGtime()));
                            break;
                        case "E":
                            BF_15.setText(Integer.toString(p.getFirstpoint()));
                            BF_25.setText(Integer.toString(p.getGtime()));
                            break;
                    }
                }
            }
        });
        WF_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                a = new Process("A",time_a,s_a,time_a);
                b = new Process("B",time_b,s_b,time_b);
                c = new Process("C",time_c,s_c,time_c);
                d = new Process("D",time_d,s_d,time_d);
                e1 = new Process("E",time_e,s_e,time_e);
                list.clear();
                list.add(e1);
                list.add(d);
                list.add(c);
                list.add(b);
                list.add(a);
                List<Process> finish = ar.WF(list, points);
                for (int i = 0; i < finish.size(); i++) {
                    Process p = finish.get(i);
                    switch (p.getName()) {
                        case "A":
                            WF_11.setText(Integer.toString(p.getFirstpoint()));
                            WF_21.setText(Integer.toString(p.getGtime()));
                            break;
                        case "B":
                            WF_12.setText(Integer.toString(p.getFirstpoint()));
                            WF_22.setText(Integer.toString(p.getGtime()));
                            break;
                        case "C":
                            WF_13.setText(Integer.toString(p.getFirstpoint()));
                            WF_23.setText(Integer.toString(p.getGtime()));
                            break;
                        case "D":
                            WF_14.setText(Integer.toString(p.getFirstpoint()));
                            WF_24.setText(Integer.toString(p.getGtime()));
                            break;
                        case "E":
                            WF_15.setText(Integer.toString(p.getFirstpoint()));
                            WF_25.setText(Integer.toString(p.getGtime()));
                            break;
                    }
                }
            }
        });
    }
}
