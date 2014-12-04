
# Load data
data19 <- read.csv2(file="~/Github/MiningSoftwareRepositories/results/results1.9.0.txt", sep=",", dec=".", header=FALSE, colClasses=c("Date",NA))
data19[[1]] <- as.Date(data19[[1]])

data110 <- read.csv2(file="~/Github/MiningSoftwareRepositories/results/results1.10.0.txt", sep=",", dec=".", header=FALSE, colClasses=c("Date",NA))
data110[[1]] <- as.Date(data110[[1]])

weekends <-subset(data, (weekdays(V1) %in% c('zaterdag','zondag')))
weekdays <-subset(data, !(weekdays(V1) %in% c('zaterdag','zondag')))
versions <- read.csv2(file="~/Github/MiningSoftwareRepositories/results/jquery1.9.0.versions.txt", sep=",", header=FALSE)
versions[[2]] <- as.Date(versions[[2]])

# Create graph
#plot(weekdays, type="l", col="blue")
#lines(weekends, col="red")
plot(data19, xlim=as.Date(c("2013-01-01","2014-08-01")), type="l", col="green")
lines(data110, xlim=as.Date(c("2013-05-24","2014-08-01")), type="l", col="red")
for(i in versions[2])
  abline(v=i)

########
# writes to file: {version, #posts, offset lm, gradient lm, error lm}
releaseDate = as.Date("2013-05-24")
numbDays = 60
rawdata = data110
v = "1.9.0"
#########
selection = rawdata[rawdata$V1 >= releaseDate  & rawdata$V1 <= (releaseDate + numbDays), ]
error = sd(selection$V2)
count = tail(cumsum(selection$V2), n=1)
linearreg = lm(V2 ~ V1, data=selection)
linearreg
abline(linearreg, col="black")
lineparams = unname(coefficients(linearreg)[c("V1", "(Intercept)")])

line = paste(v, count, lineparams[1], lineparams[2], error, sep=",")
write(line,file="statistics.txt",append=TRUE)

