package tasks.irregularverbstest;

/**
 * Created by bas on 27.04.2015.
 */
public class Statistic {
    private int size;
    private int checkCount;
    private int skipCount;
    private int balance;

    public Statistic(int size) {
        this.size = size;
        this.balance = size;
    }

    public int getBalance() {
        return balance;
    }

    public int getCheckCount() {
        return checkCount;
    }

    public int getSkipCount() {
        return skipCount;
    }

    public int getSize() {
        return size;
    }

    public void check() {
        if (balance > 0) {
            checkCount++;
            balance--;
        }
    }

    public void skip() {
        if (balance > 0) {
            skipCount++;
            balance--;
        }
    }

    public String toString() {
        return " balance: " + balance + ";  check: " + checkCount + ";  skip: " + skipCount;
    }

}
