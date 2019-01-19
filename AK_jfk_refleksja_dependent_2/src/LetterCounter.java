import com.adKoch.callable.ILetterCounter;

public class LetterCounter implements ILetterCounter {
    @Override
    public int countLetter(String phrase, char letter) {
        int count = 0;
        for(int i=0;i<phrase.length();i++){
            if(phrase.charAt(i) == letter) count++;
        }
        return count;
    }

    @Override
    public int countLetters(String phrase) {
        return phrase.length();
    }

}
