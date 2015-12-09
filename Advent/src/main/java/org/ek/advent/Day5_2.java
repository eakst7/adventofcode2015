package org.ek.advent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day5_2 {

    public static boolean hasDoublePairs(String input) {
        Pattern p = Pattern.compile(".*(.)(.).*\\1\\2.*");
        Matcher m = p.matcher(input);
        boolean matches = m.matches();
//        
//        System.out.println(m.group(0));
//        System.out.println(m.group(1));
//        System.out.println(m.group(2));
        return matches;
    }
    
    public static boolean hasRepeatWithSkip(String input) {
        Pattern p = Pattern.compile(".*(.).\\1.*");
        Matcher m = p.matcher(input);
        boolean matches = m.matches();
//        
//        System.out.println(m.group(0));
//        System.out.println(m.group(1));
//        System.out.println(m.group(2));
        return matches;
    }

    public static boolean isValid(String input) {
        return (hasDoublePairs(input) && hasRepeatWithSkip(input));
    }
    
    
    public static void main(String[] args) throws Exception {
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
