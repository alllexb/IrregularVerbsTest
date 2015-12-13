package tasks.irregularverbstest;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allexb.
 */
public class LoadVocabulary {
    private RandomAccessFile file;

    public LoadVocabulary(String fullFileName) throws IOException{
//        try {
            this.file = new RandomAccessFile(fullFileName, "r");

//        } catch (IOException e) {
//            System.out.println("Data file not found!");
//            e.printStackTrace();
//        }
    }

    public RandomAccessFile getFile() {
        return file;
    }

    public List<IrregularVerb> loadData() {
        List<IrregularVerb> verbs = new ArrayList<IrregularVerb>();
        try {
            while (file.getFilePointer() != file.length()) {

                String line = file.readLine();
                
                String[] words = line.split(" ");
                if(words.length == 4) {
                    String infinitive = new String(words[0].getBytes("ISO-8859-1"),"Cp1251");
                    String pastSimple = new String(words[1].getBytes("ISO-8859-1"),"Cp1251");
                    String pastParticiple = new String(words[2].getBytes("ISO-8859-1"),"Cp1251");
                    String translation = new String(words[3].getBytes("ISO-8859-1"),"Cp1251");
                    verbs.add(new IrregularVerb(infinitive,pastSimple,pastParticiple,translation));
                }
            }
            file.seek(0);
        } catch (IOException e) {
            System.out.println("Data file can not read!");
            e.printStackTrace();
        }
        return verbs;
    }
}
