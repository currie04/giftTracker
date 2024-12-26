package objects;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class person implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private Map<String, gift> gifts;

    public person(String name){
        this.name = name;
        gifts = new HashMap<String, gift>();
    }

    public void changeName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }


    //Add or remove gifts from a person

    public void addGift(gift Gift){
        if(!gifts.containsKey(Gift.getGiftName())) {
            gifts.put(Gift.getGiftName(), Gift);
        } else {
            System.out.println("This gift is already in the list");
        }
    }

    public void removeGift(gift Gift){
        if (gifts.containsKey(Gift.getGiftName())) {
            gifts.remove(Gift.getGiftName());
            System.out.println("Removed gift " + Gift.getGiftName());
        } else {
            System.out.println("This gift is not in the list");
        }

    }

    public Map<String, gift> getGifts() {
        return gifts;
    }
}
