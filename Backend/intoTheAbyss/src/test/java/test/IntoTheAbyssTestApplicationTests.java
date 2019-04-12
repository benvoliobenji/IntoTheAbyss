package test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Point;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.SpringBootConfiguration;

import app.level.Level;
import app.room.RoomInterface;

@SpringBootConfiguration
public class IntoTheAbyssTestApplicationTests {

	@Mock
	private RoomInterface room;

	@InjectMocks
	private Level level = new Level();

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		room = mock(RoomInterface.class);
		when(room.getCorner()).thenReturn(new Point(5, 7));

	}

	@Test
	public void test() {
		assertTrue(room.getCorner().x != 5);
	}
}
