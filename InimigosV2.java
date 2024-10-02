package Jogo;

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
        // Instancia as variaveis de dificuldades dos animatrônicos
        dificuldadeBonnie = 0;
        dificuldadeChica = 0;
        dificuldadeFreddy = 0;
        dificuldadeFoxy = 0;

        // Agendar aumentos de dificuldade de acordo com a noite atual e a hora de jogo
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                horas = jogo.getHoraAtual(); // Atualizar a hora atual do jogo

                switch (noite) {
                    case 1 -> {
                        switch (horas) {
                            case 2 -> dificuldadeBonnie = 1;
                            case 3 -> {
                                dificuldadeBonnie = 3;
                                dificuldadeChica = 1;
                                dificuldadeFoxy = 1;
                            }
                            case 4 -> {
                                dificuldadeChica = 2;
                                dificuldadeFoxy = 2;
                            }
                        }
                    }
                    case 2 -> {
                        if (horas <= 1) {
                            dificuldadeBonnie = 3;
                            dificuldadeChica = 1;
                            dificuldadeFoxy = 1;
                        } else {
                            switch (horas) {
                                case 2 -> dificuldadeBonnie = 4;
                                case 3 -> {
                                    dificuldadeBonnie = 5;
                                    dificuldadeChica = 2;
                                    dificuldadeFoxy = 2;
                                }
                                case 4 -> {
                                    dificuldadeBonnie = 6;
                                    dificuldadeChica = 3;
                                    dificuldadeFoxy = 3;
                                }
                            }
                        }
                    }
                    case 3 -> {
                        switch (horas) {
                            case 0 -> {
                                dificuldadeChica = 5;
                                dificuldadeBonnie = 0;
                                dificuldadeFoxy = 2;
                                dificuldadeFreddy = 1;
                            }
                            case 2 -> dificuldadeBonnie = 1;
                            case 3 -> {
                                dificuldadeBonnie = 2;
                                dificuldadeChica = 6;
                                dificuldadeFoxy = 3;
                            }
                            case 4 -> {
                                dificuldadeBonnie = 3;
                                dificuldadeChica = 7;
                                dificuldadeFoxy = 4;
                            }
                        }
                    }
                    case 4 -> {
                        switch (horas) {
                            case 0 -> {
                                dificuldadeBonnie = 2;
                                dificuldadeChica = 4;
                                dificuldadeFoxy = 6;
                                dificuldadeFreddy = 1;
                            }
                            case 2 -> {
                                dificuldadeBonnie = 3;
                                dificuldadeChica = 4;
                            }
                            case 3 -> {
                                dificuldadeBonnie = 4;
                                dificuldadeChica = 5;
                                dificuldadeFoxy = 7;
                            }
                            case 4 -> {
                                dificuldadeBonnie = 5;
                                dificuldadeChica = 6;
                                dificuldadeFoxy = 8;
                            }
                        }
                    }
                    // Implementar condições para outras noites
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
                        //ajustar para foxy n ter oportunidade de mov com camera aberta
                    	if (nome.equals("Foxy") && !jogo.isMonitorAberto()) {
                            avancarEstagioFoxy();
                        }
                        // Lógica de movimento dos animatrônicos, deixar privado antes de Versão final.
                        System.out.println(nome + " se movimentou!");
                    } else {
                        System.out.println(nome + " não se movimentou.");
                    }
                }
            }
        }, 0, delay);
    }

    private void avancarEstagioFoxy() {
        switch (foxyEstagio) {
            case NAO_VISIVEL -> foxyEstagio = FoxyEstagio.VISIVEL;
            case VISIVEL -> foxyEstagio = FoxyEstagio.SAINDO_DA_CAMERA;
            case SAINDO_DA_CAMERA -> foxyEstagio = FoxyEstagio.NA_FRENTE_DA_CAMERA;
            case NA_FRENTE_DA_CAMERA -> foxyEstagio = FoxyEstagio.NAO_VISIVEL;
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
