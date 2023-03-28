import java.util.List;

public class Main {
    public static void main(String[] args) {
        Tries tries = new Tries();

        tries.add("ab");
        tries.add("abc");
        tries.add("abd");
        tries.add("abcd");
        tries.add("abcc");
        tries.add("qweas");

        //tries.remove("abc");

        //System.out.println(tries.contain("abc"));
        //System.out.println(tries.contain("abcxyzb"));
        //System.out.println(tries.contain("qweas"));
        List<String> list = tries.autoCompletion("ab");

        for (var item : list)
            System.out.println(item);



    }
}