package org.ek.advent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7 {
    public static Map<String, Wire> wires = new HashMap<String, Wire>();
    public static Collection<Gate> gates = new ArrayList<Gate>();

    static class Wire {
        public String name;
        private short value;
        private boolean isset;

        public Wire(String name) {
            this.name = name;
            this.value = -1;
            this.isset = false;
        }
        
        public Wire(String name, short value) {
            this.name = name;
            this.value = value;
            this.isset = true;
        }

        public void set(short value) {
            this.value = value;
            this.isset = true;
        }

        public int get() {
            return unsigned(value);
        }

        public boolean isset() {
            return isset;
        }

        @Override
        public String toString() {
            if (isset) {
                return String.format("%s(%d)", name, unsigned(value));
            } else {
                return String.format("%s(XX)", name);
            }
        }
    }
    
    static abstract class Gate {
        public Wire out;
        
        public Gate(Wire out) {
            this.out = out;
        }
        
        public void setOut(Wire w) {
            out = w;
        }
        
        public abstract boolean evaluate();

        public abstract short execute();
    }

    static abstract class OneInputGate extends Gate {
        protected String opname;
        public Wire in;

        public OneInputGate(Wire in, Wire out) {
            super(out);
            this.in = in;
        }

        @Override
        public boolean evaluate() {
            if ((in != null) && (in.isset()) && (!out.isset())) {
                out.set(execute());
                return true;
            }
            
            return false;
        }

        @Override
        public String toString() {
            return String.format("%s %s -> %s", opname, in, out);
        }
    }

    static abstract class TwoInputGate extends Gate {
        protected String opname;
        public Wire in1;
        public Wire in2;
        
        public TwoInputGate(Wire in1, Wire in2, Wire out) {
            super(out);
            this.in1 = in1;
            this.in2 = in2;
        }

        @Override
        public boolean evaluate() {
            if ((in1 != null) && (in2 != null) && (in1.isset()) && (in2.isset()) && (!out.isset())) {
                out.set(execute());
                return true;
            }

            return false;
        }

        @Override
        public String toString() {
            return String.format("%s %s %s -> %s", in1, opname, in2, out);
        }
    }

    static class AndGate extends TwoInputGate {
        public AndGate(Wire in1, Wire in2, Wire out) {
            super(in1, in2, out);
            opname = "AND";
        }

        @Override
        public short execute() {
            return (short) (in1.get() & in2.get());
        }
    }
    
    static class OrGate extends TwoInputGate {
        public OrGate(Wire in1, Wire in2, Wire out) {
            super(in1, in2, out);
            opname = "OR";
        }
        @Override
        public short execute() {
            return (short) (in1.get() | in2.get());
        }
    }

    static class RShiftGate extends OneInputGate {
        private final int shiftby;

        public RShiftGate(Wire in, int shiftby, Wire out) {
            super(in, out);
            this.shiftby = shiftby;
            opname = "RSHIFT";
        }

        @Override
        public short execute() {
            return (short) (in.get() >> shiftby);
        }
    }

    static class LShiftGate extends OneInputGate {
        private final int shiftby;

        public LShiftGate(Wire in, int shiftby, Wire out) {
            super(in, out);
            this.shiftby = shiftby;
            opname = "RSHIFT";
        }

        @Override
        public short execute() {
            return (short) (in.get() << shiftby);
        }
    }

    static class NotGate extends OneInputGate {
        public NotGate(Wire in, Wire out) {
            super(in, out);
            opname = "NOT";
        }
        @Override
        public short execute() {
            return (short) ~in.get();
        }
    }

    static class IdentityGate extends OneInputGate {
        public IdentityGate(Wire in, Wire out) {
            super(in, out);
            opname = "ID";
        }

        @Override
        public short execute() {
            return (short) in.get();
        }
    }

    private static Pattern andGate = Pattern.compile("(.*) AND (.*) -> (.*)");
    private static Pattern orGate = Pattern.compile("(.*) OR (.*) -> (.*)");
    private static Pattern setWireValue = Pattern.compile("(\\d*) -> (.*)");
    private static Pattern identityGate = Pattern.compile("([a-z]*) -> (.*)");
    private static Pattern rShift = Pattern.compile("(.*) RSHIFT (\\d*) -> (.*)");
    private static Pattern lShift = Pattern.compile("(.*) LSHIFT (\\d*) -> (.*)");
    private static Pattern not = Pattern.compile("NOT (.*) -> (.*)");

    private static Wire getWire(String name) {
        if (name.matches("\\d*")) {
            Wire w = new Wire("unnamed");
            w.set(Short.parseShort(name));
            return w;
        }

        if (!wires.containsKey(name)) {
            Wire w = new Wire(name);
            wires.put(name, w);
        }
        return wires.get(name);
    }

    private static void setWireValue(Matcher m) {
        Wire wire = getWire(m.group(2));
        wire.set(Short.parseShort(m.group(1)));
    }

    private static void addAndGate(Matcher m) {
        Wire wire1 = getWire(m.group(1));
        Wire wire2 = getWire(m.group(2));
        Wire wire3 = getWire(m.group(3));

        Gate g = new AndGate(wire1, wire2, wire3);

        gates.add(g);
    }

    private static void addOrGate(Matcher m) {
        Wire wire1 = getWire(m.group(1));
        Wire wire2 = getWire(m.group(2));
        Wire wire3 = getWire(m.group(3));

        Gate g = new OrGate(wire1, wire2, wire3);

        gates.add(g);
    }

    private static void addRShiftGate(Matcher m) {
        Wire in = getWire(m.group(1));
        Wire out = getWire(m.group(3));
        int shiftby = Integer.parseInt(m.group(2));

        Gate g = new RShiftGate(in, shiftby, out);
        gates.add(g);
    }

    private static void addLShiftGate(Matcher m) {
        Wire in = getWire(m.group(1));
        Wire out = getWire(m.group(3));
        int shiftby = Integer.parseInt(m.group(2));

        Gate g = new LShiftGate(in, shiftby, out);
        gates.add(g);
    }

    private static void addIdentityGate(Matcher m) {
        Wire in = getWire(m.group(1));
        Wire out = getWire(m.group(2));

        Gate g = new IdentityGate(in, out);
        gates.add(g);
    }

    private static void addNotGate(Matcher m) {
        Wire in = getWire(m.group(1));
        Wire out = getWire(m.group(2));

        Gate g = new NotGate(in, out);
        gates.add(g);
    }

    public static void processLine(String s) {
        Matcher m;
        
        m = andGate.matcher(s);
        if (m.matches()) {
            addAndGate(m);
            return;
        }
        
        m = orGate.matcher(s);
        if (m.matches()) {
            addOrGate(m);
            return;
        }

        m = identityGate.matcher(s);
        if (m.matches()) {
            addIdentityGate(m);
            return;
        }

        m = setWireValue.matcher(s);
        if (m.matches()) {
            setWireValue(m);
            return;
        }
        
        m = lShift.matcher(s);
        if (m.matches()) {
            addLShiftGate(m);
            return;
        }

        m = rShift.matcher(s);
        if (m.matches()) {
            addRShiftGate(m);
            return;
        }

        m = not.matcher(s);
        if (m.matches()) {
            addNotGate(m);
            return;
        }
        throw new RuntimeException("Unmatched line: " + s);
    }

    public static void main(String[] args) {
//        Map<String, Wire> wires = new HashMap<String, Wire>();
//
//        Wire a = new Wire("a");
//        Wire b = new Wire("b");
//        Wire c = new Wire("c");
//        Wire d = new Wire("d");
//        Wire e = new Wire("e");
//
//        wires.put("a", a);
//        wires.put("b", b);
//        wires.put("c", c);
//        wires.put("d", d);
//
//        Gate g1 = new AndGate();
//        g1.setIn(a);
//        g1.setIn(b);
//        g1.setOut(e);
//        
//        Gate g2 = new AndGate();
//        g2.setIn(e);
//        g2.setIn(c);
//        g2.setOut(d);
//        
//        a.value = 7;
//        b.value = 5;
//        c.value = 5;
//        
//        Collection<Gate> gates = new ArrayList<Gate>();
//        gates.add(g2);
//        gates.add(g1);

        TextFile file = new TextFile("/tmp/eaking/day7.txt");
        for (String s : file) {
            processLine(s);
        }

        boolean done = false;
        while (!done) {
            System.err.println("Loop");
            done = true;
            boolean setone = false;
            for (Gate g : gates) {
                System.out.println("Evaluating gate " + g);
                if ((g.evaluate() == false) && (!g.out.isset())) {
                    done = false;
                } else {
                    System.out.println("Set wire " + g.out.name + " to " + g.out.get());
                    setone = true;
                }

            }
            if ((setone == false) && (done == false)) {
                throw new RuntimeException();
            }
        }
        
        for (Entry<String, Wire> e : wires.entrySet()) {
            System.out.println(String.format("%s: %s", e.getKey(), e.getValue().get()));
        }

        System.out.println(wires.get("a"));
    }

    private static int unsigned(short n) {
        return ((int) n) & 0x0000FFFF;
    }
}
