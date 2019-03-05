package uhh_lt.webserver;
public class wordCount {

    public static void main(String[] args) {
        System.out.println(wordCount.countWord("    Ich liebe es efdc ewfsdc wdw"));
    }

    public static int  countWord(String message) {

        int count = 0;
        char ch[] = new char[message.length()];
        for (int i = 0; i < message.length(); i++) {
            ch[i] = message.charAt(i);
            if (((i > 0) && (ch[i] != ' ') && (ch[i - 1] == ' ')) || ((ch[0] != ' ') && (i == 0)))
                count++;
        }
        return count;
    }

}


