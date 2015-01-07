require(graphics)
library(drc)

## the nls() internal cheap guess for starting values can be sufficient:
data <- read.csv2(file="C:\\Users\\Erwin\\Documents\\GitHub\\MiningSoftwareRepositories\\results\\posts_per_day_total.txt", sep=",", dec=".", header=FALSE, colClasses=c("Date",NA))
data[[1]] <- as.Date(data[[1]])
x = as.numeric(data[[1]])
y = data[[2]]


plot(data)

g <- rep(1:2, c(21, 20))
fit1 <- drm(y ~ x, fct = LL2.5(), subset = g == 1)
fit2 <- drm(y ~ x, fct = LL2.5())

#lines(fitted(fit1) ~ x[g == 1])
fy = fitted(fit2)
lines(x,fy)