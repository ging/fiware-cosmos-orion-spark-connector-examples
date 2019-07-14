package org.fiware.cosmos.orion.spark.connector.examples.example1


import org.apache.spark._
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.fiware.cosmos.orion.spark.connector._
/**
  * Example1 Orion Connector
  * @author @sonsoleslp
  */
object Example1{

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster("local[4]").setAppName("Temperature")
    val ssc = new StreamingContext(conf, Seconds(10))
    // Create Orion Source. Receive notifications on port 9001
    val eventStream = ssc.receiverStream(new OrionReceiver(9001))

    // Process event stream
    eventStream
      .flatMap(event => event.entities)
      .map(ent => {
        val temp: Float = ent.attrs("temperature").value.asInstanceOf[Number].floatValue()
        (ent.id, temp)
      })
      .reduceByKeyAndWindow(_ min _ ,Seconds(10))

      .print


    ssc.start()
    ssc.awaitTermination()
  }
}