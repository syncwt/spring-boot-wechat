
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class EditSimulation extends Simulation {

   
  object Edit {
    val headers_1 = Map("Content-Type" -> "application/json")

    val edit = exec(http("updateEvaluate")
        .post("/app/dealer/updateEvaluate/appId/A0I000I000I00100")
        .headers(headers_1)
	.body(StringBody("{\"id\":1483,\"appointmentTime\":2016-11-13 下午}")).asJSON)
   }



   //设置请求的根路径
   val httpConf2 = http
    .baseURL("http://app.lierdapark.com:8031/dealer") 
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") 
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

   val u2 = scenario("U2").during(100){
	exec(Edit.edit)
   }

   setUp(
	u2.inject(atOnceUsers(10)
   ).protocols(httpConf2))
 
}
