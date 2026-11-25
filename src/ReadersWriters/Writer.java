package ReadersWriters;

public class Writer implements Runnable {
    private ReadWriteLock db;

    public Writer(ReadWriteLock db) {
        this.db = db;
    }

    // SleepUtilities simülasyonu için basit bir metot
    private void nap() {
        try {
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void run() {
        while (true) {
            nap(); // Başka işler yap (Kalan Bölüm)

            db.acquireWriteLock(); // YAZMA KİLİDİNİ AL (Özel erişim)

            // KRİTİK KISIM: Veritabanına Yaz
            System.out.println("--> YAZICI " + Thread.currentThread().getName() + " yazmaya başladı. (Diğer her şey BLOKE!)");
            nap(); // Yazma sürüyor...
            System.out.println("--> YAZICI " + Thread.currentThread().getName() + " yazmayı bitirdi.");

            db.releaseWriteLock(); // YAZMA KİLİDİNİ BIRAK
        }
    }
    
}
