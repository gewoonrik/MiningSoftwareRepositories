MiningSoftwareRepositories
==========================

Mining question
--------------------
Description:
- Matching StackOverflow questions with the releases of open source software.

Projects to investigate:
- [jQuery](https://github.com/jquery/jquery/releases): up to the release of jQuery 2.0


TODO
------------------------
- [x] Setup Project
- [x] Import data into Database
- [x] Select projects to investigate
- [x] Retrieve versions of project
- [x] Retrieve number of posts by day
- [x] Scrape versions of jQuery
- [x] Plot the number of posts per day in a graph 
- [x] Add lines to the graph on the days of releases
- [x] Normalize graph to ocunter increases/decreases in jquery popularity.
- [ ] Use linear regression to analyse past releases
- [ ] Use linear regression to predict unhype-day of releases
- [ ] Prepare presentation


Text search
----------------------
- Find out how many posts refer to specific jquery version. (questions of specific version)/(total number of questions)
- Find untagged questions about jquery using text search. 

There is enough refering to specific versions to have an impact: http://stackoverflow.com/questions/22560963/bxslider-not-working-with-jquery-2-1-0

SQL queries to remember
-----------------------
`SELECT COUNT(*) FROM posts INNER JOIN posttags ON posts.id = posttags.PostId WHERE posttags.TagId = 820 GROUP BY CAST(posts.CreationDate AS DATE)`
This query selects all posts with the tag "jquery" and groups them by day and returns the count of posts per day

