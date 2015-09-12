# -*- coding: utf-8 -*-
'''
Created on 2014年9月30日

@author: Rayleigh
'''
import KNN as kNN
from numpy import * 

dataSet, labels = kNN.createDataSet()

testX = array([0.2, 0.9])
k = 3
outputLabel = kNN.kNNClassify(testX, dataSet, labels, 3)
print "Your input is:", testX, "and classified to class: ", outputLabel

testX = array([0.1, 0.3])
outputLabel = kNN.kNNClassify(testX, dataSet, labels, 3)
print "Your input is:", testX, "and classified to class: ", outputLabel
