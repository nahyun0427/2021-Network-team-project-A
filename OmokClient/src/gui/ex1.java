package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

public class ex1 extends JFrame {// �α���ȭ��

	public ex1() {
		JPanel p = new JPanel();
		p.setLayout(null);
		Label b2 = new Label("ID:");
		add(b2);
		Label b3 = new Label("Password:");
		add(b3);
		TextField b4 = new TextField();
		add(b4);
		TextField b5 = new TextField();
		add(b5);
		b5.setEchoChar('*');// ��ȣȭ
		JButton b6 = new JButton("�α���");
		add(b6);
		JButton b7 = new JButton("ȸ������");
		add(b7);

		b2.setBounds(40, 65, 40, 40);
		b3.setBounds(40, 105, 60, 40);
		b4.setBounds(150, 65, 200, 30);
		b5.setBounds(150, 105, 200, 30);
		b6.setBounds(70, 165, 100, 30);
		b6.setFont(new Font("���� ���", Font.BOLD, 14));
		b7.setBounds(250, 165, 100, 30);
		b7.setFont(new Font("���� ���", Font.BOLD, 14));


		add(p);
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("login");
		setVisible(true);
		b7.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {// ȸ������â���� �̵�
				// TODO Auto-generated method stub
				ex2 f2 = new ex2();
			}
		});
		;
		b6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e2) {// �α��� �Ҷ�
				// TODO Auto-generated method stub
				try {
					String s;
					String[] array;
					BufferedReader bos = new BufferedReader(new FileReader("ȸ�����.txt"));
					int flag = 0;
					while ((s = bos.readLine()) != null) {
						array = s.split("/");
						if (array[1].equals(b4.getText()) && array[2].equals(b5.getText())) {
							flag = 1;	// �α��� ����
							break;
						} else {
							flag = 0;	// �α��� ����
						}
					}
					if (flag == 0) { // �α��� ����
						JOptionPane.showMessageDialog(null, "ID / ��й�ȣ�� Ȯ�����ּ���.");
					}
						
					else { // �α��� ����
						JOptionPane.showMessageDialog(null, "ȯ���մϴ�!");
						Omok.Main OM = new Omok.Main();
						OM.run();
						dispose();
					}

					bos.close();
				} catch (IOException E10) {
					E10.printStackTrace();
				}
			}
		});
	}
}
