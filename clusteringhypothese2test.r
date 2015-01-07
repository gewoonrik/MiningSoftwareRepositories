# Load data
data1 <- read.csv2(file="/home/erwin/Github/MiningSoftwareRepositories/results/average_score_per_version_30_days_after_release_right_2.3.2_release.txt", sep=",", dec=".", header=FALSE)
data2 <- read.csv2(file="/home/erwin/Github/MiningSoftwareRepositories/results/posts_per_version_total_normalized_30_days.txt", sep=",", dec=".", header=FALSE)
x <- rbind(data1[[2]],data2[[2]])
colnames(x) <- c("x", "y")

# plotting
(cl <- kmeans(x, 3, nstart = 25))
plot(x, col = cl$cluster)
points(cl$centers, col = 1:3, pch = 8)
