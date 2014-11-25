package bazen

import java.sql.{ResultSet, DriverManager}

import com.mysql.jdbc.MySQLConnection

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
      val rs = statement.executeQuery("SELECT Id, TagName FROM tags")
      statement.setFetchSize(Integer.MIN_VALUE);

      // Iterate Over ResultSet
      while (rs.next) {
        tags += rs.getString("TagName") -> rs.getInt("Id")
      }
      statement.close()
      println("finished loading tags")

      val statement2 = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
      statement2.setFetchSize(Integer.MIN_VALUE);
      val posts = statement2.executeQuery("SELECT Tags,Id FROM posts")

      val st = conn2.createStatement();
      var i:Long = 0;
      while(posts.next()) {
        val strTags = posts.getString("Tags");
        val postId = posts.getInt("Id")
        if(strTags != null) {
          val curTags = splitTags(strTags)
          val ids = curTags.map(tags(_))
          ids.foreach(tagId =>{
            st.addBatch("INSERT INTO posttags VALUES (" + postId + "," +tagId+ ")");
          })
        }
        if(i%1000 == 0) {
          st.executeBatch()
        }
        i+=1;
      }
      st.executeBatch()
      st.close()
      statement2.close()


    }
    finally {
      conn.close
    }
  }

  def splitTags(tags : String) : Seq[String] = {
    tags.filterNot(_ == '<').split('>')
  }

}
