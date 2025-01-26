package wrnkt.aoc.year.y24;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import aoc.framework.Day;

public class Five extends Day {

    @Override
    public void solution(BufferedReader reader) {
        Data data = readData(reader);
        print(String.format("Read %d entries", data.getEntries().size()));
        print(String.format("Read %d updates", data.getUpdates().size()));
        partOne(data);
    }

    private void partOne(Data data) {
        print("Part One:");

        List<List<Integer>> orderedUpdates = data.updates.stream()
                            .filter((update) -> isOrdered(update, data.entries))
                            .toList();
        
        print("Correctly-ordered updates:");
        for (List<Integer> update : orderedUpdates) {
            print(update.stream()
                  .map(String::valueOf)
                  .collect(Collectors.joining(", ")));
        }
    }

    private boolean isOrdered(List<Integer> update, List<Data.Entry> entries) {
        for (int idx = 0+1; idx < update.size()-1 ; idx++) {
            Integer number = update.get(idx);
            List<Integer> validSuccessors = entries.stream()
                                    .filter((entry -> entry.before == number))
                                    .map((entry) -> entry.after)
                                    .toList();
            List<Integer> validPreceders = entries.stream()
                                    .filter((entry) -> entry.after == number)
                                    .map((entry) -> entry.before)
                                    .toList();

            List<Integer> invalidPreceders = validSuccessors;
            List<Integer> invalidSuccessors = validPreceders;

            for (int afterIdx = (idx+1); afterIdx < update.size(); afterIdx++) {
                if (invalidSuccessors.contains(update.get(afterIdx))) return false;
            }

            for (int beforeIdx = (idx-1); beforeIdx >= 0; beforeIdx--) {
                if (invalidPreceders.contains(update.get(beforeIdx))) return false;
            }
        }
        return true;
    }

    private Data readData(BufferedReader reader) {
        var parser = new Parser(new Data());
        try {

            String line = null;
            while ((line = reader.readLine()) != null) {
                parser.process(line);
            }
        } catch (Exception e) {
            log.error("Failed to read data ", e);
            return null;
        }
        return parser.getData();
    }
    
    class Parser {

        Processing processing;

        Data data;

        public Parser(Data data) {
            this.data = data;
            this.processing = Processing.ENTRIES;
        }

        public void process(String line) {
            if (line.isBlank()) {
                processing = Processing.UPDATES;
                return;
            }
            if (processing == Processing.ENTRIES) {
                processEntry(line);;
            } else if (processing == Processing.UPDATES) {
                processUpdate(line);;
            }
        }

        public void processEntry(String line) {
            String[] split = line.split("|");
            data.getEntries().add(
                new Data.Entry(
                    Integer.parseInt(split[0]), 
                    Integer.parseInt(split[1])
            ));
        }

        public void processUpdate(String line) {
            String[] split = line.split(",");
            var update = Arrays.stream(split)
                                .map(Integer::parseInt)
                                .toList();
            data.getUpdates().add(update);
        }

        enum Processing { ENTRIES, UPDATES }

        public Data getData() { return data; }
    }


    class Data {

        public List<Entry> entries = new ArrayList<>(); 

        public List<List<Integer>> updates = new ArrayList<>();

        public Data() {}

        public void setEntries(List<Entry> entries) { this.entries = entries; }
        public List<Entry> getEntries() { return this.entries; }

        public void setUpdates(List<List<Integer>> updates) { this.updates = updates; }
        public List<List<Integer>> getUpdates() { return this.updates; }

        record Entry(int before, int after) {}

    }

}
