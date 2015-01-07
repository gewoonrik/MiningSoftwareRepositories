package bazen

import java.io.{File, PrintWriter}
import java.sql.{ResultSet, DriverManager}
import java.time.Instant
import java.time.temporal.ChronoUnit



/**
 * @author ${user.name}
 */
object App {

  var tags = scala.collection.mutable.Map[String, Int]()

  def main(args : Array[String]) {
    // Change to Your Database Config
    val conn_str = "jdbc:mysql://localhost:3306/stackoverflow?user=&password="

    // Load the driver
    classOf[com.mysql.jdbc.Driver]

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

      val inputDate = scala.io.Source.fromFile("..\\results\\posts_per_day_total.txt").getLines()
      val dateMap = inputDate.map(s => {
        val splitted = s.split(",")
        (splitted(0),splitted(1).toInt)
      }
      ).toMap



      // Configure to be Read Only
      val statement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)

      // Execute Query
      val rs = statement.executeQuery("SELECT Title, Body, CAST(posts.CreationDate AS DATE) as dag FROM posts INNER JOIN posttags ON posts.id = posttags.PostId WHERE posttags.TagId = 72270 AND PostTypeId = 1")

      // Iterate Over ResultSet

      val numberOfPostsPerVersion = scala.collection.mutable.Map[String, Double]()
      while (rs.next) {
        val title = rs.getString("Title")
        val body = rs.getString("Body")
        val date = rs.getDate("dag")
        val dateInstant = new java.util.Date(date.getTime).toInstant
        versionsReleaseDate.keys.foreach(v => {
          val releaseDate = versionsReleaseDate.get(v).get
          if(title.contains(v) || body.contains(v) && dateInstant.isBefore(releaseDate.plus(100, ChronoUnit.DAYS))) {
            val postCount = numberOfPostsPerVersion.getOrElseUpdate(v, 0)
            val normalized = 1.toDouble/dateMap.get(date.toString).get.toDouble
            numberOfPostsPerVersion.put(v, postCount+normalized)
          }
        })
      }
      println("starting writing")
      val writer = new PrintWriter(new File("..\\results\\posts_per_version_total_normalized_100_days.txt" ))
      numberOfPostsPerVersion.toSeq.sortBy(_._1).foreach(tuple =>{
        writer.write(tuple._1+","+tuple._2+"\n")
      })
      writer.close()

      statement.close()
      println("finished wrinting to file")
    }
    finally {
      conn.close
    }
  }

  def splitTags(tags : String) : Seq[String] = {
    tags.filterNot(_ == '<').split('>')
  }

}
