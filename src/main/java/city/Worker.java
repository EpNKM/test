package city;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Worker extends Thread implements Client {
    private static final List<Worker> workers = new ArrayList<>();
    private static final Random random = new Random();
    private final int id;
    private int money;
    private boolean isBusy;

    public Worker(int id) {
        this.id = id;
        this.money = Config.getPersonInitialMoney();
        this.setName("Worker-" + id);
        workers.add(this);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (money >= Config.getWorkerMoneyLimit()) {
                    depositToBank();
                }
                Thread.sleep(Config.getWorkerWorkDuration());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Thread " + getName() + " has been stopped.");
    }

    private void depositToBank() {
        List<Bank> banks = Bank.getBanks();
        if (!banks.isEmpty()) {
            Bank bank = banks.get(random.nextInt(banks.size()));
            synchronized (bank) {
                if (!bank.isBusy() && bank.isOpen()) {
                    bank.setBusy(true);
                    depositMoneyToBank(bank);
                    bank.setBusy(false);
                }
            }
        }
    }

    public synchronized void receiveSalary(int amount) {
        money += amount;
    }

    public synchronized void depositMoneyToBank(Bank bank) {
        bank.depositMoney(money);
        money = 0;
    }

    public synchronized boolean isBusy() {
        return isBusy;
    }

    public synchronized void setBusy(boolean busy) {
        isBusy = busy;
    }

    public synchronized int getMoney() {
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