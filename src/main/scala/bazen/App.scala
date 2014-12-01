package bazen

import java.io.{File, PrintWriter}
import java.nio.charset.StandardCharsets
import java.nio.file.{Paths, Files}
import java.sql.{ResultSet, DriverManager}


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
      // Configure to be Read Only
      val statement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)

      // Execute Query
      val rs = statement.executeQuery("SELECT COUNT(*) as count, CAST(posts.CreationDate AS DATE) as dag FROM posts INNER JOIN posttags ON posts.id = posttags.PostId WHERE posttags.TagId = 820 GROUP BY dag")

      // Iterate Over ResultSet
      println("starting writing")
      val writer = new PrintWriter(new File("C:\\Users\\Rik\\Documents\\results.txt" ))
      while (rs.next) {
        println(rs.getDate("dag")+","+rs.getInt("count"))
        writer.write(rs.getDate("dag")+","+rs.getInt("count")+"\n")
      }
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
