package lms.menu;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import lms.interfaces.NotificationObserver;
import lms.interfaces.UserOperations;
import lms.manager.UserManager;
import lms.model.AdminUser;
import lms.model.MemberUser;
import lms.model.User;
import lms.observer.NotificationManager;

public class MainMenu extends javax.swing.JFrame {
    private final UserOperations userOperations = UserManager.getInstance();
    
    private String[] PromptLogin() {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        JTextField idField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        // Prompt login pane
        if (JOptionPane.showConfirmDialog(null, panel, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) != JOptionPane.OK_OPTION) return null;

        String id = idField.getText();
        String password = new String(passwordField.getPassword());
        return new String[]{id, password};
    }
    
    
    public MainMenu() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton_AdminPanel = new javax.swing.JButton();
        jButton_MemberPanel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton_AdminPanel.setText("Admin Panel");
        jButton_AdminPanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_AdminPanelActionPerformed(evt);
            }
        });

        jButton_MemberPanel.setText("Member Panel");
        jButton_MemberPanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_MemberPanelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_MemberPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_AdminPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(77, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jButton_AdminPanel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_MemberPanel)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_AdminPanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_AdminPanelActionPerformed
        String[] result = PromptLogin();
        if (result == null) return;
        
        User user = userOperations.login(result[0], result[1]);
        if (user == null) {
            JOptionPane.showMessageDialog(null, "Incorrect login!", "", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (user instanceof AdminUser adminUser) {
            AdminMenu adminMenu = new AdminMenu(adminUser);
            adminMenu.setTitle("Library Management System - Admin Menu");
            adminMenu.setLocationRelativeTo(null);  // Center menu
            adminMenu.setVisible(true); // Make menu visible
        }
        else {
            JOptionPane.showMessageDialog(null, "No access!", "", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton_AdminPanelActionPerformed

    private void jButton_MemberPanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_MemberPanelActionPerformed
        String[] result = PromptLogin();
        if (result == null) return;
        
        User user = userOperations.login(result[0], result[1]);
        if (user == null) {
            JOptionPane.showMessageDialog(null, "Incorrect login!", "", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (user instanceof MemberUser memberUser) {
            NotificationObserver notifyObserver = new NotificationManager();
            notifyObserver.addObserver(user);
            notifyObserver.checkMemberBooks(user);
            
            MemberMenu memberMenu = new MemberMenu(memberUser);
            memberMenu.setTitle("Library Management System - Member Menu");
            memberMenu.setLocationRelativeTo(null);  // Center menu
            memberMenu.setVisible(true); // Make menu visible
        }
    }//GEN-LAST:event_jButton_MemberPanelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_AdminPanel;
    private javax.swing.JButton jButton_MemberPanel;
    // End of variables declaration//GEN-END:variables
}
