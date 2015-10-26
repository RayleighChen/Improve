# -*- coding: utf-8 -*-

vocab = open('vocab.txt', 'r') 

vocabDict = {}
i = 0
for line in vocab.readlines():
    line = line.strip('\n')
    vocabDict[line] = i	
    i = i + 1

f = open('paper.txt', 'r') 
title = open('title.txt', 'w')

for line in f.readlines():
    line = line.strip('\n').split('\t',1)[-1]
    line = line.split(' ')
    itemDict = {}
    lineStr = ""
    for item in line:
        if item not in itemDict:
            itemDict[item] = 1
        else:
            itemDict[item] += 1
        lineStr = lineStr + " " + str(vocabDict[item]) + ":" + str(itemDict[item])
    title.write(str(len(itemDict)) + lineStr + '\n')
#str(vocabDict[item])