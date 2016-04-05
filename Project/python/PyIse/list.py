# -*- coding:utf-8 -*- 
import numpy as np
import itertools
from decode import *

listfile = open('list.txt')
listlines = listfile.readlines()
codes = {}

for line in listlines:
    codes[line.split(',')[0]] = line.split(',')[1].replace(' ', '').replace('\n', '').lower()

def sort_key(key):
    tmp = np.arange(len(key))
    for i in np.arange(len(key)):
        tmp[key[i] - 1] = i + 1
    return tmp

for code in codes:
    str_index = []
    for char in codes[code]:
        str_index.append(alph.index(char))
	
    for i in np.arange(26):
        str = decode_shift(str_index, i)
        if valid(str) >= 3:
            print "%s, Shift Cipher, key = %d\n" %(code, i),str, "\n"

    for key in keydict:
        for i in np.arange(26):
            str = decode_affine(str_index, keydict[key], i)
            if valid(str) >= 3:
                print "%s, Affine Cipher, key1 = %d, key2 = %d\n" %(code,key,i) ,str , "\n"
	
    for i in np.arange(8):
        key_str = ''
        iter = i + 1
        for j in np.arange(iter):
            key_str += '%d' %(j+1)			
        if len(str_index) % iter ==0:
            keylist = []
            for i in itertools.permutations(key_str,j+1):
                keylist = [int(j) for j in list(i)]
                str = decode_permutation(str_index, keylist)
                if valid(str) >= 2:
                    print "%s, Permutation Cipher, key = " %code, sort_key(keylist), "\n", str , "\n"

    for i in np.arange(26):
        str = decode_vigenere_autokey(str_index, i)
        if valid(str) >= 2:
            print "%s, Vigenere Autokey, key = %d\n" %(code, i),str, "\n"


for code in codes:
    str_index = []
    for char in codes[code]:
        str_index.append(alph.index(char))
   
    keyfile = open('dict.txt')
    keylines = keyfile.readlines()
    
    for line in keylines:
        key = line.split('|')
    for i in np.arange(len(key)):
        if key[i] != '':
            str = decode_vigenere(str_index, key[i])
            if valid(str) >= 3:
                print "%s, Vigenere Cipher, key = " %code,key[i],"\n", str , "\n"
            str = decode_beaufortm(str_index, key[i])
            if valid(str) >= 3:
                print "%s, Beaufortm Cipher, key = " %code,key[i],"\n", str , "\n"	

