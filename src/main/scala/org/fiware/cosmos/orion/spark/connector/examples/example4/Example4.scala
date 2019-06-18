package org.fiware.cosmos.orion.spark.connector.examples.example4


import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.fiware.cosmos.orion.spark.connector.OrionReceiver


/**
  * Example4 Orion Connector
  * @author @sonsoleslp
  */
object Example4{

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[4]").setAppName("AvgTemperature")
    val ssc = new StreamingContext(conf, Seconds(10))
    ssc.checkpoint("./output")
    // Create Orion Source. Receive notifications on port 9001
    val eventStream = ssc.receiverStream(new OrionReceiver("localhost", 9001))

    // Process event stream
    val processedDataStream = eventStream
      .flatMap(event => event.entities)
      .map(ent => ent.id -> {(ent.attrs("temperature").value.asInstanceOf[Number].floatValue(),1L)} )
      .reduceByKeyAndWindow((acc:(Float,Long),value:(Float,Long))=>{(acc._1 + value._1, acc._2 + value._2)}, Seconds(10))
      .map((agg :  (String,(Float,Long))) =>  (agg._1, agg._2._1 / agg._2._2))

    processedDataStream.print()
    ssc.start()
    ssc.awaitTermination()
  }
}

