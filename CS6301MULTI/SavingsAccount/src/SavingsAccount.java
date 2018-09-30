import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 95-1. Implement this savings account using locks and conditions.
 */
public class SavingsAccount implements Account {

  private int balance;

  final Lock mutex = new ReentrantLock();

  private final Condition canWithdraw = this.mutex.newCondition();

  public SavingsAccount() {
    this(0);
  }

  public SavingsAccount(int balance) {
    this.balance = balance;
  }

  /**
   * Return current balance.
   */
  @Override
  public int getBalance() {
    this.mutex.lock();
    try {
      return this.balance;
    } finally {
      this.mutex.unlock();
    }
  }

  /**
   * deposit(k) adds k to the balance
   */
  @Override
  public void deposit(int k) {
    this.mutex.lock();
    try {
      this.balance += k;
      this.canWithdraw.signalAll();
    } finally {
      this.mutex.unlock();
    }
  }

  /**
   * withdraw(k) subtracts k from balance, if the balance is at least k,
   * and otherwise blocks until the balance becomes k or greater
   */
  @Override
  public void withdraw(int k) {
    this.mutex.lock();
    try {
      while (k > this.balance) {
        this.canWithdraw.awaitUninterruptibly();
      }
      this.balance -= k;
    } finally {
      this.mutex.unlock();
    }
  }
}
