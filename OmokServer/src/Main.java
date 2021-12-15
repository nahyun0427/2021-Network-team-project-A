import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.DropMode;
import java.awt.Color;
import javax.swing.JScrollBar;

// ������ ������ Ŭ����. ���� ���� ������ OmokServer�� �̿�
public class Main extends JFrame implements Runnable {

	static JTextArea textArea = new JTextArea();
	OmokServer OmokServer = new OmokServer();
	Thread t2 = new Thread(OmokServer);
	private JPanel contentPane;

	public void run() {
		try {
			Main frame = new Main();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ���� ���α׷� ����
	public static void main(String[] args) {
		Thread t1 = new Thread(new Main());
		t1.start();
	}

	// JFrame ����
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textArea.setFont(new Font("���� ���", Font.BOLD, 13));
		textArea.setEditable(false);
		textArea.setLineWrap(true);

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(33, 129, 338, 161);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setVisible(false);
		contentPane.add(scrollPane);

		JLabel operateAction = new JLabel("");
		operateAction.setVisible(false);

		// ���� ���� ��ư
		JLabel operate = new JLabel("");
		operate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				operate.setIcon(new javax.swing.ImageIcon(getClass().getResource("operate-hover.png")));
			}

			public void mouseExited(MouseEvent arg0) {
				operate.setIcon(new javax.swing.ImageIcon(getClass().getResource("operate.png")));
			}

			public void mouseClicked(MouseEvent arg0) {
				// ������ �����Ѵ�.
				t2.start();
				scrollPane.setVisible(true);
				operateAction.setVisible(true);
			}
		});
		operate.setIcon(new ImageIcon(Main.class.getResource("operate.png")));
		operate.setBounds(113, 129, 184, 57);
		contentPane.add(operate);

		// ���� ��� ��ư
		operateAction.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				operateAction.setIcon(new javax.swing.ImageIcon(getClass().getResource("operate-stop-hover.png")));
			}

			public void mouseExited(MouseEvent arg0) {
				operateAction.setIcon(new javax.swing.ImageIcon(getClass().getResource("operate-stop.png")));
			}

			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
			}
		});
		operateAction.setIcon(new ImageIcon(Main.class.getResource("operate-stop.png")));
		operateAction.setBounds(110, 54, 184, 63);
		contentPane.add(operateAction);

		// ���α׷� ���� ��ư
		JLabel close = new JLabel("");
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				close.setIcon(new javax.swing.ImageIcon(getClass().getResource("close-hover.png")));
			}

			public void mouseExited(MouseEvent arg0) {
				close.setIcon(new javax.swing.ImageIcon(getClass().getResource("close.png")));
			}

			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
			}
		});
		close.setIcon(new ImageIcon(Main.class.getResource("close.png")));
		close.setBounds(365, 7, 28, 30);
		contentPane.add(close);

		// ��� ȭ�� ����
		JLabel background = new JLabel("");
		background.setIcon(new ImageIcon(Main.class.getResource("background.png")));
		background.setBounds(0, 0, 400, 300);
		contentPane.add(background);

		// �ڹ� ������ ����
		setUndecorated(true);
		setLocationRelativeTo(null);
	}
}