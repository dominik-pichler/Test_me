from hypothesis import given,settings, Verbosity
from hypothesis.strategies import text
from rosetta import encode,decode
import logging
settings.register_profile("custom_settings", max_examples=10)  # Set max_examples to the desired number

@given(text(min_size=1, max_size=100))
@settings(verbosity=Verbosity.verbose)
def test_decode_inverts_encode(s):
    logging.debug(f"Testing with input: {s}")
    assert decode(encode(s)) == s
    logging.debug(f"Expected: {s}, Actual: {s}")

if __name__ == "__main__":
    test_decode_inverts_encode()
    
