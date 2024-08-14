import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The 'austinChalange' class is a multi-threaded program that simulates rolling a dice
 * and counts the maximum number of ones obtained in a series of trials.
 * The program is designed to utilize all available processor threads.
 */
public class austinChalange {

    /** The time when the program starts running. */
    public static long startTime = System.currentTimeMillis();

    /** The total number of tests to be performed. */
    public static long tests = 1_000_000_000;

    /**
     * The main method where the program execution begins.
     * This method initializes the thread pool, divides the workload among threads,
     * and outputs the results.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Random rng = new Random();
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        long numTests = (tests / numThreads) + 1;

        System.out.println("This computer has " + numThreads + " threads");
        System.out.println("Each thread will do " + numTests + " tests");

        // Define the task that each thread will execute
        Runnable task = () -> {
            int maxOnes = 0;
            int n;
            int amount = 0;
            for (int i = 0; i < numTests; i++) {
                n = dice(rng);
                amount++;
                if (maxOnes < n) {
                    maxOnes = n;
                }
            }
            System.out.println("The max amount of 1s in this thread was: " + maxOnes);
            System.out.println("This thread has done " + amount + " tests");
        };

        // Submit the task to each thread in the thread pool
        for (int i = 0; i < numThreads; i++) {
            executor.submit(task);
        }

        // Shutdown the executor and await termination
        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.DAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("The program has run for " + (System.currentTimeMillis() - startTime) / 1_000.0 + " seconds");
    }

    /**
     * Simulates rolling a dice multiple times and counts how many times the number 1 appears.
     *
     * @param rng The random number generator used to simulate dice rolls.
     * @return The number of times the number 1 appeared during the dice rolls.
     */
    public static int dice(Random rng) {
        int maxOnes = 0;
        int n;
        n = rng.nextInt(4); // Generates a random number between 0 and 3
        for (int i = 0; i < 231; i++) {
            if (n == 0) { // Assuming 0 represents the number 1 on the dice
                maxOnes++;
            }
            n = rng.nextInt(4); // Generates a random number between 0 and 3
        }

        return maxOnes;
    }
}
