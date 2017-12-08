# Day 5

## Problem Statement 1 (abridged)
```
The message includes a list of the offsets for each jump. Jumps are relative: -1 moves to the previous instruction, and 2 skips the next one. Start at the first instruction in the list. The goal is to follow the jumps until one leads outside the list.
After each jump, the offset of that instruction increases by 1.
```
## Approach 1
- Build a helper function that can increment a list at a certain position
- Recursively iterate over the list, performing the actions as described, straightforward

## Problem Statement 2 (abridged)
```
Now, the jumps are even stranger: after each jump, if the offset was three or more, instead decrease it by 1. Otherwise, increase it by 1 as before.
```

## Approach 2
- Adjust the function by adding a conditional operator for jumps longer than three
- Wonder why `utop` seems to hang
- Search for a logic error causing an endless loop
- Decide to let the program run for a while
- Spend some time building a reference implementation in Kotlin
- Get the correct result from the Kotlin implementation in less than 2 seconds
- Wait another few minutes, until OCaml implementation terminates with the same result. 🤔

### A note
I am aware that my OCaml implementation only uses immutable lists instead of mutable arrays. This is somewhat intentional as I try to get the maximum practice benefit for my university class in which we also cover OCaml. There, we have not worked with arrays yet at all. I might revisit this exercise at a later point when and if arrays are covered at some point, or when I've had the time to dive deeper into this subject on my own
