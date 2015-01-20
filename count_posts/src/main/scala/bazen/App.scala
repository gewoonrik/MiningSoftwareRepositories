package bazen

import java.io.{File, PrintWriter}
import java.sql.{ResultSet, DriverManager}
import java.time.Instant
import java.time.temporal.ChronoUnit

import scala.collection.mutable


/**
 * @author ${user.name}
 */
object App {

  var tags = scala.collection.mutable.Map[String, Int]()


  def matchVersion(version : String, title: String, body : String): Boolean = {
    if(version.matches("[0-9]\\.0\\.0")) {
      val majorVersionNumber = version.split('.')(0)
      if(title.contains(" "+majorVersionNumber+".0 ") || body.contains(" "+majorVersionNumber+".0 ")) {
        return true
      }
    }
    return title.contains(version) || body.contains(version)
  }

  def stripThings(text : String): String = {
    text.toLowerCase()
      //.replaceAll("<code>.*</code>"," ")
      .replaceAll("twitter"," ")
      .replaceAll("bootstrap", " ")
      .replaceAll("https?://[^\\s<>\"]+|www\\.[^\\s<>\"]+", " ")
      .replaceAll("<[^<]+?>", " ")
      .replaceAll("&[#a-zA-Z ]+?;", " ")
    //  .replaceAll("([\\w]*[^a-zA-Z0-9]+?[\\w]*)", " ")
  }

  def main(args : Array[String]) {
    // Change to Your Database Config
    val conn_str = "jdbc:mysql://localhost:3306/stackoverflow?user=&password="

    // Load the driver

    // Setup the connection
    val conn = DriverManager.getConnection(conn_str)
    val conn2 = DriverManager.getConnection(conn_str)

    try {
      val input = scala.io.Source.fromFile("..\\data\\bootstrap-versions-date.csv").getLines()
      val versionsReleaseDate = input.map(v => {
        val vers = v.split(",")
        vers(0) = vers(0).stripPrefix("v")
        val instant = Instant.parse(vers(1));
        (vers(0), instant)
      }).toMap





      // Configure to be Read Only
      val statement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)

      // Execute Query
      val rs = statement.executeQuery("SELECT p1.Title, p1.Body, p1.id, CAST(p1.CreationDate AS DATE) as dag FROM posts p1 INNER JOIN posttags ON p1.id = posttags.PostId WHERE posttags.TagId = 72270 AND p1.PostTypeId = 1")

      val versionToPost = mutable.Map[String, mutable.MutableList[(String, Int)]]()


      versionsReleaseDate.keys.foreach(v=> {
        val listOfPosts = mutable.MutableList[(String,Int)]()
        versionToPost.put(v, listOfPosts)
      })

      while (rs.next) {
        val title = rs.getString("Title")
        val body = rs.getString("Body")
        val date = rs.getDate("dag")
        val id = rs.getInt("id")
        val dateInstant = new java.util.Date(date.getTime).toInstant
        versionsReleaseDate.keys.foreach(v => {
          val releaseDate = versionsReleaseDate.get(v).get
          val listOfPosts = versionToPost.get(v).get
          if(matchVersion(v, title, body)&& dateInstant.compareTo(releaseDate) >= 0) {
            val strippedTitle = stripThings(title)
            val strippedBody = stripThings(body)
            listOfPosts +=((strippedTitle+" "+strippedBody, id))
          }
        })
      }
      println("starting writing")
      versionsReleaseDate.foreach(v => {
        println("mkdir versie "+v)
        val dir = new File("..\\results\\malletInput\\"+v._1);
        dir.mkdirs()
        versionToPost.get(v._1).toSeq.foreach(t => {
          t.foreach(post => {
            val id = post._2
            val writer = new PrintWriter(new File("..\\results\\malletInput\\"+v._1+"\\"+id+".txt"))
            writer.write(post._1)
            writer.close()
          })
        })
      })


      statement.close()
      println("finished wrinting to files")
    }
    finally {
      conn.close
    }
  }

  def splitTags(tags : String) : Seq[String] = {
    tags.filterNot(_ == '<').split('>')
  }

}
