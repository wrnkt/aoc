package wrnkt.aoc.year.y24;

import java.io.BufferedReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aoc.framework.Day;

public class Seven extends Day {
    @Override
    public void solution(BufferedReader br) {
        var equations = parse(br);
        print(String.format("Parsed %s equations", equations.size()));
        partOne(equations);
    }

    private void partOne(List<Equation> equations) {
        print("Part One:");
    }

    private List<Equation> parse(BufferedReader br) {
        var equations = new ArrayList<Equation>();
        br.lines().forEach((line) -> {
            try {
                equations.add(Equation.parse(line));
            } catch (ParseException pEx) {
                print(String.format("%s in %s", 
                    pEx.getLocalizedMessage(), 
                    line));
            }
        });
        return equations;
    }

    public static class Equation {
        private long value;
        private List<Integer> terms = new ArrayList<>();
        private List<Character> operators = new ArrayList<>();

        public Equation() {}

        public static Equation parse(String input) throws ParseException {
            var e = new Equation();
            var parts = input.split(":");

            if (parts.length != 2) throw new ParseException("Malformed input", 0);
            e.setValue(Long.parseLong(parts[0].trim()));

            var afterColon = parts[1].trim().split("\\s+");
            var terms = Arrays.stream(afterColon).map((s) -> Integer.parseInt(s)).toList();
            e.setTerms(terms);

            return e;
        }

        public long getValue() { return this.value; }
        public void setValue(long value) { this.value = value; }

        public List<Integer> getTerms() { return this.terms; }
        public void setTerms(List<Integer> terms) { this.terms = terms; }
        public void addTerm(Integer term) { this.terms.add(term); }
        public Integer getTerm(int idx) { return this.terms.get(idx); }

        public List<Character> getOperators() { return this.operators; }
        public void setOperators(List<Character> operators) { this.operators = operators; }
        public void addOperator(Character operator) { this.operators.add(operator); }
        public Character getOperator(int idx) { return this.operators.get(idx); }
    }
}
