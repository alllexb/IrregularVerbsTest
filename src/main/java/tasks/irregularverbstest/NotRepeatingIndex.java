package tasks.irregularverbstest;

import java.util.*;

/**
 * Created by Allexb
 */
public class NotRepeatingIndex {
    private int range;
    private List<Integer> numbers;
    private int counter = 0;

    public NotRepeatingIndex(int range) {
        this.range = range;
        setNumbers();
    }

    private void setNumbers() {
        Set<Integer> numbers = new HashSet<Integer>();
        Random random = new Random();
        while (numbers.size() < range) {
            numbers.add(random.nextInt(range));
        }

        List<Integer> result = new ArrayList<Integer>(numbers);

        // shuffle the results:
        int i = result.size();
        while (i > 1) {
            i--;
            int k = random.nextInt(i + 1);
            int value = result.get(k);
            result.set(k, result.get(i));
            result.set(i, value);
        }

        this.numbers = result;
    }

    public boolean hasNext() {
        if (counter < range) {
            return true;
        }
        return false;
    }

    public int nextInt() {
        int next;
        if (hasNext()) {
            next = numbers.get(counter);
            counter++;
        } else {
            next = -1;
        }
        return next;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }
}
