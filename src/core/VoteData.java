package core;

import java.io.Serializable;
import java.util.ArrayList;

public class VoteData implements Serializable {
    private int VN;
    private int SC;
    private ArrayList<Character> DS;

    VoteData(int VN, int SC, ArrayList<Character> DS) {
        this.VN = VN;
        this.SC = SC;
        this.DS = DS;
    }

    int getVN() {
        return VN;
    }

    int getSC() {
        return SC;
    }

    ArrayList<Character> getDS() {
        return DS;
    }

    @Override
    public String toString() {
        return  "\nCurrent Vote Data:" +
                "\n    Version Number            :  " + VN +
                "\n    Update Sites Cardinality  :  " + SC +
                "\n    Distinguished Sites List  :  " + DS;
    }

    String toStringCompact() {
        return  "Current Vote Data:" + "VN(" + VN + ") USC(" + SC + ") DS(" + DS + ")";
    }
}
