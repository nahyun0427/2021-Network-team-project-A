import java.net.*;
import java.io.*;
import java.util.*;

public class OmokServer implements Runnable { // 서버가 동작하게 해주는 클래스

	private ServerSocket server; // 소켓 생성
	private Massenger Man = new Massenger(); // 메세지 전달자
	private Random rnd = new Random(); // 흑과 백을 랜덤으로 고름

	void startServer() { // 서버를 실행하는 함수
		try {
			server = new ServerSocket(9735);
			Main.textArea.append("서버를 동작시킵니다.\n서버가 연결되었습니다.\n");
			while (true) {
				Socket socket = server.accept(); // 클라이언트와 연결된 스레드 획득

				controller con = new controller(socket); // 스레드를 만들고 실행
				con.start();

				Man.add(con); // bMan에 스레드를 추가한다.
				Main.textArea.append("현재 " + Man.size() + "명이 접속해 있습니다.\n");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	class controller extends Thread { // 클라이언트와 통신하는 스레드 클래스
		private int roomNumber = -1; // 방 번호
		private String userName = null; // 사용자 이름
		private Socket socket; // 소켓

		private boolean ready = false; // 게임 준비 여부: true이면 게임을 시작할 준비가 되었음을 의미
		private BufferedReader reader; // 입력 스트림
		private PrintWriter writer; // 출력 스트림

		controller(Socket socket) { // 생성자
			this.socket = socket;
		}

		Socket getSocket() { // 소켓을 반환
			return socket;
		}

		int getRoomNumber() { // 방 번호를 반환
			return roomNumber;
		}

		String getUserName() { // 사용자 이름을 반환
			return userName;
		}

		boolean isReady() { // 준비 상태를 반환
			return ready;
		}

		public void run() {
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new PrintWriter(socket.getOutputStream(), true);
				String msg; // 클라이언트의 메세지
				while ((msg = reader.readLine()) != null) {
					if (msg.startsWith("[NAME]")) { // msg가 "[NAME]"으로 시작되는 메세지이면
						userName = msg.substring(6); // userName에 저장
					}

					else if (msg.startsWith("[ROOM]")) { // msg가 "[ROOM]"으로 시작되면 방 번호에 저장
						int roomNum = Integer.parseInt(msg.substring(6));

						if (!Man.isFull(roomNum)) { // 방이 찬 상태가 아니면 (2명이 안넘으면)
							// 현재 방의 다른 사용에게 사용자의 퇴장을 알린다.
							if (roomNumber != -1)
								Man.sendToOthers(this, "[EXIT]" + userName);

							roomNumber = roomNum; // 사용자의 새 방 번호를 지정한다.
							writer.println(msg); // 사용자에게 메시지를 그대로 전송하여 입장할 수 있음을 알림
							writer.println(Man.getNamesInRoom(roomNumber)); // 사용자에게 새 방에 있는 사용자 이름 리스트를 전송
							Man.sendToOthers(this, "[ENTER]" + userName); // 새 방에 있는 다른 사용자에게 사용자의 입장을 알림
						} else
							writer.println("[FULL]"); // 사용자에 방이 찼음을 알림
					}

					else if (roomNumber >= 1 && msg.startsWith("[STONE]")) // "[STONE]" 메시지는 상대편에게 전송
						Man.sendToOthers(this, msg);

					else if (msg.startsWith("[MSG]")) // 대화 메세지를 방에 전송
						Man.sendToRoom(roomNumber, "[" + userName + "]: " + msg.substring(5));

					else if (msg.startsWith("[START]")) { // "[START]" 메시지이면
						ready = true; // 게임을 시작할 준비가 됨

						if (Man.isReady(roomNumber)) { // 다른 사용자도 게임을 시작한 준비가 되었으면
							int a = rnd.nextInt(2); // 흑과 백을 임의로 정하고 사용자와 상대편에게 알려줌
							if (a == 0) {
								writer.println("[COLOR]BLACK");
								Man.sendToOthers(this, "[COLOR]WHITE");
							} else {
								writer.println("[COLOR]WHITE");
								Man.sendToOthers(this, "[COLOR]BLACK");
							}
						}
					}

					else if (msg.startsWith("[STOPGAME]")) // 사용자가 게임을 중지하는 메시지를 보내면
						ready = false;

					else if (msg.startsWith("[DROPGAME]")) { // 사용자가 게임을 기권하는 메시지를 보내면
						ready = false;
						Man.sendToOthers(this, "[DROPGAME]"); // 상대편에게 사용자의 기권을 알림
					}

					else if (msg.startsWith("[WIN]")) { //
						ready = false;
						writer.println("[WIN]"); // 사용자가 이겼으면 사용자에게 이겼다는 메시지를 보냄
						Man.sendToOthers(this, "[LOSE]"); // 상대편에는 졌음을 알림
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
					if (userName == null) // 이름이 null이면 오류로 간주
						userName = "신원 불명의 사용자";
					Main.textArea.append(userName + "님이 접속을 끊었습니다.\n");
					Main.textArea.append("현재 " + Man.size() + "명이 접속해 있습니다.\n");
					Man.sendToRoom(roomNumber, "[DISCONNECT]" + userName); // 사용자가 접속을 끊었음을 같은 방에 알림
				} catch (Exception e) {
				}
			}
		}
	}

	class Massenger extends Vector { // 메세지를 전달하는 클래스
		void add(controller con) { // 스레드를 추가
			super.add(con);
		}

		void remove(controller con) { // 스레드를 제거
			super.remove(con);
		}

		controller getOT(int i) { // i번째 스레드를 반환
			return (controller) elementAt(i);
		}

		Socket getSocket(int i) { // i번째 스레드의 소켓을 반환
			return getOT(i).getSocket();
		}

		// i번째 스레드와 연결된 클라이언트에게 메세지를 전송한다.
		void sendTo(int i, String msg) {
			try {
				PrintWriter pw = new PrintWriter(getSocket(i).getOutputStream(), true);
				pw.println(msg);
			} catch (Exception e) {
			}
		}

		int getRoomNumber(int i) { // i번째 스레드의 방 번호를 반환
			return getOT(i).getRoomNumber();
		}

		synchronized boolean isFull(int roomNum) { // 방이 찼는지 확인함
			if (roomNum == 0)
				return false; // 대기실은 인원수 제한이 없음

			int count = 0;
			for (int i = 0; i < size(); i++)
				if (roomNum == getRoomNumber(i))
					count++;
			if (count >= 2)
				return true;
			return false;
		}

		void sendToRoom(int roomNum, String msg) { // roomNum 방에 msg를 전송
			for (int i = 0; i < size(); i++)
				if (roomNum == getRoomNumber(i))
					sendTo(i, msg);
		}

		void sendToOthers(controller ot, String msg) { // ot와 같은 방에 있는 다른 사용자에게 msg를 전달
			for (int i = 0; i < size(); i++)
				if (getRoomNumber(i) == ot.getRoomNumber() && getOT(i) != ot)
					sendTo(i, msg);
		}

		synchronized boolean isReady(int roomNum) { // 두 사용자 모두 게임을 시작할 준비가 되었으면 true를 반환
			int count = 0;
			for (int i = 0; i < size(); i++)
				if (roomNum == getRoomNumber(i) && getOT(i).isReady())
					count++;
			if (count == 2)
				return true;
			return false;
		}

		String getNamesInRoom(int roomNum) { // roomNum방에 있는 사용자들의 이름을 반환
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