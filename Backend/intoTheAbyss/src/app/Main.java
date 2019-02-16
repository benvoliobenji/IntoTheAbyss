package app;
import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import level.Level;
import network.packets.*;
import world.World;

public class Main {
	public static int portTCP = 44444;
	public static int portUDP = 44445;
	
	public static void main(String[] args) {
		World world = new World();
		world.addLevel();
		world.addLevel();
		
		Server server = new Server(16384, 2048);
	    
	    Kryo kryo = server.getKryo();
		kryo.register(ConnectionPacket.class);
		
		server.addListener(new Listener() {
			public void connect (Connection connetion) {
				System.out.println("Connected");
			}
			
	        public void received (Connection connection, Object object) {
	           if (object instanceof ConnectionPacket) {
	              ConnectionPacket request = (ConnectionPacket)object;
	              System.out.println(request.text);
	           }else if(object instanceof MapPacket){
	        	   MapPacket map = (MapPacket)object;
	           }else if(object instanceof MapRequestPacket) {
	        	   System.out.println("Hey this is a request for a map.");
	        	   int floor = 0;//((MapRequestPacket)object).getFloorNum();
	        	   Level requestedLevel = world.getLevel(floor);
	        	   server.sendToAllTCP((Object)requestedLevel);
	        	   
	           }
	        }
	     });
		
	    try {
			server.bind(portTCP, portUDP);
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	}

}
