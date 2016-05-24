package GUI;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;

public class GenerateDetectors extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	/**
	 * Create the frame.
	 */
	public GenerateDetectors() {
	
		setIconImage(Toolkit.getDefaultToolkit().getImage(GenerateDetectors.class.getResource("/GUI/img/detectorT.png")));
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
		
		JLabel lblGeneratingDetectors = new JLabel("Generating detectors. Wait a moment please!");
		lblGeneratingDetectors.setFont(new Font("SimSun-ExtB", Font.PLAIN, 25));
		lblGeneratingDetectors.setHorizontalAlignment(SwingConstants.CENTER);
		lblGeneratingDetectors.setIcon(new ImageIcon(GenerateDetectors.class.getResource("/GUI/img/detector.png")));
		contentPane.add(lblGeneratingDetectors, BorderLayout.CENTER);
	}
}
