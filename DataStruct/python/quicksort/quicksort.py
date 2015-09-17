def quicksort(data, low = 0, high = None):
    if high == None:
        high = len(data) - 1
    if low < high:
        s, i, j = data[low], low, high
        while i < j:
            while i < j and data[j] >= s:
                j = j - 1
            if i < j:
                data[i] = data[j]
                i = i + 1
            while i < j and data[i] <= s:
                i = i + 1
            if i < j:
                data[j] = data[i]
                j = j - 1
        data[i] = s
        quicksort(data, low, i - 1)
        quicksort(data, i + 1, high)