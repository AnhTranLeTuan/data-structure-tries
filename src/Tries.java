import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tries {

    private class Node {
       private Character character;
       private  Map<Character, Node> nodeHashMap = new HashMap<>();
       private boolean isCompleteWord = false;

       public Node() {}

       public Node(Character character) {
           this.character = character;
       }

        public Node(Character character, boolean isCompleteWord) {
            this.character = character;
            this.isCompleteWord = isCompleteWord;
        }

       public void setCharacter(Character character) {
           this.character = character;
       }

        public void setIsCompleteWord(boolean isCompleteWord) {
            this.isCompleteWord = isCompleteWord;
        }

    }

    private Node rootNode = new Node(null);

    public Tries(){}

    public void add(String string){
        char[] charArray = string.toCharArray();
        int arraySize = charArray.length;

        Node currentNode = rootNode;

        for (int i = 0; i < arraySize; i++){
            if (!currentNode.nodeHashMap.containsKey(charArray[i])){
                if (i == arraySize - 1) {
                    currentNode.nodeHashMap.put(charArray[i], new Node(charArray[i], true));
                    currentNode = currentNode.nodeHashMap.get(charArray[i]);
                    break;
                }

                currentNode.nodeHashMap.put(charArray[i], new Node(charArray[i]));
            }

            currentNode = currentNode.nodeHashMap.get(charArray[i]);
        }

        if (!currentNode.isCompleteWord)
            currentNode.setIsCompleteWord(true);
    }

    public boolean contain(String string){
        char[] charArray = string.toCharArray();
        Node currentNode = rootNode;
        int counter = 0;

        for (var item : charArray){
            if (!currentNode.nodeHashMap.containsKey(item))
                return false;

            currentNode = currentNode.nodeHashMap.get(item);

            if (counter == charArray.length - 1){
                if (!currentNode.isCompleteWord)
                    return false;
            }

            counter++;
        }

        return true;
    }

    public void remove(String string){
        char[] charArray = string.toCharArray();

        traverseToRemove(charArray, 0, rootNode);
    }

    public List<String> autoCompletion(String prefix){
        Node node = findNodeOfThePrefixLastCharacter(prefix);

        if (node == null)
            return null;

        List<String> suffixList = findSuffix(node);

        return concatPrefixWithSuffix(prefix, suffixList);
    }

    private Node traverseToRemove(char[] charArray, int index, Node currentNode){
        if (!currentNode.nodeHashMap.containsKey(charArray[index]))
            return currentNode;

        Node childNode = currentNode.nodeHashMap.get(charArray[index]);

        if (index == charArray.length - 1) {

            if (!childNode.isCompleteWord)
                return currentNode;

            if (childNode.nodeHashMap.isEmpty()) {
                currentNode.nodeHashMap.remove(charArray[index]);

                if (!currentNode.isCompleteWord)
                    return null;

                return currentNode;
            }

            childNode.setIsCompleteWord(false);
            return currentNode;
        }

        childNode = traverseToRemove(charArray, index + 1, childNode);

        if (childNode == null) {
            currentNode.nodeHashMap.remove(charArray[index]);

            if (!currentNode.isCompleteWord)
                return null;
        }

        return currentNode;
    }

    private Node findNodeOfThePrefixLastCharacter(String string){
        char[] charArray = string.toCharArray();
        Node currentNode = rootNode;

        for (int i = 0; i < charArray.length; i++){
            if (!currentNode.nodeHashMap.containsKey(charArray[i]))
                return null;

            currentNode = currentNode.nodeHashMap.get(charArray[i]);
        }

        return currentNode;
    }

    private List<String> findSuffix(Node node){
        String currentCharacter = String.valueOf(node.character);
        List<String> stringList = new ArrayList();
        Map<Character, Node> hashMap = node.nodeHashMap;

        if (node.isCompleteWord)
            stringList.add(currentCharacter);

        if (hashMap.isEmpty())
            return stringList;

        for (var key : hashMap.keySet()){
            List<String> newStringList = findSuffix(hashMap.get(key));

            for (var string : newStringList)
                stringList.add(currentCharacter.concat(string));
        }

        return stringList;
    }

    private List<String> concatPrefixWithSuffix(String prefix, List<String> suffixList){
        List<String> stringList = new ArrayList();
        String newPrefix = removeThePrefixLastCharacter(prefix);

        for (var string : suffixList)
            stringList.add(newPrefix.concat(string));

        return stringList;
    }

    private String removeThePrefixLastCharacter(String prefix){
        char[] charArray = new char[prefix.length() - 1];

        for (int i = 0; i < charArray.length; i++)
            charArray[i] = prefix.charAt(i);

        return String.valueOf(charArray);
    }

}
