package ReadersWriters;

import java.util.concurrent.Semaphore;

public class Database implements ReadWriteLock {
    private int readerCount;
    private Semaphore mutex; // readerCount'ı korur (Mutex)
    private Semaphore db;    // Veri kümesini korur (Mutex / Ana Bayrak)

    public Database() {
        readerCount = 0;
        // Başlangıç değerleri 1, yani tüm kilitler serbest.
        mutex = new Semaphore(1);
        db = new Semaphore(1);
    }
    public int getReaderCount() {
        return readerCount;
    }

    // -------------------------------------------------------------------
    // OKUYUCU METOTLARI
    // -------------------------------------------------------------------

    public void acquireReadLock() {
        try {
            mutex.acquire(); // readerCount'u değiştirmeden önce kilitle

            // Okuyucu sayısını artır
            readerCount++;

            // EĞER BU İLK OKUYUCU İSE, db kilidini al (Yazıcıları Engelle)
            if (readerCount == 1) {
                db.acquire(); // db=1 -> db=0 olur. Yazıcılar bekleme kuyruğuna girer.
            }

            mutex.release(); // readerCount güncellendi, mutex'i bırak

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void releaseReadLock() {
        try {
            mutex.acquire(); // readerCount'u değiştirmeden önce kilitle

            // Okuyucu sayısını azalt
            readerCount--;

            // EĞER BU SON OKUYUCU İSE, db kilidini bırak (Yazıcıları Uyandır)
            if (readerCount == 0) {
                db.release(); // db=0 -> db=1 olur. Bekleyen Yazıcı uyanır.
            }

            mutex.release(); // readerCount güncellendi, mutex'i bırak

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // -------------------------------------------------------------------
    // YAZICI METOTLARI
    // -------------------------------------------------------------------

    public void acquireWriteLock() {
        try {
            // Yazıcı, diğer tüm okuyucu/yazıcıları engellemek için db'yi ister.
            db.acquire(); // db=1 -> db=0 olur. Eğer 0 ise BLOKLANIR.
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void releaseWriteLock() {
        // Yazma işi bitti, db kilidini serbest bırak
        db.release(); // db=0 -> db=1 olur. Bekleyen Okuyucu veya Yazıcı uyanır.
    }
}
