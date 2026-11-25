package CigaretteSmokersProblem;

import java.util.concurrent.Semaphore;
public class SharedResources {
    // --- MALZEME SEMAFORLARI (Ajanın sinyal gönderdiği) ---
    public static Semaphore tobacco = new Semaphore(0);
    public static Semaphore paper = new Semaphore(0);
    public static Semaphore match = new Semaphore(0);

    // --- TİRYAKİ UYANDIRMA ZİLLERİ (Pusher'ın sinyal gönderdiği) ---
    public static Semaphore tobaccoSmoker = new Semaphore(0); // Tütüncü
    public static Semaphore paperSmoker = new Semaphore(0);   // Kağıtçı
    public static Semaphore matchSmoker = new Semaphore(0);   // Kibritçi

    // --- SENKRONİZASYON KİLİTLERİ ---
    public static Semaphore agentSemaphore = new Semaphore(1); // Ajan sırası
    public static Semaphore mutex = new Semaphore(1);          // Pusher kilitleri

    // --- MASA HAFIZASI (Durum Değişkenleri) ---
    public static boolean isTobaccoOnTable = false;
    public static boolean isPaperOnTable = false;
    public static boolean isMatchOnTable = false;
}
