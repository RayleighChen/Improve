# -*- coding: utf-8 -*-

vocab = open('vocab.txt', 'r') 

vocabDict = {}
i = 0
for line in vocab.readlines():
    line = line.strip('\n')
    vocabDict[i] = line	
    i = i + 1

f = open('word-assignments.dat', 'r') 

f_topic0 = open('topic-0.txt', 'w')
f_topic1 = open('topic-1.txt', 'w')
f_topic2 = open('topic-2.txt', 'w')
f_topic3 = open('topic-3.txt', 'w')
f_topic4 = open('topic-4.txt', 'w')

def tp0(word):  
    f_topic0.write(word + "\n")  
  
def tp1(word):  
    f_topic1.write(word + "\n")  
  
def tp2(word):  
    f_topic2.write(word + "\n")  
  
def tp3(word):  
    f_topic3.write(word + "\n")  

def tp4(word):  
    f_topic4.write(word + "\n")
	
operator = {'00':tp0,'01':tp1,'02':tp2,'03':tp3,'04':tp4}  

def tp(fileNo, word):  
    operator.get(fileNo)(word) 

for line in f.readlines():
    line = line.strip('\n').split(' ',1)[-1]
    line = line.split(' ')
    #print line
    type = {}
    for item in line:
        item.split(':')
        key = int(item.split(':')[0])
        topic = item.split(':')[1]
        if topic not in type:
            type[topic] = vocabDict[key]
        else:
            type[topic] = type[topic] + " " + vocabDict[key]
        
		#tp(topic, vocabDict[key])
    #print type
    for (k, v) in  type.items():
        tp(k, v)   