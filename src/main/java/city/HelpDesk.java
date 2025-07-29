package city;

public class HelpDesk {
    private static HelpDesk instance;
    private int totalMoney;

    private HelpDesk() {
        this.totalMoney = 0;
    }

    public static synchronized HelpDesk getInstance() {
        if (instance == null) {
            instance = new HelpDesk();
        }
        return instance;
    }

    public synchronized void addMoney(int amount) {
        totalMoney += amount;
    }

    public synchronized void subtractMoney(int amount) {
        totalMoney -= amount;
    }

    public synchronized int getTotalMoney() {
        return totalMoney;
    }

    public synchronized void printTotalMoney() {
        System.out.println("Total money in system: " + totalMoney + "$");
    }
}