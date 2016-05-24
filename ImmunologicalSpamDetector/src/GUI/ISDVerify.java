package GUI;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main.ImmunologicalSpamDetector;
import util.MapValidEmails;
import util.file.IOManipulation;

import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ISDVerify extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tblResults;
	private JFrame main;
	private List<MapValidEmails> results;
	private JButton btnViewEmail;
	/**
	 * Create the frame.
	 * @param frmImmunologicalSpamDetector 
	 * @param frmImmunologicalSpamDetector 
	 */
	public ISDVerify(JFrame frmImmunologicalSpamDetector, String filename) {
		addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent arg0) {
				frmImmunologicalSpamDetector.setVisible(true);
			}
		});
		results = new ArrayList<MapValidEmails>();
		main = frmImmunologicalSpamDetector;
		setIconImage(Toolkit.getDefaultToolkit().getImage(ISDVerify.class.getResource("/GUI/img/verify.png")));
		setTitle("ISD ~ Verify Email");
		setResizable(false);
		setBackground(Color.WHITE);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 550, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel lblAnalysing = new JLabel("Analysing emails...");
		lblAnalysing.setIcon(new ImageIcon(ISDVerify.class.getResource("/GUI/img/analysing.png")));
		lblAnalysing.setHorizontalAlignment(SwingConstants.CENTER);
		lblAnalysing.setFont(new Font("SimSun", Font.PLAIN, 30));
		contentPane.add(lblAnalysing, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		tblResults = new JTable();
		tblResults.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblResults.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Email filename", "Is spam", "Email body"
				}
			) {
				private static final long serialVersionUID = -2008352559678302265L;
				Class<?>[] columnTypes = new Class[] {
					String.class, Boolean.class, String.class
				};
				public Class<?> getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				boolean[] columnEditables = new boolean[] {
					false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
		tblResults.getColumnModel().getColumn(0).setPreferredWidth(350);
		tblResults.getColumnModel().getColumn(0).setMinWidth(150);
		tblResults.getColumnModel().getColumn(0).setMaxWidth(450);
		tblResults.getColumnModel().getColumn(1).setPreferredWidth(150);
		tblResults.getColumnModel().getColumn(1).setMinWidth(50);
		tblResults.getColumnModel().getColumn(1).setMaxWidth(350);
		tblResults.getColumnModel().getColumn(2).setPreferredWidth(0);
		tblResults.getColumnModel().getColumn(2).setMinWidth(0);
		tblResults.getColumnModel().getColumn(2).setMaxWidth(0);
		DefaultTableModel model = (DefaultTableModel) tblResults.getModel();
		List<File> emails = IOManipulation.listFilesInDirectory(filename);
		new Timer(3000, null);
		new Thread(new Runnable() {
			public void run() {
				for(File f : emails){
					results = ImmunologicalSpamDetector.verifyEmailByLocation(f.getAbsolutePath());
					lblAnalysing.setText("Results...");
					lblAnalysing.setIcon(new ImageIcon(ISDVerify.class.getResource("/GUI/img/analysing.png")));
					if(results != null){
						btnViewEmail.setEnabled(true);
						for(MapValidEmails email : results){
							Object[] line = new Object[] {email.getEmailFilename(), !email.isValid(), email.getEmailBody()};
							model.addRow(line);
						}
					}
				}
			}
		}).start();
		tblResults.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 16));
		tblResults.setBackground(Color.WHITE);
		scrollPane.setViewportView(tblResults);
		
		btnViewEmail = new JButton("View Email");
		btnViewEmail.setEnabled(false);
		btnViewEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selectedRow = tblResults.getSelectedRow();
				String emailBody = (String) tblResults.getValueAt(selectedRow, 2);
				new ViewEmail(emailBody).setVisible(true);
			}
		});
		btnViewEmail.setIcon(new ImageIcon(ISDVerify.class.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Break-Black.png")));
		btnViewEmail.setBackground(SystemColor.inactiveCaptionBorder);
		contentPane.add(btnViewEmail, BorderLayout.SOUTH);
	}
	
	public int disposeForMainPage(){
		main.setVisible(true);
		return DISPOSE_ON_CLOSE;
	}

}
