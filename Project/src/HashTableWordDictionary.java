import java.util.Hashtable;

public class HashTableWordDictionary implements WordDictionary {
    Hashtable<String, Boolean> dictionary;

    public HashTableWordDictionary(){
        dictionary = new Hashtable<>();
    }

    @Override
    public void insertWord(String word) {
        if(!dictionary.containsKey(word))
            dictionary.put(word, true);
    }

    @Override
    public boolean containsWord(String word) {
        return dictionary.containsKey(word);
    }
}
