
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import android.util.Log;

public class Server {

	public static final int PORT_NUMBER = 8888;
	
	public static void main(String[] args) {
    new Server().look();

	}
    
	
	public void look(){
		String message = "";
		try{
			ServerSocket ss = new ServerSocket (PORT_NUMBER);
		while(true){
			System.out.println("Running!");
        Socket sack = ss.accept();
		BufferedReader br = new BufferedReader(new InputStreamReader(sack.getInputStream()));
		message = br.readLine();
		System.out.println(message);
		OutputStreamWriter out = new OutputStreamWriter(sack.getOutputStream());
		BufferedWriter write = new BufferedWriter(out);
		write.write("Client:"+message+"\nServer:Thank you for sending "+message+"\n");
		write.flush();
		}
		}catch(Exception e){
			Log.d("NOO","NO WORK"+e.getMessage());
			
		}
	}
	
	
}
