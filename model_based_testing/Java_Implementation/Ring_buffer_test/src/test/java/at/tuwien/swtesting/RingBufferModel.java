package at.tuwien.swtesting;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;

public class RingBufferModel implements FsmModel {
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
	}

	public boolean resetGuard() {
		return size != 0;
	}
	@Action
	public void reset() {
		ringBuffer = new RingBuffer<>(CAPACITY); // Corrected raw type usage
		size = 0;
	}

	public boolean enqueueGuard() {
		return size < CAPACITY; // Corrected the guard condition
	}

	@Action
	public void enqueue() {
		ringBuffer.enqueue("test " + size);
		size++; // Increment size
	}

	public boolean dequeueGuard() {
		return size > 0;
	}

	@Action
	public void dequeue() {
		 ringBuffer.dequeue();
		size--; // Decrement size
	}


	public boolean dequeueFromEmptyBufferGuard(){
		return size == 0;
	}
	@Action
	public void dequeueFromEmptyBuffer(){
		ringBuffer.dequeue();
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

	// The %Empty% functions break the tests, i still left them in as they were required
	public boolean peekEmptyBufferGuard() {
		return size == 0;
	}
	@Action
	public void peekEmptyBuffer() {
		ringBuffer.peek();
	}

}
