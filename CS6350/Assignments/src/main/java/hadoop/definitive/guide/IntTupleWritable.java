package hadoop.definitive.guide;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class IntTupleWritable implements Writable {

    private IntWritable fst;
    private IntWritable snd;

    public IntTupleWritable() {
        set(new IntWritable(), new IntWritable());
    }

    public IntTupleWritable(IntWritable fst, IntWritable snd) {
        this.fst = fst;
        this.snd = snd;
    }

    public IntWritable getFst() {
        return fst;
    }

    public IntWritable getSnd() {
        return snd;
    }

    public void set(IntWritable fst, IntWritable snd) {
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
        if (obj instanceof IntTupleWritable) {
            IntTupleWritable it = (IntTupleWritable) obj;
            return fst.equals(it.fst) && snd.equals(it.snd);
        }
        return false;
    }

    @Override
    public String toString() {
        return  fst + "\t" + snd;
    }
}