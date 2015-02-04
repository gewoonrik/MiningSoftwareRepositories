package bazen

import java.io.{PrintWriter, FileReader}
import java.time.Instant

import org.apache.commons.csv.CSVFormat
import scala.collection.JavaConverters
import scala.util.Random

object PostVersionMatcher {
  val PATH_POSTS = "../bootstrap-posts.csv"
  val PATH_VERSIONS = "../data/bootstrap-versions-date.csv"
  val PATH_OUTPUT = "../matched-posts.csv"

  def main(args : Array[String]) = {
    val posts = getRandomPosts(500)
    val postVersionMatcher = new PostVersionMatcher(getVersions)
    val annotatedPosts = postVersionMatcher.matchPosts(posts)
    //annotatedPosts.foreach(ap => println(ap.post.id + "\t" + ap.version))
    println("# of annotated posts: " + annotatedPosts.length);
    //val unmatchedPosts = posts.filter(post => postVersionMatcher.matchPostToVersions(post).isEmpty)
    writeToFile(annotatedPosts)
  }

  def getPost(): List[Post] = {
    val dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val records = CSVFormat.DEFAULT.parse(new FileReader(PATH_POSTS))
    println("Posts retrieved from: " + PATH_POSTS);
    JavaConverters.asScalaIteratorConverter(records.iterator())
      .asScala
      .map(row => new Post(row.get(0).toInt, row.get(1), row.get(2), dateFormat.parse(row.get(3)).toInstant))
      .toList
  }

  def getVersions(): Map[String, Instant] = {
    val input = scala.io.Source.fromFile(PATH_VERSIONS).getLines()
    println("Versions retrieved from: " + PATH_VERSIONS);
    input.map(v => {
      val vers = v.split(",")
      vers(0) = vers(0).stripPrefix("v")
      val instant = Instant.parse(vers(1))
      (vers(0), instant)
    }).toMap
  }

  def writeToFile(posts : List[AnnotatedPost]): Unit = {
    val writer = new PrintWriter(PATH_OUTPUT)
    posts.foreach(ap => {
        writer.write(ap.post.id + "," + ap.version + System.lineSeparator())
    })
    println("Results printed to: " + PATH_OUTPUT);
    writer.close()
  }

  // Used for testing the matching mechanism
  def getRandomPosts(samples : Int): List[Post] = {
    val rand = new Random(System.currentTimeMillis())
    var remainingPosts = getPost
    Stream.range(0, samples, 1).map(_ => {
      val index = rand.nextInt(remainingPosts.length)
      val result = remainingPosts(index)
      remainingPosts = remainingPosts diff List(index)
      result
    }) toList
  }
}

class PostVersionMatcher(val versionsWithReleaseDate : Map[String, Instant]) {

  def matchPosts(posts : List[Post]): List[AnnotatedPost] = {
    println("Matching " + posts.length + " posts to " + versionsWithReleaseDate.size + " versions...")
    posts.flatMap(matchPostToVersions)
  }

  def matchPostToVersions(post : Post): List[AnnotatedPost] = {
    versionsWithReleaseDate
      .filter(version => postMatchesVersion(post, version._1))
      .map(version => new AnnotatedPost(post, version._1))
      .toList
  }

  def postMatchesVersion(post : Post, version : String): Boolean = {
    if(version.matches("[0-9]\\.0\\.0")) {
      val majorVersionNumber = version.split('.')(0)
      if(post.title.contains(" "+majorVersionNumber+".0 ") || post.body.contains(" "+majorVersionNumber+".0 ")) {
        return true
      }
    }
    return post.title.contains(version) || post.body.contains(version)
  }

  def cleanupText(text : String): String = {
    text
      .replaceAll("<code>.*</code>"," ") // Remove code samples
      .replaceAll("bootstrap", " ") // Remove obvious keywords
      .replaceAll("https?://[^\\s<>\"]+|www\\.[^\\s<>\"]+", " ") // Remove links
      .replaceAll("<[^<]+?>", " ") // Remove HTML
      .replaceAll("&[#a-zA-Z ]+?;", " ") // Remove special characters
  }
}

class Post(val id : Int, val title : String, val body : String, val date : Instant)

class AnnotatedPost(val post : Post, val version : String)