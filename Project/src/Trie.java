import java.util.*;

public class Trie implements WordDictionary
{
	private TrieNode root;
	public Trie(){
		root = new TrieNode();
	}

	private class TrieNode // ------------------------------------------
	{
		// This tree map holds all of the subtries of this node
		Map<Character, TrieNode> children; 
		boolean aword;						

		protected TrieNode()
		{
			this.children = new TreeMap<>();
			this.aword = false;
		}
	
	}// end TrieNode class ---------------------------------------------

	public void insertWord(String s)
	{
		TrieNode cur = root;
		String prefix = "";
		for (char ch : s.toCharArray()) 
		{
			prefix += ch;
			TrieNode next = cur.children.get(ch);
			if (next == null)
			{
				cur.children.put(ch, next = new TrieNode());
			}
		    cur = next;
		}
		cur.aword = true;
	}
	
	public void printSorted() { printSorted(root, ""); }

	private void printSorted(TrieNode node, String s) {
		if (node.aword) {
			System.out.println(s);
		}
		for (Character ch : node.children.keySet()) {
			printSorted(node.children.get(ch), s + ch);
		}
	}

	public boolean containsWord(String word) {return containsWord(root, word);}
	
	private boolean containsWord(TrieNode node, String s)
	{
		if(s != null && s.length() > 0) {
			String rest = s.substring(1);          //rest is a substring of s, by excluding the first character in s
			char ch = s.charAt(0);                 //ch is the first letter of s
			TrieNode child = node.children.get(ch);//return the child that ch associated with. 
			if(s.length() == 1 && child != null && child.aword)   //if s contains only one letter, and current node has a child associated with that letter, we find the prefix in Trie!
				return true;	                   //base case
			if(child == null)
				return false;
			else
				return containsWord(child, rest);      //recursive, In this way, we follow the path of the trie from root down towards leaf
		}
		return false;
	}
}