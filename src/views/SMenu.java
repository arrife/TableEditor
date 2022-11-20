package views;

import models.STableModel;
import services.CSVTools;
import services.TermParser;
import services.functions.Term;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class SMenu implements ActionListener {
    public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;

        // Create the menu bar.
        menuBar = new JMenuBar();

        // Build file menu.
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(menu);

        // Import csv
        menuItem = new JMenuItem("Open csv...",
                KeyEvent.VK_O);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);

        // Export csv
        menu.addSeparator();
        submenu = new JMenu("Export to csv...");

        menuItem = new JMenuItem("Export values", KeyEvent.VK_V);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
        submenu.add(menuItem);
        menuItem.addActionListener(this);

        menuItem = new JMenuItem("Export expressions", KeyEvent.VK_E);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
        submenu.add(menuItem);
        menu.add(submenu);
        menuItem.addActionListener(this);

        // Build table action menu
        menu = new JMenu("Table");
        menu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(menu);

        // Add row
        menuItem = new JMenuItem("Add row",
                KeyEvent.VK_R);
        menuItem.addActionListener(this);
        menu.add(menuItem);

        // Add column
        menuItem = new JMenuItem("Add column",
                KeyEvent.VK_C);
        menuItem.addActionListener(this);
        menu.add(menuItem);

        // Build help menu
        menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_H);
        menu.getAccessibleContext().setAccessibleDescription(
                "This menu does nothing");
        menuBar.add(menu);

        menuItem = new JMenuItem("Functions description", KeyEvent.VK_F);
        menu.add(menuItem);
        menuItem.addActionListener(this);

        return menuBar;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        JMenuItem source = (JMenuItem) (event.getSource());
        switch (source.getMnemonic()) {
            case KeyEvent.VK_O:
                openCSVEvent(source);
                break;
            case KeyEvent.VK_F:
                showFunctions(source);
                break;
            case KeyEvent.VK_E:
                exportCSVEvent(source, false);
                break;
            case KeyEvent.VK_V:
                exportCSVEvent(source, true);
                break;
            case KeyEvent.VK_R:
                ((STableModel) MainFrame.getMainTable().getModel()).addRow();
                break;
            case KeyEvent.VK_C:
                ((STableModel) MainFrame.getMainTable().getModel()).addColumn();
                break;
        }
    }

    private void openCSVEvent(JMenuItem item) {
        JFileChooser jfileChooser = new JFileChooser(System.getProperty("user.dir"), FileSystemView.getFileSystemView());
        int actionCode = jfileChooser.showOpenDialog(item);
        if (actionCode == JFileChooser.APPROVE_OPTION) {
            try {
                List<List<String>> data = CSVTools.readCSV(jfileChooser.getSelectedFile());
                MainFrame.uploadTable(data);
            } catch (IOException e) {
                showMessageDialog(item, e.getMessage());
            }
        }
    }

    private void exportCSVEvent(JMenuItem item, boolean saveValues) {
        JFileChooser jfileChooser = new JFileChooser(System.getProperty("user.dir"), FileSystemView.getFileSystemView());
        int actionCode = jfileChooser.showSaveDialog(item);
        if (actionCode == JFileChooser.APPROVE_OPTION) {
            STable table = MainFrame.getMainTable();
            try {
                CSVTools.saveCSV(jfileChooser.getSelectedFile(), ((STableModel) table.getModel()).getData(), saveValues);
            } catch (IOException e) {
                showMessageDialog(item, e.getMessage());
            }
        }
    }

    private void showFunctions(JMenuItem item) {
        StringBuilder message = new StringBuilder();
        for (Class<?> func : TermParser.FUNCTIONS.values()) {
            try {
                Term term = (Term) func.getConstructor().newInstance();
                message.append(term.name);
                message.append(": ");
                message.append(term.description);
                message.append("\n");
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                System.out.println(e.getMessage());
            }
        }
        showMessageDialog(item, message.toString());
    }
}
