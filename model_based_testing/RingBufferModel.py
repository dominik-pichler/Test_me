class RingBuffer:
    def __init__(self, capacity):
        self.capacity = capacity
        self.buffer = []

    def enqueue(self, item):
        if len(self.buffer) < self.capacity:
            self.buffer.append(item)
        else:
            return False  # Indicate failure (buffer full)
        return True  # Successful enqueue

    def dequeue(self):
        if self.buffer:
            return self.buffer.pop(0)
        else:
            return None  # Indicate failure (buffer empty)

    def peek(self):
        if self.buffer:
            return self.buffer[0]
        else:
            return None  # Indicate failure (buffer empty)

    def reset(self):
        self.buffer = []
