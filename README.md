# <img src="https://miro.medium.com/v2/resize:fit:640/format:webp/1*gzEyNsTYNHvqLoatYCZqqQ.png" width="70" height="60" /> Greedy-Programming-Project <br>
##### <i> Lohit Bhambri (lohit.bhambri@ufl.edu) </i>
##### <i> Sharath Bhushan Podila (spodila@ufl.edu) </i>
<p>COT5405 Analysis of Algorithm (AOA)</p>

### Strategy 1
<i>Algorithm</i>:<br>
```aidl
Algorithm strat1(timelines, n, m)
Input: 
    timelines: a list of m pairs of integers representing the painting timelines for each house
    n: an integer representing the number of days available for painting
    m: an integer representing the number of houses to paint
Output: 
    A string containing the sequence of house numbers in the order they were painted

Initialize an empty string housePaintString
Initialize a queue houseQueue to store the painting timelines for each house
For each painting timeline in timelines, add the house number to the end of the list, and add the list to houseQueue
For each day pDay from 1 to n do the following:
    a. While houseQueue is not empty do the following:
        i. Peek the first element of houseQueue and assign it to peekHouse
        ii. If the painting timeline of peekHouse covers pDay do the following:
            1. Remove peekHouse from houseQueue
            2. Append the house number of peekHouse to housePaintString
            3. Append a space to housePaintString
            4. Break the loop
        iii. Else, if the painting timeline of peekHouse has already passed pDay remove it from houseQueue and continue the loop
        iv. Else, if the painting timeline of peekHouse starts after pDay break the loop
    b. If houseQueue is empty, break the loop
Return housePaintString with leading and trailing whitespaces removed
```


### Strategy 2
<i>Algorithm:</i><br>
```aidl
Algorithm strat2(timelines, n, m)
Input: 
    timelines: a list of m pairs of integers representing the painting timelines for each house
    n: an integer representing the number of days available for painting
    m: an integer representing the number of houses to paint
Output: 
    A string containing the sequence of house numbers in the order they were painted

Initialize an empty string housePaintString
Initialize a priority queue latestHouses to store the painting timelines for each house, sorted by decreasing start day and increasing end day
Initialize housePtr to 0
For each day pDay from 1 to n do the following:
    a. While housePtr < m do the following:
        i. Peek the house timeline at index housePtr in timelines and assign it to pair
        ii. If the start day of pair equals pDay do the following:
            Add the index of the house (housePtr+1) to the end of pair
            Add pair to latestHouses
            Increment housePtr by 1
        iii. Else, break the loop
    b. If latestHouses is empty, continue the loop
    c. While latestHouses is not empty do the following:
        i. Peek the first element of latestHouses and assign it to peekHouse
        ii. If pDay is between the start and end days of peekHouse do the following:
            Remove peekHouse from latestHouses
            Append the house number of peekHouse to housePaintString
            Append a space to housePaintString
            Break the loop
        iii. Else, if pDay is greater than the end day of peekHouse remove it from latestHouses and continue the loop
        iv. Else, if pDay is less than the start day of peekHouse, break the loop
Return housePaintString with leading and trailing whitespaces removed
```


### Strategy 3
<i>Algorithm</i>:
```aidl
Algorithm strat3(timelines, n, m)
Input: 
    timelines: a list of m pairs of integers representing the painting timelines for each house 
    n: an integer representing the number of days available for painting 
    m: an integer representing the number of houses to paint
Output: 
    A string containing the sequence of house numbers in the order they were painted

Initialize an empty string housePaintString
Initialize a priority queue latestHouses to store the painting timelines for each house
The priority queue latestHouses is sorted based on the duration of the painting timeline, and if the duration is the same, based on the end date of the painting timeline
Initialize housePtr to 0
For each day pDay from 1 to n do the following:
    a. If pDay is greater than 1, do the following:
        i. Initialize an empty list previousBuffer
        ii. While latestHouses is not empty do the following:
            Remove the head of latestHouses and assign it to priorityQueueHead
            Decrement the duration of priorityQueueHead by 1
            If the duration of priorityQueueHead is still greater than or equal to 0, add it to previousBuffer and continue the loop
        iii. For each painting timeline in previousBuffer, add it back to latestHouses
    b. While housePtr is less than m and the start date of the painting timeline at housePtr is equal to pDay do the following:
        i. Create a list pair consisting of the painting timeline at housePtr, the duration of the painting timeline, and the house number
        ii. Add pair to latestHouses
        iii. Increment housePtr by 1
    c. If latestHouses is empty, continue to the next iteration of the loop
    d. While latestHouses is not empty do the following:
        i. Peek the head of latestHouses and assign it to peekHouse
        ii. If the painting timeline of peekHouse covers pDay do the following:
            Remove peekHouse from latestHouses
            Append the house number of peekHouse to housePaintString
            Append a space to housePaintString
            Break the loop
        iii. Else, if the painting timeline of peekHouse has already passed pDay remove it from latestHouses and continue the loop
        iv. Else, if the painting timeline of peekHouse starts after pDay break the loop
Return housePaintString with leading and trailing whitespaces removed
```


