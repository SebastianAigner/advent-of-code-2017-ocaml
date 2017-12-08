# Day 4

## Problem Statement 1 (abridged)
```
To ensure security, a valid passphrase must contain no duplicate words.
```

## Approach 1
- For each passphrase:
    - Calculate the number of occurences of each word in the passphrase
    - If a word occurs more than one time, it's a duplicate

## Problem Statement 2 (abridged)
```
Now, a valid passphrase must contain no two words that are anagrams of each other.
```

## Approach 2

- To find an anagram, take each word, sort its characters, and check for duplicates.
- My implementation of this is a little messy, but it gets the job done