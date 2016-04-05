#encode
直接改句子和key就行，之前bug已经修正

#decode
带小测试的，简单试试

#list.py
最多可以解出六种类型
将需要解密的数据放入list.txt,格式按照 
    id,code
密文可以是大小写任意，可以带空格
在list.py中，扫了二次文件数据，第一次用了前四种比较简单的方法，后面那个循环是解Beaufortm 和 vigenere
在6位的时候，存的数据已经上G了，故只能解除5位一下的key, 时间较久，一组8条估计在二小时左右，可以在解出前几条时候
ctrl + c 强行终止任务

#其它
vocab.txt 词典，如果你的密文没有被解密，说明你用的词汇，不在这些词汇里面
dict.txt 字母1-5的全组合，为了破解部分 Beaufortm 和 vigenere

