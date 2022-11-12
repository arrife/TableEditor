import models.Cell;
import models.SimpleTable;

import javax.swing.*;

public class Main {
    private static void createAndShowGUI (Object[][] data, String[] columnNames) {
        JFrame frame = new JFrame("SimpleTableDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        SimpleTable newContentPane = new SimpleTable(data, columnNames);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        String[] columnNames = {"First Name",
                "Last Name",
                "Sport",
                "# of Years",
                "Vegetarian"};

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

        int n = data.length;
        int m = data[0].length;
        Cell[][] processedData = new Cell[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                processedData[i][j] = new Cell(data[i][j]);
            }
        }



        SwingUtilities.invokeLater(() -> createAndShowGUI(processedData, columnNames));
    }
}