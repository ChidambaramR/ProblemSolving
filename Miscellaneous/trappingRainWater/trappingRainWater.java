package Miscellaneous.trappingRainWater;

/**
 * Created by chidr on 12/9/16.
 *
 * https://leetcode.com/problems/trapping-rain-water/
 *
 * LOGIC - trap_mine:
 *
 * Easy Case: [3,1,2,0,3] Start from a pillar with non-zero height and keep going right till you
 *      hit a pillar of same or greater size. In the way keep counting the
 *      number of units of water.
 *      You start with arr[0] / 3, move right (1<3), find the difference (3-1) and
 *      see 2 units of water can be stored. Go right (2<3), find the difference (3-2)
 *      Till now we can hold 2(3-1) + 1(3-2) = 3 units. Right (0<3), so 3 + (3-0) = 6
 *      Now we find a pillar of height 3. Final answer is 6
 *
 * Harder Case: [1,3,2,1,2,1] Start from 1. Cant make progress. Goto 3.
 *      In this case we dont have another pillar of size 3. So we do all the work
 *      (3-2)+(3-1)+(3-2)+(3-1) and go out of bounds. We discard the result and start from 2 / arr[2]
 *
 * Tricky Case: [5,4,0,2] All the above mentioned logic wont work. We start from 5, do all the work
 *      (5-4)+(5-0)+(5-2), go out of bounds and discard. Start from 4, do all the work
 *      (4-0)+(4-2), go out of bounds and discard. Start from 0, fail, start from 2 and go out of bounds
 *      So the logic would return 0. The answer is 2. It can trap 2 units in this part [4,0,2]
 *      The logic would have worked right if we found another pillar of size 5. But there is a hole we
 *      can make use of. Because we know there is no pillar of size 5 or above, we can use the same logic
 *      in the reverse direction. [2,0,4,5]. The logic in first two steps will work now. We use this result
 *      and break out of the function because we have already traversed till the end.
 *
 *
 *
 * LOGIC - trap_madhu:
 *
 *
 */
public class trappingRainWater {
    public int trap_mine(int[] height) {
        // Final answer - Units of water that can be trapped
        int units = 0;

        // predicted units - If we find a bigger pillar then
        // whatever we predicted can be added to actual trapped units
        int p_units = 0;

        // maximum height of the pillar upto which water can be trapped
        int m_height = 0;

        int i = 0, j = 0;

        if(isSorted(height)) {
            // If all the pillars are in increasing height, then
            // no water can be trapped anyway
            return 0;
        }

        // Find the 1st pillar
        while(i < height.length) {
            if (height[i] != 0)
                break;

            i++;
        }

        for (; i < height.length; i++) {
            m_height = height[i];
            p_units = 0;

            for (j = i + 1; j < height.length; j++) {
                if (height[j] < m_height) {
                    p_units = p_units + (m_height - height[j]);
                    continue;
                }

                // we found a pillar on the other end
                // add to actual answer
                units = units + p_units;

                // start afresh from the new pillar
                i = j - 1;
                break;
            }

            if (j >= height.length) {
                // When recursion is called, it is NOT expected
                // to enter this function.
                int[] rev_height = new int[(j - 1) - i + 1];
                int cnt = 0;

                for (int k = j - 1; k >= i; k--)
                    rev_height[cnt++] = height[k];

                units = units + trap_mine(rev_height);
                break;
            }
        }

        return units;
    }

    public int trap(int[] height) {
        // Counts at what height we are in
        int h = 1;

        // Find the tallest pillar
        int max_height = max_height(height);

        // predicted units. If we dont find a supporting
        // pillar to hold, we discard the values
        int p_units = 0;

        // Final units that can be trapped
        int units = 0;

        int end = height.length;
        int i = 0, j = 0;

        for (h = 1; h <= max_height; h++) {
            i = 0;

            // Find the tallest pillar after the level increases
            while (i < height.length) {
                if (height[i] - h >= 0)
                    break;

                i++;
            }

            p_units = 0;

            for (j = i + 1; j < end; j++) {
                if (height[j] == 0 || (height[j] - h) < 0) {
                    p_units++;
                    continue;
                }

                units = units + p_units;
                //System.out.println("h = " + h + " units = " + units + " height " + height[j]);
                p_units = 0;
            }

        }

        return units;
    }

    private int max_height(int[] arr) {
        int max = -1;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] >= max)
                max = arr[i];
        }

        return max;
    }

    private boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i+1])
                return false;
        }

        return true;
    }

    public static void main(String[] args) {
        trappingRainWater solution = new trappingRainWater();
        //int[] height = {0,1,0,2,1,0,1,3,2,1,2,1};
        int[] height = {4,2,3};

        System.out.println("Number of water units = " + solution.trap_mine(height));
        System.out.println("Number of water units = " + solution.trap(height));
    }
}
