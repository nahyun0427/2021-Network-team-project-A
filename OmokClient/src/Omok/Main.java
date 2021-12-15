package Omok;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main extends JFrame {

	String myblack = "/black1.png";
	String mywhite = "/white1.png";
	private JPanel contentPane;
	private JTextField nametext;
	private OmokClient client = new OmokClient("���� ����");

	public void run() {
		try {
			Main frame = new Main();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Main() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// �ڹ� ������ ����
		setUndecorated(true);
		setLocationRelativeTo(null);

		// ���α׷� ���� ��ư
		JLabel close = new JLabel("");
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/close-hover.png")));
			}

			public void mouseExited(MouseEvent arg0) {
				close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/close.png")));
			}

			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
			}
		});
		close.setIcon(new ImageIcon(Main.class.getResource("/close.png")));
		close.setBounds(365, 7, 28, 30);
		contentPane.add(close);

		///////////////

		nametext = new JTextField();
		nametext.setFont(new Font("���� ���", Font.BOLD, 15));
		nametext.setBounds(202, 119, 149, 35);
		contentPane.add(nametext);
		nametext.setColumns(10);
		JLabel setup = new JLabel("");
		JLabel double_ = new JLabel("");
		// �����ϱ� ��ư
		JLabel join = new JLabel("");
		join.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				join.setIcon(new javax.swing.ImageIcon(getClass().getResource("/join-hover.png")));
			}

			public void mouseExited(MouseEvent arg0) {
				join.setIcon(new javax.swing.ImageIcon(getClass().getResource("/join.png")));
			}

			public void mouseClicked(MouseEvent arg0) {
				boolean exist = false;
				String nickname = "";
				String s;
				String[] array;
				try {
					BufferedReader bos = new BufferedReader(new FileReader("ȸ�����.txt"));

					while ((s = bos.readLine()) != null) {
						array = s.split("/");
						if (array[1].equals(nametext.getText())) { // �����ϴ� ID�� ��Ȯ�� �Է��� ��� <-id�� ����ũ�� Ű�̹Ƿ� id�� �Է��ϰ� ��.
							nickname = array[3]; // �г��� ����
							exist = true;
						} else { // ���� ID�� �Է��� ���. ���Է��ϰ� �ؾ���
							continue;

						}
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (HeadlessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (exist) {
					client.nameBox.setText(nickname);
					client.setSize(1280, 1000);
					client.setVisible(false);
					client.connect();
					// client.nameBox.setText(nametext.getText());
					client.goToWaitRoom();
					client.startButton.setEnabled(false);
					client.stopButton.setEnabled(false);
					client.setVisible(true);
					setVisible(false);
				} else { // ������� �κ�
					JOptionPane.showMessageDialog(null, "�ش� ID�� �����ϴ�. �ٽ� �Է����ּ���.");
				}

			}

		});
		join.setIcon(new

		ImageIcon(Main.class.getResource("/join.png")));
		join.setBounds(107, 194, 179, 63);
		contentPane.add(join);

		JLabel background_d = new JLabel("");
		background_d.setIcon(new

		ImageIcon(Main.class.getResource("/background_d2.png")));
		background_d.setBounds(0, 0, 400, 300);
		contentPane.add(background_d);

		join.setVisible(false);
		nametext.setVisible(false);
		background_d.setVisible(false);
		//////////////////////

		// ���� �� ��ư
		JLabel deco = new JLabel("");
		// �⺻ �� ��ư
		JLabel basic = new JLabel("");
		deco.setVisible(false);
		basic.setVisible(false);
		basic.addMouseListener(new

		MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				basic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basic-hover.png")));
			}

			public void mouseExited(MouseEvent arg0) {
				basic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basic.png")));
			}

			public void mouseClicked(MouseEvent arg0) {
				double_.setVisible(true);
				setup.setVisible(true);
				basic.setVisible(false);
				deco.setVisible(false);
				myblack = "/black1.png";
				mywhite = "/white1.png";
			}
		});
		basic.setIcon(new

		ImageIcon(Main.class.getResource("/basic.png")));
		basic.setBounds(106, 80, 179, 65);
		contentPane.add(basic);

		deco.addMouseListener(new

		MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				deco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/deco-hover.png")));
			}

			public void mouseExited(MouseEvent arg0) {
				deco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/deco.png")));
			}

			public void mouseClicked(MouseEvent arg0) {
				double_.setVisible(true);
				setup.setVisible(true);
				basic.setVisible(false);
				deco.setVisible(false);
				myblack = "/black2.png";
				mywhite = "/white2.png";
			}
		});
		deco.setIcon(new

		ImageIcon(Main.class.getResource("/deco.png")));
		deco.setBounds(107, 183, 179, 63);
		contentPane.add(deco);

		double_.addMouseListener(new

		MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				double_.setIcon(new javax.swing.ImageIcon(getClass().getResource("/double-hover.png")));
			}

			public void mouseExited(MouseEvent arg0) {
				double_.setIcon(new javax.swing.ImageIcon(getClass().getResource("/double.png")));
			}

			public void mouseClicked(MouseEvent arg0) {
				// ���� �ϱ�
				double_.setVisible(false);
				setup.setVisible(false);
				join.setVisible(true);
				nametext.setVisible(true);
				background_d.setVisible(true);
				OmokClient.myblack = myblack;
				OmokClient.mywhite = mywhite;
			}
		});
		double_.setIcon(new

		ImageIcon(Main.class.getResource("/double.png")));
		double_.setBounds(106, 144, 179, 65);
		contentPane.add(double_);

		setup.addMouseListener(new

		MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				setup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setup-hover.png")));
			}

			public void mouseExited(MouseEvent arg0) {
				setup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setup.png")));
			}

			public void mouseClicked(MouseEvent arg0) {
				double_.setVisible(false);
				setup.setVisible(false);
				basic.setVisible(true);
				deco.setVisible(true);
			}
		});
		setup.setIcon(new

		ImageIcon(Main.class.getResource("/setup.png")));
		setup.setBounds(106, 221, 179, 65);
		contentPane.add(setup);

		JLabel background = new JLabel("");
		background.setIcon(new

		ImageIcon(Main.class.getResource("/background.png")));
		background.setBounds(0, 0, 400, 300);
		contentPane.add(background);
	}
}
