package CigaretteSmokersProblem;

import java.util.concurrent.Semaphore;
public class Smoker extends Thread {
    private String name;
    private Semaphore myTurnSemaphore; // Beni uyandıracak özel semafor

    public Smoker(String name, Semaphore sem) {
        this.name = name;
        this.myTurnSemaphore = sem;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // (tiryaki semaforu) Pusher beni çağırana kadar bekle
                myTurnSemaphore.acquire();

                System.out.println(">>> " + name + " uyandı.");
                System.out.println("    Sigara sarılıyor... (Çakmak sesi)");
                System.out.println("    DUMANLAR ÇIKIYOR... (Keyif)");

                Thread.sleep(1000); // Sigara içme süresi

                System.out.println("    " + name + " bitirdi. Masa boş.\n");

                // Ajana haber ver: "Masa boş, yenisini koy"
                SharedResources.agentSemaphore.release();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
