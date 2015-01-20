# Load data
data1 <- read.csv2(file="~/Github/MiningSoftwareRepositories/work/clustering - score/average_score_per_version_30_days.txt", sep=",", dec=".", header=FALSE)
data2 <- read.csv2(file="~/Github/MiningSoftwareRepositories/work/clustering - # of posts/posts_per_version_total_normalized_30_days.txt", sep=",", dec=".", header=FALSE)
data1t = data1[[2]]
data2t = data2[[2]]

# Perform 2-dimensional k-means with k = 3
x <- t(rbind(data1t, data2t))
(cl <- kmeans(x, 3, nstart = 25))

# plotting
plot(x, col = cl$cluster, ylab="# of posts", xlab="average score per question")
points(cl$centers, col = 1:3, pch = 8)