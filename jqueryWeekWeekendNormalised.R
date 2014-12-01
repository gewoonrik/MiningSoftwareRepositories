
# Load data
data <- read.csv2(file="~/Github/MiningSoftwareRepositories/results.txt", sep=",", dec=".", header=FALSE, colClasses=c("Date",NA))
data[[1]] <- as.Date(data[[1]])


weekends <-subset(data, (weekdays(V1) %in% c('zaterdag','zondag')))
weekdays <-subset(data, !(weekdays(V1) %in% c('zaterdag','zondag')))
versions <- read.csv2(file="C:\\Users\\Rik\\Documents\\jquery.txt", sep=",", header=FALSE)
versions[[2]] <- as.Date(versions[[2]])


linearreg = lm(V2 ~ V1, data=weekdays)
weekdays$V2 = linearreg$residuals
plot(weekdays, xlab="Date", ylab="#posts-regression of #posts", main="Attempt to normalize #posts by plotting residual of regression.", type="l", col="blue")
for(i in versions[2])
  abline(v=i)

