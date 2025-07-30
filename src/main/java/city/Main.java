package city;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        HelpDesk helpDesk = HelpDesk.getInstance();
        
        List<Bank> banks = new ArrayList<>();
        for (int i = 1; i <= Config.getBanksCount(); i++) {
            Bank bank = new Bank(i);
            banks.add(bank);
            bank.start();
        }

        List<Worker> workers = new ArrayList<>();
        for (int i = 1; i <= Config.getWorkersCount(); i++) {
            Worker worker = new Worker(i);
            workers.add(worker);
            worker.start();
        }

        List<Spender> spenders = new ArrayList<>();
        for (int i = 1; i <= Config.getSpendersCount(); i++) {
            Spender spender = new Spender(i);
            spenders.add(spender);
            spender.start();
        }

        Media media = new Media(banks, workers, spenders, helpDesk);
        media.start();

        int initialTotal = helpDesk.calculateTotalMoney(banks, workers, spenders);
        System.out.println("Total money amount in city on day start: " + initialTotal + "$");

        try {
            Thread.sleep(Config.getWorkDayDuration());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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

        int finalTotal = helpDesk.calculateTotalMoney(banks, workers, spenders);
        System.out.println("Total money amount in city on day end: " + finalTotal + "$");
    }
}