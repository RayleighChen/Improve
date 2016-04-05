# -*- coding:utf-8 -*- 
import numpy as np

alph = ['a','b','c','d','e','f', 'g', 'h','i','j','k', \
        'l','m','n','o','p','q','r','s','t', \
		      'u','v','w','x','y','z'] \

#Shift,  key = 3
#statements1
#do one thing at a time and do well
str = 'do one thing at a time and do well'.replace(' ', 'z')
str_index = []
for char in str:
    str_index.append(alph.index(char))

print "Shift Cipher"
key = 3
for i in str_index:
    print alph[(i + key) % 26].upper(),
print '\n'
	
#Affine, key1 = 11 key2 = 6
#statements2
#never forget to say thanks
str = 'never forget to say thanks'.replace(' ', 'z')
str_index = []
for char in str:
    str_index.append(alph.index(char))
key1, key2 = 11, 6
print "Affine Cipher"
for i in str_index:
    print alph[(i * key1 + key2) % 26].upper(),
print '\n'

#Substitution
#key: 字母表进行对称对换，例如:a->y, c->x
#statements3
#i can because i think i can
str = 'i can because i think i can'.replace(' ', 'z')
str_index = []
for char in str:
    str_index.append(alph.index(char))

print "Substitution Cipher"
for i in str_index:
    print alph[25 - i].upper(),
print '\n'

#Vigenere, key = 'yes'
#statements4
#action speak louder than words
str = 'action speak louder than words'.replace(' ', 'z')
str_index = []
for char in str:
    str_index.append(alph.index(char))

key = 'yes'
iter = 0
key_index = []
for char in key:
    key_index.append(alph.index(char))

print 'Vigenere Cipher'
for i in np.arange(len(str)):
    if iter == len(key):
        iter = 0
        print alph[(str_index[i] + key_index[iter]) % 26].upper(),
        iter = iter + 1
    else:
        print alph[(str_index[i] + key_index[iter]) % 26].upper(),
        iter = iter + 1
print '\n'

#Beaufortm, key = 'hello'
#statements5
#judge not from appearances
str = 'judge not from appearances'.replace(' ', 'z')
for char in str:
    str_index.append(alph.index(char))

key = 'hello'
iter = 0
key_index = []
for char in key:
    key_index.append(alph.index(char))

print 'Beaufortm Cipher'.replace(' ', 'z')
for i in np.arange(len(str)):
    if iter == len(key):
        iter = 0
        print alph[(str_index[i] - key_index[iter] - 1) % 26].upper(),
        iter = iter + 1
    else:
        print alph[(str_index[i] - key_index[iter] - 1) % 26].upper(),
        iter = iter + 1
print '\n'

#Hill,   key = 
#[[ 0  1  2 ..., 18 19 20]
#[ 0  1  2 ..., 18 19 20]
#[ 0  1  2 ..., 18 19 20]
#...,
#[ 0  1  2 ..., 18 19 20]
#[ 0  1  2 ..., 18 19 20]
#[ 0  1  2 ..., 18 19 20]]
#statements6
#justice has long arms
str = 'justice has long arms'.replace(' ', 'z')
str_index = []
for char in str:
    str_index.append(alph.index(char))

count = 1
key_mtrix = []
for i in np.arange(0, len(str)):
    tmp = []
    for j in np.arange(0, len(str)):
        tmp.append(j)
        count += 1
    key_mtrix.append(tmp)
key_matrix = np.array(key_mtrix)
shift = np.dot(str_index, key_matrix)

print "Hill Cipher"
iter = 0
for i in np.arange(len(str_index)):
    print alph[(str_index[i] + shift[i]) % 26].upper(),
    iter = iter + 1
print '\n'

#Permutation,  key
#  x  1  2  3  4  5
#f(x) 4  3  1  5  2 
#statements7
#kings have long arms
str = 'kings have long arms'.replace(' ', 'z')
str_index = []
for char in str:
    str_index.append(alph.index(char))

print "Permutation Cipher"
key = [4, 3, 1, 5, 2]
key_len = len(key)
for i in np.arange(len(str_index)):
    #index = key[i % key_len] - 1 + i / key_len * key_len    
    print alph[str_index[key[i % len(key)] - 1 + i / key_len * key_len]].upper(),
print '\n'
		
#Vigenere Autokey, key = 8
#statements8
#the first blow is half the battle
str = 'the first blow is half the battle'.replace(' ', 'z')
for char in str:
    str_index.append(alph.index(char))

print "Vigenere Autokey Cipher"
key = 9
for i in np.arange(len(str_index)):
    if i == 0:
        print alph[(str_index[i] + key) % 26].upper(),
    else:
        print alph[(str_index[i] + str_index[i - 1]) % 26].upper(),
print '\n'