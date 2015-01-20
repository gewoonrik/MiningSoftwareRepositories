package bazen

import java.io.{PrintWriter, File}

import scala.io.Source
import sys.process._
import scala.collection.mutable


/**
 * @author ${user.name}
 */
object App {


  def main(args : Array[String]) {
    val numberOfTopics = 40
    val numberOfWords = 20
    //"/opt/mallet/bin/mallet import-dir --input ../content_posts/data/all --output all-bootstrap-data.mallet --keep-sequence --remove-stopwords" !! ;
    //"/opt/mallet/bin/mallet train-topics --input all-bootstrap-data.mallet --num-topics "+ numberOfTopics+" --output-topic-keys topic_keys_all --inferencer-filename inferencer_all.mallet --output-model model_all.mallet --evaluator-filename evaluator.mallet --num-top-words "+numberOfWords !!;
    val parentDir = new File("..//results/malletInput")
    val dirs = parentDir.listFiles.filter(_.isDirectory).map(_.getAbsolutePath)
    dirs.foreach(dir => {
      val version = dir.split('/').last
      "/opt/mallet/bin/mallet import-dir --input "+dir+" --output "+dir+"/../import-data-"+version+".mallet --keep-sequence --remove-stopwords"!!;
      "/opt/mallet/bin/mallet infer-topics --inferencer inferencer_all.mallet --input "+dir+"/../import-data-"+version+".mallet --output-doc-topics "+dir+"/../results-"+version+".topics"!!;
      val topics = new File(dir+"/../","results-"+version+".topics")
      println(topics)
      val file =  Source.fromFile(topics)
      val lines = file.getLines().drop(1)
      val numberOfFiles = lines.length

      val countPerTopic = collection.mutable.Map[Int, Double]()
      lines.foreach(line => {

        val topicValues = line.split("\\t").drop(2)
        var i = 0;
        for(topicValue <- topicValues)  {
          val value = topicValue.toDouble
          val current = countPerTopic.getOrElse[Double](i, 0)
          countPerTopic.put(i, current+value)
          i+=1
        }
      })
      val writer = new PrintWriter(new File(dir+"/../","result-"+version+".txt"))
      countPerTopic.map(a => {
        a._2/numberOfFiles
      }).foreach( value => {
        writer.write(value+"\\n")
      })
      writer.close()



    })
  }
}

