from mbt_model import is_even
import pytest

def test_is_even():
    assert is_even(2)
    assert not is_even(3)

