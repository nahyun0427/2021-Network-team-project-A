package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.*;
import java.awt.*;

public class ex2 extends JFrame {// 회원가입화면
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
		JButton j1 = new JButton("저장");
		JButton j2 = new JButton("취소");
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
		j1.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		j2.setBounds(240, 300, 80, 30);
		j2.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		
		add(p);
		setSize(500, 500);
		setTitle("register");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		j1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent T) {// 회원가입 데이터 저장
				String s;
				String[] array;
				boolean isDuplicate = false; //중복 여부를 확인하기 위한 불린 타입 변수
				
				try {
					BufferedWriter bos = new BufferedWriter(new FileWriter("회원명단.txt", true));
	   				BufferedReader br = new BufferedReader(new FileReader("회원명단.txt"));

					while ((s = br.readLine()) != null) {
						array = s.split("/");
						if (array[1].equals(t2.getText())) { // 이미 존재하는 아이디를 입력했는지 확인
							isDuplicate = true;
							break;
						}
					}
					if(isDuplicate == false) { //중복이 없는 경우
						bos.write(t1.getText() + "/");
						bos.write(t2.getText() + "/");
						bos.write(t3.getText() + "/");
						bos.write(t4.getText() + "/");
						bos.write(t5.getText() + "/");
						bos.write(t6.getText() + "/");
						bos.write(0 + "/"); //승리 수. 0으로 초기화 시켜 가입.
						bos.write(0 + "\r\n"); //패배 수. 0으로 초기화 시켜 가입.
						bos.close();
						JOptionPane.showMessageDialog(null, "회원가입 성공!");
						dispose();	
					}
					else { //중복이 있는 경우 -> 재입력 요청
						JOptionPane.showMessageDialog(null, "ID중복. 다른 ID로 시도해주세요.");
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "failure");
				}
			}
		});
		j2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent T2) {// 회원가입창 닫기
				// TODO Auto-generated method stub
				dispose();
			}
		});
		;
	}
}
