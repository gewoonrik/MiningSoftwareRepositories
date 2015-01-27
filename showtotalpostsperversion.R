versions <- read.csv2(file="/home/erwin/Github/MiningSoftwareRepositories/results/postCount.txt", sep=",", dec=".", header=FALSE)
# graph
plot(versions, xlab="versions", ylab="Total posts matched", main="Total posts matched per version")