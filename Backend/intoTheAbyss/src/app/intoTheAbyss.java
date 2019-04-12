package app;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import app.db.LevelRepository;
import app.db.PlayerRepository;
import app.level.Level;
import app.world.World;
import network.server.NetworkHandler;

@SpringBootApplication
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
		levelRepository.save((Level) world.getLevel(0));
		levelRepository.save((Level) world.getLevel(1));

		NetworkHandler network = new NetworkHandler(world, playerRepository, levelRepository);
		network.registerPackets();
		network.setupListener();
		network.startNetwork();
	}
}
