import objects.person;
import java.io.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class common implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Map<String, person> personList;

    common(){
        personList = new HashMap<String, person>();
    }

    public void addPerson(person newPerson){
        personList.put(newPerson.getName(), newPerson);
    }

    public void removePerson(person person){
        personList.remove(person.getName());
    }

    public person getPerson(String selectedPersonName) {
        return personList.get(selectedPersonName);
    }

    public Map<String, person> getPersonList() {
        return personList;
    }

    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static common loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (common) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new common();
        }
    }
}

