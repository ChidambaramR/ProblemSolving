package Miscellaneous.hamming_distance;

/**
 * Hamming distance
 * Total hamming distance
 *
 * https://leetcode.com/problems/hamming-distance/
 *
 */
public class hamming_distance {
    int hammingDistance(int x, int y){
        int hamming_code = x ^ y;
        int result = 0;

        while (hamming_code != 0) {
            if ((hamming_code & 1) == 1)
                result++;

            hamming_code = hamming_code >> 1;
        }

        return result;
    }

    /**
     *
     * @param nums - Input array containing the numbers
     * @return - Total hamming distance
     *
     * The brute force method will be to take each distinct combination,
     * eg [2, 14, 10] = (2,14), (2,10), (14,10), find the hamming distance
     * for each pair and do a sum.
     * Instead if we take each bit of all the numbers, we can see how many
     * 0's and how many 1's are there. When we multiply them, for that bit
     * position in each number we find what is the hamming distance.
     * Similarly we do for all the other bits and add the cumulative sum.
     * By multiplying the no of 0's and no of 1's, we are effectively getting
     * the hamming distance for that bit in all the numbers. We dont count the
     * reverse into calculation (for ex, (2, 14) and (14, 2)) because we only
     * multiply the number of 0's and number of 1's. We dont do the vice versa.
     */
    int total_hamming_distance(int[] nums) {
        if (nums.length == 0 || nums.length == 1)
            return 0;

        int num_zeros, num_ones;
        int bits, i;
        int total_hd = 0; // total hamming distance

        for (bits = 0; bits < 32; bits++) {
            num_zeros = 0;
            num_ones = 0;

            for (i = 0; i < nums.length; i++) {
                if ((nums[i] & 1) == 1)
                    num_ones++;
                else
                    num_zeros++;

                nums[i] = nums[i] >> 1;
            }

            total_hd += (num_ones * num_zeros);
        }

        return total_hd;
    }

    public static void main(String[] args) {
        hamming_distance obj = new hamming_distance();

        System.out.println("Distance for 8 and 2 is " + obj.hammingDistance(2, 8));

        int[] nums = {5, 9, 1, 0, 9, 5};
        System.out.println("Total hamming distance for nums = " + obj.total_hamming_distance(nums));
    }
}


