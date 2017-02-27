import numpy as np

A = np.arange(9).reshape(3,3)
B = np.arange(9, 18).reshape(3,3)

print A, B

print A + B

print A.dot(B)

print A * B



