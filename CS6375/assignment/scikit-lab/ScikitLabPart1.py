
# coding: utf-8

# In[1]:


# 1. We learned in class that the XOR problem can't be solved using a
# single perceptron and requires a neural network to solve. In this
# part, you have to create the best possible neural net i.e. the one
# with the minimum number of layers and fewest number of parameters
# that will solve the XOR problem.

# Give the weights and intercepts for each neuron and any other
# parameters that you have used.


# In[2]:


from sklearn.neural_network import MLPClassifier


# In[3]:


X = [[0, 0], [1, 1], [1, 0], [0, 1]]
y = [0, 0, 1, 1]


# In[4]:


clf = MLPClassifier(solver='lbfgs', activation='logistic',
                    hidden_layer_sizes=(2), random_state=200)


# In[5]:


clf.fit(X, y)


# In[6]:


clf.coefs_


# In[7]:


clf.predict([[0, 0], [1, 1], [1, 0], [0, 1]])

