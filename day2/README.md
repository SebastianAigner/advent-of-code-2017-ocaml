# Day 2

## Problem Statement 1 (abridged)

```
The spreadsheet consists of rows of apparently-random numbers. To make sure the recovery process is on the right track, they need you to calculate the spreadsheet's checksum. For each row, determine the difference between the largest value and the smallest value; the checksum is the sum of all of these differences.
```

## Approach 1

- Write a whole bunch of helper functions that also exist in the standard library to get even more comfortable with OCaml syntax and functional thinking
- Map each row to the difference between their max and min value
- Fold neatly to obtain sum

## Problem Statement 2 (abridged)

```
It sounds like the goal is to find the only two numbers in each row where one evenly divides the other - that is, where the result of the division operation is a whole number. They would like you to find those numbers on each line, divide them, and add up each line's result.
```

## Approach 2

- Implement method to find divisors in a single row
    - We use a helper method `headify` to move the current element to the front of our list, this way we have it easily accessible via `h::t` notation.
    - We check recursively for each value whether it has a divider in the rest of the list, and if so return it.
- Flatten, map and fold together the final result