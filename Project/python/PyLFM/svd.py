# -*- coding: utf8 -*-
from numpy import *

def loadData():
    return [[5,5,0,4,3],
             [1,2,0,0,0],
             [4,5,3,2,0],
             [5,5,5,5,4]]

data=loadData()

u,sigma,vt=linalg.svd(data)
print sigma

sig3=mat([[sigma[0],0,0],
      [0,sigma[1],0],
      [0,0,sigma[2]]])

print u[:,:3]*sig3*vt[:3,:]