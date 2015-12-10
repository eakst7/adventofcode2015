package org.ek.advent;

public class Day8_2 {

    public static void main(String[] args) throws Exception {
//        String input = "\"aaa\\\"aaa\\x27\"\n\"\"\n\"abc\"".replace("\n", "");
//        //input = FileUtils.readFileToString(new File("/tmp/eaking/day8.txt"));
//
//        input = "\"aaa\\\"aaa\"";
//        input = "\"\\x27\"";

        TextFile file = new TextFile("/tmp/eaking/day8.txt");
        int strcount = 0;
        int repcount = 0;
        for (String input : file) {

            System.out.println(input);
            char[] chararray = input.toCharArray();
            for (int i = 0; i < chararray.length; i++) {
                char c = chararray[i];
                strcount += 1;
                repcount += (c == '"' || c == '\\') ? 2 : 1;
            }

            repcount += 2;
        }
        System.out.println(String.format("%d %d %d", strcount, repcount, repcount - strcount));
    }

}
