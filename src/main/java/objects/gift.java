package objects;

import java.io.Serializable;

public class gift implements Serializable {
    private static final long serialVersionUID = 1L;
    private String giftName;
    private String imgSrc;
    private String infoField;

    public gift(String giftName, String imgSrc, String infoField) {
        this.giftName = giftName;
        this.imgSrc = imgSrc;
        this.infoField = infoField;
    }

    public String getGiftName() {
        return giftName;
    }
}
