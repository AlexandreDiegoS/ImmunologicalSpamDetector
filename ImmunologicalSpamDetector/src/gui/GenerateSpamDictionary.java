package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class GenerateSpamDictionary extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	/**
	 * Create the frame.
	 */
	public GenerateSpamDictionary() {
	
		setIconImage(Toolkit.getDefaultToolkit().createImage("res/guiImages/dictionary.png"));
		setTitle("Generating...");
		setResizable(false);
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 675, 145);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel lblGeneratingSpamDictionary = new JLabel("Generating dictionary. Wait a moment please!");
		lblGeneratingSpamDictionary.setFont(new Font("SimSun-ExtB", Font.PLAIN, 25));
		lblGeneratingSpamDictionary.setHorizontalAlignment(SwingConstants.CENTER);
		lblGeneratingSpamDictionary.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("res/guiImages/dictionary.png")));
		contentPane.add(lblGeneratingSpamDictionary, BorderLayout.CENTER);
	}
}
