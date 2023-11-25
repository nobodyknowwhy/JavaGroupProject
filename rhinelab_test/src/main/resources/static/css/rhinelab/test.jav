package chapter02;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
  // 定义服务器端口号
  private int port = 8008;
  // 定义服务器套接字
  private ServerSocket serverSocket;

  public TCPServer() throws IOException{
    // 创建服务器套接字，监听端口号
    serverSocket = new ServerSocket(port);
    System.out.println("server is listening at port " + port);
  }

  private PrintWriter getWriter(Socket socket) throws  IOException{
    // 获取socket的输出流
    OutputStream socketOut = socket.getOutputStream();

    // 创建PrintWriter对象，用于将字符串写入socket的输出流
    return new PrintWriter(
      new OutputStreamWriter(socketOut, "utf-8"), true
    );
  }

  private BufferedReader getReader(Socket socket) throws IOException{
    // 获取socket的输入流
    InputStream socketIn = socket.getInputStream();

    // 创建BufferedReader对象，用于从socket的输入流中读取字符串
    return new BufferedReader(new InputStreamReader(socketIn, "utf-8"));
  }

  public void Service(){
    // 循环监听客户端连接
    while (true){
      Socket socket = null;
      try{
        // 监听客户端连接
        socket = serverSocket.accept();
        System.out.println("New connection accepted： "+socket.getInetAddress().getHostAddress());
        // 获取socket的输出流
        BufferedReader br = getReader(socket);
        // 获取socket的输出流
        PrintWriter pw = getWriter(socket);
        // 向客户端发送欢迎信息
        pw.println("From server: welcome to use the serve");
        String msg = null;
        // 循环读取客户端发送的消息
        while ((msg = br.readLine())!=null){

          // 如果消息为bye，则退出循环
          if(msg.trim().equals("bye")){
            pw.println("From server: server disconnect, serve end");
            System.out.println("client leave");
            break;
          }
          // 向客户端发送消息
          System.out.println(msg);
          pw.println("From server:" + msg);
        }
      }catch (IOException e){
        e.printStackTrace();
      }finally {
        try {
          // 关闭socket
          if(socket!= null)
            socket.close();
        }catch (IOException e){
          e.printStackTrace();
        }
      }
    }
  }
  public static void main(String[] args) throws IOException{
    // 创建TCPServer对象
    new TCPServer().Service();
  }
}







