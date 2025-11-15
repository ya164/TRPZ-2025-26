package ua.edu.university.client;

import ua.edu.university.shared.CommandType;
import ua.edu.university.shared.FileSystemEntry;
import ua.edu.university.shared.Request;
import ua.edu.university.shared.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainFrame extends JFrame {

    private final DefaultListModel<FileSystemEntry> model = new DefaultListModel<>();
    private final JList<FileSystemEntry> list = new JList<>(model);
    private final JComboBox<String> driveBox = new JComboBox<>();
    private final JTextField pathField = new JTextField();

    private final JButton btnNewFolder = new JButton("New folder");
    private final JButton btnDelete = new JButton("Delete");
    private final JButton btnCopy = new JButton("Copy to...");

    private final NetworkClient net = new NetworkClient("127.0.0.1", 8888);

    private String currentPath = "";

    public MainFrame() {
        setTitle("Client - Total Commander");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel top = new JPanel(new BorderLayout(5, 5));
        top.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        top.add(driveBox, BorderLayout.WEST);

        pathField.setEditable(false);
        top.add(pathField, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        buttonsPanel.add(btnNewFolder);
        buttonsPanel.add(btnCopy);
        buttonsPanel.add(btnDelete);
        top.add(buttonsPanel, BorderLayout.EAST);

        add(top, BorderLayout.NORTH);

        list.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(list), BorderLayout.CENTER);

        driveBox.addActionListener(e -> {
            String d = (String) driveBox.getSelectedItem();
            if (d != null) {
                loadDir(d);
            }
        });

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    FileSystemEntry sel = list.getSelectedValue();
                    if (sel == null) return;
                    if (sel.isDirectory()) {
                        Path p = Paths.get(currentPath, sel.getName()).normalize();
                        loadDir(p.toString());
                    }
                }
            }
        });

        btnNewFolder.addActionListener(e -> {
            if (currentPath == null || currentPath.isEmpty()) return;

            String name = JOptionPane.showInputDialog(
                    MainFrame.this,
                    "Folder name:",
                    "Create folder",
                    JOptionPane.PLAIN_MESSAGE
            );
            if (name == null) return;
            name = name.trim();
            if (name.isEmpty()) return;

            String fullPath = Paths.get(currentPath, name).toString();
            Response r = net.send(new Request(CommandType.CREATE_DIRECTORY, fullPath, null));
            if (!r.isSuccess()) {
                JOptionPane.showMessageDialog(this, r.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            loadDir(currentPath);
        });

        btnDelete.addActionListener(e -> {
            FileSystemEntry sel = list.getSelectedValue();
            if (sel == null) return;

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Delete \"" + sel.getName() + "\"?",
                    "Confirm delete",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm != JOptionPane.YES_OPTION) return;

            String fullPath = Paths.get(currentPath, sel.getName()).toString();
            Response r = net.send(new Request(CommandType.DELETE, fullPath, null));
            if (!r.isSuccess()) {
                JOptionPane.showMessageDialog(this, r.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            loadDir(currentPath);
        });

        btnCopy.addActionListener(e -> {
            FileSystemEntry sel = list.getSelectedValue();
            if (sel == null) return;

            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Select target folder");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            int result = chooser.showOpenDialog(this);
            if (result != JFileChooser.APPROVE_OPTION) return;

            File targetDir = chooser.getSelectedFile();
            String sourcePath = Paths.get(currentPath, sel.getName()).toString();
            String targetPath = Paths.get(targetDir.getAbsolutePath(), sel.getName()).toString();

            Response r = net.send(new Request(CommandType.COPY, sourcePath, targetPath));
            if (!r.isSuccess()) {
                JOptionPane.showMessageDialog(this, r.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Copied to:\n" + targetPath, "Copy", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        loadDrives();
    }

    private void loadDrives() {
        Response r = net.send(new Request(CommandType.GET_DRIVES, null, null));
        if (!r.isSuccess()) {
            JOptionPane.showMessageDialog(this, r.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        driveBox.removeAllItems();
        if (r.getDrives() != null) {
            r.getDrives().forEach(driveBox::addItem);
        }
    }

    private void loadDir(String p) {
        currentPath = p;
        pathField.setText(p);

        Response r = net.send(new Request(CommandType.GET_DIRECTORY_CONTENT, p, null));
        if (!r.isSuccess()) {
            JOptionPane.showMessageDialog(this, r.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        model.clear();
        if (r.getEntries() != null) {
            r.getEntries().sort((a, b) -> {
                if (a.isDirectory() && !b.isDirectory()) return -1;
                if (!a.isDirectory() && b.isDirectory()) return 1;
                return a.getName().compareToIgnoreCase(b.getName());
            });
            r.getEntries().forEach(model::addElement);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
