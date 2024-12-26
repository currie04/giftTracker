import objects.gift;
import objects.person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CommonGUI {
    private JFrame frame;
    private common commonInstance;
    private JButton addPerson;
    private JButton deletePerson;
    private JTextField enterName;
    private JPanel peoplePanel;
    private static final String FILENAME = "data.ser";

    CommonGUI() {
        frame = new JFrame("Person and Gift Manager");
        commonInstance = common.loadFromFile(FILENAME);
        addPerson = new JButton("Add Person");
        deletePerson = new JButton("Delete Person");
        enterName = new JTextField(15);
        peoplePanel = new JPanel();
        peoplePanel.setLayout(new BoxLayout(peoplePanel, BoxLayout.Y_AXIS));

        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Enter Name:"));
        controlPanel.add(enterName);
        controlPanel.add(addPerson);
        controlPanel.add(deletePerson);

        frame.setLayout(new BorderLayout());
        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(peoplePanel), BorderLayout.CENTER);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        addPerson.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = enterName.getText();
                if (!name.isEmpty()) {
                    createPersonField(name);
                    enterName.setText("");
                }
            }
        });

        deletePerson.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = enterName.getText();
                if (!name.isEmpty()) {
                    commonInstance.removePerson(new person(name));
                    displayPeople();
                    enterName.setText("");
                }
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                commonInstance.saveToFile(FILENAME);
            }
        });

        displayPeople();
    }

    private void createPersonField(String name) {
        person newPerson = new person(name);
        commonInstance.addPerson(newPerson);

        JPanel personPanel = new JPanel();
        personPanel.setLayout(new BoxLayout(personPanel, BoxLayout.Y_AXIS));
        personPanel.setBorder(BorderFactory.createTitledBorder(name));

        JList<String> giftList = new JList<>(new DefaultListModel<>());
        personPanel.add(new JScrollPane(giftList));

        JTextField giftNameField = new JTextField(10);
        JTextField giftImgField = new JTextField(10);
        JTextField giftInfoField = new JTextField(10);
        JButton addGiftButton = new JButton("Add Gift");
        JButton removeGiftButton = new JButton("Remove Gift");

        JPanel giftControlPanel = new JPanel();
        giftControlPanel.add(new JLabel("Gift Name:"));
        giftControlPanel.add(giftNameField);
        giftControlPanel.add(new JLabel("Image:"));
        giftControlPanel.add(giftImgField);
        giftControlPanel.add(new JLabel("Info:"));
        giftControlPanel.add(giftInfoField);
        giftControlPanel.add(addGiftButton);
        giftControlPanel.add(removeGiftButton);

        personPanel.add(giftControlPanel);

        addGiftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String giftName = giftNameField.getText();
                String giftImg = giftImgField.getText();
                String giftInfo = giftInfoField.getText();
                if (!giftName.isEmpty()) {
                    gift newGift = new gift(giftName, giftImg, giftInfo);
                    newPerson.addGift(newGift);
                    ((DefaultListModel<String>) giftList.getModel()).addElement(giftName);
                    giftNameField.setText("");
                    giftImgField.setText("");
                    giftInfoField.setText("");
                }
            }
        });

        removeGiftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedGift = giftNameField.getText();
                if (selectedGift != null) {
                    gift giftToRemove = newPerson.getGifts().get(selectedGift);
                    newPerson.removeGift(giftToRemove);
                    ((DefaultListModel<String>) giftList.getModel()).removeElement(selectedGift);
                    giftNameField.setText("");
                    giftImgField.setText("");
                    giftInfoField.setText("");
                }
            }
        });

        peoplePanel.add(personPanel);
        frame.revalidate();
        frame.repaint();
    }

    private void displayPeople() {
        peoplePanel.removeAll();
        for (String personName : commonInstance.getPersonList().keySet()) {
            createPersonField(personName);
        }
        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CommonGUI();
            }
        });
    }
}