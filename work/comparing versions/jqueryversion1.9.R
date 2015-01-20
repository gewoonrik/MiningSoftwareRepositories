
# Load data
data <- read.csv2(file="~/Github/MiningSoftwareRepositories/results.txt", sep=",", dec=".", header=FALSE, colClasses=c("Date",NA))
data[[1]] <- as.Date(data[[1]])

data19 <- read.csv2(file="C:\\Users\\Rik\\Documents\\results1.9.0.txt", sep=",", dec=".", header=FALSE, colClasses=c("Date",NA))
data19[[1]] <- as.Date(data19[[1]])


weekends <-subset(data, (weekdays(V1) %in% c('zaterdag','zondag')))
weekdays <-subset(data, !(weekdays(V1) %in% c('zaterdag','zondag')))
versions <- read.csv2(file="C:\\Users\\Rik\\Documents\\jquery1.9.0.versions.txt", sep=",", header=FALSE)
versions[[2]] <- as.Date(versions[[2]])

# Create graph
#plot(weekdays, type="l", col="blue")
#lines(weekends, col="red")
plot(data19, xlim=as.Date(c("2013-01-01","2014-08-01")), type="l", col="purple")
for(i in versions[2])
  abline(v=i)

