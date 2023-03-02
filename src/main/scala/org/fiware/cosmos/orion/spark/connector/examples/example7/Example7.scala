package org.fiware.cosmos.orion.spark.connector.examples.example7


import org.apache.spark._
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.fiware.cosmos.orion.spark.connector.NGSILDReceiver

object Example7{
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster("local[4]").setAppName("Temperature")
    val ssc = new StreamingContext(conf, Seconds(10))
    // Create NGSILD Receiver. Receive NGSI-LD notifications on port 9001
    val eventStream = ssc.receiverStream(new NGSILDReceiver(9001))
    // Process event stream
    val processedDataStream = eventStream
      .flatMap(event => event.entities)
      .map(entity => {
        val temp = entity.attrs("temperature")("value")
        (entity.id, temp)
      })
      
    processedDataStream.print
    ssc.start()
    ssc.awaitTermination()
  }
}