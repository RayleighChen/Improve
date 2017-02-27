import numpy as np
import matplotlib.pyplot as plt

D = np.random.randn(1000, 500)
hidden_layer_sizes = [500]*10
#***************active funtion "tanh"
nonlinearities = ['tanh']*len(hidden_layer_sizes)
#***************active funtion "tanh"
#nonlinearities = ['relu']*len(hidden_layer_sizes)


act = {'relu':lambda x:np.maximun(0, x), 'tanh':lambda x:np.tanh(x)}
Hs = {}
for i in xrange(len(hidden_layer_sizes)):
    X = D if i == 0 else Hs[i-1] #input layer
    fan_in = X.shape[1]
    fan_out = hidden_layer_sizes[i]

    #-------little random number
    #W = np.random.randn(fan_in, fan_out) * 0.01
    #-------zero
    #W = 0
    #-------big number 
    W = np.random.randn(fan_in, fan_out) * 1
    #-------xavier and tanh (xavier and relu)
    #W = np.random.randn(fan_in, fan_out) / np.sqrt(fan_in)
    #-------Delving deep into rectifiers: Surpassing human-level performance on ImageNet classification by He et al., 2015 
    #W = np.random.randn(fan_in, fan_out) / np.sqrt(fan_in/2)

    H = np.dot(X, W)
    H = act[nonlinearities[i]](H)
    Hs[i] = H # cache result on this layer

print 'input layer had mean %f and std %f' % (np.mean(D), np.std(D))
layer_means = [np.mean(H) for i,H in Hs.iteritems()]
layer_stds = [np.std(H) for i,H in Hs.iteritems()]
for i,H in Hs.iteritems():
    print 'hidden layer %d had mean %f and std %f' % (i+1, layer_means[i], layer_stds[i])

plt.figure()
plt.subplot(121)
plt.plot(Hs.keys(), layer_means, 'ob-')
plt.title('layer mean')
plt.subplot(122)
plt.plot(Hs.keys(), layer_stds, 'or-')
plt.title('layer std')

#plot the raw distributions
plt.figure()
for i,H in Hs.iteritems():
    plt.subplot(1,len(Hs),i+1)
    plt.hist(H.ravel(), 30, range=(-1,1))

plt.show()

