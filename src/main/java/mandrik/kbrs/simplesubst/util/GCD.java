package mandrik.kbrs.simplesubst.util;

import java.util.ArrayList;

public class GCD {

    public static int gcd(int a, int b)
    {
        while (b > 0)
        {
            int temp = b;
            b = a % b; // % is remainder
            a = temp;
        }
        return a;
    }

    public static int gcd(ArrayList<Integer> input)
    {
        int result = input.get(0);
        for(Integer next: input) {
            result = gcd(result, next);
        }
        return result;
    }
}
