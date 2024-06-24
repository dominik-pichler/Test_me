from hypothesis import given, strategies as st
from hypothesis.stateful import RuleBasedStateMachine, rule, precondition, invariant, initialize
import pytest
from RingBufferModel import RingBuffer  # Assuming RingBuffer is correctly implemented

class RingBufferModel(RuleBasedStateMachine):
    CAPACITY = 6

    def __init__(self):
        super().__init__()
        self.ring_buffer = None
        self.size = 0

    @initialize()
    def init(self):
        self.ring_buffer = RingBuffer(self.CAPACITY)
        self.size = 0

    @precondition(lambda self: self.size != 0)
    @rule()
    def reset(self):
        self.ring_buffer.reset()
        self.size = 0

    @precondition(lambda self: self.size < self.CAPACITY)
    @rule()
    def enqueue(self):
        self.ring_buffer.enqueue(f"test {self.size}")
        self.size += 1

    @precondition(lambda self: self.size > 0)
    @rule()
    def dequeue(self):
        self.ring_buffer.dequeue()
        self.size -= 1

    @precondition(lambda self: self.size > 0)
    @rule()
    def peek(self):
        result = self.ring_buffer.peek()
        assert result is not None
        return result

    @invariant()
    def buffer_size_invariant(self):
        assert 0 <= self.size <= self.CAPACITY

    @invariant()
    def buffer_content_invariant(self):
        if self.size > 0:
            assert self.ring_buffer.peek() is not None


# Use the hypothesis instance for strategy definition
hypothesis = st.builds(RingBufferModel)

@given(hypothesis)
def test_ring_buffer_model(model):
    model.init()
    # Example: Perform operations based on generated inputs
    for _ in range(10):  # Adjust based on desired test length
        operation = st.one_of(st.just(model.reset),
                              st.just(model.enqueue),
                              st.just(model.dequeue),
                              st.just(model.peek))

        print(operation)
@given(hypothesis)
def test_ring_buffer_model2(model):
    model.init()
    # Shuffle and perform operations based on generated inputs
    operations = st.permutations([
        lambda: model.reset(),
        lambda: model.enqueue(),
        lambda: model.dequeue(),
        lambda: model.peek()
    ])

    for operation in operations:
            operation()

        # Ensure invariants are maintained
    assert model.buffer_size_invariant()
    assert model.buffer_content_invariant()


if __name__ == "__main__":
    pytest.main()
