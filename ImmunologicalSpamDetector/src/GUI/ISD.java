package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import main.ImmunologicalSpamDetector;

import javax.swing.JFileChooser;

import java.awt.Font;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.JMenuBar;
import java.awt.Component;
import java.awt.Desktop;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ISD {

	private JFrame frmImmunologicalSpamDetector;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ISD window = new ISD();
					window.frmImmunologicalSpamDetector.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ISD() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmImmunologicalSpamDetector = new JFrame();
		frmImmunologicalSpamDetector.setResizable(false);
		frmImmunologicalSpamDetector.getContentPane().setBackground(Color.WHITE);
		frmImmunologicalSpamDetector.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setAutoscrolls(true);
		frmImmunologicalSpamDetector.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblImmunologicalSpamDetector = new JLabel("Immunological Spam Detector");
		lblImmunologicalSpamDetector.setBounds(105, 34, 375, 40);
		lblImmunologicalSpamDetector.setHorizontalAlignment(SwingConstants.CENTER);
		lblImmunologicalSpamDetector.setFont(new Font("Candara", Font.BOLD, 25));
		panel.add(lblImmunologicalSpamDetector);
		
		JTextPane txtAbout = new JTextPane();
		txtAbout.setEditable(false);
		txtAbout.setFont(new Font("Serif", Font.PLAIN, 16));
		txtAbout.setText("This system aims to carry out the spam detection in e-mails, it was based on the proposal made by Dr. Luane Cunha, the detection is done by the message body analysis using negative selection technique, characterizing this system with an AIS (Artificial Immune System).\r\n\r\nImplemented by: Airton Soares, Alexandre Silva and Alany Oliveira\r\n\t\t\t\t\t\t\t");
		txtAbout.setBounds(82, 100, 458, 160);
		panel.add(txtAbout);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setName("Menu");
		menuBar.setFont(new Font("Dialog", Font.PLAIN, 12));
		menuBar.setAlignmentY(Component.CENTER_ALIGNMENT);
		menuBar.setForeground(new Color(0, 0, 0));
		menuBar.setBackground(Color.WHITE);
		menuBar.setBounds(6, 330, 117, 35);
		panel.add(menuBar);
		
		JMenu mMenu = new JMenu("Menu");
		mMenu.setFont(new Font("Dialog", Font.PLAIN, 16));
		mMenu.setHorizontalAlignment(SwingConstants.CENTER);
		mMenu.setBackground(Color.WHITE);
		menuBar.add(mMenu);
		
		JMenuItem mMoreAbout = new JMenuItem("More About");
		mMoreAbout.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent arg0) {
				mMoreAbout.setBackground(Color.GRAY);
			}
			public void mouseExited(MouseEvent arg0) {
				mMoreAbout.setBackground(Color.WHITE);
			}
		});
		mMoreAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Desktop desk = Desktop.getDesktop();
				try {
					desk.browse(new URI("http://www.google.com"));
				} catch (IOException | URISyntaxException e) {
					e.printStackTrace();
				}
			}
		});
		mMoreAbout.setBackground(Color.WHITE);
		mMenu.add(mMoreAbout);
		
		JMenuItem mVerify = new JMenuItem("Verify Emails - Using Saved Detectors");
		mVerify.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent arg0) {
				mVerify.setBackground(Color.GRAY);
			}
			public void mouseExited(MouseEvent arg0) {
				mVerify.setBackground(Color.WHITE);
			}
		});
		mVerify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(
						mVerify, "Select one email file or one directory that containing them.", 
						"Verify Emails", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if(option == JOptionPane.OK_OPTION){
					final JFileChooser search = new JFileChooser();
					search.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					search.showOpenDialog(mVerify);
					File selected = search.getSelectedFile();
					if(selected != null){
						String filename = selected.getAbsolutePath();
						ISDVerify newVerification = new ISDVerify(frmImmunologicalSpamDetector, filename);
						newVerification.setVisible(true);
						frmImmunologicalSpamDetector.setVisible(false);
					}
				}
			}
		});
		mVerify.setBackground(Color.WHITE);
		mMenu.add(mVerify);
		
		JMenuItem mVerifyGenerate = new JMenuItem("Verify Emails - Generate New Detectors");
		mVerifyGenerate.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent arg0) {
				mVerifyGenerate.setBackground(Color.GRAY);
			}
			public void mouseExited(MouseEvent arg0) {
				mVerifyGenerate.setBackground(Color.WHITE);
			}
		});
		mVerifyGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GenerateDetectors newDetectors = new GenerateDetectors();
				newDetectors.setVisible(true);
				
				new Thread(new Runnable() {
					public void run() {
						ImmunologicalSpamDetector.generateBaseOfDetectors();
						newDetectors.dispose();

						int option = JOptionPane.showConfirmDialog(
								mVerifyGenerate, "Select one email file or one directory that containing them.", 
								"Verify Emails", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
						if(option == JOptionPane.OK_OPTION){
							final JFileChooser search = new JFileChooser();
							search.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
							search.showOpenDialog(mVerifyGenerate);
							File selected = search.getSelectedFile();
							if(selected != null){
								String filename = selected.getAbsolutePath();
								ISDVerify newVerification = new ISDVerify(frmImmunologicalSpamDetector, filename);
								newVerification.setVisible(true);
								frmImmunologicalSpamDetector.setVisible(false);
							}
						}
					}
				}).start();	
			}
		});
		mVerifyGenerate.setBackground(Color.WHITE);
		mMenu.add(mVerifyGenerate);
		
		JLabel lblCopyright = new JLabel("\u00A9 2016  A.A.A.");
		lblCopyright.setBounds(493, 342, 95, 23);
		panel.add(lblCopyright);
		frmImmunologicalSpamDetector.setIconImage(Toolkit.getDefaultToolkit().getImage(ISD.class.getResource("/GUI/img/negative_icon.png")));
		frmImmunologicalSpamDetector.setMaximumSize(new Dimension(1024, 768));
		frmImmunologicalSpamDetector.setTitle("Immunological Spam Detector");
		frmImmunologicalSpamDetector.setBounds(100, 100, 600, 400);
		frmImmunologicalSpamDetector.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
