# Load data
data <- read.csv2(file="/home/erwin/Github/MiningSoftwareRepositories/results/posts_per_day_total.txt", sep=",", dec=".", header=FALSE, colClasses=c("Date",NA))
data[[1]] <- as.Date(data[[1]])

weekends <-subset(data, (weekdays(V1) %in% c('zaterdag','zondag')))
weekdays <-subset(data, !(weekdays(V1) %in% c('zaterdag','zondag')))

# plot
plot(weekdays, type="l", col="blue", main="# of posts about twitter-bootstrap", xlab="date", ylab="# of posts")
lines(weekends, col="red")