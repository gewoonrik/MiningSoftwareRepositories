
# Load data
data <- read.csv2(file="~/Github/MiningSoftwareRepositories/results.txt", sep=",", dec=".", header=FALSE, colClasses=c("Date",NA))
data[[1]] <- as.Date(data[[1]])

data19 <- read.csv2(file="C:\\Users\\Rik\\Documents\\results1.9.0.txt", sep=",", dec=".", header=FALSE, colClasses=c("Date",NA))
data19[[1]] <- as.Date(data19[[1]])

data110 <- read.csv2(file="C:\\Users\\Rik\\Documents\\results1.10.0.txt", sep=",", dec=".", header=FALSE, colClasses=c("Date",NA))
data110[[1]] <- as.Date(data110[[1]])




versions <- read.csv2(file="C:\\Users\\Rik\\Documents\\jquery1.9.0.versions.txt", sep=",", header=FALSE)
versions[[2]] <- as.Date(versions[[2]])

# Create graph
#plot(weekdays, type="l", col="blue")
#lines(weekends, col="red")
plot(data19, xlab="Date", ylab="#posts",main="#posts for jQuery version 1.9.0 and version 1.10.0.", xlim=as.Date(c("2013-01-01","2014-08-01")), type="l", col="purple")
lines(data110, xlim=as.Date(c("2013-05-24","2014-08-01")), type="l", col="red")

legend('topright', c('posts about version 1.9.0', 'posts about version 1.10.0'), 
        lty=1, col=c('purple', 'red'),  cex=.75)

for(i in versions[2])
  abline(v=i)

