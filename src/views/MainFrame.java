package views;

import services.TermParser;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.util.List;

public class MainFrame extends JFrame {
    public JFrame mainFrame;
    public static final String NAME = "Expression table editor";

    private MainFrame() {
    }

    public static MainFrame getInstance() {
        return Initializer.INSTANCE;
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame(MainFrame.NAME);
        frame.setSize(300, 300);
        getInstance().mainFrame = frame;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JScrollPane scrollPane = new JScrollPane(null, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(scrollPane);
        uploadTable(TermParser.getEmptyData(20, 20));

        frame.setJMenuBar(new SMenu().createMenuBar());

        //Display the window.
        frame.setVisible(true);
    }

    public static void uploadTable(List<List<String>> data) {
        STable table = new STable(data);
        JFrame frame = MainFrame.getInstance().mainFrame;
        JScrollPane pane = (JScrollPane) frame.getContentPane();
        pane.setViewportView(table);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getTableHeader().setReorderingAllowed(false);
        table.setCellSelectionEnabled(true);

        TableColumn index = table.getColumnModel().getColumn(0);
        index.setCellRenderer(table.getTableHeader().getDefaultRenderer());
        index.setPreferredWidth(30);
    }

    public static STable getMainTable() {
        JFrame frame = MainFrame.getInstance().mainFrame;
        JScrollPane pane = (JScrollPane) frame.getContentPane();
        return (STable) pane.getViewport().getView();
    }

    private static class Initializer {
        private static final MainFrame INSTANCE = new MainFrame();
    }
}
