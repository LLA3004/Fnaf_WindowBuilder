import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class FnafGame extends JFrame {

    private JLabel timeLabel;
    private JLabel nightLabel;
    private int segundoAtual = 0;
    private int noiteAtual = 1;
    private Timer timer;
    private boolean monitorAberto = false; // Variável para sinalizar se o monitor está aberto
    private JButton btnFechaPortaE;
    private JButton btnFechaPortaD;
    private JButton btnLuzE;
    private JButton btnFechaPortaE_2;
    private JProgressBar progressBar; // Barra de progresso
    private int bateria = 100; // Nível de bateria
    private InimigosV2 inimigos; // Instância de InimigosV2
    private TimerTask drenarBateriaTask; // Task para drenar bateria
    
    public int getNoiteAtual(){
        return noiteAtual;
    }
    public void ferramentas() {
    	int ferramentas = 1;
    	if (isMonitorAberto() == true){
    		ferramentas++;
    	}
    	
    	//melhorar para adicionar as luzes dps.
    
    }
    public FnafGame() {
        System.out.println(noiteAtual);
        setTitle("FNAF Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        nightLabel = new JLabel("Noite: 1", SwingConstants.LEFT);
        nightLabel.setBounds(21, 11, 193, 32);
        nightLabel.setFont(new Font("Serif", Font.BOLD, 24));
        getContentPane().add(nightLabel);

        timeLabel = new JLabel("00", SwingConstants.CENTER); // Mostra apenas as horas
        timeLabel.setBounds(183, 6, 201, 32);
        timeLabel.setFont(new Font("Serif", Font.BOLD, 36));
        getContentPane().add(timeLabel);

        JButton monitorButton = new JButton("MONITOR");
        monitorButton.setBounds(135, 198, 100, 30);
        getContentPane().add(monitorButton);
        
        btnFechaPortaE = new JButton("Fecha Porta E");
        btnFechaPortaE.setFont(new Font("Tahoma", Font.PLAIN, 7));
        btnFechaPortaE.setBounds(21, 165, 100, 30);
        getContentPane().add(btnFechaPortaE);
        
        btnFechaPortaD = new JButton("Fecha Porta D");
        btnFechaPortaD.setFont(new Font("Tahoma", Font.PLAIN, 7));
        btnFechaPortaD.setBounds(257, 169, 100, 30);
        getContentPane().add(btnFechaPortaD);
        
        btnLuzE = new JButton("Luz E");
        btnLuzE.setBounds(21, 198, 100, 30);
        getContentPane().add(btnLuzE);
        
        btnFechaPortaE_2 = new JButton("Luz D");
        btnFechaPortaE_2.setBounds(257, 202, 100, 30);
        getContentPane().add(btnFechaPortaE_2);
        
        progressBar = new JProgressBar();
        progressBar.setValue(100);
        progressBar.setBounds(31, 239, 146, 14);
        getContentPane().add(progressBar);
        
        JTextPane txtpnBateria = new JTextPane();
        txtpnBateria.setFont(new Font("Tahoma", Font.PLAIN, 6));
        txtpnBateria.setText("bateria");
        txtpnBateria.setBounds(31, 225, 70, 14);
        getContentPane().add(txtpnBateria);

        monitorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!monitorAberto) {
                    monitorAberto = true;
                    monitor2 monitor = new monitor2();
                    monitor.setVisible(true);
                    monitor.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                            monitorAberto = false;
                            ajustarDrenagemDeBateria();
                        }

                        @Override
                        public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                            monitorAberto = false;
                            ajustarDrenagemDeBateria();
                        }
                    });
                }
                ajustarDrenagemDeBateria();
            }
        });

        iniciaNoite();
    }

    private void iniciaNoite() {
        if (noiteAtual > 5) {
            mostraTelaVitoria();
            return;
        }

        segundoAtual = 0;
        bateria = 99;
        progressBar.setValue(bateria);
        atuTela();
        nightLabel.setText("Noite: " + noiteAtual);

        inimigos = new InimigosV2(this); // Inicializa a instância de InimigosV2

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                segundoAtual++;
                atuTela();

                if (segundoAtual >= 516) {
                    timer.cancel();
                    noiteAtual++;
                    mostraTelaVitoria();
                }
            }
        }, 0, 1000);

        ajustarDrenagemDeBateria(); // Inicia a drenagem de bateria com o monitor fechado
    }

    private void ajustarDrenagemDeBateria() {
        if (drenarBateriaTask != null) {
            drenarBateriaTask.cancel();
        }

        drenarBateriaTask = new TimerTask() {
            @Override
            public void run() {
                if (bateria > 0) {
                    bateria -= (monitorAberto ? 1.5 : 1);
                    progressBar.setValue(bateria);
                } else {
                    timer.cancel();
                    JOptionPane.showMessageDialog(null, "Acabou a bateria! Você perdeu.");
                    System.exit(0);
                }
            }
        };

        long intervalo = monitorAberto ? 6500 : 9600; // 6.5 segundos se o monitor estiver aberto, 9.6 segundos se estiver fechado
        timer.scheduleAtFixedRate(drenarBateriaTask, 0, intervalo);
    }

    private void atuTela() {
        int segundos = segundoAtual;
        int horasJogo = segundos / 86;
        timeLabel.setText(String.format("%02d:00 a.m.", horasJogo)); // Mostra apenas as horas
        System.out.println(horasJogo);
    }
    
    public int getHoraAtual() {
        return segundoAtual / 86;
    }

    private void mostraTelaVitoria() {
        JOptionPane.showMessageDialog(this, "Você venceu a noite " + (noiteAtual - 1) + "!");
        if (noiteAtual <= 5) {
            int response = JOptionPane.showConfirmDialog(this, "Deseja iniciar a próxima noite?", "Próxima Noite", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                iniciaNoite();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Você completou todas as noites! Parabéns! Você foi demitido!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FnafGame game = new FnafGame();
            game.setVisible(true);
        });
    }

    public boolean isMonitorAberto() {
        return monitorAberto;
    }
}

//200 linhas de codigo socorrokkkk