# -*- coding: utf-8 -*-
f = open('paper.txt', 'r') 

result = list()  
for line in f.readlines():
    line = line.strip('\n').split('\t',1)[-1] 
    result = result + line.split(' ')                       
result = sorted(set(result), key = result.index)

open('vocab.txt', 'w').write('\n'.join(result))