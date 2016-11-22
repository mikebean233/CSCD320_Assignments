import java.io.*;
import java.nio.file.AccessDeniedException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tester{
    private String[] args;
    private String dictFileName;
    private DictionaryType dictType;
    private WordDictionary dictionary;
    private Character numpadMap[][] = new Character[10][];
    private boolean useBoundAndBranch = true;

    private enum DictionaryType{
        HashTable,
        Trie
    }

    public Tester(String[] inArgs){args = inArgs;}
    public static void main(String[] args) throws IOException {
        //String[] testArgs = new String[]{"-f", "dictionary.txt", "-dt", "HT", "-bb", "F"};
        (new Tester(args)).run();
    }

    public void run() throws IOException {
        validateArguments(args);
        buildNumpadMap();

        long dictBuildStart = System.currentTimeMillis();
        buildDictionary();
        long dictBuildEnd   = System.currentTimeMillis();

        String input = getValidInput();
        int intDigits[] = new int[input.length()];
        int i = 0;
        for(char thisDigit : input.toCharArray())
            intDigits[i++] = Integer.valueOf(thisDigit + "");

        long findMatchesStart = System.currentTimeMillis();
        String[] matches = generateMatches(intDigits);
        long findmatchesEnd   = System.currentTimeMillis();

        System.out.println("------ Start Results -------");
	    for(String thisMatch: matches)
	        System.out.println(thisMatch);
        System.out.println("------- End Results --------");

	    System.out.println("Using " + ((dictType == DictionaryType.HashTable) ? "Hash Table" : "Trie") + " for dictionary");
        if(useBoundAndBranch)
            System.out.println("Using Branch and Bound.");
        System.out.println("Dictionary Build Time: " + (dictBuildEnd - dictBuildStart) + " milliseconds");
        System.out.println("Find Matches Time:     " + (findmatchesEnd - findMatchesStart) + " milliseconds");
        System.out.println();
    }

    private String getValidInput(){
        boolean isValid = false;
        Scanner inputScanner = new Scanner(System.in);
        String input = "";


        while(!isValid) {
            isValid = true;
            System.out.println("Enter a numbered keypad sequence: ");
            input = inputScanner.nextLine();
            if (!Pattern.matches("^[0-9]*$", input)) {
                System.err.println("The provided input must only contain numbers");
                isValid = false;
            }
        }
        return input;
    }

    private void buildNumpadMap(){
        numpadMap[0]= new Character[]{                  };
        numpadMap[1]= new Character[]{                  };
        numpadMap[2]= new Character[]{'a', 'b', 'c'     };
        numpadMap[3]= new Character[]{'d', 'e', 'f'     };
        numpadMap[4]= new Character[]{'g', 'h', 'i'     };
        numpadMap[5]= new Character[]{'j', 'k', 'l'     };
        numpadMap[6]= new Character[]{'m', 'n', 'o'     };
        numpadMap[7]= new Character[]{'p', 'q', 'r', 's'};
        numpadMap[8]= new Character[]{'t', 'u', 'v'     };
        numpadMap[9]= new Character[]{'w', 'x', 'y', 'z'};
    }

    private String[] generateMatches(int[] digits){
        Character[][] digitCharsetMap = new Character[digits.length][];
        for(int i = 0;i < digits.length; ++i)
            digitCharsetMap[i] = numpadMap[digits[i]];

        Trie matches = new Trie();

        generateMatches("", matches, digitCharsetMap, 0);
        return matches.getWords().toArray(new String[]{}) ;
    }

    private void generateMatches(String prefix, Trie matches, Character[][] digitCharsetMap, int digitIndex){
        for (Character thisChar : digitCharsetMap[digitIndex]) {
            String thisPrefix = prefix + thisChar;

            if(digitIndex == digitCharsetMap.length -1) {
                // We have reached the last digit, time to check if any of the permutations build using this digits charset
                // actually forms an english word if so add it to the results list.
                if (dictionary.containsWord(thisPrefix))
                    matches.insertWord(thisPrefix);

            } else if (!useBoundAndBranch || dictionary.containsPrefix(thisPrefix)) {
                // If we haven't reached the last digit and this is a prefix for an actual word, then continue checking
                // more digits to the right.

                generateMatches(thisPrefix, matches, digitCharsetMap, digitIndex + 1);
            }
        }
    }

    private void buildDictionary() throws IOException {
	    File inputFile = new File(dictFileName);
	    if (!inputFile.exists()) {
		    System.err.println("Error: the specified " + dictFileName + " does not exist.");
		    System.exit(1);
	    }

	    if (!inputFile.canRead()){
            System.err.println("Error: the user " + System.getProperty("user.name") + " does not have read privileges to the dictionary file " + dictFileName);
	        System.exit(1);
	    }

        dictionary = (dictType == DictionaryType.Trie) ? new Trie() : new HashTableWordDictionary();

        Scanner fileScanner = new Scanner(inputFile);
        while(fileScanner.hasNext()){
            String thisToken = fileScanner.next();
            Matcher matcher = Pattern.compile("[a-z]+").matcher(thisToken);
            if(matcher.find())
                dictionary.insertWord(matcher.group());
        }
    }

    public void validateArguments(String[] args){
        if(args.length == 0){
            dictFileName = "dictionary.txt";
            dictType = DictionaryType.Trie;
            useBoundAndBranch = true;
            return;
        }

        if(args.length != 6)
            usage(1);

        for(int i = 0; i < 5; i += 2){
            String argKey   = args[i];
            String argValue = args[i + 1];
            if(argKey.equals("-f"))
                dictFileName = argValue;
            else if(argKey.equals("-dt")){
                if     (argValue.equals("HT")) dictType = DictionaryType.HashTable;
                else if(argValue.equals("TR")) dictType = DictionaryType.Trie;
                else usage(1);
            }
            else if(argKey.equals("-bb")){
                if     (argValue.equals("T")) useBoundAndBranch = true;
                else if(argValue.equals("F")) useBoundAndBranch = false;
                else usage(1);
            }
            else
                usage(1);
        }

        if(dictFileName == null || dictType == null)
            usage(1);
    }

    public static void usage(int returnValue){
        System.err.println("Usage: PROGRAM [ -f filename -dt HT|TR -bb T|F ]");
        System.err.println("            -f  Dictionary text file name");
        System.err.println("            -dt Abstract datatype used to store dictionary (HT: hash table, TR: Trie)");
        System.err.println("            -bb Use Bound and Branch (T: True, F: False)");
        System.exit(returnValue);
    }
}
