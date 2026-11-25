package CigaretteSmokersProblem;

public class PusherC extends Thread{
    @Override
    public void run() {
        while (true) {
            try {
                SharedResources.match.acquire();
                SharedResources.mutex.acquire();

                if (SharedResources.isPaperOnTable) {
                    SharedResources.isPaperOnTable = false;
                    SharedResources.tobaccoSmoker.release(); // Kibrit + Kağıt -> Tütüncü
                    System.out.println("PusherC çalışıyor -- Kibrit + Kağıt -> Tütüncüyü çağır");
                } else if (SharedResources.isTobaccoOnTable) {
                    SharedResources.isTobaccoOnTable = false;
                    SharedResources.paperSmoker.release(); // Kibrit + Tütün -> Kağıtçı
                    System.out.println("PusherC çalışıyor -- Kibrit + tütün -> kağıtçıyı çağır");
                } else {
                    SharedResources.isMatchOnTable = true;
                }

                SharedResources.mutex.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
