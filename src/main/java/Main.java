import java.util.*;

public class Main {
    public static final Map<Integer, Integer> SIZE_TO_FREQ = new HashMap<>();
    public static int counting = 0;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(() -> {
                String string = generateRoute("RLRFR", 100);
                int size = 0;
                for (char j : string.toCharArray()) {
                    if (j == 'R') {
                        size++;
                    }
                }
                synchronized (SIZE_TO_FREQ) {
                    if (SIZE_TO_FREQ.containsKey(size)) {
                        SIZE_TO_FREQ.put(size, SIZE_TO_FREQ.get(size) + 1);
                    } else {
                        SIZE_TO_FREQ.put(size, 1);
                    }
                    counting++;
                    if (counting == 1000) {
                        SIZE_TO_FREQ.notify();
                    }
                }
            });
            thread.start();
        }
        Map.Entry<Integer, Integer> maxValue = null;
        synchronized (SIZE_TO_FREQ) {
            if (counting < 1000) {
                SIZE_TO_FREQ.wait();
            }
            for (Map.Entry<Integer, Integer> entry : SIZE_TO_FREQ.entrySet()) {
                if (maxValue == null || entry.getValue().compareTo(maxValue.getValue()) > 0) {
                    maxValue = entry;
                }
            }
            System.out.println("Самое частое количество повторений " + maxValue.getKey() + " (встретилось " + maxValue.getValue() + " раз) ");
            System.out.println("Другие размеры ");
            for (Map.Entry<Integer, Integer> entry : SIZE_TO_FREQ.entrySet()) {
                if (entry.getKey() != maxValue.getKey()) {
                    System.out.println("- " + entry.getKey() + " (" + entry.getValue() + " раз) ");
                }
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