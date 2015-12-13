package tasks.irregularverbstest;

/**
 * Created by Allexb.
 */
public class IrregularVerb {
    private String infinitive,
            pastSimple,
            pastParticiple,
            translation;
    private boolean test = false;

    public IrregularVerb(String infinitive, String pastSimple, String pastParticiple, String translation) {
        this.infinitive = infinitive;
        this.pastSimple = pastSimple;
        this.pastParticiple = pastParticiple;
        this.translation = translation;
    }

    public String getInfinitive() {
        return infinitive;
    }

    public String getPastSimple() {
        return pastSimple;
    }

    public String getPastParticiple() {
        return pastParticiple;
    }

    public String getTranslation() {
        return translation;
    }

    public boolean isTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }
}
