import java.net.*;
import java.io.*;
import java.util.*;

public class OmokServer implements Runnable { // ������ �����ϰ� ���ִ� Ŭ����

	private ServerSocket server; // ���� ����
	private Massenger Man = new Massenger(); // �޼��� ������
	private Random rnd = new Random(); // ��� ���� �������� ��

	void startServer() { // ������ �����ϴ� �Լ�
		try {
			server = new ServerSocket(9735);
			Main.textArea.append("������ ���۽�ŵ�ϴ�.\n������ ����Ǿ����ϴ�.\n");
			while (true) {
				Socket socket = server.accept(); // Ŭ���̾�Ʈ�� ����� ������ ȹ��

				controller con = new controller(socket); // �����带 ����� ����
				con.start();

				Man.add(con); // bMan�� �����带 �߰��Ѵ�.
				Main.textArea.append("���� " + Man.size() + "���� ������ �ֽ��ϴ�.\n");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	class controller extends Thread { // Ŭ���̾�Ʈ�� ����ϴ� ������ Ŭ����
		private int roomNumber = -1; // �� ��ȣ
		private String userName = null; // ����� �̸�
		private Socket socket; // ����

		private boolean ready = false; // ���� �غ� ����: true�̸� ������ ������ �غ� �Ǿ����� �ǹ�
		private BufferedReader reader; // �Է� ��Ʈ��
		private PrintWriter writer; // ��� ��Ʈ��

		controller(Socket socket) { // ������
			this.socket = socket;
		}

		Socket getSocket() { // ������ ��ȯ
			return socket;
		}

		int getRoomNumber() { // �� ��ȣ�� ��ȯ
			return roomNumber;
		}

		String getUserName() { // ����� �̸��� ��ȯ
			return userName;
		}

		boolean isReady() { // �غ� ���¸� ��ȯ
			return ready;
		}

		public void run() {
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new PrintWriter(socket.getOutputStream(), true);
				String msg; // Ŭ���̾�Ʈ�� �޼���
				while ((msg = reader.readLine()) != null) {
					if (msg.startsWith("[NAME]")) { // msg�� "[NAME]"���� ���۵Ǵ� �޼����̸�
						userName = msg.substring(6); // userName�� ����
					}

					else if (msg.startsWith("[ROOM]")) { // msg�� "[ROOM]"���� ���۵Ǹ� �� ��ȣ�� ����
						int roomNum = Integer.parseInt(msg.substring(6));

						if (!Man.isFull(roomNum)) { // ���� �� ���°� �ƴϸ� (2���� �ȳ�����)
							// ���� ���� �ٸ� ��뿡�� ������� ������ �˸���.
							if (roomNumber != -1)
								Man.sendToOthers(this, "[EXIT]" + userName);

							roomNumber = roomNum; // ������� �� �� ��ȣ�� �����Ѵ�.
							writer.println(msg); // ����ڿ��� �޽����� �״�� �����Ͽ� ������ �� ������ �˸�
							writer.println(Man.getNamesInRoom(roomNumber)); // ����ڿ��� �� �濡 �ִ� ����� �̸� ����Ʈ�� ����
							Man.sendToOthers(this, "[ENTER]" + userName); // �� �濡 �ִ� �ٸ� ����ڿ��� ������� ������ �˸�
						} else
							writer.println("[FULL]"); // ����ڿ� ���� á���� �˸�
					}

					else if (roomNumber >= 1 && msg.startsWith("[STONE]")) // "[STONE]" �޽����� ������� ����
						Man.sendToOthers(this, msg);

					else if (msg.startsWith("[MSG]")) // ��ȭ �޼����� �濡 ����
						Man.sendToRoom(roomNumber, "[" + userName + "]: " + msg.substring(5));

					else if (msg.startsWith("[START]")) { // "[START]" �޽����̸�
						ready = true; // ������ ������ �غ� ��

						if (Man.isReady(roomNumber)) { // �ٸ� ����ڵ� ������ ������ �غ� �Ǿ�����
							int a = rnd.nextInt(2); // ��� ���� ���Ƿ� ���ϰ� ����ڿ� ������� �˷���
							if (a == 0) {
								writer.println("[COLOR]BLACK");
								Man.sendToOthers(this, "[COLOR]WHITE");
							} else {
								writer.println("[COLOR]WHITE");
								Man.sendToOthers(this, "[COLOR]BLACK");
							}
						}
					}

					else if (msg.startsWith("[STOPGAME]")) // ����ڰ� ������ �����ϴ� �޽����� ������
						ready = false;

					else if (msg.startsWith("[DROPGAME]")) { // ����ڰ� ������ ����ϴ� �޽����� ������
						ready = false;
						Man.sendToOthers(this, "[DROPGAME]"); // ������� ������� ����� �˸�
					}

					else if (msg.startsWith("[WIN]")) { //
						ready = false;
						writer.println("[WIN]"); // ����ڰ� �̰����� ����ڿ��� �̰�ٴ� �޽����� ����
						Man.sendToOthers(this, "[LOSE]"); // ������� ������ �˸�
					}
				}
			} catch (Exception e) {
			} finally {
				try {
					Man.remove(this);
					if (reader != null)
						reader.close();
					if (writer != null)
						writer.close();
					if (socket != null)
						socket.close();
					reader = null;
					writer = null;
					socket = null;
					if (userName == null) // �̸��� null�̸� ������ ����
						userName = "�ſ� �Ҹ��� �����";
					Main.textArea.append(userName + "���� ������ �������ϴ�.\n");
					Main.textArea.append("���� " + Man.size() + "���� ������ �ֽ��ϴ�.\n");
					Man.sendToRoom(roomNumber, "[DISCONNECT]" + userName); // ����ڰ� ������ �������� ���� �濡 �˸�
				} catch (Exception e) {
				}
			}
		}
	}

	class Massenger extends Vector { // �޼����� �����ϴ� Ŭ����
		void add(controller con) { // �����带 �߰�
			super.add(con);
		}

		void remove(controller con) { // �����带 ����
			super.remove(con);
		}

		controller getOT(int i) { // i��° �����带 ��ȯ
			return (controller) elementAt(i);
		}

		Socket getSocket(int i) { // i��° �������� ������ ��ȯ
			return getOT(i).getSocket();
		}

		// i��° ������� ����� Ŭ���̾�Ʈ���� �޼����� �����Ѵ�.
		void sendTo(int i, String msg) {
			try {
				PrintWriter pw = new PrintWriter(getSocket(i).getOutputStream(), true);
				pw.println(msg);
			} catch (Exception e) {
			}
		}

		int getRoomNumber(int i) { // i��° �������� �� ��ȣ�� ��ȯ
			return getOT(i).getRoomNumber();
		}

		synchronized boolean isFull(int roomNum) { // ���� á���� Ȯ����
			if (roomNum == 0)
				return false; // ������ �ο��� ������ ����

			int count = 0;
			for (int i = 0; i < size(); i++)
				if (roomNum == getRoomNumber(i))
					count++;
			if (count >= 2)
				return true;
			return false;
		}

		void sendToRoom(int roomNum, String msg) { // roomNum �濡 msg�� ����
			for (int i = 0; i < size(); i++)
				if (roomNum == getRoomNumber(i))
					sendTo(i, msg);
		}

		void sendToOthers(controller ot, String msg) { // ot�� ���� �濡 �ִ� �ٸ� ����ڿ��� msg�� ����
			for (int i = 0; i < size(); i++)
				if (getRoomNumber(i) == ot.getRoomNumber() && getOT(i) != ot)
					sendTo(i, msg);
		}

		synchronized boolean isReady(int roomNum) { // �� ����� ��� ������ ������ �غ� �Ǿ����� true�� ��ȯ
			int count = 0;
			for (int i = 0; i < size(); i++)
				if (roomNum == getRoomNumber(i) && getOT(i).isReady())
					count++;
			if (count == 2)
				return true;
			return false;
		}

		String getNamesInRoom(int roomNum) { // roomNum�濡 �ִ� ����ڵ��� �̸��� ��ȯ
			StringBuffer sb = new StringBuffer("[PLAYERS]");
			for (int i = 0; i < size(); i++)
				if (roomNum == getRoomNumber(i))
					sb.append(getOT(i).getUserName() + "\t");
			return sb.toString();
		}
	}

	@Override
	public void run() {
		OmokServer server = new OmokServer();
		server.startServer();
	}

	public void out() throws IOException {
		this.server.close();
	}
}