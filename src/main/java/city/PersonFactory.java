package city;

public interface PersonFactory {
    Worker createWorker(int id);
    Spender createSpender(int id);
}

class CityPersonFactory implements PersonFactory {
    @Override
    public Worker createWorker(int id) {
        return new Worker(id);
    }

    @Override
    public Spender createSpender(int id) {
        return new Spender(id);
    }
}