import java.io.Serializable;

/**
 * Created by hanlin on 3/28/17.
 */
public class StoredContent implements Serializable {
    private Integer contentID;
    private Integer integerValue;
    private VectorTime vectorTime;

    public StoredContent(Integer contentID) {
        this.contentID = contentID;
        this.integerValue = null;
        this.vectorTime = null;
    }

    public StoredContent(Integer contentID, Integer integerValue) {
        this.contentID = contentID;
        this.integerValue = integerValue;
    }

    public Integer getContentID() {
        return contentID;
    }

    public VectorTime getVectorTime() {
        return vectorTime;
    }

    public Integer getIntegerValue() {
        return integerValue;
    }

    public void setVectorTime(Integer [] vectorTime) {
        this.vectorTime = new VectorTime(vectorTime.length);
        this.vectorTime.setTimestamp(vectorTime);
    }

    @Override
    public String toString() {
        return "StoredContent{" +
                "contentID=" + contentID +
                ", integerValue=" + integerValue +
                ", vectorTime=" + vectorTime +
                '}';
    }
}
