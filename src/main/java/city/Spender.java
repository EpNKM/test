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
        HelpDesk.getInstance().addMoney(money);
        System.out.println("Created " + getName() + " with initial money: " + money + "$");
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(Config.getWorkerWorkDuration());
                
                if (money <= 0) {
                    List<Bank> banks = Bank.getBanks();
                    if (!banks.isEmpty()) {
                        Bank bank = banks.get(random.nextInt(banks.size()));
                        bank.takeLoan(this);
                    }
                } else {
                    Worker worker = findAvailableWorker();
                    if (worker != null) {
                        paySalary(worker);
                        worker.setBusy(false);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("Thread " + getName() + " has been stopped.");
    }

    private Worker findAvailableWorker() {
        List<Worker> workers = Worker.getWorkers();
        for (Worker worker : workers) {
            if (!worker.isBusy()) {
                worker.setBusy(true);
                return worker;
            }
        }
        return null;
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

    public int getMoney() {
        return money;
    }

    @Override
    public String getInfo() {
        return getName() + " has money: " + money + "$";
    }
}