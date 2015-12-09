package org.ek.advent;

public class Day5_1 {

    public static boolean hasThreeVowels(String input) {
        return input.matches(".*[aeiou].*[aeiou].*[aeiou].*");
    }
    
    public static boolean hasDoubleLetters(String input) {
        return input.matches(".*(.)\\1.*");
    }
    
    public static boolean hasNoBadSequences(String input) {
        return !input.matches(".*(ab|cd|pq|xy).*");
    }

    public static boolean isValid(String input) {
        return (hasThreeVowels(input) && hasDoubleLetters(input) && hasNoBadSequences(input));
    }
    
    
    public static void main(String[] args) throws Exception {
        String input = "fasdjfjasdfasdf";
        
        TextFile file = new TextFile("/tmp/eaking/day5.txt");
        int count = 0;
        for (String s : file) {
            if (isValid(s)) {
                count++;
            }
        }
        
        
        System.out.println(count);
    }

}
