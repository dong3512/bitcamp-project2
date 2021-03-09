package com.eomcs.pms;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerApp {

  int port;

  public static void main(String[] args) {
    ServerApp app = new ServerApp(8888);
    app.service();
  }

  public ServerApp(int port) {
    this.port = port;
  }

  public void service() {
    try (ServerSocket serverSocket = new ServerSocket(this.port)){

      System.out.println("서버 실행!");

      processRequest(serverSocket.accept());

    }catch (Exception e) {
      System.out.println("서버 실행 중 오류 발생!");
      e.printStackTrace();
    }
  }

  private void processRequest(Socket socket) {
    try (DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream())) {

      while (true) {
        String request = in.readUTF();
        int length = in.readInt();
        ArrayList<String> data = null;
        if (length > 0) {
          data = new ArrayList<>();
          for (int i = 0; i < length; i++) {
            data.add(in.readUTF());
          }
        }

        System.out.println("------------------");
        System.out.printf("명령: %s\n", request);
        System.out.printf("데이터 개수: %d\n", length);
        if (data != null) {
          System.out.println("데이터:");
          for (String str : data) {
            System.out.println(str);
          }
        }

        out.writeUTF("success");
        out.writeInt(1);
        out.writeUTF("test....ok!");
        out.flush();
        if (request.equals("quit")) {
          break;
        }
      }

    } catch (Exception e) {
      System.out.println("클라이언트의 요청을 처리하는 중에 오류 발생!");
      e.printStackTrace();
    }
  }
}
