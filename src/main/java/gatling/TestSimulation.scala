
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class TestSimulation extends Simulation { 
	object Search {
		 val search = exec(http("Home")
	      .get("/"))
	      .pause(1)
	      .exec(http("Search")
	        .get("/computers?f=macbook"))
	      .pause(1)
	      .exec(http("Select")
	        .get("/computers/6"))
	      .pause(1)
	}


   //设置请求的根路径
   val httpConf = http
    .baseURL("http://www.baidu.com") 
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") 
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
   val scn = scenario("TestSimulation").during(100){
	exec(http("baidu_home").get("/"))
   }
   setUp(scn.inject(atOnceUsers(10)).protocols(httpConf))
 
}
