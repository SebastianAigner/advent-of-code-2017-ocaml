# Day 1

## Problem Statement 1 (abridged)
```
The captcha requires you to review a sequence of digits (your puzzle input) and find the sum of all digits that match the next digit in the list. The list is circular, so the digit after the last digit is the first digit in the list.
```

## Approach 1

- Learn how to write an input parser in OCaml 🙈
- Do some wild conversion between Strings and Characters to get actual digits
- Since the list is circular, a possible solution is:
    - Create a shifted list where the last element is moved to the first position
    - (Build your own zip operator for fun)
    - Zip the two lists so you have tuples
    - The only tuples of interest are those where the entries are the same `(x,x)`
- Sum it up!

## Problem Statement 2 (Abridged)

```
Now, instead of considering the next digit, it wants you to consider the digit halfway around the circular list. That is, if your list contains 10 items, only include a digit in your sum if the digit 10/2 = 5 steps forward matches it. Fortunately, your list has an even number of elements.
```

## Approach 2
- Celebrate that you went with a route that already had to implement a shift operation 🎉
- Provide function to shift by arbitrary number instead of just by `1`
- Sum up the new tuples!