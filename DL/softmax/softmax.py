import numpy as np

def softmax(x):
    return np.exp(x - np.max(x)) / np.sum(np.exp(x - np.max(x)))

scores = [3.0, 1.0, 0.2]
print(softmax(scores))