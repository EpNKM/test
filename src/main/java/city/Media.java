package city;

import java.util.List;

public class Media extends Thread {
    private final List<Bank> banks;
    private final List<Worker> workers;
    private final List<Spender> spenders;
    private final HelpDesk helpDesk;

    public Media(List<Bank> banks, List<Worker> workers, 
                List<Spender> spenders, HelpDesk helpDesk) {
        this.banks = banks;
        this.workers = workers;
        this.spenders = spenders;
        this.helpDesk = helpDesk;
        this.setDaemon(true);
        this.setName("Media");
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(3000);
                broadcast();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void broadcast() {
        int total = helpDesk.calculateTotalMoney(banks, workers, spenders);
        System.out.println("\nGood news for everyone! Total amount money in city is: " + 
                         total + "$");
        banks.forEach(bank -> System.out.println("This " + bank.getInfo()));
        workers.forEach(worker -> System.out.println("This " + worker.getInfo()));
        spenders.forEach(spender -> System.out.println("This " + spender.getInfo()));
    }
}