package app;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

import network.server.NetworkHandler;
import app.db.LevelRepository;
import app.db.PlayerRepository;
import app.world.World;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class intoTheAbyss {
	@Autowired
	private PlayerRepository playerRepository;
	@Autowired
	private LevelRepository levelRepository;
	@PersistenceContext
	EntityManager entityManager;

	public static int portTCP = 44444;
	public static int portUDP = 44445;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(intoTheAbyss.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void startListener() {
		World world = new World();
		levelRepository.save(world.getLevel(0));
		world.addLevel();
		world.addLevel();

		NetworkHandler network = new NetworkHandler(world, playerRepository, levelRepository);
		network.registerPackets();
		network.setupListener();
		network.startNetwork();
	}
}
