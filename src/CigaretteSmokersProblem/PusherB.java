package CigaretteSmokersProblem;

public class PusherB extends Thread{
    @Override
    public void run() {
        while (true) {
            try {
                SharedResources.paper.acquire();
                SharedResources.mutex.acquire();

                if (SharedResources.isTobaccoOnTable) {
                    SharedResources.isTobaccoOnTable = false;
                    SharedResources.matchSmoker.release(); // Kağıt + Tütün -> Kibritçi
                    System.out.println("PusherB çalışıyor -- kağıt + tütün -> kibritçiyi çağır");
                } else if (SharedResources.isMatchOnTable) {
                    SharedResources.isMatchOnTable = false;
                    SharedResources.tobaccoSmoker.release(); // Kağıt + Kibrit -> Tütüncü
                    System.out.println("PusherB çalışıyor -- kağıt + kibrit -> tütüncüyü çağır");
                } else {
                    SharedResources.isPaperOnTable = true;
                }

                SharedResources.mutex.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
