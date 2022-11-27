import java.util.*;

public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(() -> {
                String string = generateRoute("RLRFR", 100);
                int size = 0;
                for (char j : string.toCharArray()) {
                    if (j == 'R') {
                        size++;
                    }
                }
                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(size)) {
                        sizeToFreq.put(size, sizeToFreq.get(size) + 1);
                    } else {
                        sizeToFreq.put(size, 1);
                    }
                }
            });
            thread.start();
        }
        Map.Entry<Integer, Integer> maxValue = null;
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (maxValue == null || entry.getValue().compareTo(maxValue.getValue()) > 0) {
                maxValue = entry;
            }
        }
        System.out.println("Самое частое количество повторений " + maxValue.getKey() + " (встретилось " + maxValue.getValue() + " раз) ");
        System.out.println("Другие размеры ");
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (entry.getKey() != maxValue.getKey()) {
                System.out.println("- " + entry.getKey() + " (" + entry.getValue() + " раз) ");
            }
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}