package view;

import controller.UserController;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserManagementView extends JFrame {
  private final UserController userController;
  private DefaultTableModel tableModel;
  private JTable table;

  public UserManagementView() {
    userController = new UserController();

    setTitle("User Management");
    setSize(1000, 600);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);

    // Panel Utama dengan BorderLayout
    JPanel mainPanel = new JPanel(new BorderLayout());

    // Header
    JPanel headerPanel = new JPanel(new BorderLayout());
    headerPanel.setBackground(new Color(0, 153, 76)); // Hijau
    headerPanel.setPreferredSize(new Dimension(1000, 80));

    JLabel lblTitle = new JLabel("User Management", JLabel.CENTER);
    lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
    lblTitle.setForeground(Color.WHITE);
    headerPanel.add(lblTitle, BorderLayout.CENTER);

    JLabel lblWelcome = new JLabel("Welcome, Admin", JLabel.RIGHT);
    lblWelcome.setForeground(Color.WHITE);
    lblWelcome.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
    headerPanel.add(lblWelcome, BorderLayout.EAST);

    mainPanel.add(headerPanel, BorderLayout.NORTH);

    // Sidebar
    JPanel sidebar = createSidebar();

    // Panel Konten
    JPanel contentPanel = new JPanel(new BorderLayout());
    contentPanel.setBackground(Color.WHITE);

    // Tabel untuk daftar pengguna
    tableModel = new DefaultTableModel(new String[] { "ID", "Username", "Role" }, 0);
    table = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    contentPanel.add(scrollPane, BorderLayout.CENTER);

    // Panel tombol di bawah tabel
    JPanel buttonPanel = new JPanel(new FlowLayout());
    JButton btnAdd = createButton("Add User");
    JButton btnEdit = createButton("Edit User");
    JButton btnDelete = createButton("Delete User");

    buttonPanel.add(btnAdd);
    buttonPanel.add(btnEdit);
    buttonPanel.add(btnDelete);
    contentPanel.add(buttonPanel, BorderLayout.SOUTH);

    // Event Handling untuk Tombol
    btnAdd.addActionListener(e -> openUserForm(null));
    btnEdit.addActionListener(e -> {
      int selectedRow = table.getSelectedRow();
      if (selectedRow >= 0) {
        int userId = (int) tableModel.getValueAt(selectedRow, 0);
        User user = userController.getUserById(userId);
        openUserForm(user);
      } else {
        JOptionPane.showMessageDialog(this, "Select a user to edit.", "Warning", JOptionPane.WARNING_MESSAGE);
      }
    });

    btnDelete.addActionListener(e -> {
      int selectedRow = table.getSelectedRow();
      if (selectedRow >= 0) {
        int userId = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this user? This action cannot be undone.",
            "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
          userController.deleteUser(userId);
          loadUserData(); // Reload table data
        }
      } else {
        JOptionPane.showMessageDialog(this, "Select a user to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
      }
    });

    // Muat data pengguna
    loadUserData();

    // Tambahkan sidebar dan konten utama ke panel utama
    mainPanel.add(sidebar, BorderLayout.WEST);
    mainPanel.add(contentPanel, BorderLayout.CENTER);

    // Tambahkan panel utama ke frame
    add(mainPanel);
    setVisible(true);
  }

  private JPanel createSidebar() {
    JPanel sidebar = new JPanel();
    sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
    sidebar.setBackground(Color.WHITE); // Sidebar putih
    sidebar.setPreferredSize(new Dimension(200, 600));

    JButton btnMainMenu = createSidebarButton("Main Menu", new Color(0, 153, 76));
    JButton btnLogout = createSidebarButton("Logout", new Color(255, 51, 51));

    sidebar.add(Box.createVerticalStrut(20));
    sidebar.add(btnMainMenu);
    sidebar.add(Box.createVerticalStrut(10));
    sidebar.add(btnLogout);

    // Event Tombol Sidebar
    btnMainMenu.addActionListener(e -> {
      new MainMenu();
      dispose();
    });

    btnLogout.addActionListener(e -> {
      int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout",
          JOptionPane.YES_NO_OPTION);
      if (confirm == JOptionPane.YES_OPTION) {
        JOptionPane.showMessageDialog(this, "Logged out successfully.", "Logout", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
      }
    });

    return sidebar;
  }

  private JButton createSidebarButton(String text, Color hoverColor) {
    JButton button = new JButton(text);
    button.setAlignmentX(Component.CENTER_ALIGNMENT);
    button.setMaximumSize(new Dimension(150, 40));
    button.setBackground(Color.WHITE);
    button.setForeground(Color.BLACK);
    button.setFocusPainted(false);
    button.setFont(new Font("Arial", Font.BOLD, 14));

    // Hover efek tombol
    button.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(java.awt.event.MouseEvent evt) {
        button.setBackground(hoverColor);
        button.setForeground(Color.WHITE);
      }

      public void mouseExited(java.awt.event.MouseEvent evt) {
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
      }
    });

    return button;
  }

  private JButton createButton(String text) {
    JButton button = new JButton(text);
    button.setFont(new Font("Arial", Font.BOLD, 14));
    button.setBackground(new Color(0, 153, 76));
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);
    return button;
  }

  private void loadUserData() {
    tableModel.setRowCount(0); // Bersihkan tabel
    List<User> users = userController.getAllUsers();
    for (User user : users) {
      tableModel.addRow(new Object[] { user.getId(), user.getUsername(), user.getRole() });
    }
  }

  private void openUserForm(User user) {
    JDialog formDialog = new JDialog(this, "User Form", true);
    formDialog.setSize(400, 300);
    formDialog.setLayout(new GridLayout(4, 2));
    formDialog.setLocationRelativeTo(this);

    JLabel lblUsername = new JLabel("Username:");
    JTextField txtUsername = new JTextField();
    JLabel lblPassword = new JLabel("Password:");
    JPasswordField txtPassword = new JPasswordField();
    JLabel lblRole = new JLabel("Role:");
    JComboBox<String> cbRole = new JComboBox<>(new String[] { "admin", "courier" });

    if (user != null) {
      txtUsername.setText(user.getUsername());
      txtPassword.setText(user.getPassword());
      cbRole.setSelectedItem(user.getRole());
    }

    JButton btnSave = createButton("Save");
    JButton btnCancel = createButton("Cancel");

    formDialog.add(lblUsername);
    formDialog.add(txtUsername);
    formDialog.add(lblPassword);
    formDialog.add(txtPassword);
    formDialog.add(lblRole);
    formDialog.add(cbRole);
    formDialog.add(btnSave);
    formDialog.add(btnCancel);

    btnSave.addActionListener(e -> {
      String username = txtUsername.getText();
      String password = new String(txtPassword.getPassword());
      String role = (String) cbRole.getSelectedItem();

      if (username.isEmpty() || password.isEmpty() || role == null) {
        JOptionPane.showMessageDialog(formDialog, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }

      if (user == null) {
        // Tambah user
        userController.addUser(new User(0, username, password, role));
      } else {
        // Perbarui user
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        userController.updateUser(user);
      }

      loadUserData();
      formDialog.dispose();
    });

    btnCancel.addActionListener(e -> formDialog.dispose());

    formDialog.setVisible(true);
  }
}
