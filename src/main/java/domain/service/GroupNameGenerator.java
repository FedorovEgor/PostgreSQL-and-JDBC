package domain.service;

import java.util.Random;

public class GroupNameGenerator {

    public String getRandomGroupName() {
        return generateRandomString(new Random()) + "-" + generateRandomNumber(new Random());
    }

    private String generateRandomString(Random random){
        return random.ints(65,122)
                .filter(i -> (i > 64 && i < 91) || (i > 96 && i < 123))
                .mapToObj(i -> (char) i)
                .limit(2)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    private String generateRandomNumber(Random random) {
        return random.ints(0, 9)
                .limit(2).collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}

