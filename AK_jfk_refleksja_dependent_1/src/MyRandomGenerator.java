import com.adKoch.callable.IRandomGenerator;

import java.util.Random;

public class MyRandomGenerator implements IRandomGenerator {



    @Override
    public int nextInt(int from, int to) {
        Random rand = new Random();
        return rand.nextInt(to-from)+from;
    }

    @Override
    public double nextDouble(double from, double to) {
        Random rand = new Random();
        return rand.nextDouble()*(to-from)+from;
    }
}
