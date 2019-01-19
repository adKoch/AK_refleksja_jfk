import com.adKoch.callable.IMean;

public class MeanCalculator implements IMean {
    @Override
    public double arithemic(double arg1, double arg2) {
        return (arg1+arg2)/2;
    }

    @Override
    public double geometric(double arg1, double arg2) {
        return Math.sqrt(arg1*arg2);
    }
}
