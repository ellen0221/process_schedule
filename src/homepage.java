import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class homepage extends JFrame{
    public static void main(String[] args) {
        new homepage();
    }

    private JPanel panel1;
    private JButton ProcessButton;
    private JButton WorkButton;
    private JButton SaveButton;

    public homepage() {

        super("首页");
        this.add(panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        ProcessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OS_process();
            }
        });
        WorkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OS_work();
            }
        });
        SaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OS_save();
            }
        });
    }
}
