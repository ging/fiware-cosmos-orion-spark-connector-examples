# fiware-cosmos-orion-spark-connector-examples

[![](https://img.shields.io/badge/FIWARE-Processing\Analysis-88a1ce.svg?label=FIWARE&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABsAAAAVCAYAAAC33pUlAAAABHNCSVQICAgIfAhkiAAAA8NJREFUSEuVlUtIFlEUx+eO+j3Uz8wSLLJ3pBiBUljRu1WLCAKXbXpQEUFERSQF0aKVFAUVrSJalNXGgmphFEhQiZEIPQwKLbEUK7VvZrRvbr8zzjfNl4/swplz7rn/8z/33HtmRhn/MWzbXmloHVeG0a+VSmAXorXS+oehVD9+0zDN9mgk8n0sWtYnHo5tT9daH4BsM+THQC8naK02jCZ83/HlKaVSzBey1sm8BP9nnUpdjOfl/Qyzj5ust6cnO5FItJLoJqB6yJ4QuNcjVOohegpihshS4F6S7DTVVlNtFFxzNBa7kcaEwUGcbVnH8xOJD67WG9n1NILuKtOsQG9FngOc+lciic1iQ8uQGhJ1kVAKKXUs60RoQ5km93IfaREvuoFj7PZsy9rGXE9G/NhBsDOJ63Acp1J82eFU7OIVO1OxWGwpSU5hb0GqfMydMHYSdiMVnncNY5Vy3VbwRUEydvEaRxmAOSSqJMlJISTxS9YWTYLcg3B253xsPkc5lXk3XLlwrPLuDPKDqDIutzYaj3eweMkPeCCahO3+fEIF8SfLtg/5oI3Mh0ylKM4YRBaYzuBgPuRnBYD3mmhA1X5Aka8NKl4nNz7BaKTzSgsLCzWbvyo4eK9r15WwLKRAmmCXXDoA1kaG2F4jWFbgkxUnlcrB/xj5iHxFPiBN4JekY4nZ6ccOiQ87hgwhe+TOdogT1nfpgEDTvYAucIwHxBfNyhpGrR+F8x00WD33VCNTOr/Wd+9C51Ben7S0ZJUq3qZJ2OkZz+cL87ZfWuePlwRcHZjeUMxFwTrJZAJfSvyWZc1VgORTY8rBcubetdiOk+CO+jPOcCRTF+oZ0okUIyuQeSNL/lPrulg8flhmJHmE2gBpE9xrJNkwpN4rQIIyujGoELCQz8ggG38iGzjKkXufJ2Klun1iu65bnJub2yut3xbEK3UvsDEInCmvA6YjMeE1bCn8F9JBe1eAnS2JksmkIlEDfi8R46kkEkMWdqOv+AvS9rcp2bvk8OAESvgox7h4aWNMLd32jSMLvuwDAwORSE7Oe3ZRKrFwvYGrPOBJ2nZ20Op/mqKNzgraOTPt6Bnx5citUINIczX/jUw3xGL2+ia8KAvsvp0ePoL5hXkXO5YvQYSFAiqcJX8E/gyX8QUvv8eh9XUq3h7mE9tLJoNKqnhHXmCO+dtJ4ybSkH1jc9XRaHTMz1tATBe2UEkeAdKu/zWIkUbZxD+veLxEQhhUFmbnvOezsJrk+zmqMo6vIL2OXzPvQ8v7dgtpoQnkF/LP8Ruu9zXdJHg4igAAAABJRU5ErkJgggA=)](https://www.fiware.org/developers/catalogue/)
[![License](https://img.shields.io/github/license/ging/fiware-cosmos-orion-spark-connector-examples.svg)](#)
[![Documentation badge](https://readthedocs.org/projects/fiware-cosmos-spark-examples/badge/?version=latest)](https://fiware-cosmos-spark-examples.readthedocs.io/en/latest/)
[![](https://img.shields.io/badge/tag-fiware--cosmos-orange.svg?logo=stackoverflow)](http://stackoverflow.com/questions/tagged/fiware-cosmos)

This repository contains a few examples for getting started with the [**fiware-cosmos-orion-spark-connector**](https://github.com/ging/fiware-cosmos-orion-spark-connector/):

## Setup

In order to run the examples, first you need to clone the repository:
```
git clone https://github.com/ging/fiware-cosmos-orion-spark-connector-examples
cd fiware-cosmos-orion-spark-connector-examples
```

Next, [download](https://github.com/ging/fiware-cosmos-orion-spark-connector/releases/latest) the connector JAR from the connector repository and from the `fiware-cosmos-orion-spark-connector` run:

```
mvn install:install-file -Dfile=$(PATH_DOWNLOAD)/orion.spark.connector-1.2.1.jar -DgroupId=org.fiware.cosmos -DartifactId=orion.spark.connector -Dversion=1.2.1 -Dpackaging=jar
```

where `PATH_DOWNLOAD` is the path where you downloaded the JAR.

## Example 1 : Receive simulated notifications

The first example makes use of the `OrionSource` in order to receive notifications from the Orion Context Broker. For simplicity, in this example the notifications are simulated with a curl command.
Specifically, the example receives a notification every second that a node changed its temperature, and calculates the minimum temperature in a given interval.

### Simulating a notification
In order to simulate the notifications coming from the Context Broker you can run the following script (available at `files/example1/curl_Notification.sh`):

```bash
while true
do
    temp=$(shuf -i 18-53 -n 1)
    number=$(shuf -i 1-3113 -n 1)

    curl -v -s -S X POST http://localhost:9001 \
    --header 'Content-Type: application/json; charset=utf-8' \
    --header 'Accept: application/json' \
    --header 'User-Agent: orion/0.10.0' \
    --header "Fiware-Service: demo" \
    --header "Fiware-ServicePath: /test" \
    -d  '{
         "data": [
             {
                 "id": "R1","type": "Node",
                 "co": {"type": "Float","value": 0,"metadata": {}},
                 "co2": {"type": "Float","value": 0,"metadata": {}},
                 "humidity": {"type": "Float","value": 40,"metadata": {}},
                 "pressure": {"type": "Float","value": '$number',"metadata": {}},
                 "temperature": {"type": "Float","value": '$temp',"metadata": {}},
                 "wind_speed": {"type": "Float","value": 1.06,"metadata": {}}
             }
         ],
         "subscriptionId": "57458eb60962ef754e7c0998"
     }'


    sleep 1
done
```

### Receiving data and performing operations
This is the code of the example which is explained step by step below:
```scala
package org.fiware.cosmos.orion.spark.connector.examples.example1


import org.apache.spark._
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.fiware.cosmos.orion.spark.connector.OrionReceiver

object Example1{

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster("local[4]").setAppName("Temperature")
    val ssc = new StreamingContext(conf, Seconds(10))
    // Create Orion Source. Receive notifications on port 9001
    val eventStream = ssc.receiverStream(new OrionReceiver(9001))
    // Process event stream
    val processedDataStream = eventStream
      .flatMap(event => event.entities)
      .map(entity => {
        val temp: Float = ent.attrs("temperature").value.asInstanceOf[Number].floatValue()
        (entity.id, temp)
      })
      .reduceByKeyAndWindow(_ min _ ,Seconds(10))
    processedDataStream.print
    ssc.start()
    ssc.awaitTermination()
  }
}
```

After importing the necessary dependencies, the first step is creating the source and adding it to the environment.

```scala
val eventStream = ssc.receiverStream(new OrionReceiver(9001))
```

The `OrionReceiver` accepts a port number as a parameter. The data received from this source is a `DataStream` of `NgsiEvent` objects.
You can check the details of this object in the [connector docs](https://github.com/ging/fiware-cosmos-orion-spark-connector/blob/master/README.md#orionsource)

In the example, the first step of the processing is flat-mapping the entities. This operation is performed in order to put together the entity objects of several NGSI Events.

```scala
val processedDataStream = eventStream
  .flatMap(event => event.entities)
```

Once you have all the entities, you can iterate over them (with `map`) and extract the desired attributes; in this case, it is the temperature. And In each iteration you create a tuple with the entity id and the temperature.  
```scala
// ...
.map(entity => {
    val temp: Float = ent.attrs("temperature").value.asInstanceOf[Number].floatValue()
    (entity.id, temp)
})
```
Now you can group the created objects by entity id and perform the operation in a time interval providing a custom processing window:
```scala
// ...
.reduceByKeyAndWindow(_ min _ ,Seconds(10))
```

After the processing, you can print the results on the console:
```scala
processedDataStream.print
```

Or you can persist them using the sink of your choice.


## Example 2: Complete Orion Scenario with docker-compose

The second example does the same processing as the previous one but it writes the processed data back in the Context Broker.

### Setting up the scenario
In order to test this feature, we need to have a Context Broker up and running. For this purpose, a `docker-compose` file is provided under `files/example2`, which deploys all the necessary containers for this scenario.
You just need to run the following command (probably with `sudo`):
```bash
docker-compose up
```

Once you have the Context Broker and the rest of the machines up and running, you need to create some entities and subscribe to them in order to get a notification whenever their value change.
First, let's create a room entity (you can find the script under `files/example2/curl_CreateNewEntity.sh`):
```bash
curl localhost:1026/v2/entities -s -S -H 'Content-Type: application/json' -d @- <<EOF
{
  "id": "Room1",
  "type": "Room",
  "temperature": {
    "value": 23,
    "type": "Float"
  },
  "pressure": {
    "value": 720,
    "type": "Integer"
  },
  "temperature_min": {
    "value": 0,
    "type": "Float"
  }
}
EOF
```

Now you can subscribe to any changes in the attributes you are interested in. Again, you can find this script under (`files/example2/curl_SubscribeToEntityNotifications.sh`). Do not forget to change `$MY_IP` to your machine's IP Address (must be accesible from the docker container):
```bash
curl -v localhost:1026/v2/subscriptions -s -S -H 'Content-Type: application/json' -d @- <<EOF
{
  "description": "A subscription to get info about Room1",
  "subject": {
	"entities": [
  	{
    	"id": "Room1",
    	"type": "Room"
  	}
	],
	"condition": {
  	"attrs": [
    	"pressure",
	"temperature"
  	]
	}
  },
  "notification": {
	"http": {
  	"url": "http://$MY_IP:9001/notify"
	},
	"attrs": [
  	"temperature",
	"pressure"
	]
  },
  "expires": "2040-01-01T14:00:00.00Z",
  "throttling": 5
}
EOF
```

You might want to check that you have created it correctly by running:
```bash
curl localhost:1026/v2/subscriptions
```

### Triggering notifications
Now you may start performing changes in the entity's attributes. For that, you can use the following script (`files/example2/curl_ChangeAttributes.sh`):
```bash
while true
do
    timestamp=$(shuf -i 1-100000000 -n 1)
    temp=$(shuf -i 18-53 -n 1)
    number=$(shuf -i 1-3113 -n 1)
    # echo

    curl localhost:1026/v2/entities/Room1/attrs -s -S -H 'Content-Type: application/json' -X PATCH -d '{
      "temperature": {
        "value": '$temp',
        "type": "Float"
      },
      "pressure": {
        "value": '$number',
        "type": "Float"
      }
    }'
    sleep 1
done
```

### Receiving data, performing operations and writing back to the Context Broker
Let's take a look at the Example2 code now:

```scala
package org.fiware.cosmos.orion.spark.connector.examples.example2

import org.apache.spark._
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.fiware.cosmos.orion.spark.connector._

object Example2 {
  final val URL_CB = "http://sparkexample_orion_1:1026/v2/entities/"
  final val CONTENT_TYPE = ContentType.JSON
  final val METHOD = HTTPMethod.POST

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster("local[4]").setAppName("Temperature")
    val ssc = new StreamingContext(conf, Seconds(10))
    // Create Orion Source. Receive notifications on port 9001
    val eventStream = ssc.receiverStream(new OrionReceiver(9001))

    // Process event stream
    val processedDataStream = eventStream
      .flatMap(event => event.entities)
      .map(ent => {
        val temp: Float = ent.attrs("temperature").value.asInstanceOf[Number].floatValue()
        (ent.id, temp)
      })
      .reduceByKeyAndWindow(_ min _ ,Seconds(10))
      .map(a=> new Temp_Node(a._1,a._2))
      .map(tempNode => {
        val url = URL_CB + tempNode.id + "/attrs"
        OrionSinkObject(tempNode.toString, url, CONTENT_TYPE, METHOD)
      })
    // Add Orion Sink
    OrionSink.addSink( processedDataStream )
    // print the results with a single thread, rather than in parallel
    processedDataStream.print()
    ssc.start()
    ssc.awaitTermination()
  }
  case class Temp_Node(id: String, temperature: Float) extends  Serializable {
    override def toString :String = { "{\"temperature_min\": { \"value\":" + temperature + ", \"type\": \"Float\"}}" }
  }
}
```

As you can see, it is very similar to the previous example. The main difference is that it writes the processed data back in the Context Broker through the **`OrionSink`**.
After calculating the minimum temperature, the output data needs to be adapted to the format accepted by the **`OrionSink`**. It accepts a stream of **`OrionSinkObject`**, which accepts 4 mandatory arguments:

* **Message**: It is the content of the update that is going to be sent to the Context Broker. It can be a single value converted to string or, more commonly, a stringified JSON containing the new data. The connector does not build the JSON from a Scala object for us; we need to build it ourselves. We may want to override the `toString` message of the case class we've created in order to facilitate this process, as seen on the example.

* **URL**: It is the URL to which the message will be posted. Normally it has a common base but it somewhat varies depending on the entity we're receiving data from.

* **Content Type**: Whether the message is in JSON format (`ContentType.JSON`) or in plain text (`ContentType.Plain`).

* **HTTP Method**: The HTTP method used for sending the update. It can be: `HTTPMethod.POST`, `HTTPMethod.PUT` or `HTTPMethod.PATCH`.

In the example, an **`OrionSinkObject`** is built from the `Temp_Node` object converted to JSON. Thus, the specified data type is JSON. The URL is formed with the hostname of the docker container where the Context Broker is, and the id of the specific entity we are receiving data from. It uses the HTTP Post method in order to send the message to the Context Broker.
```scala
// ...
.map(tempNode => {
    val url = URL_CB + tempNode.id + "/attrs"
    OrionSinkObject(tempNode.toString, url, CONTENT_TYPE, METHOD)
})
```
Finally, we send the processed DataStream through the OrionSink
```scala
OrionSink.addSink( processedDataStream )
```
If you run the example, you will see that the minimum temperature calculated is displayed in the console.
You can test that it has been changed in the Context Broker as well by running the following command several times and checking that the `temperature_min` attribute is constantly changing:
```
curl localhost:1026/v2/entities/Room1
```

### Example 3: Packaging the code and submitting it to the Spark Job Manager
In the previous examples, we've seen how to get the connector up and running from an IDE like IntelliJ. In a real case scenario, we might want to package our code and submit it to a Spark cluster in order to run our operations in parallel.

Follow the [**Setting up the scenario**](#setting-up-the-scenario) section if you haven't already in order to deploy the containers needed.
After that, we need to make some changes to our code.

### Subscribing to notifications
First, we need to change the notification URL of our subscription to point to our Spark node like so (`files/example3/curl_SubscribeToEntityNotifications.sh`):

```bash
curl -v localhost:1026/v2/subscriptions -s -S -H 'Content-Type: application/json' -d @- <<EOF
{
  "description": "A subscription to get info about Room1",
  "subject": {
	"entities": [
  	{
    	"id": "Room1",
    	"type": "Room"
  	}
	],
	"condition": {
  	"attrs": [
    	"pressure",
	    "temperature"
  	]
	}
  },
  "notification": {
	"http": {
  	"url":"http://spark-master:9001"
	},
	"attrs": [
  	"temperature",
	"pressure"
	]
  },
  "expires": "2040-01-01T14:00:00.00Z",
  "throttling": 5
}
EOF
```

### Modifying the Context Broker URL

Since we are going to run the code from inside a Spark Task Manager container, we no longer can refer to the Context Broker as "http://localhost:1026". Instead, the code in Example 3 only differs from the previous example in the URL specified for the Context Broker: "http://orion:1026".

### Packaging the code

Let's build a JAR package of the example. In it, we need to include all the dependencies we have used, such as the connector, but exclude some of the dependencies provided by the environment (Spark, Scala...).
This can be done through the `maven package` command without the `add-dependencies-for-IDEA` profile checked.
This will build a JAR file under `target/orion.spark.connector.examples-1.2.1.jar`.

### Submitting the job 


Let's submit the Example 3 code to the Spark cluster we have deployed. In order to do this you can use the spark-submit command provided by Spark.

You can check that the vale for `temperature_min` is changing in the Context Broker by running:
```bash
curl localhost:1026/v2/entities/Room1
```


## Example 4: Other operations

The previous examples focus on how to get the connector up and running but do not give much importance to the actual operations performed on the data received. In fact, the only operation done is calculating the minimum temperature on a time window.
Nevertheless, Spark allows us to perform custom operations such as calculating the average.

For this, we use the accumulator and the value of the 'reduceByKeyAndByWindow' function inside the given timespan to have a tuple with the sum of the values and the count of them.
```scala
.reduceByKeyAndWindow((acc:(Float,Long),value:(Float,Long))=>{(acc._1 + value._1, acc._2 + value._2)}, Seconds(10))
```
So then we can use the function 'map' to get tuples classify by id and its average temperature.
```scala
.map((agg :  (String,(Float,Long))) =>  (agg._1, agg._2._1 / agg._2._2))
```
```scala
object Example4{

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[4]").setAppName("AvgTemperature")
    val ssc = new StreamingContext(conf, Seconds(10))
    ssc.checkpoint("./output")
    // Create Orion Source. Receive notifications on port 9001
    val eventStream = ssc.receiverStream(new OrionReceiver(9001))

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
```

## Example 5: Other operations (2)

In this example, similar to the previous one, we calculate the average of all temperatures received without separating by id.
```scala
object Example5{

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[4]").setAppName("AvgTemperature")
    val ssc = new StreamingContext(conf, Seconds(10))
    ssc.checkpoint("./output")
    // Create Orion Source. Receive notifications on port 9001
    val eventStream = ssc.receiverStream(new OrionReceiver(9001))

    // Process event stream
    val processedDataStream = eventStream
      .flatMap(event => event.entities)
      .map(ent =>(ent.attrs("temperature").value.asInstanceOf[Number].floatValue(),1L))
      .reduceByWindow((acc:(Float,Long),value:(Float,Long))=>{(acc._1 + value._1, acc._2 + value._2)},Seconds(10), Seconds(10))
      .map((agg :  (Float,Long)) =>( agg._1 / agg._2))

    processedDataStream.print()
    ssc.start()
    ssc.awaitTermination()
  }
}
```

## Example 6: Structured values for attributes

So far, the examples provided have dealt with simple attributes in the shape of integers or strings. Some use cases require more complex attributes, such as objects (https://fiware-orion.readthedocs.io/en/master/user/structured_attribute_valued/index.html).
This connector parses this sort of values as scala Maps[String,Any], which eases the process of iterating through their properties.

Example 6 provides an example in which the data received is a list of bus schedules and their prices. These prices are constantly changing and Spark is used in order to calculate the minimum prices within a time window.

The simulated Orion notification is as follows (available at `files/example5/curl_Notification.sh`):
```bash
while true
do
    bus1=$(shuf -i 10-53 -n 1)
    bus2=$(shuf -i 10-44 -n 1)

    curl -v -s -S X POST http://localhost:9001 \
    --header 'Content-Type: application/json; charset=utf-8' \
    --header 'Accept: application/json' \
    --header 'User-Agent: orion/0.10.0' \
    --header "Fiware-Service: demo" \
    --header "Fiware-ServicePath: /test" \
    -d  '{
         "data": [
             {
                 "id": "R1",
                 "type": "Node",
                 "information": {
                     "type": "object",
                     "value": {
                        "buses":[
                            {
                                "name": "BusCompany1",
                                "schedule": {
                                    "morning": [7,9,11],
                                    "afternoon": [13,15,17,19],
                                    "night" : [23,1,5]
                                },
                                "price": '$bus1'
                            },
                            {
                                "name": "BusCompany2",
                                "schedule": {
                                    "morning": [8,10,12],
                                    "afternoon": [16,20],
                                    "night" : [23]
                                },
                                "price": '$bus2'
                            }
                        ]
                     },
                     "metadata": {}
                    }
             }
         ],
         "subscriptionId": "57458eb60962ef754e7c0998"
     }'


    sleep 1
done
```

The code for Example 6 is similar to the previous examples. The only difference is that it is necessary to manually parse every item of the object attribute in order to make use of it.

```scala
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

```

