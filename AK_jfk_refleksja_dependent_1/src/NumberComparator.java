import com.adKoch.callable.IComparable;

public class NumberComparator implements IComparable {

    @Override
    public boolean gt(double arg1, double arg2) {
        if(arg1>arg2) return true;
        return false;
    }

    @Override
    public boolean eq(double arg1, double arg2) {
        if(arg1==arg2) return true;
        return false;
    }
}
