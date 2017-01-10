
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class DemoCsvSimulation extends Simulation {
 //用于存储登录数据的文件
 val user = csv("../data/searchDemo.csv").random
 val headers_1 = Map("Content-Type" -> "application/json")
 object Search {
    val search = exec(http("Home")
    .get("/"))
    .pause(1)
    .feed(user) 
    .exec(http("Login")
    .post("/app/user/login/appId/A0I000I000I00100")
    .headers(headers_1)
    .formParam("loginName", "${loginName}")
    .formParam("password", "${password}")
    .formParam("clientType", "${clientType}")
    .formParam("clientId", "${clientId}"))

  }

   //设置请求的根路径
   val httpConf1 = http
    .baseURL("http://app.lierdapark.com:8011/ella") 
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") 
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  

   val u1 = scenario("U1").during(100){
	exec(Search.search)
   }

   setUp(u1.inject(atOnceUsers(10)).protocols(httpConf1))
}
