package unit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import game.events.PauseHandler;
import game.events.SaveHandler;
import io.InputHandler;

import java.awt.event.KeyEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InputHandlerTest {
	private PauseHandler pauseHandler;
	private SaveHandler saveHandler;
	private KeyEvent e;
	private InputHandler input;
	
	@Before
	public void setUp() {
		pauseHandler = mock(PauseHandler.class);
		doNothing().when(pauseHandler).handlePause();
		
		saveHandler = mock(SaveHandler.class);
		doNothing().when(saveHandler).handleSave();
		
		e = mock(KeyEvent.class);
		input = InputHandler.getInstance();
		
		input.addPauseHandler(pauseHandler);
		input.addSaveHandler(saveHandler);
	}
	
	@After
	public void tearDown() {
		e = null;
		input.reset();
	}
	
	@Test
	public void testIsPressed() {
		assertFalse(input.getUpKey().isPressed());
		assertFalse(input.getDownKey().isPressed());
		assertFalse(input.getLeftKey().isPressed());
		assertFalse(input.getRightKey().isPressed());
		assertFalse(input.getSpaceKey().isPressed());
	}
	
	@Test
	public void testIsPressedWhenUpPressed() {
		when(e.getKeyCode()).thenReturn(KeyEvent.VK_UP);
		input.keyPressed(e);
		
		assertTrue(input.getUpKey().isPressed());
	}
	
	@Test
	public void testIsPressedWhenDownPressed() {
		when(e.getKeyCode()).thenReturn(KeyEvent.VK_DOWN);
		input.keyPressed(e);
		
		assertTrue(input.getDownKey().isPressed());
	}
	
	@Test
	public void testIsPressedWhenLeftPressed() {
		when(e.getKeyCode()).thenReturn(KeyEvent.VK_LEFT);
		input.keyPressed(e);
		
		assertTrue(input.getLeftKey().isPressed());
	}
	
	@Test
	public void testIsPressedWhenRightPressed() {
		when(e.getKeyCode()).thenReturn(KeyEvent.VK_RIGHT);
		input.keyPressed(e);
		
		assertTrue(input.getRightKey().isPressed());
	}
	
	@Test
	public void testIsPressedWhenSpacePressed() {
		when(e.getKeyCode()).thenReturn(KeyEvent.VK_SPACE);
		input.keyPressed(e);
		
		assertTrue(input.getSpaceKey().isPressed());
	}
	
	@Test
	public void testIsPressedWhenAlternateUpPressed() {
		when(e.getKeyCode()).thenReturn(KeyEvent.VK_W);
		input.keyPressed(e);
		
		assertTrue(input.getUpKey().isPressed());
	}
	
	@Test
	public void testIsPressedWhenAlternateDownPressed() {
		when(e.getKeyCode()).thenReturn(KeyEvent.VK_S);
		input.keyPressed(e);
		
		assertTrue(input.getDownKey().isPressed());
	}
	
	@Test
	public void testIsPressedWhenAlternateLeftPressed() {
		when(e.getKeyCode()).thenReturn(KeyEvent.VK_A);
		input.keyPressed(e);
		
		assertTrue(input.getLeftKey().isPressed());
	}
	
	@Test
	public void testIsPressedWhenAlternateRightPressed() {
		when(e.getKeyCode()).thenReturn(KeyEvent.VK_D);
		input.keyPressed(e);
		
		assertTrue(input.getRightKey().isPressed());
	}
	
	@Test
	public void testKeyReleased() {
		when(e.getKeyCode()).thenReturn(KeyEvent.VK_UP);
		
		assertFalse(input.getUpKey().isPressed());
		
		input.keyPressed(e);
		assertTrue(input.getUpKey().isPressed());
		
		input.keyReleased(e);
		assertFalse(input.getUpKey().isPressed());
	}
	
	@Test
	public void testSaveHandler() {
		when(e.getKeyCode()).thenReturn(KeyEvent.VK_U);
		input.keyReleased(e);
		
		verify(saveHandler, times(1)).handleSave();
		
		input.removeSaveHandler(saveHandler);
		input.keyReleased(e);
		
		// Should not have increased
		verify(saveHandler, times(1)).handleSave();
	}
	
	@Test
	public void testPauseHandler() {
		when(e.getKeyCode()).thenReturn(KeyEvent.VK_P);
		input.keyReleased(e);
		
		verify(pauseHandler, times(1)).handlePause();
		
		input.removePauseHandler(pauseHandler);
		input.keyReleased(e);
		
		// Should not have increased
		verify(pauseHandler, times(1)).handlePause();
	}
}
