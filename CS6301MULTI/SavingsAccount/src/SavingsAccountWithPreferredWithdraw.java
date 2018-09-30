import java.util.concurrent.locks.Condition;

/**
 * 95-2. Implement two kinds of withdrawals for SavingsAccount: ordinary and preferred.
 */
public class SavingsAccountWithPreferredWithdraw extends SavingsAccount {

  private final Condition canOrdinaryWithdraw = this.mutex.newCondition();
  private int preferredWithdrawCount = 0;

  /**
   * ordinaryWithdraw perform withdrawal that yield to preferred withdrawal.
   */
  public void ordinaryWithdraw(int k) {
    // if preferred withdrawals waiting, wait for them to finish
    this.mutex.lock();
    try {
      while (this.preferredWithdrawCount > 0) {
        this.canOrdinaryWithdraw.awaitUninterruptibly();
      }
      // if no preferred withdrawals waiting, perform withdraw
      this.withdraw(k);
    } finally {
      this.mutex.unlock();
    }
  }

  /**
   * preferredWithdraw perform withdrawal with higher priority.
   */
  public void preferredWithdraw(int k) {
    // increment preferred withdraw count using mutex
    this.mutex.lock();
    try {
      this.preferredWithdrawCount++;
    } finally {
      this.mutex.unlock();
    }

    // perform withdraw
    this.withdraw(k);

    // decrement preferred withdraw count using mutex
    this.mutex.lock();
    try {
      this.preferredWithdrawCount--;
      this.canOrdinaryWithdraw.signalAll();
    } finally {
      this.mutex.unlock();
    }
  }
}
