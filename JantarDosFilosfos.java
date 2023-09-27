package resolucao;

import java.util.Random;

import java.util.concurrent.Semaphore;

public class JantarDosFilosfos {
	
    private static final int NUM_FILOSOFOS = 5;
    private static Filosofo[] filosofos = new Filosofo[NUM_FILOSOFOS];
    private static Semaphore[] garfos = new Semaphore[NUM_FILOSOFOS];

    public static void main(String[] args) {
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            garfos[i] = new Semaphore(1);
        }
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            filosofos[i] = new Filosofo(i, garfos[i], garfos[(i + 1) % NUM_FILOSOFOS]);
            new Thread(filosofos[i]).start();
        }
    }
}

class Filosofo implements Runnable {
	private final int[] cont = new int[5];
    private final int id;
    private final Semaphore garfoEsquerdo;
    private final Semaphore garfoDireito;
    private final Random random = new Random();

    
    public Filosofo(int id, Semaphore garfoEsquerdo, Semaphore garfoDireito) {
        this.id = id;
        this.garfoEsquerdo = garfoEsquerdo;
        this.garfoDireito = garfoDireito;
    }

    @Override
    public void run() {
        try {
            while (true) {
                pensar();
                pegarGarfos();
                comer();
                soltarGarfos();
                
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void pensar() throws InterruptedException {
    	System.out.println("==========================================");
        System.out.println("Filósofo " + id + " está pensando...");
        System.out.println("==========================================");
        Thread.sleep(random.nextInt(1000));
    }

    private void pegarGarfos() throws InterruptedException {
    	System.out.println("==========================================");
        System.out.println("Filósofo " + id + " está tentando pegar os garfos...");
        System.out.println("==========================================");
        garfoEsquerdo.acquire();
        garfoDireito.acquire();
    }

    private void comer() throws InterruptedException {
    	System.out.println("==========================================");
        System.out.println("Filósofo " + id + " está comendo...");
        cont[id]++;
        System.out.println("Filosofo " + id + " comeu " + cont[id] + " vezes.");
        System.out.println("==========================================");
        Thread.sleep(random.nextInt(1000));
    }

    private void soltarGarfos() {
    	System.out.println("==========================================");
        System.out.println("Filósofo " + id + " soltou os garfos.");
        System.out.println("==========================================");
        garfoEsquerdo.release();
        garfoDireito.release();
    }
}