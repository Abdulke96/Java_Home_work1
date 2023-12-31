//package org.helper;
//
//import javax.swing.*;
//import java.awt.*;
//import java.util.Calendar;
//
//public class BirthDateInputExample {
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("Birth Date Input");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setSize(300, 150);
//
//            JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
//
//            // Year ComboBox
//            JLabel yearLabel = new JLabel("Year:");
//            JComboBox<Integer> yearComboBox = createComboBox(getYearList());
//
//            // Month ComboBox
//            JLabel monthLabel = new JLabel("Month:");
//            JComboBox<String> monthComboBox = new JComboBox<>(new String[]{"January", "February", "March", "April",
//                    "May", "June", "July", "August", "September", "October", "November", "December"});
//
//            // Day ComboBox
//            JLabel dayLabel = new JLabel("Day:");
//            JComboBox<Integer> dayComboBox = createComboBox(getDayList());
//
//            panel.add(yearLabel);
//            panel.add(yearComboBox);
//            panel.add(monthLabel);
//            panel.add(monthComboBox);
//            panel.add(dayLabel);
//            panel.add(dayComboBox);
//
//            JButton submitButton = new JButton("Submit");
//            submitButton.addActionListener(e -> {
//                int year = (int) yearComboBox.getSelectedItem();
//                String month = (String) monthComboBox.getSelectedItem();
//                int day = (int) dayComboBox.getSelectedItem();
//                String formattedDate = String.format("%04d-%02d-%02d", year, getMonthNumber(month), day);
//                JOptionPane.showMessageDialog(frame, "Selected Birth Date: " + formattedDate);
//            });
//
//            frame.add(panel, BorderLayout.CENTER);
//            frame.add(submitButton, BorderLayout.SOUTH);
//            frame.setVisible(true);
//        });
//    }
//
//    private static JComboBox<Integer> createComboBox(Integer[] items) {
//        return new JComboBox<>(items);
//    }
//
//    private static Integer[] getYearList() {
//        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
//        Integer[] years = new Integer[100];
//        for (int i = 0; i < 100; i++) {
//            years[i] = currentYear - i;
//        }
//        return years;
//    }
//
//    private static Integer[] getDayList() {
//        Integer[] days = new Integer[31];
//        for (int i = 0; i < 31; i++) {
//            days[i] = i + 1;
//        }
//        return days;
//    }
//
//    private static int getMonthNumber(String month) {
//        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
//                "October", "November", "December"};
//        for (int i = 0; i < monthNames.length; i++) {
//            if (monthNames[i].equalsIgnoreCase(month)) {
//                return i + 1; // Months are 1-indexed in the API
//            }
//        }
//        return -1; // Invalid month
//    }
//}
