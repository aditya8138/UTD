# Quiz 1 Inductive Learning

Note that all ~scratch words~ indicate wrong part in the option, and all
_italic_ or __bold__ sentences are notes.

1. Match the learning scenarios on the left with their type on the right:

    | Learning Scenarios | Type |
    | --- | --- |
    | Predicting the lottery based on past 4 week's numbers | A. Not learnable scenario |
    | Training your dog by rewarding good behavior by treats and punishing bad behavior by taking away treats. | B. Reinforcement Learning |
    | Finding groups (or clusters) of similar news items by comparing words in them | D. Unsupervised learning |
    | Classifying a car as sports or family by comparing its features to previous labeled examples | C. Supervised learning |

2. Which of the following are true of inductive learning? (Choose all that apply):

    - [x] Tries to learn general principles from limited data.
    - [x] Training data is labeled i.e. data contains the attributes and class
      labels.
    - [ ] Learner needs to see ~more than 50% of the population~ data before
      making a model. __(no need to more than 50%)__
    - [x] Learner's model improves with more training data

3. Which of the following are true about inductive and deductive learning?

    - [x] In inductive learning, the learner looks at a limited number of
      examples and tries to extract principles that can be applied to other
      data.
    - [x] In deductive learning, the learner applied general or basic
      principles to speciﬁc scenarios.
    - [ ] Machine learning is mostly about ~deductive learning~. __(should be
      inductive learning)__
    - [ ] Inductive learning needs to see ~at least 50% of the population~ to
      be successful.

4. In the formal view of inductive learning, what is the role of the learning
   algorithm?

    - [ ] It checks if the data is labled properly.
    - [ ] It creates the global best function that matches ~all the possible
      data points~.
    - [x] It selects the hypothesis from the set of possible hypothesis that
      best matches the ___training example___.
    - [ ] It checks if data is redundant.

    _First and last statements just don't make sense. And the best function
    should not matches all possible data points, since noise and overfitting
    should be considered._

5. Which of the following represents a general straight line in 2-D, where the
   coordinates represent ![](http://latex.codecogs.com/svg.latex?x_1) and
   ![](http://latex.codecogs.com/svg.latex?x_2) axis.

   Hint: A general straight line is one which can represent any possible line
   by changing parameters. You can assume that in the equations below
   ![](http://latex.codecogs.com/svg.latex?w_0),
   ![](http://latex.codecogs.com/svg.latex?w_1) and
   ![](http://latex.codecogs.com/svg.latex?w_2) are parameters.

    - [x] ![](http://latex.codecogs.com/svg.latex?w_0+w_1x_1+w_2x_2=0)
    - [ ] ![](http://latex.codecogs.com/svg.latex?w_0+x_1^2=0)
    - [ ] ![](http://latex.codecogs.com/svg.latex?x_1+x_2=0)
    - [ ] ![](http://latex.codecogs.com/svg.latex?x_2=0)

6. If the vector
   ![](http://latex.codecogs.com/svg.latex?W=%5Cbegin%7Bpmatrix%7D%20-1%5C%5C%202%5C%5C%201%20%5Cend%7Bpmatrix%7D)
   and vector
   ![](http://latex.codecogs.com/svg.latex?X=%5Cbegin%7Bpmatrix%7D%202%5C%5C%20-3%5C%5C%201%20%5Cend%7Bpmatrix%7D),
   what is the value of ![](http://latex.codecogs.com/svg.latex?W^TX), where
   ![](http://latex.codecogs.com/svg.latex?W^T) represents the transpose of the
   ![](http://latex.codecogs.com/svg.latex?W) vector.

   Answer:
   ![](http://latex.codecogs.com/svg.latex?-1\times{2}+2\times{-3}+1\times{1}=-7).

7. The decision boundary of a perceptron is:

    - [ ] circle
    - [ ] rectangle
    - [x] straight line
    - [ ] convex polygon

8. Which of the following are true about decision trees?

    - [ ] We keep on splitting till the node contains data from a single
      ~attribute~. __(should be *class*)__
    - [x] Each path from the root to the leaf can be used to represent a rule.
      For example: if (gpa> 3.5) ![](http://latex.codecogs.com/svg.latex?\land)
      (work experience > 2.0) then class = 1
    - [x] If a node contains data from more than one class, it is split based
      on some attribute.
    - [x] The root node contains all the training examples.

9. In case of inductive learning, you can tune your model to achieve which of
   the following:

    - [x] zero training error.
    - [ ] zero test error.
    - [ ] zero training error and zero test error.
    - [ ] you can not achieve zero error for any of the datasets.

    _Note that, zero training error is possible, since all training data is
    available, however, it would lead to overfitting._

10. According to Hoeffding's inequality, the difference between sample and
    population parameter decreases when:

    - [x] the sample size becomes large enough.
    - [ ] the sample size becomes small enough.
    - [ ] it doesn't depend on sample size.
    - [ ] it can never become small.
