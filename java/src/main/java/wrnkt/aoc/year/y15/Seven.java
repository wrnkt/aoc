package wrnkt.aoc.year.y15;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import aoc.framework.Day;

public class Seven extends Day {

    public static final Pattern BINARY_OP_PATTERN = Pattern.compile("^(\\w+)\\s+(AND|OR|LSHIFT|RSHIFT)\\s+(\\w+)\\s+->\\s+(\\w+)$");
    public static final Pattern UNARY_YIELD_PATTERN = Pattern.compile("^(\\w+)\\s+->\\s+(\\w+)$");
    public static final Pattern UNARY_NOT_PATTERN = Pattern.compile("^NOT\\s+(\\w+)\\s+->\\s+(\\w+)$");

    private List<Wire> wires = new ArrayList<>();
    private List<Operation> operations = new ArrayList<>();

    /*
     * 123
     *          1111011
     * 1111111110000100
     *
     */

    @Override
    public void solution(BufferedReader reader) {
        readData(reader);
        partOne();
    }

    public void partOne() {
        print("---------------------");
        print("Pre Evaluation:");
        operations.stream().forEach((op) -> {
            print(op.toString());
            op.evaluate();
        });
        print("---------------------");
        print("Post Evaluation:");
        operations.stream().forEach((op) -> {
            print(op.toString());
        });
    }

    public void readData(BufferedReader reader) {
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                Operation op = parseOperation(line);
                operations.add(op);
            }
        } catch (IOException e) {
            log.error("Failed to read input: ", e);
        }
    }

    public Operation parseOperation(String input) throws IOException {
        Matcher m;

        m = BINARY_OP_PATTERN.matcher(input);
        if (m.matches()) {
            Operand a = getWireOrDigit(m.group(1));
            Operand b = getWireOrDigit(m.group(3));
            BinaryOp.Type type = BinaryOp.Type.valueOf(m.group(2));
            Wire yield = (Wire) getWireOrDigit(m.group(4));

            return new BinaryOp(a, b, type, yield);
        }

        m = UNARY_YIELD_PATTERN.matcher(input);
        if (m.matches()) {
            Operand a = getWireOrDigit(m.group(1));
            Wire yield = (Wire) getWireOrDigit(m.group(2));

            return new UnaryOp(a, UnaryOp.Type.PROVIDE, yield);
        }

        m = UNARY_NOT_PATTERN.matcher(input);
        if (m.matches()) {
            Operand a = getWireOrDigit(m.group(1));
            Wire yield = (Wire) getWireOrDigit(m.group(2));

            return new UnaryOp(a, UnaryOp.Type.NOT, yield);
        }

        throw new IOException("Unknown operation");
    }

    public Operand getWireOrDigit(String input) {
        try {
            Integer i = Integer.parseInt(input);
            return new Digit(i);
        } catch (IllegalArgumentException e) {}
        return dedupeWire(input);
    }

    public Wire dedupeWire(String name) {
        List<Wire> foundWires = wires.stream()
                                    .filter((wire) -> wire.name.equals(name))
                                    .collect(Collectors.toList());
        if (foundWires.size() == 1) {
            return foundWires.get(0);
        }
        else if (foundWires.size() == 0) {
            Wire newWire = new Wire(name);
            wires.add(newWire);
            return newWire;
        } else {
            print("!!! ERROR: Found multiple wires with name: " + name);
        }
        return null;
    }


    abstract class Operation {
        protected Wire yield;

        public Operation() {}
        public Operation(Wire yield) {
            this.yield = yield;
        }

        abstract void evaluate();
    }

    class BinaryOp extends Operation {
        enum Type { AND, OR, LSHIFT, RSHIFT; };

        private Type type;

        private Operand a;
        private Operand b;

        public BinaryOp(Operand a, Operand b, Type type, Wire yield) {
            super(yield);
            this.a = a;
            this.b = b;
            this.type = type;
        }

        @Override
        void evaluate() {
            int aVal = a.getValue();
            int bVal = b.getValue();
            switch (type) {
                case AND:
                    yield.setValue(aVal & bVal);
                    break;
                case OR:
                    yield.setValue(aVal | bVal);
                    break;
                case LSHIFT:
                    yield.setValue(aVal << bVal);
                    break;
                case RSHIFT:
                    yield.setValue(aVal >>> bVal);
                    break;
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            return sb.append("BinaryOp[")
                .append(a)
                .append(" ")
                .append(type)
                .append(" ")
                .append(b)
                .append(" -> ")
                .append(yield)
                .append("]")
                .toString();
        }
    }

    class UnaryOp extends Operation {
        enum Type { NOT, PROVIDE; };

        private Type type;

        private Operand a;

        public UnaryOp(Operand a, Type type, Wire yield) {
            super(yield);
            this.a = a;
            this.type = type;
        }

        @Override
        void evaluate() {
            int aVal = a.getValue(); 
            switch (type) {
                case NOT:
                    yield.setValue(~aVal & 0xFFFF);
                    break;
                case PROVIDE:
                    yield.setValue(aVal);
                    break;
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            return sb.append("UnaryOp[")
                .append(a)
                .append(" ")
                .append(type)
                .append(" -> ")
                .append(yield)
                .append("]")
                .toString();
        }
    }

    abstract class Operand {
        abstract public Integer getValue();
    }

    class Wire extends Operand {
        private final String name;
        private Integer value = null;

        public Wire(String name) {
            this.name = name;
        }

        public Integer getValue() { return this.value; }
        public void setValue(int value) { this.value = value; }
        public void setValue(Integer value) { this.value = value; }

        public boolean hasValue() { return this.value != null; }

        public String getName() { return this.name; }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            return sb.append("Wire[")
                .append(name)
                .append(" ")
                .append(value)
                .append("]")
                .toString();
        }
    }

    class Digit extends Operand {
        private Integer value;

        public Digit(int value) { this.value = value; }
        public Digit(Integer value) { this.value = value; }

        public Integer getValue() { return value; }
        public void setValue(int value) { this.value = value; }
        public void setValue(Integer value) { this.value = value; }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            return sb.append("Digit[")
                .append(value)
                .append("]")
                .toString();
        }
    }

}
