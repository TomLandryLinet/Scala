import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe


object SparkStreaming {
  def main(args: Array[String]) {

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "localhost:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "local",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    val sparkConf = new SparkConf().setAppName("RecupContravention").setMaster("local[*]")
    val scc = new StreamingContext(sparkConf, Seconds(1))

    val topics = Array("ImportDrone","ImportCSV")
    val stream = KafkaUtils.createDirectStream[String, String](
      scc,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    ).map{record => (record.key(),record.value)}.print()

    //foreachRDD => saveas dans Cassandra
    scc.start()
    scc.awaitTermination()

  }
}