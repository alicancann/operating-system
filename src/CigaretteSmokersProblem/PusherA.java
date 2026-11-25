package CigaretteSmokersProblem;

public class PusherA extends Thread{
    @Override
    public void run() {
        while (true) {
            try {
                // Tütün geldi mi?
                SharedResources.tobacco.acquire();

                // Masayı kilitle
                SharedResources.mutex.acquire();

                if (SharedResources.isPaperOnTable) {
                    SharedResources.isPaperOnTable = false; // Temizle
                    SharedResources.matchSmoker.release();  // tütün + kağıt -> kibritçiyi çağır
                    System.out.println("PusherA çalışıyor -- tütün + kağıt -> kibritçiyi çağır");

                } else if (SharedResources.isMatchOnTable) {
                    SharedResources.isMatchOnTable = false; // Temizle
                    SharedResources.paperSmoker.release();  // Tütün + Kibrit -> Kağıtçıyı çağır
                    System.out.println("PusherA çalışıyor -- tütün + kibrit -> kağıtçıyı çağır");
                } else {
                    SharedResources.isTobaccoOnTable = true; // Sadece tütün var, bekle
                }

                // Kilidi aç
                SharedResources.mutex.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
