versions <- read.csv2(file="/home/erwin/Github/MiningSoftwareRepositories/results/postCount.txt", sep=",", dec=".", header=FALSE)
versions[[2]] <- as.Date(versions[[2]])
dates = as.numeric(versions[[2]])

len = length(versions[2]);
durations = dates
for(i in 2:length(dates)) {
  durations[i] = dates[i-1] - dates[i] 
}
durations[1] = 1

versionsduration = versions
versionsduration[2] = durations

# graph
plot(versionsduration, xlab="versions", ylab="Days until next release", main="Days until next release for each release")