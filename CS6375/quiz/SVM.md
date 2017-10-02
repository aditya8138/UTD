# Quiz SVM

Note that all ~scratch words~ indicate wrong part in the option, and all
_italic_ or __bold__ sentences are notes.

1.  Consider the straight line below:

        4x + 2y + 7 = 0
        
    Which of the following vectors would be normal (perpendicular) to this line?
    
    -   [ ] (4,2)
    -   [ ] (2,4)
    -   [ ] (-4,-2)
    -   [ ] (-2,-4)

2.  Consider the straight line in 2-D:

        -2x + 3y + 4 = 0
    
    Which of the following are true?
    
    -   [ ] The distance from the origin to this line is: ![](http://latex.codecogs.com/svg.latex?\frac{4}{\sqrt{13}})
    -   [ ] A straight line parallel to this line and passing through the point (5,6) is ![](http://latex.codecogs.com/svg.latex?-2x+3y-8=0)
    -   [ ] A straight line parallel to this line and passing through the point (5,6) is ![](http://latex.codecogs.com/svg.latex?-2x+3y+8=0)
    -   [ ] The distance of the given line from point (5, 6) is: ![](http://latex.codecogs.com/svg.latex?\frac{12}{\sqrt{13}})

3.  Suppose for a two attribute scenario, we design the following decision function:

        y = 4x 1 + 2x 2 + 7
    
    (Remember y = 0 on the decision boundar and sign of y gives the classiﬁcation label for a test data point) What would be the distance from the decision boundary and classiﬁcation for the data point (2, 1)

    - [ ] Distance: ![](http://latex.codecogs.com/svg.latex?\frac{17}{\sqrt{20}}) and classification positive
    - [ ] Distance: ![](http://latex.codecogs.com/svg.latex?\frac{17}{\sqrt{10}}) and classification positive
    - [ ] Distance: ![](http://latex.codecogs.com/svg.latex?\frac{17}{\sqrt{20}}) and classification negative
    - [ ] Distance: ![](http://latex.codecogs.com/svg.latex?\frac{17}{4}) and classification positive

4.  In SVM classifier design, what does the following represent: ![](http://latex.codecogs.com/svg.latex?y_i(w^Tx_i+b))

    where ![](http://latex.codecogs.com/svg.latex?y_i) is the true class label, w is the weights vector ![](http://latex.codecogs.com/svg.latex?x_i) is the attributes vector b is the bias term.
    
    -   [ ] It represents the error of classiﬁcation. We would like this term to be as small as possible.
    -   [ ] It represents the functional margin or the confidence of classification. We would like this term to be as small as possible.
    -   [X] It represents the functional margin or the confidence of classification. We would like this term to be as large as possible. 
    -   [ ] It represents the overﬁtting of classification. We would like this term to be as small as possible.

5.  What design objectives does a SVM classifier have:

    -   [X] maximize the margin between the surface and the closest __training data__ points on either side 
    -   [ ] maximize the margin between the surface and the all the training data points on either side
    -   [ ] maximize the margin between the surface and the closest test data points on either side 
    -   [ ] minimize the margin between the surface and the closest training data points on either side

6.      For the situation shown below, how many points are support vectors?

    -   [ ] 1 
    -   [ ] 2
    -   [ ] can't say 
    -   [ ] 3

7.      Consider a 2-D SVM separator whose equation is given by:

        x 2 = 2 x 1 Assuming that we have normalized the data such that the distance from the separator to the lines passing through support vectors is at a distance of 1 on each side. The situation is illustrated below:

        What will be the total margin of separation for the SVM i.e. sum of the distance from the separator to the support vectors.
        
    -   [ ] ![](http://latex.codecogs.com/svg.latex?\frac{2}{\sqrt{5}})
    -   [ ] ![](http://latex.codecogs.com/svg.latex?\frac{1}{\sqrt{5}})
    -   [ ] 1
    -   [ ] 2       

8.  What is the solution obtained for the best value of weights when the SVM problem is solved using Lagrange multipliers?

    Assume that we are using the linear kernel and the Lagrange multipliers are denoted by , true class value by y and attributes by x alpha 
        
    -   [ ] ![](http://latex.codecogs.com/svg.latex?w=\sum_i{\alpha_iy_ix_i}) (w space equals sum for i of alpha subscript i y subscript i x subscript i )
    
    -   [ ] ![](http://latex.codecogs.com/svg.latex?w=\sum_i{\alpha_iy_ix_i^2}) (w space equals sum for i of alpha subscript i y subscript i x subscript i squared )
    -   [ ] ![](http://latex.codecogs.com/svg.latex?w=\sum_i{\alpha_iy_i) (w space equals sum for i of alpha subscript i y subscript i )
    -   [ ] ![](http://latex.codecogs.com/svg.latex?w=\sum_i{\alpha_ix_i) (w space equals sum for i of alpha subscript i x subscript i)
        
9.  How does SVM classiﬁer work with non-linear datasets?

    -   [ ] By using kernels, which can evaluate similarity of data points in transformed dimensions. 
    -   [ ] By making a non-linear decision surface
    -   [ ] By outputting a classiﬁer with non-zero training error values 
    -   [ ] SVM cannot work with non-linear dataset

10. What is the purpose of introducing slack variables in SVM?

    -   [ ] It is done to randomize the model 
    -   [ ] In case where perfect classification cannot be done on the training dataset, we introduce slack variables as a way to penalize mis-classified points. 
    -   [ ] It is done to avoid overfitting 
    -   [ ] It is done as a way to prune the model





