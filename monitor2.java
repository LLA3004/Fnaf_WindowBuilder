package Jogo;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class monitor2 extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    monitor2 frame = new monitor2();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public monitor2() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnNewButton = new JButton("CAM 1A");
        btnNewButton.setBounds(115, 11, 92, 23);
        contentPane.add(btnNewButton);

        JButton btnCamb = new JButton("CAM 1B");
        btnCamb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnCamb.setBounds(80, 55, 97, 23);
        contentPane.add(btnCamb);

        JButton btnCam = new JButton("CAM 5");
        btnCam.setBounds(0, 88, 97, 23);
        contentPane.add(btnCam);

        JButton btnCamc = new JButton("CAM 1C");
        btnCamc.setBounds(47, 134, 97, 23);
        contentPane.add(btnCamc);

        JButton btnCam_1 = new JButton("CAM 7");
        btnCam_1.setBounds(321, 66, 92, 23);
        contentPane.add(btnCam_1);

        JButton btnCam_1_1 = new JButton("CAM 6");
        btnCam_1_1.setBounds(321, 160, 92, 23);
        contentPane.add(btnCam_1_1);

        JButton btnCam_1_1_1 = new JButton("CAM 2B");
        btnCam_1_1_1.setBounds(100, 227, 92, 23);
        contentPane.add(btnCam_1_1_1);

        JButton btnCam_1_1_2 = new JButton("CAM 2A");
        btnCam_1_1_2.setBounds(100, 193, 92, 23);
        contentPane.add(btnCam_1_1_2);

        JButton btnCam_1_1_3 = new JButton("CAM 4A");
        btnCam_1_1_3.setBounds(233, 193, 92, 23);
        contentPane.add(btnCam_1_1_3);

        JButton btnCam_1_1_4 = new JButton("CAM 4B");
        btnCam_1_1_4.setBounds(233, 227, 92, 23);
        contentPane.add(btnCam_1_1_4);

        // Adicionando o botão de fechar
        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnFechar.setBounds(321, 227, 92, 23);
        contentPane.add(btnFechar);
    }
}
