package core;

import java.util.ArrayList;

/**
 * Created by hanlin on 4/28/17.
 */
public class VoteData {
    private int VN;
    private int SC;
    private ArrayList<Character> DS;

    public VoteData(int VN, int SC, ArrayList<Character> DS) {
        this.VN = VN;
        this.SC = SC;
        this.DS = DS;
    }

    public int getVN() {
        return VN;
    }

    public void setVN(int VN) {
        this.VN = VN;
    }

    public int getSC() {
        return SC;
    }

    public void setSC(int SC) {
        this.SC = SC;
    }

    public ArrayList<Character> getDS() {
        return DS;
    }

    public void setDS(ArrayList<Character> DS) {
        this.DS = DS;
    }

    @Override
    public String toString() {
        return "VoteData{" +
                "VN=" + VN +
                ", SC=" + SC +
                ", DS=" + DS +
                '}';
    }
}
