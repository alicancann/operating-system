package ReadersWriters;

public class Reader implements Runnable {
    private ReadWriteLock db;

    public Reader(ReadWriteLock db) {
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

            db.acquireReadLock(); // OKUMA KİLİDİNİ AL (Grup halinde gir)

            // KRİTİK KISIM: Veritabanından Oku

            System.out.println("Okuyucu " + Thread.currentThread().getName() + " okumaya başladı. Aktif Okuyucu: " + ((Database) db).getReaderCount());
            nap(); // Okuma sürüyor...
            System.out.println("Okuyucu " + Thread.currentThread().getName() + " okumayı bitirdi.");

            db.releaseReadLock(); // OKUMA KİLİDİNİ BIRAK (Son Okuyucu, Yazıcıları uyandırır)
        }
    }
}
