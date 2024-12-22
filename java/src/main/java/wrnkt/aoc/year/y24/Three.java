package wrnkt.aoc.year.y24;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import aoc.framework.Day;

public class Three extends Day {

    @Override
    public void solution(BufferedReader reader) {
        List<Expression> exprs = readData(reader);
        print(String.format("%d expressions parsed\n", exprs.size()));

        partOne(exprs);
        partTwo(exprs);
    }

    private void partOne(List<Expression> exprs) {
        print("Part One:");
        try {
            int sum = exprs.stream()
                        .mapToInt(this::eval)
                        .sum();
            print(String.format("sum of multiplication expressions: %d", sum));
        } catch (Exception e) {
            log.error("Failed to evaluate expressions", e);
        }
    }

    private void partTwo(List<Expression> exprs) {
        print("Part Two:");
        int sum = 0;
        boolean isEvaluated = true;
        for (Expression expr : exprs) {
            if (expr.op == Operation.DO) isEvaluated = true;
            if (expr.op == Operation.DONT) isEvaluated = false;
            if (isEvaluated) sum += eval(expr);
        }
        print(String.format("sum of multiplication expressions: %d", sum));
    }

    private int eval(Expression expr) {
        if (expr.op.isEvaluatable()) {
            return expr.op.eval(expr.a, expr.b);
        }
        return 0;
    }

    public List<Expression> readData(BufferedReader reader) {
        List<Expression> expressions = new ArrayList<>();
        String line = null;

        Pattern pattern = Pattern.compile("(?:mul\\(\\d+,\\d+\\))|(?:do\\(\\))|(?:don't\\(\\))");
        try {
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    String expr = matcher.group();
                    if (expr.startsWith("mul")) {
                        int a = Integer.parseInt(expr.substring(expr.indexOf('(')+1, expr.indexOf(',')));
                        int b = Integer.parseInt(expr.substring(expr.indexOf(',')+1, expr.indexOf(')')));
                        expressions.add(new Expression(Operation.MULT, a, b));
                    } else if (expr.startsWith("do()")) {
                        expressions.add(new Expression(Operation.DO, 0, 0));
                    } else if (expr.startsWith("don't()")) {
                        expressions.add(new Expression(Operation.DONT, 0, 0));
                    }
                }
            }
        } catch (IOException e) {
            log.error("Failed to read in data", e);
        }

        return expressions;
    }

    class Data {
        public List<Expression> expressions;

        public Data(List<Expression> expressions) {
            this.expressions = expressions;
        }
    }

    class Expression {
        public final int a;
        public final int b;
        public final Operation op;

        public Expression(Operation op, int a, int b) {
            this.op = op;
            this.a = a;
            this.b = b;
        }
    }

    enum Operation {
        MULT {

            @Override
            int eval(Integer... args) {
                return Stream.of(args)
                        .reduce(1, (product, num) -> product * num);
            }

            @Override
            boolean isEvaluatable() {
                return true;
            }
        },
        DO {
            @Override
            int eval(Integer... args) {
                return 0;
            }

            @Override
            boolean isEvaluatable() {
                return false;
            }
        },
        DONT {
            @Override
            int eval(Integer... args) {
                return 0;
            }

            @Override
            boolean isEvaluatable() {
                return false;
            }
        };

        abstract int eval(Integer... args);
        abstract boolean isEvaluatable();

    }

}

