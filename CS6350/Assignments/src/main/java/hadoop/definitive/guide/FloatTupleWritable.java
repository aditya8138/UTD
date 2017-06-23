package hadoop.definitive.guide;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class FloatTupleWritable implements Writable {

    private FloatWritable fst;
    private FloatWritable snd;

    public FloatTupleWritable() {
        set(new FloatWritable(), new FloatWritable());
    }

    public FloatTupleWritable(FloatWritable fst, FloatWritable snd) {
        this.fst = fst;
        this.snd = snd;
    }

    public FloatWritable getFst() {
        return fst;
    }

    public FloatWritable getSnd() {
        return snd;
    }

    public void set(FloatWritable fst, FloatWritable snd) {
        this.fst = fst;
        this.snd = snd;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        fst.write(out);
        snd.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        fst.readFields(in);
        snd.readFields(in);
    }

    @Override
    public int hashCode() {
        return fst.hashCode() * 163 + snd.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FloatTupleWritable) {
            FloatTupleWritable it = (FloatTupleWritable) obj;
            return fst.equals(it.fst) && snd.equals(it.snd);
        }
        return false;
    }

    @Override
    public String toString() {
        return  fst + "\t" + snd;
    }
}