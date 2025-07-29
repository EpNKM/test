package city;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        HelpDesk helpDesk = HelpDesk.getInstance();
        
        // Initialize banks
        List<Bank> banks = new ArrayList<>();
        for (int i = 1; i <= Config.getBanksCount(); i++) {
            banks.add(new Bank(i));
        }

        // Initialize workers
        List<Worker> workers = new ArrayList<>();
        for (int i = 1; i <= Config.getWorkersCount(); i++) {
            workers.add(new Worker(i));
        }

        // Initialize spenders
        List<Spender> spenders = new ArrayList<>();
        for (int i = 1; i <= Config.getSpendersCount(); i++) {
            spenders.add(new Spender(i));
        }

        // Initialize Media
        Media media = new Media(banks, workers, spenders, helpDesk);
        media.start();

        System.out.println("Total money amount in city on day start: " + 
                          helpDesk.getTotalMoney() + "$");

        // Wait for work day to end
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

        // Wait for all threads to finish
        try {
            for (Bank bank : banks) bank.join();
            for (Worker worker : workers) worker.join();
            for (Spender spender : spenders) spender.join();
            media.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Total money amount in city on day end: " + 
                          helpDesk.getTotalMoney() + "$");
    }
}