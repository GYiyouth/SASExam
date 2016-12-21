package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.Thread.sleep;

/**
 * Created by geyao on 2016/12/12.
 */
public class Client {
	private PrintWriter writer;              //写管道
	private BufferedReader reader;           //读管道
	private Socket socket;                   //套接字
	private String id;                       //唯一编号
	private int healthFlag = 2;                 //1健康，2异常
	private int exceptionFlag = 0;              //异常状态：1 2 3 4 5……
	private Thread updateThread;                     //线程


	public Client(int healthFlag, int exceptionFlag) {
		super();
		connect();
		this.healthFlag = healthFlag;
		this.exceptionFlag = exceptionFlag;
		senState();
		run();

		while (true){
			try {
				String message = reader.readLine();
				if (message.equals("update") && !updateThread.isInterrupted()){
					updateThread.interrupt();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void run(){
		updateThread = new Thread(new Runnable() {
			@Override
			public void run() {
				boolean stopFlag = false;
				while (!stopFlag){
					try {
						sleep(30000);            //每30s发送一次
						senState();
						System.out.println("已发送");
					} catch (InterruptedException e) {
						senState();

					}
				}
			}
		});
		updateThread.start();
	}

	private void connect(){
		try {
			socket = new Socket("127.0.0.1", 27999);
			writer = new PrintWriter(socket.getOutputStream(), true);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			id = reader.readLine();
			System.out.println("我是第几台机器？" + id);
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	private String getState(){
		String state = "236D6A" + id + "2C" + healthFlag + "2C" + exceptionFlag + "2C" + "0000000000002C55";
		return state;
	}

	private void senState(){
		String state = getState();
		writer.println(state);
		writer.flush();
		System.out.println("服务器要求提交"+state);
		return;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int i = in.nextInt();
		new Client(2, i);
	}
}
