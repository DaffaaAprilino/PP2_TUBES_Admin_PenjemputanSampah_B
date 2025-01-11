package view;

import controller.RequestController;
import controller.AssignmentController;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

  private JLabel lblTotalRequests;
  private JLabel lblTotalAssignments;
  private JLabel lblTotalWeight;
  private JLabel lblTotalPoints;

  public MainMenu() {
    setTitle("Admin Dashboard");
    setSize(1000, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    // Layout utama
    JPanel mainPanel = new JPanel(new BorderLayout());

    // Header
    JPanel headerPanel = new JPanel(new BorderLayout());
    headerPanel.setBackground(new Color(0, 153, 76)); // Hijau
    headerPanel.setPreferredSize(new Dimension(1000, 80));

    JLabel lblTitle = new JLabel("Admin Dashboard", JLabel.CENTER);
    lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
    lblTitle.setForeground(Color.WHITE);
    headerPanel.add(lblTitle, BorderLayout.CENTER);

    JLabel lblAdmin = new JLabel("Welcome, Admin", JLabel.RIGHT);
    lblAdmin.setForeground(Color.WHITE);
    lblAdmin.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
    headerPanel.add(lblAdmin, BorderLayout.EAST);

    mainPanel.add(headerPanel, BorderLayout.NORTH);

    // Sidebar
    JPanel sidebarPanel = new JPanel();
    sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
    sidebarPanel.setBackground(Color.WHITE); // Sidebar putih
    sidebarPanel.setPreferredSize(new Dimension(200, 600));

    String[] buttonLabels = { "Manage Users", "Manage Requests", "Manage Assignments", "Logout" };
    Color[] buttonColors = { new Color(0, 153, 76), new Color(0, 153, 76), new Color(0, 153, 76),
        new Color(255, 51, 51) };

    for (int i = 0; i < buttonLabels.length; i++) {
      JButton button = createSidebarButton(buttonLabels[i], buttonColors[i]);
      sidebarPanel.add(Box.createVerticalStrut(20));
      sidebarPanel.add(button);

      int index = i; // untuk action listener
      button.addActionListener(e -> {
        switch (index) {
          case 0 -> openUserManagement();
          case 1 -> openRequestManagement();
          case 2 -> openAssignmentManagement();
          case 3 -> logout();
        }
      });
    }

    mainPanel.add(sidebarPanel, BorderLayout.WEST);

    // Konten utama
    JPanel contentPanel = new JPanel(new GridLayout(2, 2, 20, 20));
    contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    contentPanel.setBackground(Color.WHITE);

    lblTotalRequests = createStatPanel(contentPanel, "Total Requests");
    lblTotalAssignments = createStatPanel(contentPanel, "Total Assignments");
    lblTotalWeight = createStatPanel(contentPanel, "Total Weight Collected");
    lblTotalPoints = createStatPanel(contentPanel, "Total Points");

    mainPanel.add(contentPanel, BorderLayout.CENTER);

    add(mainPanel);

    // Load statistics from controllers
    loadStatistics();
    setVisible(true);
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

  private JLabel createStatPanel(JPanel parent, String title) {
    JPanel statPanel = new JPanel(new BorderLayout());
    statPanel.setBackground(new Color(230, 230, 230));
    statPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

    JLabel lblStatTitle = new JLabel(title, JLabel.CENTER);
    lblStatTitle.setFont(new Font("Arial", Font.BOLD, 18));
    lblStatTitle.setForeground(new Color(51, 51, 51));
    statPanel.add(lblStatTitle, BorderLayout.NORTH);

    JLabel lblStatValue = new JLabel("Loading...", JLabel.CENTER);
    lblStatValue.setFont(new Font("Arial", Font.PLAIN, 24));
    lblStatValue.setForeground(new Color(0, 102, 204));
    statPanel.add(lblStatValue, BorderLayout.CENTER);

    parent.add(statPanel);
    return lblStatValue;
  }

  private void openUserManagement() {
    new UserManagementView(); // Membuka UserManagementView
    dispose(); // Menutup Main Menu
  }

  private void openRequestManagement() {
    new RequestManagementView(); // Membuka RequestManagementView
    dispose(); // Menutup Main Menu
  }

  private void openAssignmentManagement() {
    new AssignmentManagementView(); // Membuka AssignmentManagementView
    dispose(); // Menutup Main Menu
  }

  private void logout() {
    int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout",
        JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
      dispose();
    }
  }

  private void loadStatistics() {
    RequestController requestController = new RequestController();
    AssignmentController assignmentController = new AssignmentController();

    lblTotalRequests.setText(String.valueOf(requestController.getTotalRequests()));
    lblTotalAssignments.setText(String.valueOf(assignmentController.getTotalAssignments()));
    lblTotalWeight.setText(String.format("%.2f kg", assignmentController.getTotalWeightCollected()));
    lblTotalPoints.setText(String.valueOf(assignmentController.getTotalPoints()));
  }

  public static void main(String[] args) {
    new MainMenu();
  }
}
