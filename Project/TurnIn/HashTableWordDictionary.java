import java.util.Arrays;
import java.util.Hashtable;

public class HashTableWordDictionary implements WordDictionary {
    private static final int IS_PREFIX = 1;
    private static final int IS_WORD   = 2;
	private int wordCount = 0;

    Hashtable<String, Integer> dictionary;

    public HashTableWordDictionary(){
        dictionary = new Hashtable<>();
    }

    @Override
    public void insertWord(String word) {
		if(word == null || word.length() == 0)
			throw new NullPointerException();

        wordCount += (dictionary.putIfAbsent(word, 0) == null) ? 1 : 0;
	    dictionary.put(word, dictionary.get(word) | IS_WORD);

	    String thisPrefix = (word.length() == 1) ? word :  word.substring(0, word.length() - 1);
		while(thisPrefix.length() > 0){
			dictionary.putIfAbsent(thisPrefix, 0);
	        dictionary.put(thisPrefix, dictionary.get(thisPrefix) | IS_PREFIX);
	        thisPrefix = (thisPrefix.length() == 1) ? "" : thisPrefix.substring(0, thisPrefix.length() - 1);
        }
    }

    @Override
    public boolean containsWord(String word) {
    return dictionary.containsKey(word) && ((dictionary.get(word).intValue() & IS_WORD) != 0);
    }

	@Override
	public boolean containsPrefix(String prefix) {
		return dictionary.containsKey(prefix) && ((dictionary.get(prefix).intValue() & IS_PREFIX) != 0);
	}

	@Override
	public int wordCount() {
		return wordCount;
	}
}
