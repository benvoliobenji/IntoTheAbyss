package app;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import network.packets.ConnectionPacket;
import network.packets.MapPacket;
import network.packets.MapRequestPacket;
import network.packets.PlayerPacket;
import network.server.NetworkHandler;
import app.level.Level;
import app.player.Player;
import app.world.World;

@SpringBootApplication
public class intoTheAbyss {
	public static int portTCP = 44444;
	public static int portUDP = 44445;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(intoTheAbyss.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void startListener() {
		World world = new World();
		world.addLevel();
		world.addLevel();
		
		NetworkHandler network = new NetworkHandler(world);
		network.setupListener();
		network.startNetwork();
	}
}
