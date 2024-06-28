package at.tuwien.swtesting;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class RingBufferModelWithAdapter implements FsmModel {
	private static final int CAPACITY = 3; // Corrected typo here
	private int size = 0;
	private RingBuffer<String> ringBuffer = new RingBuffer<>(CAPACITY); // Specify the type of elements

	@Override
	public Object getState() {
		if (size == 0) return "EMPTY";
		if (size == CAPACITY) return "FULL";
		return "FILLED";
	}

	@Override
	public void reset(boolean b) {
		ringBuffer = new RingBuffer<>(CAPACITY); // Corrected raw type usage
		size = 0;
		assertEquals(ringBuffer.size(),0);
		//assertNull(ringBuffer.peek());
		assertTrue(ringBuffer.isEmpty());
	}

	public boolean resetGuard() {
		return size != 0;
	}
	@Action
	public void reset() {
		ringBuffer = new RingBuffer<>(CAPACITY); // Corrected raw type usage
		size = 0;
		assertEquals(ringBuffer.size(),0);
		//assertNull(ringBuffer.peek());
		assertTrue(ringBuffer.isEmpty());
	}

	public boolean enqueueGuard() {
		return size < CAPACITY; // Corrected the guard condition
	}

	@Action
	public void enqueue() {
		ringBuffer.enqueue("test " + size);
		size++; // Increment size
		assertNotNull(ringBuffer.peek());
		assertFalse(ringBuffer.isEmpty());

	}

	public boolean dequeueGuard() {
		return size > 0;
	}

	@Action
	public void dequeue() {
		ringBuffer.dequeue();
		size--; // Decrement size
		assertFalse(ringBuffer.isFull());
		assertFalse(ringBuffer.size() == CAPACITY);

	}


	public boolean dequeueFromEmptyBufferGuard(){
		return ringBuffer.isEmpty();
	}
	@Action
	public void dequeueFromEmptyBuffer(){

		Exception thrown = assertThrows(
				RuntimeException.class,() -> ringBuffer.dequeue()

		);
		assertTrue(thrown.getMessage().contains("Empty ring buffer"));


		size --;
	}


	public boolean enqueueToFullBufferGuard(){
		return size == CAPACITY;
	}
	@Action
	public void enqueueToFullBuffer(){
		ringBuffer.enqueue("test " + size);
		size ++;


	}

	public boolean peekGuard() {
		return size > 0;
	}
	@Action
	public void peek() {
		if (size == 0) {
			throw new RuntimeException("Empty ring buffer.");
		}
	}

	public boolean peekEmptyBufferGuard() {
		return ringBuffer.isEmpty();
	}
	@Action
	public void peekEmptyBuffer() {

			RuntimeException thrown = assertThrows(
					RuntimeException.class,() -> ringBuffer.peek()
			);
			assertTrue(thrown.getMessage().contains("Empty ring buffer"));


	}


}
