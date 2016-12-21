package Server;


import com.opensymphony.xwork2.ActionContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by geyao on 2016/12/12.
 */
public class Server {
	private HashMap<Integer, BufferedReader> readers =new HashMap<>();               //缓冲输入流
	private HashMap<Integer, PrintWriter> writers = new HashMap<>();              //缓冲输出流
	private ServerSocket serverSocket;
	private HashMap<Integer, Socket> clientSockets = new HashMap<>();                //套接字组
	private HashMap<Integer, Thread> threads = new HashMap<>();                      //线程
	private int totalClients = 0;
	private ArrayList<Integer> onlineClients = new ArrayList<>();
	private HashMap<String, Integer>states = new HashMap<>();                      //key为便编号，value为异常编号，健康为0
	private MyXmlWriter myXmlWriter = new MyXmlWriter();


	public Server(){ // 构造函数
		try {
			serverSocket = new ServerSocket(27999);
			System.out.println("服务器已经创建成功");
			threads.put(totalClients, new Thread(new Task()));
			threads.get(totalClients).start();

		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public void AskAllUpdate(){                               //给聊天室的每个人发送信息

		for (int i = 0; i < onlineClients.size(); i++ ){
			try {
				PrintWriter writer = new PrintWriter(clientSockets.get(
						onlineClients.get(i)            //获取当前在线客户端的编号
				).getOutputStream(), true);
				writer.println("update");
				writer.flush();                                     //强制写入

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			myXmlWriter.WriteXml(states);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public void delete(){
		try {
			myXmlWriter.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		Server server = new Server();

	}

	//每个线程的任务
	class Task implements Runnable{
		@Override
		public void run(){

			System.out.println("房间正在等待第" + (clientSockets.size() + 1) + "位客户连接");
			try {
				Socket socket = serverSocket.accept();
				clientSockets.put(totalClients, socket);                                      //添加到数组
				System.out.println(socket.getInetAddress());
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				readers.put(totalClients, reader);                                            //添加到数组
				onlineClients.add(totalClients);

				PrintWriter writer = new PrintWriter(
						//发送第一条消息，告知用户是今天总共第几号
						socket.getOutputStream(), true);
				int id = totalClients + 1;
				String str16 = Integer.toHexString(id);
				for (int i = str16.length(); i < 20 ; i++){
					str16 = "0" + str16;
				}
				System.out.println("str16是" + str16);
				writer.println(str16);     /**这里必须是println，不然不会发送*/
				writer.flush();
				writers.put(totalClients, writer);
				states.put(str16, 0);

				totalClients++;

				//做好本线程以后，开启下一个等待线程
				threads.put(totalClients,new Thread(new Task()));
				threads.get(totalClients).start();

				getClientMessage(totalClients);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		private void getClientMessage(int clientID) {
			final int num = clientID -1 ;
			try {
				while (true){
					String message = readers.get(num).readLine();
					System.out.println("getMessage = " + message);

					if (!message.equals(null)) {  //如果没有这一行语句，会被null刷屏
						update(message);
					}
				}// while
			}catch (NullPointerException e){   //客户端关闭后，message会有空索引异常，catch它即可
				clientSockets.remove(num);
				threads.remove(num);
				onlineClients.remove(onlineClients.indexOf(num));

				return;
			}catch (Exception e){
				e.printStackTrace();
			}

		}


		private void update(String state){
			String str = state.substring(7, 26);
			int id16 = Integer.parseInt(str);
			int id = Integer.valueOf(id16+"", 16);
			String strZ1 = state.substring(28, 29);
			System.out.println("str"+str);
			System.out.println("strZ1"+strZ1);
			int Z1 = Integer.parseInt(strZ1);

			System.out.println("Z1"+Z1);

			Date date = new Date();
			String time = String.format("%tY-%tm-%td  %tH:%tM:%tS", date,date,date,date,date,date);
			if (Z1 == 1){
				states.remove(str);
				states.put(str, 0);
//				myXmlWriter.write(time, str,0);
				return;
			}else {
				int Z2 = Integer.parseInt(state.substring(31, 32));
				System.out.println(Z2);
				states.remove(str);
				states.put(str, Z2);
				myXmlWriter.write(time, str, Z2);
				return;
			}
		}
	}
}
