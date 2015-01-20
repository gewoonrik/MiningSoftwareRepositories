# Load data
data <- read.csv2(file="~/Github/MiningSoftwareRepositories/work/clustering - score/average_score_per_version_30_days.txt", sep=",", dec=".", header=FALSE)
x = data[[2]]
names(x) <- data[[1]]

# Perform 1-dimentsional k-means with k=3
(cl <- kmeans(x, 3, nstart = 25))

# Plot
plot(c(1,1),range(x),type="l",col="lightgrey",xlab="",xaxt="n", ylab="average score per question", main="clustering releases based on the average score of posts")
points(rep(1,length(x)),x, col = cl$cluster)
#points(cl$centers, col = 1:3, pch = 8) # show cluster-centers
text(1,x,names(x),pos=4) # show versions next to points