
# coding: utf-8

# In[77]:


# In this part, you will create a neural net for a dataset chosen from
# the UCI ML repository. The repository is available at:

# http://archive.ics.uci.edu/ml/datasets.html

# You will first have to read in the dataset using Pandas into a
# dataframe. The second step will involve pre-processing the dataset -
# analyze each of the attributes and scale them. Then you will
# randomly split the data into train and test parts – you are free to
# decide the split size. Next will be the model creation step – you
# will need to tune as many parameters as possible. Finally, evaluate
# the performance of the model using the best set of parameters.


# In[78]:


import numpy as np
import pandas as pd

df = pd.read_csv("https://archive.ics.uci.edu/ml/machine-learning-databases/abalone/abalone.data",
                 names=['sex', 
                        'length', 
                        'diameter', 
                        'height', 
                        'whole', 
                        'shucked', 
                        'viscera', 
                        'shell',
                        'rings'])


# In[79]:


# Turn 'sex' into three columns bool.
for label in "MFI":
    df[label] = df['sex'] == label
del df['sex']


# In[80]:


# Show data type.
df.dtypes


# In[81]:


df.describe()


# In[82]:


y = df.rings.values


# In[83]:


del df["rings"] # remove rings from data, so we can convert all the dataframe to a numpy 2D array.
X = df.values.astype(np.float)


# In[84]:


X


# In[85]:


# Random split data set into test and train.
from sklearn.model_selection import train_test_split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, shuffle='true')


# In[86]:


from sklearn.preprocessing import StandardScaler
scaler = StandardScaler()


# In[87]:


scaler.fit(X_train)


# In[88]:


# Now apply the transformations to the data:
X_train = scaler.transform(X_train)
X_test = scaler.transform(X_test)


# In[89]:


X_train


# In[90]:


from sklearn.neural_network import MLPClassifier
mlp = MLPClassifier(hidden_layer_sizes=(8,30,30), max_iter=1000)


# In[91]:


mlp.fit(X_train,y_train)


# In[92]:


predictions = mlp.predict(X_test)


# In[93]:


mlp.score(X_test, y_test)


# In[94]:


from sklearn.metrics import classification_report,confusion_matrix
print(confusion_matrix(y_test,predictions))


# In[95]:


print(classification_report(y_test,predictions))

