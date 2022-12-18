import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
public class Server {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("enter 1 for tcp and 2 for udp : ");
		int choose = scanner.nextInt();
		if(choose == 1) {
			try {
				
				ServerSocket soc = new ServerSocket(3333);
				Socket sock = soc.accept();
				System.out.println("server is operating");
				System.out.println("Connection has been established");
				
				DataInputStream in = new DataInputStream(sock.getInputStream());
				DataOutputStream out = new DataOutputStream(sock.getOutputStream());
				
				out.writeUTF("Connection has been established");
				
				int flag = 1;
				while(flag == 1) {
					
					float num1 = in.readFloat();
					char op = in.readChar();
					float num2 = in.readFloat();
					
					String operator = Character.toString(op);
					String result = num1+" "+operator+" "+num2+" = "+ calculate(num1,num2,op);
					out.writeUTF(result);
					
					flag = in.readInt();
					if(flag == 2) {
						System.out.println("srever has been killed");
						out.writeUTF("Connection has been terminated");
					}
				}
				soc.close();

			}catch(IOException e) {
				System.out.println(e);
			}
		}else if (choose == 2) {
			try {
				DatagramSocket ds = new DatagramSocket(3331);
				InetAddress ip = InetAddress.getByName("127.0.0.1");
				byte[] buffer = new byte[2056];
				DatagramPacket dpReciver = new DatagramPacket(buffer, 2056);
				DatagramPacket dpSender = new DatagramPacket(buffer, 0, ip, 3332);
				
				int flag = 1;
				while(flag == 1) {
					System.out.println("server is operating");
					
					ds.receive(dpReciver);
					Float num1 = Float.parseFloat(new String(dpReciver.getData(),0,dpReciver.getLength()));
					
					ds.receive(dpReciver);
					char op = new String(dpReciver.getData(),0,dpReciver.getLength()).charAt(0);
					
					ds.receive(dpReciver);
					Float num2 = Float.parseFloat(new String(dpReciver.getData(),0,dpReciver.getLength()));
					
					dpSender.setData((num1+" "+Character.toString(op)+" "+num2+" = "+ calculate(num1,num2,op)).getBytes());
					dpSender.setLength((num1+" "+Character.toString(op)+" "+num2+" = "+ calculate(num1,num2,op)).length());
					ds.send(dpSender);
					
					ds.receive(dpReciver);
					flag = Integer.parseInt(new String(dpReciver.getData(),0,dpReciver.getLength()));
					
					if(flag == 2) {
						String end = "thanks for using our server";
						dpSender.setData(end.getBytes());
						dpSender.setLength(end.length());
						ds.send(dpSender);
						}
					}
				ds.close();
				}catch(IOException e) {
				System.out.println(e);
			}
		}
		scanner.close();
	}
		public static float calculate(float num1, float num2,char operator) {
			float result = 0;
			switch(operator) {
			case '+':
				result = num1+num2;
				break;
			case '-':
				result = num1-num2;
				break;
			case '*':
				result = num1*num2;
				break;
			case '/':
				result = num1/num2;
				break;
			case '^':
				result = (float) Math.pow(num1, num2);
				break;
			}
			return result;
		}
	

}
