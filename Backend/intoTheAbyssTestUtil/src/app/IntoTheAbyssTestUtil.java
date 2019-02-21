package app;

import com.esotericsoftware.kryonet.Client;

import app.world.World;
import network.packets.ConnectionPacket;
import network.packets.PlayerPacket;
import network.server.NetworkHandler;

public class IntoTheAbyssTestUtil {
	public static int portTCP = 44444;
	public static int portUDP = 44445;

	public static void main(String[] args) throws Exception {
		World world = new World();
		world.addLevel();
		world.addLevel();
		
		NetworkHandler network = new NetworkHandler(world);
		network.registerPackets();
		network.setupListener();
		network.startNetwork();
		
		Client client = network.getClient();
		ConnectionPacket packet = new ConnectionPacket();
		client.sendTCP(packet);
		
		PlayerPacket p = new PlayerPacket();
		p.setFloorNumber(1);
		p.setXPos(3);
		p.setYPos(3);
		p.setUsername("Jet");
		
		client.sendTCP(p);
	}
}