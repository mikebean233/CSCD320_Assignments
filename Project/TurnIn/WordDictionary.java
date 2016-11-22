public interface WordDictionary {
    public void insertWord(String word);
    public boolean containsWord(String word);
    public boolean containsPrefix(String prefix);
    public int wordCount();
}
