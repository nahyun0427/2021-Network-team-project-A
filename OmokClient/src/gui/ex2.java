package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.*;
import java.awt.*;

public class ex2 extends JFrame {// ȸ������ȭ��
	public ex2() {
		JPanel p = new JPanel();
		Label l1 = new Label("Name");
		Label l2 = new Label("ID");
		Label l3 = new Label("Password");
		Label l4 = new Label("Nickname");
		Label l5 = new Label("E-mail");
		Label l6 = new Label("SNS");

		add(l1);
		add(l2);
		add(l3);
		add(l4);
		add(l5);
		add(l6);

		TextField t1 = new TextField();
		TextField t2 = new TextField();
		TextField t3 = new TextField();
		TextField t4 = new TextField();
		TextField t5 = new TextField();
		TextField t6 = new TextField();

		add(t1);
		add(t2);
		add(t3);
		add(t4);
		add(t5);
		add(t6);

		t3.setEchoChar('*');
		JButton j1 = new JButton("����");
		JButton j2 = new JButton("���");
		add(j1);
		add(j2);
		l1.setBounds(40, 10, 40, 40);
		l2.setBounds(40, 50, 40, 40);
		l3.setBounds(40, 90, 60, 40);
		l4.setBounds(40, 130, 60, 40);
		l5.setBounds(40, 170, 40, 40);
		l6.setBounds(40, 210, 40, 40);

		t1.setBounds(120, 10, 200, 30);
		t2.setBounds(120, 50, 200, 30);
		t3.setBounds(120, 90, 200, 30);
		t4.setBounds(120, 130, 200, 30);
		t5.setBounds(120, 180, 200, 30);
		t6.setBounds(120, 220, 200, 30);

		j1.setBounds(125, 300, 80, 30);
		j1.setFont(new Font("���� ���", Font.BOLD, 12));
		j2.setBounds(240, 300, 80, 30);
		j2.setFont(new Font("���� ���", Font.BOLD, 12));
		
		add(p);
		setSize(500, 500);
		setTitle("register");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		j1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent T) {// ȸ������ ������ ����
				String s;
				String[] array;
				boolean isDuplicate = false; //�ߺ� ���θ� Ȯ���ϱ� ���� �Ҹ� Ÿ�� ����
				
				try {
					BufferedWriter bos = new BufferedWriter(new FileWriter("ȸ�����.txt", true));
	   				BufferedReader br = new BufferedReader(new FileReader("ȸ�����.txt"));

					while ((s = br.readLine()) != null) {
						array = s.split("/");
						if (array[1].equals(t2.getText())) { // �̹� �����ϴ� ���̵� �Է��ߴ��� Ȯ��
							isDuplicate = true;
							break;
						}
					}
					if(isDuplicate == false) { //�ߺ��� ���� ���
						bos.write(t1.getText() + "/");
						bos.write(t2.getText() + "/");
						bos.write(t3.getText() + "/");
						bos.write(t4.getText() + "/");
						bos.write(t5.getText() + "/");
						bos.write(t6.getText() + "/");
						bos.write(0 + "/"); //�¸� ��. 0���� �ʱ�ȭ ���� ����.
						bos.write(0 + "\r\n"); //�й� ��. 0���� �ʱ�ȭ ���� ����.
						bos.close();
						JOptionPane.showMessageDialog(null, "ȸ������ ����!");
						dispose();	
					}
					else { //�ߺ��� �ִ� ��� -> ���Է� ��û
						JOptionPane.showMessageDialog(null, "ID�ߺ�. �ٸ� ID�� �õ����ּ���.");
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "failure");
				}
			}
		});
		j2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent T2) {// ȸ������â �ݱ�
				// TODO Auto-generated method stub
				dispose();
			}
		});
		;
	}
}
