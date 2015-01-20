# Load data
data <- read.csv2(file="~/Github/MiningSoftwareRepositories/work/clustering - # of posts/posts_per_version_total_normalized_30_days.txt", sep=",", dec=".", header=FALSE)
x = data[[2]]
names(x) <- data[[1]]

# Perform 1-dimensional k-means with k = 3
(cl <- kmeans(x, 3, nstart = 25))

# Plot
plot(x, col = cl$cluster)
points(cl$centers, col = 1:3, pch = 8)