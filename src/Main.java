import java.util.Random;

public class Main
{
    public static void main(String[] args) throws InterruptedException
    {
        BankAccount b1 = new BankAccount();
        BankAccount b2 = new BankAccount();
        Random generator = new Random();

        Runnable task1 = () ->
        {
            for(int i = 0; i < 100_000; i++)
            {
                b1.swapMoney(b2, generator.nextInt(100));
            }
        };
        Runnable task2 = () ->
        {
            for(int i = 0; i < 100_000; i++)
            {
                b2.swapMoney(b1, generator.nextInt(100));
            }
        };

        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task2);
        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println("First account " + b1.getAmount());
        System.out.println("Second account " + b2.getAmount());
        long sum = b1.getAmount() + b2.getAmount();
        System.out.println("Sum " + sum);
    }
}