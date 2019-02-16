package app;
import java.io.IOException;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import network.packets.*;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import world.World;

@Component
public class Main {
	public static int portTCP = 44444;
	public static int portUDP = 44445;

	@PostConstruct
	public static void kryonet() {
		World world = new World();
		
		Server server = new Server();
	    server.start();
	    try {
			server.bind(portTCP, portUDP);
		} catch (IOException e) {
			e.printStackTrace();
		}

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
