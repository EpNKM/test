package city;

public class Media extends Thread {
    private final Wiretapping wiretapping;

    public Media(Wiretapping wiretapping) {
        this.wiretapping = wiretapping;
        this.setDaemon(true);
        this.setName("Media");
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(Config.getLunchDuration());
                wiretapping.broadcast();
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}