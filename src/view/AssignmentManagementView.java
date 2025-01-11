package view;

import controller.AssignmentController;
import controller.RequestController;
import model.Assignment;
import model.Request;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AssignmentManagementView extends JFrame {
  private final AssignmentController assignmentController;
  private final RequestController requestController;
  private DefaultTableModel tableModel;
  private JTable table;
  private JButton btnAddAssignment, btnEditAssignment, btnDeleteAssignment, btnAcceptAssignment, btnAddCollection;
  private JLabel lblTotals;
  private JTextField txtStartDate, txtEndDate;
  private JButton btnFilter;

  public AssignmentManagementView() {
    // Inisialisasi controller
    assignmentController = new AssignmentController();
    requestController = new RequestController();

    setTitle("Assignment Management");
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

    btnMainMenu.addActionListener(e -> {
      new MainMenu();
      dispose();
    });

    btnLogout.addActionListener(e -> {
      int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
      if (confirm == JOptionPane.YES_OPTION) {
        dispose();
      }
    });

    mainPanel.add(sidebar, BorderLayout.WEST);

    // Panel untuk tabel
    JPanel contentPanel = new JPanel(new BorderLayout());

    // Tabel untuk menampilkan daftar penugasan
    tableModel = new DefaultTableModel(
        new String[] { "ID", "Request ID", "Courier ID", "Status", "Accepted At", "Collected At", "Weight (kg)",
            "Points" },
        0);
    table = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(table);
    contentPanel.add(scrollPane, BorderLayout.CENTER);

    // Panel tombol
    JPanel buttonPanel = new JPanel(new FlowLayout());
    btnAddAssignment = createButton("Add Assignment");
    btnEditAssignment = createButton("Edit Assignment");
    btnDeleteAssignment = createButton("Delete Assignment");
    btnAcceptAssignment = createButton("Accept Assignment");
    btnAddCollection = createButton("Add Collection");

    buttonPanel.add(btnAddAssignment);
    buttonPanel.add(btnEditAssignment);
    buttonPanel.add(btnDeleteAssignment);
    buttonPanel.add(btnAcceptAssignment);
    buttonPanel.add(btnAddCollection);

    // Tambahkan panel tombol dan label total ke bagian bawah
    JPanel southPanel = new JPanel(new BorderLayout());
    lblTotals = new JLabel("Total Weight: 0 kg | Total Points: 0");
    lblTotals.setHorizontalAlignment(SwingConstants.CENTER);
    southPanel.add(buttonPanel, BorderLayout.CENTER);
    southPanel.add(lblTotals, BorderLayout.SOUTH);

    contentPanel.add(southPanel, BorderLayout.SOUTH);

    // Panel filter
    JPanel filterPanel = new JPanel(new FlowLayout());
    filterPanel.add(new JLabel("Start Date (yyyy-MM-dd):"));
    txtStartDate = new JTextField(10);
    filterPanel.add(txtStartDate);

    filterPanel.add(new JLabel("End Date (yyyy-MM-dd):"));
    txtEndDate = new JTextField(10);
    filterPanel.add(txtEndDate);

    btnFilter = createButton("Filter");
    filterPanel.add(btnFilter);

    contentPanel.add(filterPanel, BorderLayout.NORTH);

    mainPanel.add(contentPanel, BorderLayout.CENTER);

    // Tambahkan panel utama ke frame
    add(mainPanel);

    // Setup listeners untuk tombol
    setupButtonListeners();

    // Load data ke tabel
    loadAssignmentData();

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

  private void setupButtonListeners() {
    // Tombol Add Assignment
    btnAddAssignment.addActionListener(e -> openAssignmentForm(null));

    // Tombol Edit Assignment
    btnEditAssignment.addActionListener(e -> {
      int selectedRow = table.getSelectedRow();
      if (selectedRow >= 0) {
        int assignmentId = (int) tableModel.getValueAt(selectedRow, 0);
        Assignment assignment = assignmentController.getAssignmentById(assignmentId);
        openAssignmentForm(assignment);
      } else {
        JOptionPane.showMessageDialog(this, "Select an assignment to edit.", "Warning", JOptionPane.WARNING_MESSAGE);
      }
    });

    // Tombol Delete Assignment
    btnDeleteAssignment.addActionListener(e -> {
      int selectedRow = table.getSelectedRow();
      if (selectedRow >= 0) {
        int assignmentId = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this assignment?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
          assignmentController.deleteAssignment(assignmentId);
          loadAssignmentData();
        }
      } else {
        JOptionPane.showMessageDialog(this, "Select an assignment to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
      }
    });

    // Tombol Accept Assignment
    btnAcceptAssignment.addActionListener(e -> {
      int selectedRow = table.getSelectedRow();
      if (selectedRow >= 0) {
        int assignmentId = (int) tableModel.getValueAt(selectedRow, 0);
        assignmentController.acceptAssignment(assignmentId);
        loadAssignmentData();
      } else {
        JOptionPane.showMessageDialog(this, "Select an assignment to accept.", "Warning", JOptionPane.WARNING_MESSAGE);
      }
    });

    // Tombol Add Collection
    btnAddCollection.addActionListener(e -> {
      int selectedRow = table.getSelectedRow();
      if (selectedRow >= 0) {
        int assignmentId = (int) tableModel.getValueAt(selectedRow, 0);
        openCollectionForm(assignmentId);
      } else {
        JOptionPane.showMessageDialog(this, "Select an assignment to add collection.", "Warning",
            JOptionPane.WARNING_MESSAGE);
      }
    });

    // Tombol Filter
    btnFilter.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String startDate = txtStartDate.getText().trim();
        String endDate = txtEndDate.getText().trim();

        if (startDate.isEmpty() || endDate.isEmpty()) {
          JOptionPane.showMessageDialog(
              AssignmentManagementView.this,
              "Please enter both start date and end date.",
              "Error", JOptionPane.ERROR_MESSAGE);
          return;
        }

        try {
          // Validasi format tanggal
          java.sql.Date.valueOf(startDate); // Akan melempar exception jika format salah
          java.sql.Date.valueOf(endDate);

          // Panggil metode untuk memuat data berdasarkan rentang tanggal
          loadAssignmentDataByDate(startDate, endDate);
        } catch (IllegalArgumentException ex) {
          JOptionPane.showMessageDialog(
              AssignmentManagementView.this,
              "Invalid date format. Please use yyyy-MM-dd.",
              "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });
  }

  private void loadAssignmentDataByDate(String startDate, String endDate) {
    tableModel.setRowCount(0); // Bersihkan tabel
    List<Assignment> assignments = assignmentController.getAssignmentsByDateRange(startDate, endDate);

    if (assignments.isEmpty()) {
      JOptionPane.showMessageDialog(
          this,
          "No assignments found for the selected date range.",
          "Information",
          JOptionPane.INFORMATION_MESSAGE);
      return;
    }

    double totalWeight = 0.0;
    int totalPoints = 0;

    for (Assignment assignment : assignments) {
      tableModel.addRow(new Object[] {
          assignment.getId(),
          assignment.getRequestId(),
          assignment.getCourierId(),
          assignment.getStatus(),
          assignment.getAcceptedAt() != null ? assignment.getAcceptedAt().toString() : "Not Accepted",
          assignment.getCollectedAt() != null ? assignment.getCollectedAt().toString() : "Not Collected",
          assignment.getWeight() != null ? assignment.getWeight() : 0.0,
          assignment.getPoints() != null ? assignment.getPoints() : 0
      });

      // Hitung total berat dan poin
      if (assignment.getWeight() != null) {
        totalWeight += assignment.getWeight();
      }
      if (assignment.getPoints() != null) {
        totalPoints += assignment.getPoints();
      }
    }

    lblTotals.setText("Total Weight: " + totalWeight + " kg | Total Points: " + totalPoints);
  }

  private void loadAssignmentData() {
    tableModel.setRowCount(0); // Bersihkan tabel
    List<Assignment> assignments = assignmentController.getAllAssignments();

    double totalWeight = 0.0;
    int totalPoints = 0;

    for (Assignment assignment : assignments) {
      tableModel.addRow(new Object[] {
          assignment.getId(),
          assignment.getRequestId(),
          assignment.getCourierId(),
          assignment.getStatus(),
          assignment.getAcceptedAt() != null ? assignment.getAcceptedAt().toString() : "Not Accepted",
          assignment.getCollectedAt() != null ? assignment.getCollectedAt().toString() : "Not Collected",
          assignment.getWeight() != null ? assignment.getWeight() : 0.0,
          assignment.getPoints() != null ? assignment.getPoints() : 0
      });

      // Hitung total berat dan poin
      if (assignment.getWeight() != null) {
        totalWeight += assignment.getWeight();
      }
      if (assignment.getPoints() != null) {
        totalPoints += assignment.getPoints();
      }
    }

    lblTotals.setText("Total Weight: " + totalWeight + " kg | Total Points: " + totalPoints);
  }

  private void openAssignmentForm(Assignment assignment) {
    JDialog formDialog = new JDialog(this, "Assignment Form", true);
    formDialog.setSize(400, 300);
    formDialog.setLayout(new GridLayout(4, 2));
    formDialog.setLocationRelativeTo(this);

    JLabel lblRequestId = new JLabel("Request ID:");
    JTextField txtRequestId = new JTextField();
    JLabel lblCourierId = new JLabel("Courier ID:");
    JTextField txtCourierId = new JTextField();
    JLabel lblStatus = new JLabel("Status:");
    JComboBox<String> cbStatus = new JComboBox<>(new String[] { "assigned", "picked_up", "completed" });

    if (assignment != null) {
      txtRequestId.setText(String.valueOf(assignment.getRequestId()));
      txtCourierId.setText(String.valueOf(assignment.getCourierId()));
      cbStatus.setSelectedItem(assignment.getStatus());
    }

    JButton btnSave = new JButton("Save");
    JButton btnCancel = new JButton("Cancel");

    formDialog.add(lblRequestId);
    formDialog.add(txtRequestId);
    formDialog.add(lblCourierId);
    formDialog.add(txtCourierId);
    formDialog.add(lblStatus);
    formDialog.add(cbStatus);
    formDialog.add(btnSave);
    formDialog.add(btnCancel);

    btnSave.addActionListener(e -> {
      try {
        int requestId = Integer.parseInt(txtRequestId.getText());
        int courierId = Integer.parseInt(txtCourierId.getText());
        String status = (String) cbStatus.getSelectedItem();

        Request request = requestController.getRequestById(requestId);
        if (request == null) {
          JOptionPane.showMessageDialog(formDialog, "Request ID not found in requests table.", "Error",
              JOptionPane.ERROR_MESSAGE);
          return;
        }

        if (assignment == null) {
          assignmentController.addAssignment(new Assignment(0, requestId, courierId, status));
        } else {
          assignment.setRequestId(requestId);
          assignment.setCourierId(courierId);
          assignment.setStatus(status);
          assignmentController.updateAssignment(assignment);
        }

        loadAssignmentData();
        formDialog.dispose();
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(formDialog, "Request ID and Courier ID must be numbers.", "Error",
            JOptionPane.ERROR_MESSAGE);
      }
    });

    btnCancel.addActionListener(e -> formDialog.dispose());

    formDialog.setVisible(true);
  }

  private void openCollectionForm(int assignmentId) {
    JDialog formDialog = new JDialog(this, "Add Collection", true);
    formDialog.setSize(300, 200);
    formDialog.setLayout(new GridLayout(3, 2));
    formDialog.setLocationRelativeTo(this);

    JLabel lblWeight = new JLabel("Weight (kg):");
    JTextField txtWeight = new JTextField();
    JLabel lblPoints = new JLabel("Points:");
    JTextField txtPoints = new JTextField();

    JButton btnSave = new JButton("Save");
    JButton btnCancel = new JButton("Cancel");

    formDialog.add(lblWeight);
    formDialog.add(txtWeight);
    formDialog.add(lblPoints);
    formDialog.add(txtPoints);
    formDialog.add(btnSave);
    formDialog.add(btnCancel);

    btnSave.addActionListener(e -> {
      try {
        double weight = Double.parseDouble(txtWeight.getText());
        int points = Integer.parseInt(txtPoints.getText());
        if (weight < 0 || points < 0) {
          throw new IllegalArgumentException("Weight and points must be positive numbers.");
        }
        assignmentController.addCollection(assignmentId, weight, points);
        loadAssignmentData(); // Refresh tabel
        formDialog.dispose();
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(formDialog, "Invalid input. Please enter valid numbers for weight and points.",
            "Error", JOptionPane.ERROR_MESSAGE);
      } catch (IllegalArgumentException ex) {
        JOptionPane.showMessageDialog(formDialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
    });

    btnCancel.addActionListener(e -> formDialog.dispose());

    formDialog.setVisible(true);
  }
}
