package org.fiware.cosmos.orion.flink.connector.examples.example6

import org.apache.spark._
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.fiware.cosmos.orion.spark.connector.OrionReceiver
/**
  * Example5 Orion Connector
  * @author @sonsoleslp
  */
object Example6 {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[4]").setAppName("Temperature")
    val ssc = new StreamingContext(conf, Seconds(10))
    // Create Orion Source. Receive notifications on port 9001
    val eventStream = ssc.receiverStream(new OrionReceiver(9001))
    // Process event stream
    val processedDataStream = eventStream
      .flatMap(event => event.entities)
      .map(ent => {
        ent.attrs("information").value.asInstanceOf[Map[String, Any]]
      })
      .map(list => list("buses").asInstanceOf[List[Map[String, Any]]])
      .flatMap(bus => bus)
      .map(bus => {
        val name = bus("name").asInstanceOf[String]
        val price = bus("price").asInstanceOf[scala.math.BigInt].intValue()
        (name, price)
      })
      .reduceByKeyAndWindow(_ min _, Seconds(10))

    processedDataStream.print
    ssc.start()
    ssc.awaitTermination()
  }
}