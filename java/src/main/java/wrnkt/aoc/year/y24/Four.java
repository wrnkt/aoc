package wrnkt.aoc.year.y24;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import aoc.framework.Day;

public class Four extends Day {
    @Override
    public void solution(BufferedReader reader) {
        List<List<Character>> table = readData(reader);

        WordSearch wordSearch = new WordSearch(table);
        wordSearch.print();

        try {
            var retreived = wordSearch.getStringByCoords(0, 0, 0, 5);
            print(retreived.get());
        } catch (Exception e) {
            log.error("{}", e);
        }
    }

    private void printTable(List<List<Character>> table) {
        for (List<Character> row : table) {
            for (Character ch : row) {
                System.out.print(ch + " ");
            }
            System.out.println();
        }
    }

    private List<List<Character>> readData(BufferedReader reader) {
        List<List<Character>> table = new ArrayList<>();

        try {
            String line = null;
            while((line = reader.readLine()) != null) {
                List<Character> row = new ArrayList<>();
                for (int col = 0; col < line.length(); col++) {
                    row.add(line.charAt(col));
                }
                table.add(row);
            }
        } catch (Exception e) {

        }
        return table;
    }
}

class WordSearch {
    private List<List<Character>> wordSearch;

    public WordSearch(List<List<Character>> wordSearch) {
        this.wordSearch = wordSearch;
    }

    public int count(String word) {
        int count = 0;
        for (int deltaY = 0; deltaY < wordSearch.get(0).size(); deltaY++) {
            for (int deltaX = 0; deltaX < wordSearch.size(); deltaX++) {
                if (foundHorizontal(word, deltaX, deltaY))          count += 1;
                if (foundHorizontalReverse(word, deltaX, deltaY))   count += 1;
                if (foundVertical(word, deltaX, deltaY))            count += 1;
                if (foundVerticalReverse(word, deltaX, deltaY))     count += 1;
                if (foundDiagonal(word, deltaX, deltaY))            count += 1;
                if (foundDiagonalReverse(word, deltaX, deltaY))     count += 1;
            }
        }
        return count;
    }

    private boolean foundHorizontal(String word, int x, int y) {
        Optional<String> foundWord = getStringByCoords(x, y, (x + word.length()), y);
        if (foundWord.isEmpty()) return false;

        if (word.equals(foundWord.get())) return true;
        return false;
    }

    private boolean foundHorizontalReverse(String word, int x, int y) {
        Optional<String> foundWord = getStringByCoords((x - word.length()), y, x, y);
        if (foundWord.isEmpty()) return false;

        if (word.equals(foundWord.get())) return true;
        return false;
    }

    private boolean foundVertical(String word, int x, int y) {
        Optional<String> foundWord = getStringByCoords(x, y, x, (y + word.length()));
        if (foundWord.isEmpty()) return false;

        if (word.equals(foundWord.get())) return true;
        return false;
    }

    private boolean foundVerticalReverse(String word, int x, int y) {
        Optional<String> foundWord = getStringByCoords(x, (y - word.length()), x, y);
        if (foundWord.isEmpty()) return false;

        if (word.equals(foundWord.get())) return true;
        return false;
    }

    private boolean foundDiagonal(String word, int x, int y) {
        // if (foundWord.isEmpty()) return false;
        // if (word.equals(foundWord.get())) return true;
        return false;
    }

    private boolean foundDiagonalReverse(String word, int x, int y) {
        // if (foundWord.isEmpty()) return false;
        // if (word.equals(foundWord.get())) return true;
        return false;
    }

    public Optional<String> getStringByCoords(int x1, int y1, int x2, int y2) {
        try {
            validateCoordinate(x1, y1);
            validateCoordinate(x2, y2);
            validateCoordinatePair(x1, y1, x2, y2);
        } catch (SearchCoordinateException e) {
            System.out.println(e.getLocalizedMessage());
            return Optional.empty();
        }

        StringBuilder sb = new StringBuilder();
        if (x1 == x2) {
            for (int deltaY = y1; deltaY < y2; deltaY++) {
                sb.append(wordSearch.get(deltaY).get(x1));
            }
        } else if (y1 == y2) {
            for (int deltaX = x1; deltaX < x2; deltaX++) {
                sb.append(wordSearch.get(y1).get(deltaX));
            }
        } else if (is45Deg(x1, y1, x2, y2)) {
            for (int deltaY = y1; deltaY < y2; deltaY++) {
                for (int deltaX = x1; deltaX < x2; deltaX++) {
                    sb.append(wordSearch.get(deltaY).get(deltaX));
                }
            }
        }
        return Optional.of(sb.toString());
    }

    private boolean is45Deg(int x1, int y1, int x2, int y2) {
        int dX = x2 - x1;
        int dY = y2 - y1;
        return (Math.abs(dX) == Math.abs(dY));
    }

    private void validateCoordinatePair(int x1, int y1, int x2, int y2) throws SearchCoordinateException {
        if (!is45Deg(x1, y1, x2, y2)) {
            if ((x1 != x2) && (y1 != y2)) {
                throw new SearchCoordinateException("two points must describe a horizontal, vertical, or 45deg line");
            }
        }
    }

    private void validateCoordinate(int x, int y) throws SearchCoordinateException {
        if (x < 0) 
            throw new SearchCoordinateException("x is less than 0");
        if (x > wordSearch.size())
            throw new SearchCoordinateException(String.format("x is greater than %d", wordSearch.size()));
        if (y < 0) 
            throw new SearchCoordinateException("y is less than 0");
        if (y > wordSearch.get(0).size())
            throw new SearchCoordinateException(String.format("y is greater than %d", wordSearch.get(0).size()));
    }


    // PRINT

    public void print() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (List<Character> row : wordSearch) {
            for (Character ch : row) {
                sb.append(ch);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}

class SearchCoordinateException extends Exception {
    public SearchCoordinateException(String msg) {
        super(msg);
    }
}
