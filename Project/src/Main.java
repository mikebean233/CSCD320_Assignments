import java.io.*;
import java.nio.file.AccessDeniedException;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main{
    private String[] args;
    private String dictFileName;
    private String digits;
    private DictionaryType dictType;

    private enum DictionaryType{
        HashTable,
        Trie
    }

    public Main(String[] inArgs){args = inArgs;}
    public static void main(String[] args) throws IOException {
        String[] testArgs = new String[]{"-f", "dictionary.txt", "-i", "12345", "-dt", "HT"};
        (new Main(testArgs)).run();
    }

    public void run() throws IOException {
        verifyArguments(args);
        WordDictionary dictionary = (dictType == DictionaryType.Trie) ? new Trie() : new HashTableWordDictionary();

        Hashtable<Integer, Character[]> numpadMap = buildNumpadMap();



        loadDictionary(dictionary);



        System.out.println();
    }

    private Hashtable<Integer, Character[]> buildNumpadMap(){
        Hashtable<Integer, Character[]> numpadMap = new Hashtable<>();

        numpadMap.put(1, new Character[]{'1'               });// TODO: get rid of the one?
        numpadMap.put(2, new Character[]{'a', 'b', 'c'     });
        numpadMap.put(3, new Character[]{'d', 'e', 'f'     });
        numpadMap.put(4, new Character[]{'g', 'h', 'i'     });
        numpadMap.put(5, new Character[]{'j', 'k', 'l'     });
        numpadMap.put(6, new Character[]{'m', 'n', 'o'     });
        numpadMap.put(7, new Character[]{'p', 'q', 'r', 's'});
        numpadMap.put(8, new Character[]{'t', 'u', 'v'     });
        numpadMap.put(9, new Character[]{'w', 'x', 'y', 'z'});

        return numpadMap;
    }


    private void loadDictionary(WordDictionary dictionary) throws IOException {
        File inputFile = new File(dictFileName);
        if(!inputFile.exists())  throw new FileNotFoundException(dictFileName);
        if(!inputFile.canRead()) throw new AccessDeniedException(dictFileName);

        Scanner fileScanner = new Scanner(inputFile);
        while(fileScanner.hasNext()){
            String thisToken = fileScanner.next();
            Matcher matcher = Pattern.compile("[a-z]+").matcher(thisToken);
            if(matcher.find())
                dictionary.insertWord(matcher.group());
        }
    }

    public void verifyArguments(String[] args){
        if(args.length != 6)
            usage(1);

        for(int i = 0; i < 5; ++i){
            String argKey   = args[i];
            String argValue = args[i + 1];
            if(argKey == "-f" ) dictFileName = argValue;
            if(argKey == "-i" ) digits       = argValue;
            if(argKey == "-dt"){
                if     (argValue == "HT") dictType = DictionaryType.HashTable;
                else if(argValue == "TR") dictType = DictionaryType.Trie;
                else usage(1);
            }
        }

        if(dictFileName == null || digits == null || dictType == null)
            usage(1);
    }

    public static void usage(int returnValue){
        System.err.println("Usage: PROGRAM -f filename -i digits -dt datatype");
        System.err.println("            filename: Dictionary text file path");
        System.err.println("            digits:   Keypad digits" );
        System.err.println("            dataType: Abstract datatype used to store dictionary");
        System.err.println("                        HT: hash table");
        System.err.println("                        TR: Trie");
        System.exit(returnValue);
    }
}
