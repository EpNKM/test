package city;

import java.util.List;
import java.util.Random;

public class Spender extends Thread implements Client {
    private static final Random random = new Random();
    private final int id;
    private int money;
    private boolean isBusy;

    public Spender(int id) {
        this.id = id;
        this.money = Config.getPersonInitialMoney();
        this.setName("Spender-" + id);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (money <= 0) {
                    takeLoanFromBank();
                } else {
                    hireWorker();
                }
                Thread.sleep(Config.getWorkerWorkDuration());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Thread " + getName() + " has been stopped.");
    }

    private void takeLoanFromBank() {
        List<Bank> banks = Bank.getBanks();
        if (!banks.isEmpty()) {
            Bank bank = banks.get(random.nextInt(banks.size()));
            synchronized (bank) {
                if (!bank.isBusy() && bank.isOpen()) {
                    bank.setBusy(true);
                    bank.takeLoan(this);
                    bank.setBusy(false);
                }
            }
        }
    }

    private void hireWorker() {
        List<Worker> workers = Worker.getWorkers();
        if (!workers.isEmpty()) {
            Worker worker = workers.get(random.nextInt(workers.size()));
            synchronized (worker) {
                if (!worker.isBusy()) {
                    worker.setBusy(true);
                    paySalary(worker);
                    worker.setBusy(false);
                }
            }
        }
    }

    public synchronized void paySalary(Worker worker) {
        if (money >= Config.getSalaryAmount()) {
            money -= Config.getSalaryAmount();
            worker.receiveSalary(Config.getSalaryAmount());
        }
    }

    public synchronized void takeLoan(int amount) {
        money += amount;
    }

    public synchronized boolean isBusy() {
        return isBusy;
    }

    public synchronized int getMoney() {
        return money;
    }

    @Override
    public String getInfo() {
        return getName() + " has money: " + money + "$";
    }
}