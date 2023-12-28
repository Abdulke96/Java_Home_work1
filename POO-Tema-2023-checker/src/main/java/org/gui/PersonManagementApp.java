package org.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Person {
    String name;
    int age;
    String email;

    public Person(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Age: " + age + ", Email: " + email;
    }
}

class PersonInputDialog extends JDialog {
    private JTextField nameField, ageField, emailField;
    private JButton okButton, cancelButton;

    private Person resultPerson;

    public PersonInputDialog(JFrame parent) {
        super(parent, "Enter Person Information", true);

        setLayout(new GridLayout(4, 2));

        nameField = new JTextField(60);
        ageField = new JTextField(60);
        emailField = new JTextField(60);

        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");

        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Age:"));
        add(ageField);
        add(new JLabel("Email:"));
        add(emailField);
        add(okButton);
        add(cancelButton);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultPerson = new Person(
                        nameField.getText(),
                        Integer.parseInt(ageField.getText()),
                        emailField.getText()
                );
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultPerson = null;
                dispose();
            }
        });

        pack();
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    public Person showDialog() {
        setVisible(true);
        return resultPerson;
    }
}

class PersonManagementFrame extends JFrame {
    private List<Person> persons = new ArrayList<>();
    private JTextArea personTextArea;

    public PersonManagementFrame() {
        setTitle("Person Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton addButton = new JButton("Add Person");
        JButton deleteButton = new JButton("Delete Person");

        personTextArea = new JTextArea(10, 30);
        personTextArea.setEditable(false);

        addButton.addActionListener(e -> addPerson());
        deleteButton.addActionListener(e -> deletePerson());

        add(addButton);
        add(deleteButton);
        add(new JScrollPane(personTextArea));

        setVisible(true);
    }

    private void addPerson() {
        PersonInputDialog dialog = new PersonInputDialog(this);
        Person newPerson = dialog.showDialog();

        if (newPerson != null) {
            persons.add(newPerson);
            updateTextArea();
        }
    }

    private void deletePerson() {
        String nameToDelete = JOptionPane.showInputDialog(this, "Enter Name to Delete:");
        if (nameToDelete == null) return; // User canceled the input

        persons.removeIf(person -> person.name.equals(nameToDelete));

        updateTextArea();
    }

    private void updateTextArea() {
        personTextArea.setText("");
        for (Person person : persons) {
            personTextArea.append(person.toString() + "\n");
        }
    }
}

public class PersonManagementApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PersonManagementFrame();
        });
    }
}



