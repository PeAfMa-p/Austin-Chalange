import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class austinChalange {

    public static long startTime = System.currentTimeMillis(); //mark the time when the program starts
    public static long tests = 1_000_000_000; //The total amount o tests that will be done
    public static void main(String[] args) {
        Random rng = new Random(); // create a instance of a random number generator
        int numThreads = Runtime.getRuntime().availableProcessors(); //identify how many threads this computer has
        ExecutorService executor = Executors.newFixedThreadPool(numThreads); // creates a pool of threads to distribute the work
        long numTests = (tests / numThreads) + 1; // divide the amount of tests per the nunber of avaliable threads plus one to garantee that the amount of tests will not be lower than the especified amount
        System.out.println("This computer has " + numThreads + " threads"); // print the amount of threads you pc has
        System.out.println("Each thread will do " + numTests + " tests"); // print how many tests each thread will do
        /*
         * creates a task that will be done to each thread simultaneously
         * repeatly calls the funcion dice() in order to run the test
         * the function will be called acordenly to the amount assigned to each thread
         */
        Runnable task = () -> {
            int maxOnes = 0;
            int n;
            int amount = 0;
            for(int i = 0;i < numTests;i++){
                n = dice(rng);
                amount++;
                if(maxOnes < n){
                    maxOnes = n;
                }
            }
            System.out.println("The max amount of 1s in this thread was: " + maxOnes);
            System.out.println("This thread has done " + amount + " tests");
        };
        /*
         * submit one task to each thread
         */
        for(int i = 0;i < numThreads;i++){
            executor.submit(task);
        }
        executor.shutdown(); // shutdown the threads after all tasks are done
        try{
            executor.awaitTermination(10, TimeUnit.DAYS); // only ecxecute the lines below after all tasks are done or after 10 days, whatever comes first
        }
        catch(Exception e){

        }
        System.out.println("The program has run for " + (System.currentTimeMillis() - startTime) / 1_000.0 + " seconds"); // mark the current time and calculate for how long the program has been runing
    }

    /*
     * generates a number betwen 0 and 3 231 times and count how many times the number 0 was generated
     */
    public static int dice(Random rng){
        int maxOnes = 0;
        int n;
        n = rng.nextInt(4); // generated a number betwen 0 and 3
        for(int i = 0;i < 231;i++){
            if(n == 0){
                maxOnes++;
            }
            n = rng.nextInt(4); // generated a number betwen 0 and 3
        }

        return maxOnes;
    }
}
