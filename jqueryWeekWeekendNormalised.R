
# Load data
data <- read.csv2(file="~/Github/MiningSoftwareRepositories/results.txt", sep=",", dec=".", header=FALSE, colClasses=c("Date",NA))
data[[1]] <- as.Date(data[[1]])


weekends <-subset(data, (weekdays(V1) %in% c('zaterdag','zondag')))
weekdays <-subset(data, !(weekdays(V1) %in% c('zaterdag','zondag')))
versions <- read.csv2(file="C:\\Users\\Rik\\Documents\\jquery.txt", sep=",", header=FALSE)
versions[[2]] <- as.Date(versions[[2]])

# Create graph
plot(weekdays, type="l", col="blue")
lines(weekends, col="red")
plot(weekdays, col="purple")

for(i in versions[2])
  abline(v=i)
linearreg = lm(V2 ~ V1, data=weekdays)
weekdays$V2 = weekdays$V2/linearreg$fitted.values
lines(weekdays, col="red")


