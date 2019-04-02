package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LevelTest {
	@Test
	public void testAdd() {
		assertEquals(42, Integer.sum(19, 23));
	}
}