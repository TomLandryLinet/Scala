import java.util.Properties
import scala.io.Source
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
object ImportCSV{

  def main (args:Array[String]): Unit = {
    if (args.length < 1) {
      println("Usage: ImportCSV <cheminFichierCSV>")
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
    val topic = "ImportCSV"
    try {

      val id_hypotethique = 1
      val fichier = Source.fromFile(args(0))
      val valFichier = fichier.getLines()
      //Demo
      /*val valFichier = List("Ah","Euh","Mince", "Allez", "Ola", "Olé",
                            "Ah","Euh","Mince", "Allez", "Ola", "Olé",
                            "Ah","Euh","Mince", "Allez", "Ola", "Olé",
                            "Ah","Euh","Mince", "Allez", "Ola", "Olé",
                            "Ah","Euh","Mince", "Allez", "Ola", "Olé",
                            "Ah","Euh","Mince", "Allez", "Ola", "Olé",
                            "Ah","Euh","Mince", "Allez", "Ola", "Olé")*/
      valFichier.foreach(x => producer.send(new ProducerRecord[String, String](topic, id_hypotethique.toString, x)))

      fichier.close()
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {

      producer.close()
    }
  }
}