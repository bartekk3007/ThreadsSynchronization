import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BankAccount {
    private long amount;
    private Lock lock = new ReentrantLock();

    public BankAccount()
    {
        amount = 10_000_000;
    }

    public void swapMoney(BankAccount b, int money)
    {
        BankAccount first = this;
        BankAccount second = b;

        // Blokowanie obu kont
        if (System.identityHashCode(first) > System.identityHashCode(second))
        {
            first = b;
            second = this;
        }

        first.lock.lock();
        try
        {
            second.lock.lock();
            try
            {
                // Aktualizacja środków
                b.setAmount(b.getAmount() + money);
                this.setAmount(this.getAmount() - money);
                System.out.println("Wymiana pieniędzy " + money);
            }
            finally
            {
                second.lock.unlock();
            }
        }
        finally
        {
            first.lock.unlock();
        }
    }

    // Synchronizacja dostępu do salda konta
    public long getAmount()
    {
        lock.lock();
        try
        {
            return amount;
        }
        finally
        {
            lock.unlock();
        }
    }

    // Synchronizacja ustawiania salda konta
    public void setAmount(long amount)
    {
        lock.lock();
        try
        {
            this.amount = amount;
        } finally
        {
            lock.unlock();
        }
    }
}