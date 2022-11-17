import models.SimpleTable;

import javax.swing.*;

public class Main {
    private static void createAndShowGUI(Object[][] data) {
        JFrame frame = new JFrame("SimpleTableDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        SimpleTable table = new SimpleTable(data);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        scrollPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(scrollPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
//        String[] columnNames = {"First Name",
//                "Last Name",
//                "Sport",
//                "# of Years",
//                "Vegetarian"};

        Object[][] data = {
                {"Kathy", "Smith",
                        "Snowboarding", 5, false},
                {"John", "Doe",
                        "Rowing", 3, true},
                {"Sue", "Black",
                        "Knitting", 2, false},
                {"Jane", "White",
                        "Speed reading", 20, true},
                {"Joe", "Brown",
                        "Pool", 10, false}
        };


        SwingUtilities.invokeLater(() -> createAndShowGUI(data));
    }
}