# Day 3

## Problem Statement 1 (abridged)
```
Each square on the grid is allocated in a spiral pattern starting at a location marked 1 and then counting up while spiraling outward.
How many steps are required to carry the data from the square identified in your puzzle input all the way to the access port?
```

## Approach 1
- Rather than implement this with OCaml, think about the Problem
    - Play around in Excel for a while
    - Visually recognize the patterns that the spiral values follow (especially the bottom-right corner) and their correlation to the width of the current square as well as the Manhattan distance to the origin
    - Do some basic maths
    - Enter your manually calculated solution

## Problem Statement 2 (abridged)
```
Square 1 starts with the value 1.
Square 2 has only one adjacent filled square (with value 1), so it also stores 1.
Square 3 has both of the above squares as neighbors and stores the sum of their values, 2.
Square 4 has all three of the aforementioned squares as neighbors and stores the sum of their values, 4.
Square 5 only has the first and fourth squares as neighbors, so it gets the value 5.
What is the first value written that is larger than your puzzle input?
```
## Approach 2
- Realize that this is a common sequence
- Open up the On-Line Encylcopedia of Integer Sequences (OEIS) at https://oeis.org
- Enter the first few values
- Check the lookup table for the first value that's bigger than our input

_(Note, while this isn't specifically coding, I think it's still just fine if we call it the Advent of Problem Solving 😉)_