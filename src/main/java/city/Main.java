package city;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        HelpDesk helpDesk = HelpDesk.getInstance();
        
        // Initialize banks
        List<Bank> banks = new ArrayList<>();
        for (int i = 1; i <= Config.getBanksCount(); i++) {
            Bank bank = new Bank(i);
            banks.add(bank);
        }

        // Initialize workers
        List<Worker> workers = new ArrayList<>();
        for (int i = 1; i <= Config.getWorkersCount(); i++) {
            Worker worker = new Worker(i);
            workers.add(worker);
        }

        // Initialize spenders
        List<Spender> spenders = new ArrayList<>();
        for (int i = 1; i <= Config.getSpendersCount(); i++) {
            Spender spender = new Spender(i);
            spenders.add(spender);
        }

        // Выводим начальные состояния ДО запуска потоков
        System.out.println("\n=== НАЧАЛЬНОЕ СОСТОЯНИЕ СИСТЕМЫ ===");
        System.out.println("Total money amount in city on day start: " + 
                          helpDesk.calculateTotalMoney(banks, workers, spenders) + "$");
        banks.forEach(bank -> System.out.println("Initial " + bank.getInfo()));
        workers.forEach(worker -> System.out.println("Initial " + worker.getInfo()));
        spenders.forEach(spender -> System.out.println("Initial " + spender.getInfo()));
        System.out.println("===============================\n");

        // Запускаем потоки ПОСЛЕ вывода начального состояния
        banks.forEach(Thread::start);
        workers.forEach(Thread::start);
        spenders.forEach(Thread::start);

        // Initialize Media
        Media media = new Media(banks, workers, spenders, helpDesk);
        media.start();

        // Остальной код остается без изменений...
        try {
            Thread.sleep(Config.getWorkDayDuration());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Stop all threads
        banks.forEach(Thread::interrupt);
        workers.forEach(Thread::interrupt);
        spenders.forEach(Thread::interrupt);
        media.interrupt();

        try {
            for (Bank bank : banks) bank.join();
            for (Worker worker : workers) worker.join();
            for (Spender spender : spenders) spender.join();
            media.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Total money amount in city on day end: " + 
                          helpDesk.calculateTotalMoney(banks, workers, spenders) + "$");
    }
}