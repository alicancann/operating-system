package CigaretteSmokersProblem;

public class Main {
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("   SİGARA İÇENLER PROBLEMİ (MODÜLER)      ");
        System.out.println("==========================================");

        // 1. Ajanı Başlat
        new Agent().start();

        // 2. Tiryakileri Başlat
        // Smoker constructor'ına SharedResources içindeki semaforları veriyoruz
        new Smoker("Tütüncü (Elinde Tütün)", SharedResources.tobaccoSmoker).start();
        new Smoker("Kağıtçı (Elinde Kağıt)", SharedResources.paperSmoker).start();
        new Smoker("Kibritçi (Elinde Kibrit)", SharedResources.matchSmoker).start();

        // 3. Yardımcıları (Pushers) Başlat
        new PusherA().start();
        new PusherB().start();
        new PusherC().start();
    }
}
