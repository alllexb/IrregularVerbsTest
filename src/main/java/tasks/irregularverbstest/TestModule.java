package tasks.irregularverbstest;

import java.util.List;
import java.util.Random;

/**
 * Created by Allexb.
 */
public class TestModule {
    public static final int RANDOM = 1;
    public static final int RANDOM_NOT_REPEATING = 2;
    public static final int SERIAL = 3;

    private List<IrregularVerb> vocabulary;
    private IrregularVerb currentVerb;

    private int counter;
    private NotRepeatingIndex notRepeatingIndex;

    public TestModule(List<IrregularVerb> vocabulary) {
        this.vocabulary = vocabulary;
        this.counter = 0;
        notRepeatingIndex = new NotRepeatingIndex(vocabulary.size());
    }

    public IrregularVerb getNext(int takingStile) {
        if (takingStile == RANDOM) {
            Random random = new Random();
            currentVerb = vocabulary.get(random.nextInt(vocabulary.size()));
        }
        if (takingStile == RANDOM_NOT_REPEATING) {
            if (notRepeatingIndex.hasNext()) {
                currentVerb = vocabulary.get(notRepeatingIndex.nextInt());
            } else {
                counter = vocabulary.size();
            }
        }
        if (takingStile == SERIAL) {
            currentVerb = vocabulary.get(counter);
            counter++;
        }
        return currentVerb;
    }

    public boolean hasNext() {
        if (counter < vocabulary.size()) {
            return true;
        }
        return false;
    }

    public boolean isRight(String pastSimple, String pastParticiple) {
        if (pastSimple.equals(currentVerb.getPastSimple()) && pastParticiple.equals(currentVerb.getPastParticiple())) {
            return true;
        }
        return false;
    }

    public int testSize(int takingStile) {
        if (takingStile == RANDOM) {
            return 0;
        }
        if (takingStile == RANDOM_NOT_REPEATING) {
            return notRepeatingIndex.getNumbers().size();
        }
        if (takingStile == SERIAL) {
            return vocabulary.size();
        }
        return -1;
    }
}
