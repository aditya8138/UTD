import java.awt.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by hanlin on 3/28/17.
 */
public class VectorTime implements Comparable<VectorTime>, Serializable {

    private Integer [] timestamp = null;

    public VectorTime(Integer n) {
        this.timestamp = new Integer[n];
        for (int i = 0; i < n; i++)
            this.timestamp[i] = 0;
    }

    public void setTimestamp(Integer[] timestamp) {
        for (int i = 0; i < this.timestamp.length; i++) {
            this.timestamp[i] = timestamp[i];
        }
    }

    public void ticktock(Integer nodeID) {
        this.timestamp[nodeID]++;
    }

    /* Tick tock and adjust the timestamp according the ts carried by message. */
    public void ticktock(Integer nodeID, VectorTime vectorTime, Integer FromID) {
        this.timestamp[nodeID]++;

        if (vectorTime == null)
            return;

        for (int i = 0; i < vectorTime.timestamp.length; i++)
            if (i != FromID)
                if (this.timestamp[i] < vectorTime.timestamp[i])
                    this.timestamp[i] = vectorTime.timestamp[i];
    }

    public void adjustTimestamp (VectorTime vectorTime, Integer nodeID) {
        for (int i = 0; i < vectorTime.timestamp.length; i++)
            if (i != nodeID)
                if (this.timestamp[i] < vectorTime.timestamp[i])
                    this.timestamp[i] = vectorTime.timestamp[i];
    }

    public Integer[] getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        StringBuilder ts = new StringBuilder("Timestamp[");
        for (Integer integer: this.timestamp)
            ts.append(integer).append(',');
        ts.append(']');
        return ts.toString();
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p>
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     * <p>
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     * <p>
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     * <p>
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     * <p>
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(VectorTime o) {
        int result = 0;
        for (int i = 0; i < this.timestamp.length; i++) {
            if (this.timestamp[i] < o.timestamp[i]) {
                if (result == 0)
                    result = -1;
                else if (result == 1)
                    return 0;
            } else if (this.timestamp[i] > o.timestamp[i])
                if (result == 0)
                    result = 1;
                else if (result == -1)
                    return 0;
        }
        return result;
    }
//
//    public static void main(String[] args) {
//        VectorTime a = new VectorTime(3);
//        VectorTime b = new VectorTime(3);
//
//        Random rand = new Random();
//
////		Integer [] av = new Integer[] {rand.nextInt(1000), rand.nextInt(1000), rand.nextInt(1000)};
////		Integer [] bv = new Integer[] {rand.nextInt(1000), rand.nextInt(1000), rand.nextInt(1000)};
//
//        Integer [] av = new Integer[] {18,14,25};
//        Integer [] bv = new Integer[] {14,24,16};
//
//        a.setTimestamp(av);
//        b.setTimestamp(bv);
//
//        System.out.println("A " + a + " compares B " + b + " is " + a.compareTo(b));
//        System.out.println("B " + b + " compares A " + a + " is " + b.compareTo(a));
//
//    }

}
