package city;

import java.util.List;

public class HelpDesk {
    private static HelpDesk instance;
    
    private HelpDesk() {}

    public static synchronized HelpDesk getInstance() {
        if (instance == null) {
            instance = new HelpDesk();
        }
        return instance;
    }

    public int calculateTotalMoney(List<Bank> banks, List<Worker> workers, List<Spender> spenders) {
        int total = 0;
        for (Bank bank : banks) {
            total += bank.getMoney();
        }
        for (Worker worker : workers) {
            total += worker.getMoney();
        }
        for (Spender spender : spenders) {
            total += spender.getMoney();
        }
        return total;
    }
}