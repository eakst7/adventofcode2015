package org.ek.advent;

import java.io.File;

import org.apache.commons.io.FileUtils;

public class Day8_1 {

    public static void main(String[] args) throws Exception {
        String input = "\"aaa\\\"aaa\\x27\"\n\"\"\n\"abc\"".replace("\n", "");
        input = FileUtils.readFileToString(new File("/tmp/eaking/day8.txt"));

        System.out.println(input);
        int codecount = 0;
        int memcount = 0;
        char[] chararray = input.toCharArray();
        for (int i = 0; i < chararray.length; i++) {
            char c = chararray[i];
            if (c == '"') {
                codecount += 1;
                continue;
            }

            if (c == '\\') {
                char d = chararray[i + 1];
                if (d == '\\' || d == '"') {
                    codecount += 2;
                    memcount += 1;
                    i++;
                    continue;
                }
                if (d == 'x') {
                    codecount += 4;
                    memcount += 1;
                    i += 3;
                    continue;
                }
            }

            codecount += 1;
            memcount += 1;
        }

        System.out.println(String.format("%d %d %d", codecount, memcount, codecount - memcount));
    }

}
