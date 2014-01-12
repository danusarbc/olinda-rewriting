package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class mainFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainFrame frame = new mainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public mainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 655, 477);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea globalquerytext = new JTextArea();
		globalquerytext.setBounds(38, 69, 413, 186);
		contentPane.add(globalquerytext);
		
		JLabel lblGlobalQuery = new JLabel("Global Query");
		lblGlobalQuery.setBounds(28, 24, 123, 33);
		contentPane.add(lblGlobalQuery);
		
		JButton btnRewriti = new JButton("Rewrite");
		btnRewriti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnRewriti.setBounds(506, 74, 117, 25);
		contentPane.add(btnRewriti);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(506, 143, 117, 25);
		contentPane.add(btnClear);
	}
}
