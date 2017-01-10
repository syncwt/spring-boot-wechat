
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class DemoSimulation extends Simulation {
   object Search {
	val search = exec(http("search_role")
	 .get("/userRole/postRoleList"))
	 .pause(1)
	 .exec(http("search_user")
	 .get("/user/postUserBase?userId=412"))
	 .pause(1)
   }
   
  object Edit {
    val headers_1 = Map("Content-Type" -> "application/x-www-form-urlencoded")

    val edit = exec(http("updateStore")
        .post("/app/store/storeUpdate")
        .headers(headers_1)
	.formParam("id", "61")
        .formParam("storeName", "test gatling"))

   }

   //设置请求的根路径
   val httpConf1 = http
    .baseURL("http://app.lierdapark.com:8011/ella") 
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") 
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

   //设置请求的根路径
   val httpConf2 = http
    .baseURL("http://app.lierdapark.com:8031/dealer") 
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") 
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

   val u1 = scenario("U1").during(100){
	exec(Search.search)
   }
   
   val u2 = scenario("U2").during(100){
	exec(Edit.edit)
   }

  // setUp(u1.inject(atOnceUsers(10)).protocols(httpConf1))
   setUp(u2.inject(atOnceUsers(10)).protocols(httpConf2))
}
