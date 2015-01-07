# Load data
data <- read.csv2(file="/home/erwin/Github/MiningSoftwareRepositories/results/average_score_per_version_30_days_after_release_right_2.3.2_release.txt", sep=",", dec=".", header=FALSE)
x = data[[2]]
names(x) <- data[[1]]


(cl <- kmeans(x, 3, nstart = 25))
plot(c(1,1),range(x),type="l",col="lightgrey",xlab="",xaxt="n", ylab="average score per question", main="clustering releases based on the average score of posts")
points(rep(1,length(x)),x, col = cl$cluster)
#points(cl$centers, col = 1:3, pch = 8)
text(1,x,names(x),pos=4)