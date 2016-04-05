# -*- coding:utf-8 -*- 
import numpy as np

alph = ['a','b','c','d','e','f', 'g', 'h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z']

vocabfile = open('vocab.txt')
lines = vocabfile.readlines();

vocabdict = {}
for line in lines:
    vocabdict[line.split(' ')[1]] = 1

def valid(str):
    str_list = [i for i in str.split(' ') if i != '']
    sum = 0
    for i in str_list:
        if vocabdict.has_key(i):
            sum = sum + 1
    return sum

def decode_shift(str_index, key):
    list = []
    for i in str_index:
        list.append(alph[(i - key) % 26])
    str = ''.join(list).replace('z', ' ')
    return str

# 1,3,5,7,9,11,15,17,19,21,23,25
# 1,9,21,15,3,19,7,23,11,5,17,25

keydict = {1:1,3:9,5:21,7:15,9:3,11:19,15:7,17:23,19:11,21:5,23:17,25:25}

def decode_affine(str_index, key1, key2):
    list = []
    for i in str_index:
        list.append(alph[key1 * (i - key2 + 26) % 26])
    str = ''.join(list).replace('z', ' ')
    return str
	
# Substitution 暴力破解可能性26！ 简单测了下，我笔记本需要 1.334e+12 年...

# Vigenere 没有key的话，纯粹暴力破解可能性有 26^len(str)
def decode_vigenere(str_index, key):
    iter = 0
    key_index = []
    #print key
    for char in key:
        key_index.append(alph.index(char))
    
    list = []
    for i in np.arange(len(str_index)):
        if iter == len(key):
            iter = 0
            list.append(alph[(str_index[i] - key_index[iter]) % 26])
            iter = iter + 1
        else:
            list.append(alph[(str_index[i] - key_index[iter]) % 26])
            iter = iter + 1
    str = ''.join(list).replace('z', ' ')
    return str
	
# Beaufortm 没有key的话，纯粹暴力破解可能性有 26^len(str)
def decode_beaufortm(str_index, key):
    iter = 0
    key_index = []
    #print key
    for char in key:
        key_index.append(alph.index(char))
    
    list = []
    for i in np.arange(len(str_index)):
        if iter == len(key):
            iter = 0
            list.append(alph[(str_index[i] + key_index[iter] + 1) % 26])
            iter = iter + 1
        else:
            list.append(alph[(str_index[i] + key_index[iter] + 1) % 26])
            iter = iter + 1
    str = ''.join(list).replace('z', ' ')
    return str

# Hill 没有key，破解感觉起来很复杂

# Permutation 做部分可能性破解
def decode_permutation(str_index, key):
    list = []
    key_len = len(key)
    for i in np.arange(len(str_index)):
        #index = key[i % key_len] - 1 + i / key_len * key_len
        list.append(alph[str_index[key[i % len(key)] - 1 + i / key_len * key_len]])
    str = ''.join(list).replace('z', ' ')
    return str

def decode_vigenere_autokey(str_index, key):
    list = []   
    for i in np.arange(len(str_index)):
        if i == 0:
            list.append(alph[(str_index[i] - key + 26) % 26])
        else:
            list.append(alph[(str_index[i] - int(alph.index(list[i - 1])) + 26) % 26])
    str = ''.join(list).replace('z', ' ')
    return str

if __name__ == "__main__":
    #do one thing at a time and do well
    str = 'G R C R Q H C W K L Q J C D W C D C W L P H C D Q G C G R C Z H O O'.replace(' ', '').lower();
    str_index = []
    for char in str:
        str_index.append(alph.index(char))
    
    for i in np.arange(26):
        str = decode_shift(str_index, i)
        if valid(str) >= 3:
            print "key = %d\n" %i ,str
		
	#never forget to say thanks
    str = 'T Y D Y L V J E L U Y H V H E V W G K V H F G T M W'.replace(' ', '').lower();
    str_index = []
    for char in str:
        str_index.append(alph.index(char))
    
    for key in keydict:
        for i in np.arange(26):
    	    str = decode_affine(str_index, keydict[key], i)
            if valid(str) >= 3:
                print "key1 = %d, key2 = %d\n" %(key,i) + str