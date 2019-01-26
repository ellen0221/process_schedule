import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class OS_work extends JFrame{
        private JTextField rtime_a;
        private JTextField rtime_b;
        private JTextField rtime_c;
        private JTextField rtime_d;
        private JTextField rtime_e;
        private JTextField ntime_b;
        private JTextField ntime_a;
        private JTextField ntime_c;
        private JTextField ntime_e;
        private JTextField ntime_d;
        private JTextField p_a;
        private JTextField p_b;
        private JTextField p_c;
        private JTextField p_d;
        private JTextField p_e;
        private JButton finish;
        private JButton SJF_button;
        private JButton HRRF_button;
        private JButton P_button;
        private JButton FCFS_button;
        private JLabel SJF_11;
        private JLabel SJF_21;
        private JLabel SJF_31;
        private JLabel SJF_12;
        private JLabel SJF_22;
        private JLabel SJF_32;
        private JLabel SJF_13;
        private JLabel SJF_23;
        private JLabel SJF_33;
        private JLabel SJF_14;
        private JLabel SJF_24;
        private JLabel SJF_34;
        private JLabel SJF_15;
        private JLabel SJF_25;
        private JLabel SJF_35;
        private JLabel SJF_avg1;
        private JLabel SJF_avg2;
        private JLabel HRRF_11;
        private JLabel HRRF_21;
        private JLabel HRRF_31;
        private JLabel HRRF_12;
        private JLabel HRRF_13;
        private JLabel HRRF_14;
        private JLabel HRRF_15;
        private JLabel HRRF_22;
        private JLabel HRRF_23;
        private JLabel HRRF_24;
        private JLabel HRRF_25;
        private JLabel HRRF_avg1;
        private JLabel HRRF_avg2;
        private JLabel HRRF_32;
        private JLabel HRRF_33;
        private JLabel HRRF_34;
        private JLabel HRRF_35;
        private JLabel P_11;
        private JLabel P_12;
        private JLabel P_13;
        private JLabel P_14;
        private JLabel P_15;
        private JLabel P_21;
        private JLabel P_22;
        private JLabel P_23;
        private JLabel P_24;
        private JLabel P_25;
        private JLabel P_avg1;
        private JLabel P_avg2;
        private JLabel P_35;
        private JLabel P_34;
        private JLabel P_33;
        private JLabel P_32;
        private JLabel P_31;
        private JLabel RR_11;
        private JLabel RR_12;
        private JLabel RR_13;
        private JLabel RR_14;
        private JLabel RR_15;
        private JLabel RR_21;
        private JLabel RR_22;
        private JLabel RR_23;
        private JLabel RR_24;
        private JLabel RR_25;
        private JLabel RR_avg1;
        private JLabel RR_avg2;
        private JLabel RR_35;
        private JLabel RR_34;
        private JLabel RR_33;
        private JLabel RR_32;
        private JLabel RR_31;

        private JPanel gui;

        public static DecimalFormat df = new DecimalFormat("#.00");

        // 进程队列
        static List<Process> ListOfProcess = new ArrayList<>();

        // 进程
        public static Process a;
        public static Process b;
        public static Process c;
        public static Process d;
        public static Process e1;


        public static int nowtime = 0;  // 当前时间

    /*
       进程     到达时间    服务时间    优先级
        A         0          3         4
        B         2          5         7
        C         1          2         1
        D         4          1         5
        E         3          4         9
     */


        public int rtime_a1 = 0;
        public int rtime_b1 = 2;
        public int rtime_c1 = 1;
        public int rtime_d1 = 4;
        public int rtime_e1 = 3;
        public int ntime_a1 = 3;
        public int ntime_b1 = 5;
        public int ntime_c1 = 2;
        public int ntime_d1 = 1;
        public int ntime_e1 = 4;
        public int p_a1 = 4;
        public int p_b1 = 7;
        public int p_c1 = 1;
        public int p_d1 = 5;
        public int p_e1 = 9;

        public static NumberFormat nbf=NumberFormat.getInstance();
        // nbf.setMinimumFractionDigits(2);  保留两位小数

        public OS_work() {
            super("进程调度");
            this.add(gui);
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.pack();
            this.setVisible(true);

            Arithmetic arithmetic = new Arithmetic(nowtime);
            nbf.setMinimumFractionDigits(2);

            rtime_a.setText(Integer.toString(rtime_a1));
            rtime_b.setText(Integer.toString(rtime_b1));
            rtime_c.setText(Integer.toString(rtime_c1));
            rtime_d.setText(Integer.toString(rtime_d1));
            rtime_e.setText(Integer.toString(rtime_e1));
            ntime_a.setText(Integer.toString(ntime_a1));
            ntime_b.setText(Integer.toString(ntime_b1));
            ntime_c.setText(Integer.toString(ntime_c1));
            ntime_d.setText(Integer.toString(ntime_d1));
            ntime_e.setText(Integer.toString(ntime_e1));
            p_a.setText(Integer.toString(p_a1));
            p_b.setText(Integer.toString(p_b1));
            p_c.setText(Integer.toString(p_c1));
            p_d.setText(Integer.toString(p_d1));
            p_e.setText(Integer.toString(p_e1));

            // 初始化进程
            a = new Process("A",p_a1,ntime_a1,rtime_a1,ntime_a1);
            b = new Process("B",p_b1,ntime_b1,rtime_b1,ntime_b1);
            c = new Process("C",p_c1,ntime_c1,rtime_c1,ntime_c1);
            d = new Process("D",p_d1,ntime_d1,rtime_d1,ntime_d1);
            e1 = new Process("E",p_e1,ntime_e1,rtime_e1,ntime_e1);

            ListOfProcess.clear();
            ListOfProcess.add(a);
            ListOfProcess.add(b);
            ListOfProcess.add(c);
            ListOfProcess.add(d);
            ListOfProcess.add(e1);

            finish.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    rtime_a1 = Integer.parseInt(rtime_a.getText());
                    rtime_b1 = Integer.parseInt(rtime_b.getText());
                    rtime_c1 = Integer.parseInt(rtime_c.getText());
                    rtime_d1 = Integer.parseInt(rtime_d.getText());
                    rtime_e1 = Integer.parseInt(rtime_e.getText());
                    ntime_a1 = Integer.parseInt(ntime_a.getText());
                    ntime_b1 = Integer.parseInt(ntime_b.getText());
                    ntime_c1 = Integer.parseInt(ntime_c.getText());
                    ntime_d1 = Integer.parseInt(ntime_d.getText());
                    ntime_e1 = Integer.parseInt(ntime_e.getText());
                    p_a1 = Integer.parseInt(p_a.getText());
                    p_b1 = Integer.parseInt(p_b.getText());
                    p_c1 = Integer.parseInt(p_c.getText());
                    p_d1 = Integer.parseInt(p_d.getText());
                    p_e1 = Integer.parseInt(p_e.getText());

                    a = new Process("A",p_a1,ntime_a1,rtime_a1,ntime_a1);
                    b = new Process("B",p_b1,ntime_b1,rtime_b1,ntime_b1);
                    c = new Process("C",p_c1,ntime_c1,rtime_c1,ntime_c1);
                    d = new Process("D",p_d1,ntime_d1,rtime_d1,ntime_d1);
                    e1 = new Process("E",p_e1,ntime_e1,rtime_e1,ntime_e1);

                    ListOfProcess.clear();
                    ListOfProcess.add(a);
                    ListOfProcess.add(b);
                    ListOfProcess.add(c);
                    ListOfProcess.add(d);
                    ListOfProcess.add(e1);
                }
            });
            SJF_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    List<Process> list = new ArrayList<>();
                    list.addAll(ListOfProcess);
                    List<Process> finish = arithmetic.SJF(list);
                    for (int i=0; i<finish.size(); i++) {
                        Process p = finish.get(i);
                        switch (p.getName()) {
                            case "A":
                                SJF_11.setText(Integer.toString(p.getFtime()));
                                SJF_21.setText(Integer.toString(p.getT()));
                                SJF_31.setText(nbf.format(p.getW()));
                                break;
                            case "B":
                                SJF_12.setText(Integer.toString(p.getFtime()));
                                SJF_22.setText(Integer.toString(p.getT()));
                                SJF_32.setText(nbf.format(p.getW()));
                                break;
                            case "C":
                                SJF_13.setText(Integer.toString(p.getFtime()));
                                SJF_23.setText(Integer.toString(p.getT()));
                                SJF_33.setText(nbf.format(p.getW()));
                                break;
                            case "D":
                                SJF_14.setText(Integer.toString(p.getFtime()));
                                SJF_24.setText(Integer.toString(p.getT()));
                                SJF_34.setText(nbf.format(p.getW()));
                                break;
                            case "E":
                                SJF_15.setText(Integer.toString(p.getFtime()));
                                SJF_25.setText(Integer.toString(p.getT()));
                                SJF_35.setText(nbf.format(p.getW()));
                                break;
                        }
                    }
                    SJF_avg1.setText(nbf.format(arithmetic.AVG_T(finish)));
                    SJF_avg2.setText(nbf.format(arithmetic.AVG_W(finish)));
                }
            });
            HRRF_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    List<Process> list = new ArrayList<>();
                    list.addAll(ListOfProcess);
                    List<Process> finish = arithmetic.HRRF(list);
                    for (int i=0; i<finish.size(); i++) {
                        Process p = finish.get(i);
                        switch (p.getName()) {
                            case "A":
                                HRRF_11.setText(Integer.toString(p.getFtime()));
                                HRRF_21.setText(Integer.toString(p.getT()));
                                HRRF_31.setText(nbf.format(p.getW()));
                                break;
                            case "B":
                                HRRF_12.setText(Integer.toString(p.getFtime()));
                                HRRF_22.setText(Integer.toString(p.getT()));
                                HRRF_32.setText(nbf.format(p.getW()));
                                break;
                            case "C":
                                HRRF_13.setText(Integer.toString(p.getFtime()));
                                HRRF_23.setText(Integer.toString(p.getT()));
                                HRRF_33.setText(nbf.format(p.getW()));
                                break;
                            case "D":
                                HRRF_14.setText(Integer.toString(p.getFtime()));
                                HRRF_24.setText(Integer.toString(p.getT()));
                                HRRF_34.setText(nbf.format(p.getW()));
                                break;
                            case "E":
                                HRRF_15.setText(Integer.toString(p.getFtime()));
                                HRRF_25.setText(Integer.toString(p.getT()));
                                HRRF_35.setText(nbf.format(p.getW()));
                                break;
                        }
                    }
                    HRRF_avg1.setText(nbf.format(arithmetic.AVG_T(finish)));
                    HRRF_avg2.setText(nbf.format(arithmetic.AVG_W(finish)));
                }
            });
            P_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    List<Process> list = new ArrayList<>();
                    list.addAll(ListOfProcess);
                    List<Process> finish = arithmetic.Priority(list);
                    for (int i=0; i<finish.size(); i++) {
                        Process p = finish.get(i);
                        switch (p.getName()) {
                            case "A":
                                P_11.setText(Integer.toString(p.getFtime()));
                                P_21.setText(Integer.toString(p.getT()));
                                P_31.setText(nbf.format(p.getW()));
                                break;
                            case "B":
                                P_12.setText(Integer.toString(p.getFtime()));
                                P_22.setText(Integer.toString(p.getT()));
                                P_32.setText(nbf.format(p.getW()));
                                break;
                            case "C":
                                P_13.setText(Integer.toString(p.getFtime()));
                                P_23.setText(Integer.toString(p.getT()));
                                P_33.setText(nbf.format(p.getW()));
                                break;
                            case "D":
                                P_14.setText(Integer.toString(p.getFtime()));
                                P_24.setText(Integer.toString(p.getT()));
                                P_34.setText(nbf.format(p.getW()));
                                break;
                            case "E":
                                P_15.setText(Integer.toString(p.getFtime()));
                                P_25.setText(Integer.toString(p.getT()));
                                P_35.setText(nbf.format(p.getW()));
                                break;
                        }
                    }
                    P_avg1.setText(nbf.format(arithmetic.AVG_T(finish)));
                    P_avg2.setText(nbf.format(arithmetic.AVG_W(finish)));
                }
            });
            FCFS_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    List<Process> list = new ArrayList<>();
                    list.addAll(ListOfProcess);
                    List<Process> finish = arithmetic.FCFS(list);
                    for (int i=0; i<finish.size(); i++) {
                        Process p = finish.get(i);
                        switch (p.getName()) {
                            case "A":
                                RR_11.setText(Integer.toString(p.getFtime()));
                                RR_21.setText(Integer.toString(p.getT()));
                                RR_31.setText(nbf.format(p.getW()));
                                break;
                            case "B":
                                RR_12.setText(Integer.toString(p.getFtime()));
                                RR_22.setText(Integer.toString(p.getT()));
                                RR_32.setText(nbf.format(p.getW()));
                                break;
                            case "C":
                                RR_13.setText(Integer.toString(p.getFtime()));
                                RR_23.setText(Integer.toString(p.getT()));
                                RR_33.setText(nbf.format(p.getW()));
                                break;
                            case "D":
                                RR_14.setText(Integer.toString(p.getFtime()));
                                RR_24.setText(Integer.toString(p.getT()));
                                RR_34.setText(nbf.format(p.getW()));
                                break;
                            case "E":
                                RR_15.setText(Integer.toString(p.getFtime()));
                                RR_25.setText(Integer.toString(p.getT()));
                                RR_35.setText(nbf.format(p.getW()));
                                break;
                        }
                    }
                    RR_avg1.setText(nbf.format(arithmetic.AVG_T(finish)));
                    RR_avg2.setText(nbf.format(arithmetic.AVG_W(finish)));
                }
            });
        }

}
