# -*- coding:utf-8 -*-
import math
import random
import string

random.seed(0)
def rand(a, b):
    return (b - a) * random.random() + a

def makeMatrix(I, J, fill = 0.0):
    m = []
    for i in range(I):
        m.append([fill] * J)
    return m

# ���� sigmoid��������� tanh����Ϊ������Ҫ�ȱ�׼�� 1/(1+e^-x) Ư��Щ
def sigmoid(x):
    return math.tanh(x)

# ���� sigmoid ����������, Ϊ�˵õ���� (����y)
def dsigmoid(y):
    return 1.0 - y ** 2

class NN:
    ''' ���㷴�򴫲������� '''
    def __init__(self, ni, nh, no):
        # ����㡢���ز㡢�����Ľڵ㣨����
        self.ni = ni + 1 # ����һ��ƫ��ڵ�
        self.nh = nh
        self.no = no

        # ��������������нڵ㣨������
        self.ai = [1.0] * self.ni
        self.ah = [1.0] * self.nh
        self.ao = [1.0] * self.no
        
        # ����Ȩ�أ�����
        self.wi = makeMatrix(self.ni, self.nh)
        self.wo = makeMatrix(self.nh, self.no)
        # ��Ϊ���ֵ
        for i in range(self.ni):
            for j in range(self.nh):
                self.wi[i][j] = rand(-0.2, 0.2)
        for j in range(self.nh):
            for k in range(self.no):
                self.wo[j][k] = rand(-2.0, 2.0)

        # ������������ӣ�����
        self.ci = makeMatrix(self.ni, self.nh)
        self.co = makeMatrix(self.nh, self.no)

    def update(self, inputs):
        if len(inputs) != self.ni - 1:
            raise ValueError('�������ڵ���������')

        # ���������
        for i in range(self.ni - 1):
            #self.ai[i] = sigmoid(inputs[i])
            self.ai[i] = inputs[i]

        # �������ز�
        for j in range(self.nh):
            sum = 0.0
            for i in range(self.ni):
                sum = sum + self.ai[i] * self.wi[i][j]
            self.ah[j] = sigmoid(sum)

        # ���������
        for k in range(self.no):
            sum = 0.0
            for j in range(self.nh):
                sum = sum + self.ah[j] * self.wo[j][k]
            self.ao[k] = sigmoid(sum)

        return self.ao[:]

    def backPropagate(self, targets, N, M):
        ''' ���򴫲� '''
        if len(targets) != self.no:
            raise ValueError('�������ڵ���������')

        # �������������
        output_deltas = [0.0] * self.no
        for k in range(self.no):
            error = targets[k] - self.ao[k]
            output_deltas[k] = dsigmoid(self.ao[k]) * error

        # �������ز�����
        hidden_deltas = [0.0] * self.nh
        for j in range(self.nh):
            error = 0.0
            for k in range(self.no):
                error = error + output_deltas[k] * self.wo[j][k]
            hidden_deltas[j] = dsigmoid(self.ah[j]) * error

        # ���������Ȩ��
        for j in range(self.nh):
            for k in range(self.no):
                change = output_deltas[k] * self.ah[j]
                self.wo[j][k] = self.wo[j][k] + N * change + M * self.co[j][k]
                self.co[j][k] = change
                #print(N*change, M*self.co[j][k])

        # ���������Ȩ��
        for i in range(self.ni):
            for j in range(self.nh):
                change = hidden_deltas[j] * self.ai[i]
                self.wi[i][j] = self.wi[i][j] + N * change + M * self.ci[i][j]
                self.ci[i][j] = change

        # �������
        error = 0.0
        for k in range(len(targets)):
            error = error + 0.5 * (targets[k]-self.ao[k]) ** 2
        return error

    def test(self, patterns):
        for p in patterns:
            print(p[0], '->', self.update(p[0]))

    def weights(self):
        print('�����Ȩ��:')
        for i in range(self.ni):
            print(self.wi[i])

        print('�����Ȩ��:')
        for j in range(self.nh):
            print(self.wo[j])

    def train(self, patterns, iterations = 1000, N = 0.5, M = 0.1):
        # N: ѧϰ����(learning rate)
        # M: ��������(momentum factor)
        for i in range(iterations):
            error = 0.0
            for p in patterns:
                inputs = p[0]
                targets = p[1]
                self.update(inputs)
                error = error + self.backPropagate(targets, N, M)
            if i % 100 == 0:
                print('��� %-.5f' % error)
				
def demo():
    pat = [
        [[1,1,1,0,0,0], [1,0]],
        [[1,0,1,0,0,0], [1,0]],
        [[1,1,1,0,0,0], [1,0]],
        [[0,0,1,1,1,0], [0,1]],
		[[0,0,1,1,0,0], [0,1]],
		[[0,0,1,1,1,0], [0,1]],
    ]
    n = NN(6, 6, 2)

    n.train(pat)
    n.test(pat)
    n.weights()
    
if __name__ == '__main__':
    demo()