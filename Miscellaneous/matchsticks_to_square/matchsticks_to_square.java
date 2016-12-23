package Miscellaneous.matchsticks_to_square;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by chidr on 12/20/16.
 * https://leetcode.com/problems/matchsticks-to-square/
 */
public class matchsticks_to_square {
    private boolean findCombination(int[] nums, int start, int sum, int target, HashSet<Integer> picked) {
        if (sum == 0)
            return true;
        else if (sum < 0) {
            return false;
        }

        boolean ret;

        for (int i = start; i < nums.length; i++) {
            // Pick this stick
            picked.add(i);

            ret = findCombination(nums, i + 1, sum - nums[i], target, picked);

            if (!ret) {
                /*
                 * Picking this stick did not help. Remove the stick
                 *     and check if the next stick can be added.
                 */
                picked.remove(i);

                /*
                 * Let set of sticks be [1, 1, 2, 3, 14, 15, 16, 16]
                 *
                 * After having picked [1, 1, 2, 3] we try picking 14 and see that
                 *     it exceeds the side of the square. So there is no point in
                 *     picking 15 and seeing if a side can be formed. This is why
                 *     the array was sorted
                 *
                 * In another example, after having picked [1, 1, 2] and knowing
                 *     picking 3 wont help, we pick 14. Again a side cannot be formed.
                 *     Similarly 15 need not be picked because we already picked 14 and
                 *     saw that a side cannot be made
                 */
                if ((i + 1) < nums.length && sum < nums[i+1])
                    return false;


                continue;
            } else {
                /*
                 * We found a combination. Now for a new array by excluding all elements
                 *     that were picked. The picked array / set was used for this
                 *     purpose, to identify what all sticks were picked and exclude them.
                 */
                int[] new_nums = new int[nums.length - picked.size()];
                int k = 0;

                for (int j = 0; j < nums.length; j++) {
                    if (picked.contains(j))
                        continue;

                    new_nums[k++] = nums[j];
                }

                HashSet<Integer> new_picked = new HashSet<>();
                new_picked.add(0);

                /*
                 * Run a new search with the sticks that are left
                 * If there are no new sticks, then we found a square. Start returning true
                 */
                if (new_nums.length == 0 || findCombination(new_nums, 1, target - new_nums[0], target, new_picked))
                    return true;
                else {
                    /*
                     * We found one combination, but they did not add up to a square. Discard
                     * the combination we picked and keep searching
                     */
                    picked.remove(i);
                    continue;
                }
            }

        }

        // Exhausted all the sticks
        return false;
    }

    /*
     * Public method to be called
     */
    public boolean makesquare(int[] nums) {
        int side;

        /*
         * Sort in ascending order. By this, if you cant pick the next matchstick,
         * then you dont have to traverse the rest of the match sticks
         */
        Arrays.sort(nums);

        // Sum of the lengths of the matcstick. sum/4 gives the length of the one side
        side = findSum(nums);

        // You cannot put the stick to a square no matter what
        if (side == 0 || side % 4 != 0)
            return false;


        HashSet<Integer> picked = new HashSet<Integer>();
        /*
         * Always pick the first stick and then find the combinations.
         * The first stick has to be in a right combination. If the first
         * stick itself cant be in the right place, then a square cannot be
         * formed anyways
         */
        picked.add(0);

        // Because the first stick (0) is picked, start from second stick (1)
        return findCombination(nums, 1, (side / 4 - nums[0]), side / 4, picked);

    }

    private int findSum(int[] nums) {
        int sum = 0;

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }

        return sum;
    }

    public static void main(String[] args) {
        matchsticks_to_square obj = new matchsticks_to_square();
        int[] nums = {3, 3, 3, 7, 4, 4, 4, 4, 5, 5, 5, 5};
        //int[] nums = {1, 1, 2, 7, 14, 15, 16, 16};

        System.out.println("Can we make a square? " + obj.makesquare(nums));

    }
}
