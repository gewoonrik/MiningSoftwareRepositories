

data <- read.csv2(file="C:\\Users\\Rik\\Documents\\resultsWholeStackoverflow.txt", sep=",", dec=".", header=FALSE, colClasses=c("Date",NA))
data[[1]] <- as.Date(data[[1]])


weekends <-subset(data, (weekdays(V1) %in% c('zaterdag','zondag')))
weekdays <-subset(data, !(weekdays(V1) %in% c('zaterdag','zondag')))


# Create graph
plot(weekdays, xlab="Date", ylab="#posts", type="l", col="blue", main="Number of posts per day of stackoverflow")
lines(weekends, col="red")