package org.ek.advent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day6_2 {
    public static int[][] grid = new int[1000][1000];

    public static void on(int x, int y) {
        //System.out.println("turning on " + x + "," + y);
        grid[x][y] += 1;
    }
    
    public static void off(int x, int y) {
        grid[x][y] -= 1;
        grid[x][y] = Math.max(0, grid[x][y]);
    }

    public static void toggle(int x, int y) {
        grid[x][y] += 2;
    }
    
    public static void setRangeOn(int x1, int y1, int x2, int y2) {
        for (int xpos = Math.min(x1,x2); xpos <= Math.max(x1,x2); xpos++) {
            for (int ypos = Math.min(y1,y2); ypos <= Math.max(y1,y2); ypos++) {
                on(xpos, ypos);
            }
        }
    }
    
    public static void setRangeOff(int x1, int y1, int x2, int y2) {
        for (int xpos = Math.min(x1,x2); xpos <= Math.max(x1,x2); xpos++) {
            for (int ypos = Math.min(y1,y2); ypos <= Math.max(y1,y2); ypos++) {
                off(xpos, ypos);
            }
        }
    }

    public static void toggleRange(int x1, int y1, int x2, int y2) {
        for (int xpos = Math.min(x1,x2); xpos <= Math.max(x1,x2); xpos++) {
            for (int ypos = Math.min(y1,y2); ypos <= Math.max(y1,y2); ypos++) {
                toggle(xpos, ypos);
            }
        }
    }

    enum Command { ON, OFF, TOGGLE };
    
    static class Ins {
        public Command command;
        public int x1;
        public int x2;
        public int y1;
        public int y2;
        
        public Ins(Command command, int x1, int y1, int x2, int y2) {
            this.command = command;
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
        
        @Override
        public String toString() {
            return String.format("%s %d,%d through %d,%d", command.toString(),
                    x1, y1, x2, y2);
        }
    }
    
    public static Ins parseInstruction(String input) {
        if ("".equals(input.trim())) {
            return null;
        }
        Pattern p = Pattern.compile("(.*) (\\d{1,3}),(\\d{1,3}) through (\\d{1,3}),(\\d{1,3}).*");
        Matcher m = p.matcher(input);
        m.matches();
        String ins = m.group(1);
        int x1 = Integer.parseInt(m.group(2));
        int y1 = Integer.parseInt(m.group(3));
        int x2 = Integer.parseInt(m.group(4));
        int y2 = Integer.parseInt(m.group(5));
        
        Command c = null;
        if (ins.equals("turn on")) {
            c = Command.ON;
        } else if (ins.equals("turn off")) {
            c = Command.OFF;
        } else {
            c = Command.TOGGLE;
        }
        
        return new Ins(c, x1, y1, x2, y2);
    }
    
    public static void main(String[] args) {
        TextFile file = new TextFile("/tmp/eaking/day6.txt");
        
        for (String s : file) {
            Ins ins = parseInstruction(s);
            if (ins != null) {
                switch (ins.command) {
                case ON:
                    setRangeOn(ins.x1, ins.y1, ins.x2, ins.y2);
                    break;
                case OFF:
                    setRangeOff(ins.x1, ins.y1, ins.x2, ins.y2);
                    break;
                case TOGGLE:
                    toggleRange(ins.x1, ins.y1, ins.x2, ins.y2);
                    break;
                default:
                    throw new RuntimeException();
                }
            }
        }
        
        int count = 0;
        for (int y = 0; y < 1000; y++) {
            for (int x = 0; x < 1000; x++) {
                count += grid[x][y];
            }
        }
        
        System.out.println(count);
    }

}
