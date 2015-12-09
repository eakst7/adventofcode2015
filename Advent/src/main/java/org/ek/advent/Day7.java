package org.ek.advent;

public class Day7 {

    static class Wire {
        public String name;
        public int value;

        public Wire(String name) {
            this.name = name;
            this.value = -1;
        }
        
        public Wire(String name, int value) {
            this.name = name;
            this.value = value;
        }
    }
    
    static abstract class Gate {
        public Wire in1;
        public Wire in2;
        public Wire out;
        
        public void setIn(Wire w) {
            if (in1 == null) {
                in1 = w;
            } else {
                in2 = w;
            }
        }
        
        public void setOut(Wire w) {
            out = w;
        }
        
        public boolean evaluate() {
            if ((in1 != null) && (in2 != null) && (in1.value > -1) && (in2.value > -1)) {
                out.value = execute();
                return true;
            }
            
            return false;
        }
        
        public abstract int execute();
    }
    
    static class AndGate extends Gate {
        @Override
        public int execute() {
            return in1.value & in2.value;
        }
    }
    
    public static void main(String[] args) {
        Wire w1 = new Wire("a");
        Wire w2 = new Wire("b");
        Wire out = new Wire("c");
        
        Gate g = new AndGate();
        g.setOut(out);
        g.setIn(w1);
        
        System.out.println(g.evaluate());
        
        g.setIn(w2);
        
        System.out.println(g.evaluate());
        
        System.out.println(out.value);

    }
    
}
