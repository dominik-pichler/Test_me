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

	@Action
	public void peek() {
		if (size == 0) {
			throw new RuntimeException("Empty ring buffer.");
		}
	}
	public boolean peekGuard() {
		return size > 0;
	}
}
