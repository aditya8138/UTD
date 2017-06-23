package hadoop.definitive.guide;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class GenericTupleWritable <FstType extends Writable, SndType extends Writable>
        implements Writable {

    private FstType fst;
    private SndType snd;

    public GenericTupleWritable(FstType fst, SndType snd) {
        this.fst = fst;
        this.snd = snd;
    }

    public FstType getFst() {
        return fst;
    }

    public SndType getSnd() {
        return snd;
    }

    public void set(FstType fst, SndType snd) {
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
        if (obj instanceof GenericTupleWritable) {
            GenericTupleWritable it = (GenericTupleWritable) obj;
            return fst.equals(it.fst) && snd.equals(it.snd);
        }
        return false;
    }

    @Override
    public String toString() {
        return  fst + "\t" + snd;
    }
}