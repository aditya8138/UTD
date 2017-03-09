# Define function getdraw(x,y) for (c) and (d).
# Arguments:
#       'x' is the number of draws to simulate.
#       'y' is the times to repeat.
# Result: Summary of E(X), Var(X) and P(X>5) of each simulation in a table.

getdraw <- function(x, y) {
    # Simulated x draws y times, result stored in variable 'draw'.
    draw <- replicate(y, runif(x)^(1/4))

    # Calculated the result of each 1000 draws,
    # i.e. apply calculation in each column.

    # Calculated the mean.
    mean5 <- apply(draw, 2, mean)

    # Calculated the variance.
    var5 <- apply(draw, 2, var)

    # Calculated the probability of X > 5.
    p5 <- apply(draw, 2, (function(x) sum(x > 0.5)/sum(x >= 0)))

    # Combine the result in a table.
    result <- cbind(mean5, var5, p5)

    # Rename column.
    colnames(result) <- c("Mean","Variance","P(X>0.5)")

    return(result)
}
