##############################################################
# R code for exercise 1
##############################################################

# Read singer.txt file.
mydata <- read.table("singer.txt", header = TRUE, sep = ",")

# Extract data by different voice part.
mydata_Bass = subset(mydata, voice.part == "Bass")
mydata_Tenor = subset(mydata, voice.part == "Tenor")
mydata_Alto = subset(mydata, voice.part == "Alto")
mydata_Soprano = subset(mydata, voice.part == "Soprano")

# Use ggplot to draw boxplots.
pdf("voice part boxplot.pdf")
library(ggplot2)
ggplot(mydata, aes(x=voice.part, y=height)) + geom_boxplot() + theme_classic()

dev.off()

# Calculate numbers, means and standard deviations of Bass singers
# and Tenor singers.
nums_Bass <- nrow(mydata_Bass)
mean_Bass <- mean(mydata_Bass[, 1])
sd_Bass <- sd(mydata_Bass[, 1])

nums_Tenor <- nrow(mydata_Tenor)
mean_Tenor <- mean(mydata_Tenor[, 1])
sd_Tenor <- sd(mydata_Tenor[, 1])

# Calculate nu.
i <- (sd_Bass^2) / nums_Bass + (sd_Tenor)^2 / nums_Tenor
j <- (sd_Bass^4) / ((nums_Bass^2)*(nums_Bass - 1))
k <- (sd_Tenor^4) / ((nums_Tenor^2)*(nums_Tenor - 1))
nu <- i / (j + k)

# Calculate t_obs.
t_obs <- (mean_Bass - mean_Tenor) /
    sqrt(sd_Bass^2 / nums_Bass + sd_Tenor^2 / nums_Tenor)

# Calculate p-value.
p_value <- 1 - pt(t_obs, nu)

##############################################################
# R code for exercise 2
##############################################################

# Calculate test statistic.
t_obs1 <- (9.02 - 10) / (2.22 / sqrt(20))
p1_value <- 1 - pt(t_obs1, 20 - 1)

# Estimate the p-value of the test using Monte Carlo simulation
time <- 10000
est_value <- sum(rt(time, 20-1) > t_obs1) / time

##############################################################
# R code for exercise 3
##############################################################

# Calculate CI.
##CI <- mean(y) - mean(x) + c(-1, 1) * qt(1-(alpha/2), nu) *
##      sqrt(sd(x)^2 / n_1 + sd(y)^2 / n_2)
##CI <- mean(y) - mean(x) + c(-1, 1) * qnorm(1-(alpha/2)) *
##      sqrt(sd(x)^2 / n_1 + sd(y)^2 / n_2)
CI <- 2887 - 2635 + c(-1, 1) * qnorm(1 - (0.05/2)) *
    sqrt(365^2 / 400 + 412^2 / 500)

# Calculate z.
z_1 <- (2887 - 2635) /sqrt(365^2 / 400 + 412^2 / 500)
# Calculate p-value
p2_value <- 1 - pnorm(z_1)
