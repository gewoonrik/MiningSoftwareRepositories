MiningSoftwareRepositories
==========================

Mining question
--------------------
Description:
- Matching StackOverflow questions with the releases of open source software.

Projects to investigate:
- [jQuery](https://github.com/jquery/jquery/releases): up to the release of jQuery 2.0


TODO:
- [x] Setup Project
- [x] Import data into Database
- [x] Select projects to investigate
- [x] Retrieve versions of project
- [ ] Retrieve number of posts by day

SQL query we are going to run:
SELECT COUNT(*) FROM posts INNER JOIN posttags ON posts.id = posttags.PostId WHERE posttags.TagId = 820 GROUP BY CAST(posts.CreationDate AS DATE)
This query selects all posts with the tag "jquery" and groups them by day and returns the count of posts per day