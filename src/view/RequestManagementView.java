package view;

import controller.RequestController;
import controller.UserController;
import model.Request;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.util.List;

public class RequestManagementView extends JFrame {
  private final RequestController requestController;
  private DefaultTableModel tableModel;
  private JTable table;
  private JButton btnUpdateStatus;

  public RequestManagementView() {
    requestController = new RequestController();

    setTitle("Request Management");
    setSize(1000, 600);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);

    // Panel utama
    JPanel mainPanel = new JPanel(new BorderLayout());

    // Sidebar
    JPanel sidebar = new JPanel();
    sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
    sidebar.setBackground(new Color(255, 255, 255));

    JButton btnMainMenu = createSidebarButton("Main Menu");
    JButton btnLogout = createSidebarButton("Logout");

    sidebar.add(Box.createVerticalStrut(20)); // Spacer
    sidebar.add(btnMainMenu);
    sidebar.add(Box.createVerticalStrut(10)); // Spacer
    sidebar.add(btnLogout);

    // Event handling untuk sidebar
    btnMainMenu.addActionListener(e -> {
      new MainMenu();
      dispose();
    });

    btnLogout.addActionListener(e -> {
      int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout",
          JOptionPane.YES_NO_OPTION);
      if (confirm == JOptionPane.YES_OPTION) {
        dispose();
      }
    });

    mainPanel.add(sidebar, BorderLayout.WEST);

    // Konten utama
    JPanel contentPanel = new JPanel(new BorderLayout());

    // Tabel untuk daftar permintaan
    tableModel = new DefaultTableModel(new String[] { "ID", "User ID", "Description", "Status", "Created At" }, 0);
    table = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(table);
    contentPanel.add(scrollPane, BorderLayout.CENTER);

    // Panel tombol
    JPanel buttonPanel = new JPanel(new FlowLayout());
    JButton btnAdd = createButton("Add Request");
    JButton btnEdit = createButton("Edit Request");
    JButton btnDelete = createButton("Delete Request");
    btnUpdateStatus = createButton("Update Status");

    buttonPanel.add(btnAdd);
    buttonPanel.add(btnEdit);
    buttonPanel.add(btnDelete);
    buttonPanel.add(btnUpdateStatus);

    contentPanel.add(buttonPanel, BorderLayout.SOUTH);

    mainPanel.add(contentPanel, BorderLayout.CENTER);

    // Event handling untuk tombol
    btnAdd.addActionListener(e -> openRequestForm(null));

    btnEdit.addActionListener(e -> {
      int selectedRow = table.getSelectedRow();
      if (selectedRow >= 0) {
        int requestId = (int) tableModel.getValueAt(selectedRow, 0);
        Request request = requestController.getRequestById(requestId);
        openRequestForm(request);
      } else {
        JOptionPane.showMessageDialog(this, "Select a request to edit.", "Warning", JOptionPane.WARNING_MESSAGE);
      }
    });

    btnDelete.addActionListener(e -> {
      int selectedRow = table.getSelectedRow();
      if (selectedRow >= 0) {
        int requestId = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this request?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
          requestController.deleteRequest(requestId);
          loadRequestData();
        }
      } else {
        JOptionPane.showMessageDialog(this, "Select a request to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
      }
    });

    btnUpdateStatus.addActionListener(e -> {
      int selectedRow = table.getSelectedRow();
      if (selectedRow >= 0) {
        int requestId = (int) tableModel.getValueAt(selectedRow, 0);
        String newStatus = JOptionPane.showInputDialog(
            this,
            "Enter new status (accepted, on_progress, completed):");

        if (newStatus != null && !newStatus.trim().isEmpty()) {
          newStatus = newStatus.trim().toLowerCase();
          if (newStatus.equals("accepted") || newStatus.equals("on_progress") || newStatus.equals("completed")) {
            requestController.updateStatus(requestId, newStatus);
            loadRequestData();
          } else {
            JOptionPane.showMessageDialog(
                this,
                "Invalid status. Please use one of the following: accepted, on_progress, completed.",
                "Error", JOptionPane.ERROR_MESSAGE);
          }
        }
      } else {
        JOptionPane.showMessageDialog(this, "Select a request to update.", "Warning", JOptionPane.WARNING_MESSAGE);
      }
    });

    // Load data ke tabel
    loadRequestData();

    add(mainPanel);
    setVisible(true);
  }

  private JButton createSidebarButton(String text) {
    JButton button = new JButton(text);
    button.setAlignmentX(Component.CENTER_ALIGNMENT);
    button.setMaximumSize(new Dimension(150, 40));
    button.setBackground(new Color(0, 153, 76));
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);
    button.setFont(new Font("Arial", Font.BOLD, 14));
    return button;
  }

  private JButton createButton(String text) {
    JButton button = new JButton(text);
    button.setBackground(new Color(0, 153, 76));
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);
    button.setFont(new Font("Arial", Font.BOLD, 14));
    return button;
  }

  private void loadRequestData() {
    tableModel.setRowCount(0);
    List<Request> requests = requestController.getAllRequests();
    for (Request request : requests) {
      tableModel.addRow(new Object[] {
          request.getId(),
          request.getUserId(),
          request.getDescription(),
          request.getStatus(),
          request.getCreatedAt()
      });
    }
  }

  private void openRequestForm(Request request) {
    JDialog formDialog = new JDialog(this, "Request Form", true);
    formDialog.setSize(400, 300);
    formDialog.setLayout(new GridLayout(4, 2));
    formDialog.setLocationRelativeTo(this);

    JLabel lblUserId = new JLabel("User ID:");
    JTextField txtUserId = new JTextField();
    JLabel lblDescription = new JLabel("Description:");
    JTextField txtDescription = new JTextField();
    JLabel lblStatus = new JLabel("Status:");
    JComboBox<String> cbStatus = new JComboBox<>(new String[] { "pending", "accepted", "completed" });

    if (request != null) {
      txtUserId.setText(String.valueOf(request.getUserId()));
      txtDescription.setText(request.getDescription());
      cbStatus.setSelectedItem(request.getStatus());
    }

    JButton btnSave = createButton("Save");
    JButton btnCancel = createButton("Cancel");

    formDialog.add(lblUserId);
    formDialog.add(txtUserId);
    formDialog.add(lblDescription);
    formDialog.add(txtDescription);
    formDialog.add(lblStatus);
    formDialog.add(cbStatus);
    formDialog.add(btnSave);
    formDialog.add(btnCancel);

    btnSave.addActionListener(e -> {
      try {
        int userId = Integer.parseInt(txtUserId.getText());
        String description = txtDescription.getText();
        String status = (String) cbStatus.getSelectedItem();

        if (description.isEmpty() || status == null) {
          JOptionPane.showMessageDialog(formDialog, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
          return;
        }

        User user = new UserController().getUserById(userId);
        if (user == null) {
          JOptionPane.showMessageDialog(formDialog, "User ID not found in users table.", "Error",
              JOptionPane.ERROR_MESSAGE);
          return;
        }

        if (request == null) {
          requestController
              .addRequest(new Request(0, userId, description, status, new Timestamp(System.currentTimeMillis())));
        } else {
          request.setUserId(userId);
          request.setDescription(description);
          request.setStatus(status);
          requestController.updateRequest(request);
        }

        loadRequestData();
        formDialog.dispose();
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(formDialog, "User ID must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
      }
    });

    btnCancel.addActionListener(e -> formDialog.dispose());

    formDialog.setVisible(true);
  }
}
