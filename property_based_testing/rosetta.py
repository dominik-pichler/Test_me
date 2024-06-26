def encode(input_string):
    if not input_string:
        return []

    count = 1
    prev = input_string[0]
    lst = []

    for character in input_string[1:]:
        if character != prev:
            entry = (prev, count)
            lst.append(entry)
            count = 1
            prev = character
        else:
            count += 1

    # Append the last sequence
    entry = (prev, count)
    lst.append(entry)

    return lst
def decode(lst):
    q = ""
    for character, count in lst:
        q += character * count
    return q
