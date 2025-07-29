package city;

import java.util.ArrayList;
import java.util.List;

public class Bank extends Thread {
    private static final List<Bank> banks = new ArrayList<>();
    private final int id;
    private int money;
    private boolean isOpen;
    private boolean isBusy;

    public Bank(int id) {
        this.id = id;
        this.money = Config.getBankInitialMoney();
        this.isOpen = true;
        this.setName("Bank-" + id);
        banks.add(this);
        HelpDesk.getInstance().addMoney(money);
        System.out.println("Created " + getName() + " with initial money: " + money + "$");
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(Config.getLunchDuration());
                isOpen = !isOpen;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("Thread " + getName() + " has been stopped.");
    }

    public synchronized void depositMoney(int amount) {
        money += amount;
    }

    public synchronized void takeLoan(Spender spender) {
        if (!isBusy && isOpen) {
            isBusy = true;
            if (money > 0) {
                int loanAmount = Math.min(money, 10);
                money -= loanAmount;
                spender.takeLoan(loanAmount);
            }
            isBusy = false;
        }
    }

    public synchronized boolean isOpen() {
        return isOpen;
    }

    public synchronized boolean isBusy() {
        return isBusy;
    }

    public int getMoney() {
        return money;
    }

    public static List<Bank> getBanks() {
        return new ArrayList<>(banks);
    }

    public String getInfo() {
        return getName() + " has money: " + money + "$";
    }
}