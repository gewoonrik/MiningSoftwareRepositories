

data <- read.csv2(file="C:\\Users\\Rik\\Documents\\jqueryNormalized.txt", sep=",", dec=".", header=FALSE, colClasses=c("Date",NA))
data[[1]] <- as.Date(data[[1]])


weekends <-subset(data, (weekdays(V1) %in% c('zaterdag','zondag')))
weekdays <-subset(data, !(weekdays(V1) %in% c('zaterdag','zondag')))


# Create graph
plot(weekdays, xlab="Date", ylab="%", col="blue", main="Percentage of posts with tag jquery on stackoverflow divided by the number of posts in total.")
points(weekends, col="red")

linearreg = lm(V2 ~ V1, data=data)
abline(linearreg)