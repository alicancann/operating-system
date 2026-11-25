package ReadersWriters;

public class Factory {
    public static void main(String[] args) {

        System.out.println("Okuyucular-Yazıcılar Problemi Başlatılıyor (Okuyucular Öncelikli)");
        System.out.println("-----------------------------------------------------------------");

        // 1. Kilit Mekanizmasını Uygulayan Veritabanı Nesnesini Oluştur
        ReadWriteLock dbLock = new Database();

        // 2. Okuyucu ve Yazıcı Sayısını Belirle
        final int NUM_READERS = 5;
        final int NUM_WRITERS = 2;

        Thread[] threads = new Thread[NUM_READERS + NUM_WRITERS];
        int index = 0;

        // 3. Yazıcıları Başlat
        for (int i = 1; i <= NUM_WRITERS; i++) {
            Thread writer = new Thread(new Writer(dbLock), "W-" + i);
            /*
            Writer sınıfı Runnable arayüzünü uyguladığı için, aslında Java'ya şunu demiş oluyorsun:
            "Sana bir görev paketi veriyorum, bu paketin içinde bir run() metodu var."
             */
            threads[index++] = writer;
            writer.start();
        }

        // 4. Okuyucuları Başlat
        for (int i = 1; i <= NUM_READERS; i++) {
            Thread reader = new Thread(new Reader(dbLock), "R-" + i);
            threads[index++] = reader;
            reader.start();
            /*
            start() dediğimiz an, Java işletim sistemine gider ve "Bana yeni bir hat aç,
             bu işçi (Thread) orada çalışmaya başlasın" der.

            Bu sayede Yazıcı 1 çalışırken, kodun geri kalanı durup onun bitmesini beklemez; döngü devam eder
            ve Yazıcı 2'yi de başlatır. Böylece hepsi aynı anda (eşzamanlı) çalışmış olur.
             */
        }

        // Programın bir süre çalışmasını bekle
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\nProgram Sonlanıyor...");

        // *Teorik olarak, sonsuz döngüden çıkış için tüm thread'lere interrupt gönderilebilir*
    }
}