### Strategy 4
<i>Algorithm</i>:
```aidl
Algorithm strat4(timelines, n, m)
Input: 
    timelines: a list of m pairs of integers representing the painting timelines for each house
    n: an integer representing the number of days available for painting
    m: an integer representing the number of houses to paint
Output: 
    A string containing the sequence of house numbers in the order they were painted

Initialize an empty string housePaintString
Initialize a priority queue latestHouses to store the painting timelines for each house, with a custom comparator function that compares based on the end date and then the start date of the painting timeline
Initialize housePtr to 0
For each day pDay from 1 to n do the following:
    a. While housePtr is less than m and the start date of the painting timeline at housePtr is equal to pDay, do the following:
        i. Add the house number (housePtr+1) to the end of the painting timeline at housePtr
        ii. Add the painting timeline at housePtr to latestHouses
        iii. Increment housePtr by 1
    b. If latestHouses is empty, continue the loop
    c. While latestHouses is not empty, do the following:
        i. Peek the first element of latestHouses and assign it to peekHouse
        ii. If the painting timeline of peekHouse covers pDay do the following:
            1. Remove peekHouse from latestHouses
            2. Append the house number of peekHouse to housePaintString
            3. Append a space to housePaintString
            4. Break the loop
        iii. Else, if the painting timeline of peekHouse has already passed pDay remove it from latestHouses and continue the loop
        iv. Else, if the painting timeline of peekHouse starts after pDay break the loop
Return housePaintString with leading and trailing whitespaces removed
```


### Strategy Bonus
<i>Algorithm</i>:
```aidl
Algorithm stratBonus(timelines, n, m)
Input:
    timelines: a list of m pairs of integers representing the painting timelines for each house
    n: an integer representing the number of days available for painting
    m: an integer representing the number of houses to paint
Output: 
    A string containing the sequence of house numbers in the order they were painted

Initialize an empty string housePaintString to store the sequence of house numbers in the order they were painted.
Initialize a priority queue latestHouses to store the painting timelines for each house in the order of their completion time, with the house that finishes painting first being at the front of the queue. Each element in the queue is an ArrayList with three integers: the start time, the end time, and the house number.
Initialize a variable housePtr to keep track of the current house being processed in timelines.

For each day pDay from 1 to n do the following:
    a. While housePtr is less than m and the start time of the house at housePtr in timelines is equal to pDay, do the following:
        i. Create an ArrayList pair containing the start time, end time, and house number.
        ii. Add the house number to pair and add pair to latestHouses.
        iii. Increment housePtr.
    b. If latestHouses is empty and housePtr is less than m:
        Set pDay to the start time of the house at housePtr in timelines minus 1, 
        Continue to the next iteration of the loop.
    c. While latestHouses is not empty, do the following:
            i. Peek the first element of latestHouses and assign it to peekHouse.
            ii. If pDay is between the start time and end time (inclusive) of peekHouse, do the following:
                1. Remove peekHouse from latestHouses.
                2. Append the house number of peekHouse to housePaintString.
                3. Append a space to housePaintString.
                4. Break the loop.
            iii. Else, if the end time of peekHouse is less than pDay, remove peekHouse from latestHouses and continue the loop.
            iv. Else, if the start time of peekHouse is greater than pDay, break the loop.
Return housePaintString with leading and trailing whitespaces removed.
```
