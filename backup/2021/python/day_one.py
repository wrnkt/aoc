from typing import List
from collections import deque


def read_in_file(f_name: str):
    values = []
    with open(f_name) as f:
        for line in f:
            values.append(int(line))
    return values


def count_increases(values: List[int]):
    prev_value = None
    count = 0
    for value in values:
        if prev_value is not None and value > prev_value:
            count += 1
        prev_value = value
    return count


def count_sliding_increases(values: List[int]):
    window = deque([])
    previous_sum = None
    for value in values:
        if len(window) < 3:
            window.append(value)
        if previous_sum is not None and len(window) == 3:
            sum = sum(window)


def main():
    values = read_in_file("../input/one.txt")
    print(count_increases(values))


if __name__ == "__main__":
    main()
