package bazen

import java.io.{File, PrintWriter}
import java.nio.charset.StandardCharsets
import java.nio.file.{Paths, Files}
import java.sql.{ResultSet, DriverManager}

import scala.util.parsing.json.{JSONArray, JSONObject, JSON}


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
      val input = scala.io.Source.fromFile("..\\data\\bootstrap-versions.csv").getLines().mkString.split(",")
      val versions = input.map(v => v.stripPrefix("v"))


      // Configure to be Read Only
      val statement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)

      // Execute Query
      val rs = statement.executeQuery("SELECT Title, Body FROM posts INNER JOIN posttags ON posts.id = posttags.PostId WHERE posttags.TagId = 72270")

      // Iterate Over ResultSet

      val numberOfPostsPerVersion = scala.collection.mutable.Map[String, Int]()
      while (rs.next) {
        val title = rs.getString("Title")
        val body = rs.getString("Body")
        versions.foreach(v => {
          if(title.contains(v) || body.contains(v)) {
            val postCount = numberOfPostsPerVersion.getOrElseUpdate(v, 0)
            numberOfPostsPerVersion.put(v, postCount+1)
          }
        })
      }
      println("starting writing")
      val writer = new PrintWriter(new File("..\\results\\posts_per_version_total.txt" ))
      numberOfPostsPerVersion.toSeq.sortBy(_._1).foreach((tuple: (String, Int)) =>{
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
