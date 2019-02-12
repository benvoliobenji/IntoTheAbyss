package app;
import packets.ConnectionPacket;

public class Main {
	public static int portTCP = 44444;
	public static int portUDP = 44445;
	public static void main(String[] args) {
		World world = new World();
		
		Server server = new Server();
	    server.start();
	    server.bind(portTCP, portUDP);
	    
	    server.addListener(new Listener() {
	        public void received (Connection connection, Object object) {
	           if (object instanceof ConnectionPacket) {
	              ConnectionPacket request = (ConnectionPacket)object;
	              System.out.println(request.text);
	           }
	        }
	     });
	    
	}

}
