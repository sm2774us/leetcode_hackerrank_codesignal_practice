/**
 *  DP Solution: Tabulation (Bottom-Up)
 *
 *  TC: O(N^2)
 *  SC: O(N^2)
 */
/**
 * @param {number[][]} A
 * @return {number}
 */
/**
 * 
 * Synopsis:
 *
 * Start from the end E and go down-right until the start S (for me, it is more natural to start at 0,0 and end at N,N). Create two DP matrices, S for the sum, and P for path-count-per-sum. These matrices cells are offset by 1 compared to A's cells in order to build a recurrence relation upon themselves for a bottom-up solution. Thus S[i][j] is the maximum sum from A[0..i)[0..j) (note: both i and j are non-inclusive) and P[i][j] is the path count for the maximum sum from A[0..i)[0..j) (note: both i and j are non-inclusive).
 *
 * For each cell i,j find the maximum sum from adjacent cells u, v which are:
 *
 *   1. above-and-to-the-left
 *   2. above
 *   3. to-the-left
 *
 * The sum of the current i, j cell is found by adding the corresponding A value (which is offset by 1, and thus located at A[i - 1][j - 1]) to the previous adjacent cell's sum, ie. S[u][v] if a path exists to u,v. Otherwise if a path does not exist to u, v, then the sum is 0. There are 3 use-cases to consider for each sum calculated:
 *
 *   1. the sum coming from path u, v is less-than the current sum S[i][j]
 *   2. the sum coming from path u, v is equal-to the current sum S[i][j]
 *   3. the sum coming from path u, v is greater-than the current sum S[i][j]
 *
 *   - Case 1: irrelevant, since we only care about maximum sums and their path counts.
 *   - Case 2: the path count to i, j is incremented by the path count from u, v (modulus 1e9 + 7)
 *   - Case 3: a new max sum is found, store this max sum and set the path count to i, j to the path count from u, v
 *
 * Skip calculating the sum for cells which are blocked, ie. A[i - 1][j - 1] == 'X. Return the maximum sum S[N][N] and path count P[N][N] for A[0..N)[0..N).
 *
 * TC: O(N^2)
 * SC: O(N^2)
 *
 * Note: strings are immutable in javascript, so I added an additional check for the start/end (ie. S and E) when calculating the sum,
 * whereas in C++, I simply set A[0][0] = A[N - 1][N - 1] = 0.
 */
let pathsWithMaxScore = (A, dirs = [[-1,-1],[-1,0],[0,-1]], mod = 1e9 + 7) => {
    let N = A.length;
    let S = [...Array(N + 1)].map(row => Array(N + 1).fill(0)),
        P = [...Array(N + 1)].map(row => Array(N + 1).fill(0));
    P[0][0] = 1;
    for (let i = 1; i <= N; ++i) {
        for (let j = 1; j <= N; ++j) {
            if (A[i - 1][j - 1] == 'X')
                continue;
            for (let d of dirs) {
                let u = i + d[0],
                    v = j + d[1];
                let sum = !P[u][v] ? 0 : S[u][v] + (i == 1 && j == 1 ? 0 : i == N && j == N ? 0 : A[i - 1].charCodeAt(j - 1) - '0'.charCodeAt(0));
                if (S[i][j] == sum)
                    P[i][j] = (P[i][j] + P[u][v]) % mod;
                if (S[i][j] < sum)
                    S[i][j] = sum,
                    P[i][j] = P[u][v];
            }
        }
    }
    return [S[N][N], P[N][N]];
};