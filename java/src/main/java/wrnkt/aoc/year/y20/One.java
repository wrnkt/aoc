package wrnkt.aoc.year.y20;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import aoc.framework.Day;
import aoc.framework.util.Pair;

import static aoc.common.io.BufferAccess.listInts;

public class One extends Day {
    public static final Logger log = LoggerFactory.getLogger(One.class);

    @Override
    public void solution(BufferedReader br) {
        int sum = 2020;
        List<Integer> ns = listInts(br);

        try {
            Pair<Integer, Integer> sumPair = findPairWithSum(ns, sum);
            int a = sumPair.getFirst();
            int b = sumPair.getSecond();

            log.info("Found pair: {} + {} = {}", a, b, sum);

            Integer product = product(sumPair);
            log.info("product: {} * {} = {}", a, b, product);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Find the product of a {@link Pair} of operands.
     */
    protected Integer product(Pair<Integer, Integer> operands) {
        return operands.getFirst() * operands.getSecond();
    }

    /**
     *  Find a pair of numbers adding up to {@code sum}.
     */
    public Pair<Integer, Integer> findPairWithSum(List<Integer> src, Integer sum) {
        Set<Integer> seen = new HashSet<>();

        for (Integer i : src) {
            Integer needed = sum - i;

            if (seen.contains(needed)) 
                return Pair.of(i, needed);

            seen.add(i);
        }

        String msg =  String.format("Traversed %d ints, none sum to %d", src.size(), sum);
        throw new RuntimeException(msg);
    }

    public Pair<Integer, Integer> twoSumPair(List<Integer> src, Integer sum) {
        int[] srcArr = src.stream()
            .mapToInt(Integer::intValue)
            .toArray();

        List<List<Integer>> sums = findNsum(srcArr, 2, sum);

        if (sums.size() > 1) {
            String msg = String.format(
                "Multiple operands adding up to %d sum were found. Defaulting to first match.", sums.size());
            log.warn(msg);
            return null;
        } else if (sums.size() == 0) {
            String msg = String.format(
                "No operands summing to %d were found", sums.size());
            log.warn(msg);
            return null;
        }

        List<Integer> foundOperands = sums.get(0);
        return Pair.of(foundOperands.get(0), foundOperands.get(1));
    }


    /* ======================================================== */

    public static List<List<Integer>> findNsum(int[] nums, int n, int target) {
        Arrays.sort(nums);
        return kSum(nums, 0, n, target);
    }

    private static List<List<Integer>> kSum(int[] nums, int start, int k, long target) {
        List<List<Integer>> res = new ArrayList<>();

        if (start >= nums.length) return res;

        // BASE CASES
        if (k == 2) return twoSum(nums, start, target);

        if (k == 3) return threeSum(nums, start, target);

        if (k == 4) return fourSum(nums, start, target);

        // GENERIC CASE (k >= 5)
        for (int i = start; i <= nums.length - k; i++) {

            if (i > start && nums[i] == nums[i - 1]) continue;

            // pruning
            long min = (long) nums[i] + (long) (k - 1) * nums[i + 1];
            long max = (long) nums[i] + (long) (k - 1) * nums[nums.length - 1];

            if (target < min) break;
            if (target > max) continue;

            for (List<Integer> subset : kSum(nums, i + 1, k - 1, target - nums[i])) {
                List<Integer> comb = new ArrayList<>();
                comb.add(nums[i]);
                comb.addAll(subset);
                res.add(comb);
            }
        }

        return res;
    }

    private static List<List<Integer>> twoSum(int[] nums, int start, long target) {
        List<List<Integer>> res = new ArrayList<>();
        Set<Integer> seen = new HashSet<>();

        for (int i = start; i < nums.length; i++) {
            int need = (int) (target - nums[i]);

            if (seen.contains(need)) {
                res.add(Arrays.asList(need, nums[i]));
            }
            seen.add(nums[i]);
        }

        return res;
    }


    private static List<List<Integer>> threeSum(int[] nums, int start, long target) {
        List<List<Integer>> res = new ArrayList<>();

        for (int i = start; i < nums.length - 2; i++) {
            if (i > start && nums[i] == nums[i - 1]) continue;

            int l = i + 1, r = nums.length - 1;

            while (l < r) {
                long sum = (long) nums[i] + nums[l] + nums[r];

                if (sum == target) {
                    res.add(Arrays.asList(nums[i], nums[l], nums[r]));

                    while (l < r && nums[l] == nums[l + 1]) l++;
                    while (l < r && nums[r] == nums[r - 1]) r--;

                    l++;
                    r--;

                } else if (sum < target) {
                    l++;
                } else {
                    r--;
                }
            }
        }
        return res;
    }

    private static List<List<Integer>> fourSum(int[] nums, int start, long target) {
        List<List<Integer>> res = new ArrayList<>();

        for (int i = start; i < nums.length - 3; i++) {
            if (i > start && nums[i] == nums[i - 1]) continue;

            for (int j = i + 1; j < nums.length - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;

                int l = j + 1, r = nums.length - 1;

                while (l < r) {
                    long sum = (long) nums[i] + nums[j] + nums[l] + nums[r];

                    if (sum == target) {
                        res.add(Arrays.asList(nums[i], nums[j], nums[l], nums[r]));

                        while (l < r && nums[l] == nums[l + 1]) l++;
                        while (l < r && nums[r] == nums[r - 1]) r--;

                        l++;
                        r--;

                    } else if (sum < target) {
                        l++;
                    } else {
                        r--;
                    }
                }
            }
        }

        return res;
    }


}

