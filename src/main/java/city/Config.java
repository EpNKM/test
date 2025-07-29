package city;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final String PROPERTIES_FILE = "config.properties";
    private static Properties properties;

    static {
        properties = new Properties();
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                System.out.println("Using default configuration");
                setDefaultProperties();
            } else {
                properties.load(input);
            }
        } catch (IOException e) {
            System.out.println("Error loading configuration: " + e.getMessage());
            setDefaultProperties();
        }
    }

    private static void setDefaultProperties() {
        properties.setProperty("workDayDuration", "60000");
        properties.setProperty("workerWorkDuration", "1000");
        properties.setProperty("lunchDuration", "5000");
        properties.setProperty("banksCount", "2");
        properties.setProperty("workersCount", "3");
        properties.setProperty("spendersCount", "4");
        properties.setProperty("bankInitialMoney", "100");
        properties.setProperty("personInitialMoney", "10");
        properties.setProperty("salaryAmount", "1");
        properties.setProperty("workerMoneyLimit", "5");
    }

    public static long getWorkDayDuration() {
        return Long.parseLong(properties.getProperty("workDayDuration"));
    }

    public static long getWorkerWorkDuration() {
        return Long.parseLong(properties.getProperty("workerWorkDuration"));
    }

    public static long getLunchDuration() {
        return Long.parseLong(properties.getProperty("lunchDuration"));
    }

    public static int getBanksCount() {
        return Integer.parseInt(properties.getProperty("banksCount"));
    }

    public static int getWorkersCount() {
        return Integer.parseInt(properties.getProperty("workersCount"));
    }

    public static int getSpendersCount() {
        return Integer.parseInt(properties.getProperty("spendersCount"));
    }

    public static int getBankInitialMoney() {
        return Integer.parseInt(properties.getProperty("bankInitialMoney"));
    }

    public static int getPersonInitialMoney() {
        return Integer.parseInt(properties.getProperty("personInitialMoney"));
    }

    public static int getSalaryAmount() {
        return Integer.parseInt(properties.getProperty("salaryAmount"));
    }

    public static int getWorkerMoneyLimit() {
        return Integer.parseInt(properties.getProperty("workerMoneyLimit"));
    }
}