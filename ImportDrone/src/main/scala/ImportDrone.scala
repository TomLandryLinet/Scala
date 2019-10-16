import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
object ImportDrone {

  def main (args:Array[String]): Unit = {
    if (args.length < 2 ) {
      println("Usage: ImportDrone <idDrone> <donneesCSV>")
      System.exit(1)
    }

    val props: Properties = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer",
      "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer",
      "org.apache.kafka.common.serialization.StringSerializer")
    props.put("acks", "all")
    val producer = new KafkaProducer[String, String](props)
    val topic = "ImportDrone"
    try {

      val record = new ProducerRecord[String, String](topic, args(0), args(1))
      //Demo
      /*for(i <- 0 to 25){
        val record = new ProducerRecord[String, String](topic,i.toString,"J'aimerai savoir lancer un programme avec des arguments")
        val metadata = producer.send(record)
      }*/

      val metadata = producer.send(record)

    }
    catch {
      case e: Exception => e.printStackTrace()
    }
    finally {
      producer.close()
    }
  }
}