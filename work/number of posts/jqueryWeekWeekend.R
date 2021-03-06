
# Load data
data <- read.csv2(file="~/Github/MiningSoftwareRepositories/results.txt", sep=",", dec=".", header=FALSE, colClasses=c("Date",NA))
data[[1]] <- as.Date(data[[1]])


weekends <-subset(data, (weekdays(V1) %in% c('zaterdag','zondag')))
weekdays <-subset(data, !(weekdays(V1) %in% c('zaterdag','zondag')))
versions <- read.csv2(file="C:\\Users\\Rik\\Documents\\jquery.txt", sep=",", header=FALSE)
versions[[2]] <- as.Date(versions[[2]])

# Create graph
plot(weekdays, type="l", main="Number of posts per day with tag jQuery", xlab="Date", ylab= "#posts", col="blue")
lines(weekends, col="red")
legend('topleft', c('weekdays', 'weekends'), 
       pch = "_",  col=c('blue', 'red'),  cex=.75)
