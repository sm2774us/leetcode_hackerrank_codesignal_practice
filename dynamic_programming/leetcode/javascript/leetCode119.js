// Here's my solution using JavaScript. The idea is pretty simple: since every next row is generated using previous row, 
// simply update the row by adding current number to the number before current (skip first and last elements as they will 
// always have 1). A simple solution would require us to keep the previous row in memory and generate the next row based on 
// last row, then replace the previous row for each next step till desired index is reached. However, there is, in fact, 
// no need to keep the last row in memory since we can simply update the row from the back (if you update values from the 
// start, you lose original previous row values you need to calculate following values, however when starting from the back, 
// you don't rewrite elements before you access them).
//
// This makes the code rather simple:
// Time complexity of this solution is O(n^2) (quadratic).
// TC: O(N^2) ; SC: O(N^2)
let getRow = function(rowIndex) {
    let row = [1];
    for (let i = 1; i <= rowIndex; ++i) {
        row[row.length] = 1;
        for (let j = i - 1; j >= 1; --j) {
            row[j] = row[j - 1] + row[j];
        }
    }
    return row;
};

// Now, quadratic time is not good enough to beat 100% submissions. 
// So I googled for a different solution and found StackOverflow discussion on getting a row of a Pascal's triangle 
// using pure math. It is pure awesomeness - you can calculate pascal triangle's row using math, 
// without relying on previous rows. Original solution was written in Python, here's my javascript version:

// Math-Based Solution: "n-choose-k formula ( or Combination Formula )"
// We calculate C(n, k) by this relation: C(n, k) = C(n, k-1) * ((n+1-k) / k), and C(n, 0) = 1

// Time complexity: O(n)
// TC: O(N) ; SC: O(1)
let getRow = function(rowIndex) {
    let row = [1];
    for (let i = 0; i < rowIndex; ++i) {
        row[row.length] = row[i] * (rowIndex - i) / (i + 1);
    }
    return row;
};

// We can optimize the calculations for larger numbers in the following way:
// This way we calculate only the first half of the pascal's triangle row and simply copy over the remaining part of the array. Time complexity remains O(n) but we do less calculations with large numbers. 
// This version is inspired by Harvey's comment on StackOverflow => https://stackoverflow.com/questions/15580291/how-to-efficiently-calculate-a-row-in-pascals-triangle#comment91191312_15580400
// (originally written in python and converted into JS by me).
let getRow = function(rowIndex) {
    let row = [1];
    if (rowIndex === 0) return row;
    for (let i = 0; i < Math.floor(rowIndex / 2); ++i) {
        row[row.length] = row[i] * (rowIndex - i) / (i + 1);
    }
    for (let i = Math.floor((rowIndex - 1) / 2); i >= 0; --i) {
        row.push(row[i]);
    }
    return row;
};

