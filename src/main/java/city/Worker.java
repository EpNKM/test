package city;

public class Worker extends Thread implements Client {
    private final int id;
    private int money;
    private boolean isBusy;

    public Worker(int id) {
        this.id = id;
        this.money = Config.getPersonInitialMoney();
        this.setName("Worker-" + id);
        HelpDesk.getInstance().addMoney(money);
        System.out.println("Created " + getName() + " with initial money: " + money + "$");
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(Config.getWorkerWorkDuration());
                if (money >= Config.getWorkerMoneyLimit()) {
                    Bank bank = Bank.getBanks().get((int)(Math.random() * Bank.getBanks().size()));
                    depositMoneyToBank(bank);
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
        HelpDesk.getInstance().addMoney(amount);
        System.out.println(getName() + " received salary: " + amount + "$");
    }

    public synchronized void depositMoneyToBank(Bank bank) {
        bank.depositMoney(money);
        HelpDesk.getInstance().subtractMoney(money);
        System.out.println(getName() + " deposited " + money + "$ to " + bank.getName());
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
}