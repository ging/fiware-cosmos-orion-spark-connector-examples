package org.fiware.cosmos.orion.flink.connector.examples.example4


import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.fiware.cosmos.orion.spark.connector.OrionReceiver

/**
  * Example4 Orion Connector
  * @author @sonsoleslp
  */
object Example4{

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[4]").setAppName("Temperature")
    val ssc = new StreamingContext(conf, Seconds(10))
    // Create Orion Source. Receive notifications on port 9001
    val eventStream = ssc.receiverStream(new OrionReceiver("localhost", 9001))

    // Process event stream
    val processedDataStream = eventStream
      .flatMap(event => event.entities)
      .map(ent => {
        val temp: Float = ent.attrs("temperature").value.asInstanceOf[Number].floatValue()
        (ent.id, temp)
      })
      .reduceByKeyAndWindow((a,b)=>average(a,b),Seconds(10),Seconds(10) )

    processedDataStream.print

    ssc.start()
    ssc.awaitTermination()
  }

  def average(s: Seq[Float]): Float = s.foldLeft((0.0, 1)) { case ((avg, idx), next) => (avg + (next - avg)/idx, idx + 1) }._1.toFloat

//  class AverageAggregate extends AggregateFunction[Temp_Node, (Float,Float), Float] {
//    override def createAccumulator() = (0L, 0L)
//
//    override def add(value: (Temp_Node), accumulator: (Float, Float)) =
//      (accumulator._1 + value.temperature, accumulator._2 + 1L)
//
//    override def getResult(accumulator: (Float, Float)) = accumulator._1 / accumulator._2
//
//    override def merge(a: (Float, Float), b: (Float, Float)) =
//      (a._1 + b._1, a._2 + b._2)
//  }
//
//
}