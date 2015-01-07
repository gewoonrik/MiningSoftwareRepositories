# Load data
data <- read.csv2(file="/home/erwin/Github/MiningSoftwareRepositories/results/posts_per_version_total_normalized_30_days.txt", sep=",", dec=".", header=FALSE)
x = data[[2]]
names(x) <- data[[1]]


(cl <- kmeans(x, 3, nstart = 25))
plot(c(1,1),range(x),type="l",col="lightgrey",xlab="",xaxt="n", ylab="# of posts", main="clustering releases based on # of posts")
points(rep(1,length(x)),x, col = cl$cluster)
#points(cl$centers, col = 1:3, pch = 8)
#text(1,x,names(x),pos=4)