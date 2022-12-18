import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class TCPThread extends Thread{
    private Socket socket;
    Calculator calc;
    
    TCPThread(Socket sock) {
        this.socket = sock;
        calc = new Calculator();
    }

    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(this.socket.getInputStream());
            DataOutputStream out = new DataOutputStream(this.socket.getOutputStream());
            
            out.writeUTF("Connection with the server has been established.");
            System.out.println("Connection with " + this.socket.getInetAddress().getHostName() + " has been established");
            
            int flag = 1;
            while(flag == 1) {
                
                float num1 = in.readFloat();
                char op = in.readChar();
                float num2 = in.readFloat();
                
                String operator = Character.toString(op);
                String result = num1+" "+operator+" "+num2+" = "+ calc.calculate(num1,num2,op);
                out.writeUTF(result);
                
                flag = in.readInt();
                if(flag == 2) {
                    System.out.println("Connection with client has been terminated");
                    out.writeUTF("Connection has been terminated");
                }
            }
            try{
                this.socket.close();
            }catch(SocketException e){}

        } catch(IOException e) {
            System.out.println(e);
        }
        
    }
}
