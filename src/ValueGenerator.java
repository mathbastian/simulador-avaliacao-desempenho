import java.util.Random;

public class ValueGenerator {

    public Integer randonInt(Integer min, Integer max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
