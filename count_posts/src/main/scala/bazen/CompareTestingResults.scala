package bazen

import scala.io.Source

object CompareTestingResults {
  val PATH_RESULTS_MANUAL = "../work/matching-test/matched-posts-manual.csv"
  val PATH_RESULTS_AUTO = "../work/matching-test/matched-posts-test.csv"

  def main (args: Array[String]) {
    val manual = getCsv(PATH_RESULTS_MANUAL)
    val auto = getCsv(PATH_RESULTS_AUTO)
    var trueNegative, truePositive, falseNegative, falsePositive = 0
    manual.foreach(post => {
      val autoPost = auto.find (p => p._1 == post._1).getOrElse((post._1, None))
      post._2 match {
        case Some(version) => {
          if(autoPost._2.contains(version)) {
            truePositive += 1
          } else {
            println("FN " + post._1 + ": " +post._2 + " != " + autoPost._2)
            falseNegative += 1
          }
        }
        case None => {
          if(autoPost._2.isEmpty) {
            trueNegative += 1
          } else {
            falsePositive += 1
            println("FP " + post._1 + ": " +post._2 + " != " + autoPost._2)
          }
        }
      }
    })
    val precision = truePositive.toFloat / (truePositive + falsePositive)
    val recall = truePositive.toFloat / (truePositive + falseNegative)
    println("TP: " + truePositive);
    println("FP: " + falsePositive);
    println("TN: " + trueNegative);
    println("FN: " + falseNegative);
    println("Precision: " + precision);
    println("Recall: " + recall);
  }

  def getCsv(path : String): Map[Int,Option[String]] = {
    val input = Source.fromFile(path).getLines()
    println("Results retrieved from: " + path);
    input.map(v => {
      val parts = v.split(",")
      val version = if(parts.length > 1) Some(parts(1)) else None
      (parts(0).trim.toInt, version)
    }).toMap
  }

}
