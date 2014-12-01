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
      val map =  scala.collection.mutable.Map[String,Double]()
      val lines = scala.io.Source.fromFile("C:\\Users\\Rik\\Documents\\GitHub\\MiningSoftwareRepositories\\results.txt").getLines()
        .map((s: String) =>s.split(","))
        .map((strings: Array[String]) => (strings(0),strings(1).toInt))
        .foreach((tuple: (String, Int)) =>map += tuple._1 -> tuple._2)

    val stackOverflowLines = scala.io.Source.fromFile("C:\\Users\\Rik\\Documents\\resultsWholeStackoverflow.txt").getLines()
      .map((s: String) =>s.split(","))


    val writer = new PrintWriter(new File("C:\\Users\\Rik\\Documents\\jqueryNormalized.txt" ))
      for(lol <- stackOverflowLines) {
        val value = map.get(lol(0))
        if(value.isDefined) {
          val newValue = value.get / lol(1).toDouble
          map += lol(0) -> newValue
        }
        //writer.write(lol(0)+","+lol(1)+"\n")
      }
      map.foreach((tuple: (String, Double)) => writer.write(tuple._1+","+tuple._2+"\n"))
      writer.close()

      println("finished wrinting to file")

  }

  def splitTags(tags : String) : Seq[String] = {
    tags.filterNot(_ == '<').split('>')
  }

}
