package CigaretteSmokersProblem;

import java.util.Random;
public class Agent extends Thread{
    @Override
    public void run() {
        Random rand = new Random();
        while (true) {
            try {
                // Ajan masanın boşalmasını bekler
                SharedResources.agentSemaphore.acquire();

                Thread.sleep(1000); // İşleyişi görmek için bekle

                int choice = rand.nextInt(3);
                System.out.println("\n[AJAN] Masaya malzeme koyuyor...");

                if (choice == 0) {
                    System.out.println("[AJAN] Masada: TÜTÜN + KAĞIT var. -> (Kibritçi bekleniyor)");
                    SharedResources.tobacco.release();
                    SharedResources.paper.release();
                } else if (choice == 1) {
                    System.out.println("[AJAN] Masada: TÜTÜN + KİBRİT var. -> (Kağıtçı bekleniyor)");
                    SharedResources.tobacco.release();
                    SharedResources.match.release();
                } else {
                    System.out.println("[AJAN] Masada: KAĞIT + KİBRİT var. -> (Tütüncü bekleniyor)");
                    SharedResources.paper.release();
                    SharedResources.match.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
