import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

public class InimigosV2 {

    private int dificuldadeBonnie;
    private int dificuldadeChica;
    private int dificuldadeFreddy;
    private int dificuldadeFoxy;
    private int dificuldadeGoldenFreddy;

    private FnafGame jogo;
    private int noite;
    private int horas;

    private Timer timer;
    private Random random;

    // Estágios de Foxy
    private enum FoxyEstagio {
        NAO_VISIVEL, VISIVEL, SAINDO_DA_CAMERA, NA_FRENTE_DA_CAMERA
    }

    private FoxyEstagio foxyEstagio = FoxyEstagio.NAO_VISIVEL;

    public InimigosV2(FnafGame jogo) {
        this.jogo = jogo;
        this.noite = jogo.getNoiteAtual();
        this.horas = jogo.getHoraAtual();

        System.out.println("Noite: " + noite);
        System.out.println("Horas: " + horas);

        timer = new Timer();
        random = new Random();

        iniciarDificuldades();
        iniciarMovimento();
    }

    private void iniciarDificuldades() {
        // Instancia as variáveis de dificuldades dos animatrônicos
        dificuldadeBonnie = 0;
        dificuldadeChica = 0;
        dificuldadeFreddy = 0;
        dificuldadeFoxy = 0;
        
        // Agendar aumentos de dificuldade de acordo com a noite atual e a hora de jogo
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                horas = jogo.getHoraAtual(); // Atualizar a hora atual do jogo

                if (noite == 1) {
                    if (horas == 2) {
                        dificuldadeBonnie = 1;
                    } else if (horas == 3) {
                        dificuldadeBonnie = 3;
                        dificuldadeChica = 1;
                        dificuldadeFoxy = 1;
                    } else if (horas == 4) {
                        dificuldadeChica = 2;
                        dificuldadeFoxy = 2;
                    }
                } else if (noite == 2) {
                    if (horas <= 1) {
                        dificuldadeBonnie = 3;
                        dificuldadeChica = 1;
                        dificuldadeFoxy = 1;
                    } else if (horas == 2) {
                        dificuldadeBonnie = 4;
                    } else if (horas == 3) {
                        dificuldadeBonnie = 5;
                        dificuldadeChica = 2;
                        dificuldadeFoxy = 2;
                    } else if (horas == 4) {
                        dificuldadeBonnie = 6;
                        dificuldadeChica = 3;
                        dificuldadeFoxy = 3;
                    }
                } else if (noite == 3) {
                    if (horas == 0) {
                        dificuldadeChica = 5;
                        dificuldadeBonnie = 0;
                        dificuldadeFoxy = 2;
                        dificuldadeFreddy = 1;
                    } else if (horas == 2) {
                        dificuldadeBonnie = 1;
                    } else if (horas == 3) {
                        dificuldadeBonnie = 2;
                        dificuldadeChica = 6;
                        dificuldadeFoxy = 3;
                    } else if (horas == 4) {
                        dificuldadeBonnie = 3;
                        dificuldadeChica = 7;
                        dificuldadeFoxy = 4;
                    }
                } else if (noite == 4) {
                    if (horas == 0) {
                        dificuldadeBonnie = 2;
                        dificuldadeChica = 4;
                        dificuldadeFoxy = 6;
                        dificuldadeFreddy = 1;
                    } else if (horas == 2) {
                        dificuldadeBonnie = 3;
                        dificuldadeChica = 4;
                    } else if (horas == 3) {
                        dificuldadeBonnie = 4;
                        dificuldadeChica = 5;
                        dificuldadeFoxy = 7;
                    } else if (horas == 4) {
                        dificuldadeBonnie = 5;
                        dificuldadeChica = 6;
                        dificuldadeFoxy = 8;
                    }
                } else if(noite == 5){
                    if((horas == 0)){
                        dificuldadeFreddy = 3;
                        dificuldadeBonnie = 5;
                        dificuldadeChica = 7;
                        dificuldadeFoxy = 5;
                    } else if(horas == 2){
                        dificuldadeBonnie = 6;
                    } else if(horas == 3){
                        dificuldadeBonnie = 7;
                        dificuldadeChica = 8;
                        dificuldadeFoxy = 6;
                    } else if(horas == 4){
                        dificuldadeBonnie = 8;
                        dificuldadeChica = 9;
                        dificuldadeFoxy = 7; 
                    }
                }
            }
        }, 0, 1000); // Atualizar a cada segundo
    }

    private void iniciarMovimento() {
        // Cria e inicia os timers para os animatrônicos com seus respectivos tempos
        iniciarTimer("Bonnie", 4970, () -> dificuldadeBonnie);
        iniciarTimer("Chica", 4980, () -> dificuldadeChica);
        iniciarTimer("Freddy", 3020, () -> dificuldadeFreddy);
        iniciarTimer("Foxy", 5010, () -> dificuldadeFoxy);

        // Timer para verificar a aparição de Golden Freddy a cada segundo
        Timer goldenFreddyTimer = new Timer();
        goldenFreddyTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                verificarGoldenFreddy();
            }
        }, 0, 1000); // Verificar a cada segundo
    }

    private void verificarGoldenFreddy() {
        // 1 em 10000 chances de Golden Freddy aparecer
        int chance = random.nextInt(10000) + 1;
        if (chance == 1) {
            System.out.println("Golden Freddy apareceu no escritório!");
            // Lógica de game over e fechamento do jogo
            System.out.println("Game Over! O jogo será fechado.");
            System.exit(0); // Fecha o jogo
        }
    }

    private void iniciarTimer(String nome, int delay, DificuldadeProvider dificuldadeProvider) {
        Timer animatronicTimer = new Timer();
        animatronicTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                horas = jogo.getHoraAtual(); // Atualizar a hora atual do jogo
                if (horas >= 6) {
                    animatronicTimer.cancel();
                } else {
                    int numeroAleatorio = random.nextInt(20) + 1; // Número aleatório entre 1 e 20
                    int dificuldade = dificuldadeProvider.getDificuldade();
                    if (dificuldade >= numeroAleatorio) {
                        // ajustar para foxy não ter oportunidade de mov com câmera aberta
                        if (nome.equals("Foxy") && !jogo.isMonitorAberto()) {
                            avancarEstagioFoxy();
                        }
                        // Lógica de movimento dos animatrônicos
                        System.out.println(nome + " se movimentou!");
                    } else {
                        System.out.println(nome + " não se movimentou.");
                    }
                }
            }
        }, 0, delay);
    }

    private void avancarEstagioFoxy() {
        if (foxyEstagio == FoxyEstagio.NAO_VISIVEL) {
            foxyEstagio = FoxyEstagio.VISIVEL;
        } else if (foxyEstagio == FoxyEstagio.VISIVEL) {
            foxyEstagio = FoxyEstagio.SAINDO_DA_CAMERA;
        } else if (foxyEstagio == FoxyEstagio.SAINDO_DA_CAMERA) {
            foxyEstagio = FoxyEstagio.NA_FRENTE_DA_CAMERA;
        } else if (foxyEstagio == FoxyEstagio.NA_FRENTE_DA_CAMERA) {
            foxyEstagio = FoxyEstagio.NAO_VISIVEL;
        }
        System.out.println("Foxy avançou para o estágio: " + foxyEstagio);
    }

    @FunctionalInterface
    private interface DificuldadeProvider {
        int getDificuldade();
    }

    public static void main(String[] args) {
        FnafGame jogo = new FnafGame();
        jogo.setVisible(true);
        new InimigosV2(jogo);
    }
}
