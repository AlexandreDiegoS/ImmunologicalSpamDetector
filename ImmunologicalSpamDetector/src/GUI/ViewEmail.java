package GUI;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import java.awt.Font;

public class ViewEmail extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	/**
	 * Create the frame.
	 */
	public ViewEmail(String emailBody) {
		setBackground(Color.WHITE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ViewEmail.class.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Left-Black@2x.png")));
		setTitle("Read Email");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 625);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setEditable(false);
		editorPane.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		editorPane.setBackground(Color.WHITE);
		editorPane.setText(emailBody);
		scrollPane.setViewportView(editorPane);
		
	}

}
