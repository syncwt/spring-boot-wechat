# Introduce
the project is based on spring boot,contain with wechat utils。

# Structure
    .
    ├── log
    ├── src
    │   ├── main
    │   │   ├── java
    │   │   │   ├── com
    │   │   │   │   └── senthink
    │   │   │   │       └── www
    │   │   │   │           ├── async       # async task
    │   │   │   │           ├── common      # common utils
    │   │   │   │           ├── config      # packaging config from bootstrap.yml
    │   │   │   │           ├── convert     # [mapstruct](http://mapstruct.org/)
    │   │   │   │           ├── dao         # IMapper
    │   │   │   │           ├── domain
    │   │   │   │           │   ├── dto     # Data Transfer Object(return to front)
    │   │   │   │           │   ├── po      # persistant object(entity corresponding to sql)
    │   │   │   │           │   │   └── wechat  # wechat entity like Article
    │   │   │   │           │   └── vo      # view object (receive param from front)
    │   │   │   │           ├── enums       # enums class
    │   │   │   │           ├── exception   # global exception catcher
    │   │   │   │           ├── filter      # filter to solve cross-domain access
    │   │   │   │           ├── response    # return class packaging
    │   │   │   │           ├── service     # Service
    │   │   │   │           ├── util        # the same as common package
    │   │   │   │           └── web         # Controller
    │   │   │   │               └── notify  # receive message from wechat server
    │   │   │   └── gatling                 # test utils
    │   │   └── resources
    │   │       ├── gatling
    │   │       │   └── data
    │   │       └── mapper                  # mybatis plus mapper
    │   └── test
    │       ├── java
    │       │   └── com
    │       │       └── senthink
    │       │           └── www
    │       └── scala
    │           └── gatling
    └── target

# Reminder
 If some error or warning happens when projects starting,it might be you missed dependency.Such as redis,mongo,etc.Please delete config about that or install the necessary software.

# How to use
 Start the project with RUN class `DemoApplication`.To test your interface on http://127.0.0.1:8010/wechatdemo/...

# Contact
 Welcome to visit my blog at [syncwt](http://www.jianshu.com/u/7a8e041a132c)
