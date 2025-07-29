package city;

import java.util.ArrayList;
import java.util.List;

public class Worker extends Thread implements Client {
    private static final List<Worker> workers = new ArrayList<>();
    private final int id;
    private int money;
    private boolean isBusy;

    public Worker(int id) {
        this.id = id;
        this.money = Config.getPersonInitialMoney();
        this.setName("Worker-" + id);
        workers.add(this);
        HelpDesk.getInstance().addMoney(money);
        System.out.println("Created " + getName() + " with initial money: " + money + "$");
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(Config.getWorkerWorkDuration());
                
                if (money >= Config.getWorkerMoneyLimit()) {
                    List<Bank> banks = Bank.getBanks();
                    if (!banks.isEmpty()) {
                        Bank bank = banks.get((int)(Math.random() * banks.size()));
                        depositMoneyToBank(bank);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("Thread " + getName() + " has been stopped.");
    }

    public synchronized void receiveSalary(int amount) {
        money += amount;
    }

    public synchronized void depositMoneyToBank(Bank bank) {
        bank.depositMoney(money);
        HelpDesk.getInstance().subtractMoney(money);
        money = 0;
    }

    public synchronized boolean isBusy() {
        return isBusy;
    }

    public synchronized void setBusy(boolean busy) {
        isBusy = busy;
    }

    public int getMoney() {
        return money;
    }

    @Override
    public String getInfo() {
        return getName() + " has money: " + money + "$";
    }

    public static List<Worker> getWorkers() {
        return new ArrayList<>(workers);
    }
}