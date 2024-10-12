-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: uni_ai
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ai_collection`
--

DROP TABLE IF EXISTS `ai_collection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ai_collection` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(32) NOT NULL,
  `editor_id` varchar(32) NOT NULL,
  `name` varchar(32) NOT NULL COMMENT '知识库名称',
  `collection_name` varchar(32) NOT NULL,
  `embedding_model_id` varchar(32) NOT NULL COMMENT '嵌入模型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='知识库';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_collection`
--

LOCK TABLES `ai_collection` WRITE;
/*!40000 ALTER TABLE `ai_collection` DISABLE KEYS */;
INSERT INTO `ai_collection` VALUES ('2bfc728b1d4e4582a7bb43285c1501ea','2024-05-31 09:47:45.974985','2024-05-31 09:47:45.974985','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','百度千帆知识库','qianfan','40c5497adee9451fbf705c85d9700aaa'),('6984123a99a8417f8cd7be8f39b339ae','2024-05-30 23:47:56.457289','2024-05-31 09:48:03.485217','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','阿里领积知识库','default','4d33d126e21041298c4f22c052de5a5f'),('6c689aaa8714402a8966271d682c24a3','2024-05-31 08:56:10.690445','2024-05-31 09:47:52.050193','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','OpenAi知识库','openai_test','323115205073473693aa1198340daa7c'),('9a953782ef54461a9431d6c9a9dd5d96','2024-05-31 23:38:32.675684','2024-05-31 23:38:32.675684','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','智谱清言知识库','zhipu','82706c5eb9e94668b331c0981f6aba5c'),('b7e48d092a9b472cb93cc368f162afae','2024-06-06 13:25:12.337732','2024-06-06 13:25:12.337696','fe3d0d7d6eb34eb7a6b7985426cf8af7','fe3d0d7d6eb34eb7a6b7985426cf8af7','我的知识库','test_database','4d33d126e21041298c4f22c052de5a5f');
/*!40000 ALTER TABLE `ai_collection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_document`
--

DROP TABLE IF EXISTS `ai_document`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ai_document` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(32) NOT NULL,
  `editor_id` varchar(32) NOT NULL,
  `name` varchar(32) NOT NULL,
  `ai_collection_id` varchar(36) NOT NULL COMMENT '知识库',
  `content` text NOT NULL COMMENT '内容',
  `doc_ids` json NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='知识库文档';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_document`
--

LOCK TABLES `ai_document` WRITE;
/*!40000 ALTER TABLE `ai_document` DISABLE KEYS */;
INSERT INTO `ai_document` VALUES ('db9f05d4e4c54197890ee3bf32afa30b','2024-06-06 13:25:38.861225','2024-06-06 13:25:38.861200','fe3d0d7d6eb34eb7a6b7985426cf8af7','fe3d0d7d6eb34eb7a6b7985426cf8af7','我的简历','b7e48d092a9b472cb93cc368f162afae','\n 13656987994 ·  1507906763@qq.com ·  jarcheng ·\n\n 个人信息  \n林家成\n男，2000 年出生\n求职意向：Java 研发工程师\n\n 教育经历  \n学士，华侨大学，信息管理与信息系统专业，2019.9~2023.7\n\n 实习经历  \n叁拾信息科技有限公司，java开发 工程师，2021.5~2021.11\n\n负责 多租户⽀付⼩程序项⽬后端全部开发和设计, 编写技术文档。\n\n 项目经历  \n华大快帮\n\nseata，openfeign，sentinel，spring gateway，rabbitmq，redis，sa-token，spring boot，jpa，\nk8s。\n\n项目介绍：\n\n一个 springcloud+springboot 做后台，uni-app 做前端的跑腿小程序。\n\n账户中心：\n\n1. 基础用户（一个手机号对应一个），关联多个第三方用户。\n2. 基础账户，个人账户，跑腿账户。\n\n认证中心：\n\n1. 验证码发送\n2. 多种登录方式\n3. redis分布式session\n\n交易中心：\n\n1. 基础订单\n2. 多种支付方式\n3. 状态机\n\n任务中心：\n\n1. 发布任务\n2. 任务订单\n\n工作流中心：\n\n1. 跑腿资格申请\n\n基础模块：\n\n1. 代码生成器\n2. oss starter\n3. 权限拦截 starter\n4. 基础公用类\n\nhttps://github.com/jarcheng\naf://n685\naf://n693\naf://n698\naf://n703\n\n难点：\n\n1. 多种订单计算价格逻辑不同，但都需要要支付功能。采用交易中心订单负责支付，支付完发送\n支付成功消息到rabbiqmq。其他业务监听自己的订单支付则执行后续逻辑。\n\n2. 工作流与资质审核集成，审核通过后发送http请求开通资格。\n3. 代码生成器，与Lombok原理一致。生成六边形架构模板。\n4. Mapstrcut，dto与entity类型转换。\n5. 幂等性，aop锁。\n6. 单点登录，统一token发放，引入权限模块查询redis认证。\n7. 状态机，订单状态流转。\n8. 责任链价格计算。\n9. 分布式事务。\n\n10. k8s部署。\n\n在线寄信小程序\n\nSpringBoot，Spring Data JPA，MapStruct，Mysql，Vue3，TypeScript，ElementUI，Uni-App\n\n项目介绍：\n\n在线寄信，在线寄明信片，在线寄商品，购物⻋，推⼴拉新，订单抽成。\n\n难点：\n\n1. 字数价格，照片价格，运费价格动态配置。\n2. 推广拉新订单抽成通过观察者模式解耦（监听消息）。\n3. 多模块打包，docker-compose快速部署。\n\n卖课件项目\n\nSpringBoot，Mybatis-Plus，Mysql，Vue3，TypeScript，ElementUI\n\n项目介绍：\n\n购买课件后可以在线预览课件(ppt,doc,pdf 等), 购物⻋, vip, 兑换码。\n\n业务难点：\n\n1. 上传时选择一整个文件夹，在小程序中依然呈现文件夹的目录结构。采用递归的形式生成树结\n构。\n\n2. VIP购买打折，采用责任链对订单价格进行计算，可以扩展以后的价格计算逻辑。\n3. 商品SKU配置表结构设计。\n4. 手写CSS实现设计稿\n\n \n\n 技能清单  \n后端开发\n\n1. JDK基础（集合，IO，并发，函数式编程）\n2. Jakarta EE\n3. MySQL，SQLServer，JDBC，JPA规范，Hibernate, Spring Data JPA，Mybatis，Mybatis-\n\nPlus\n4. Jackson，Mapstruct，Java Bean Validation\n5. Spring Core，Spring Data，Spring MVC，Spring Boot。\n6. 设计模式，六边形架构（DDD），日志规范，单元测试，Open Api\n7. Spring Cloud，Spring Cloud Aliababa，K8S。\n8. Git，Maven，IDEA\n\n前端开发\n\n1. HTML+CSS+JS\n2. Webpack\n3. vue-router，vuex，vue2，vue-cli\n4. vue-router，pinia，vue3，typescript，vite\n5. uni-app+vue3+ts \n\n运维\n\nNginx，Docker，Linux\n中间件\n\nRedis，RabbitMq，ElasticSearch\n计算机基础\n\n数据库，计算机⽹络，操作系统，数据结构与算法\n\n \n\naf://n810\n\n	 个人信息\n	 教育经历\n	 实习经历\n	 项目经历\n	 技能清单\n\n','[\"23020d45-a9a9-4cb8-9bc5-15276bfd0ece\", \"a2fd3220-66fb-4b34-abe5-0e5a0c788dbd\"]');
/*!40000 ALTER TABLE `ai_document` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_factory`
--

DROP TABLE IF EXISTS `ai_factory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ai_factory` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(32) NOT NULL,
  `editor_id` varchar(32) NOT NULL,
  `name` varchar(32) NOT NULL COMMENT '厂家名称',
  `description` varchar(1000) DEFAULT NULL COMMENT '厂家描述',
  `options` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='AI厂家';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_factory`
--

LOCK TABLES `ai_factory` WRITE;
/*!40000 ALTER TABLE `ai_factory` DISABLE KEYS */;
INSERT INTO `ai_factory` VALUES ('1736ce830fc244af87a001f6a7a5d19c','2024-06-01 15:39:32.393714','2024-06-05 22:03:46.832375','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','QIAN_FAN',NULL,'{}'),('2db4ef18b31944719f80716667f73f3c','2024-06-01 15:38:32.128076','2024-06-05 22:03:46.836037','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','ZHI_PU',NULL,'{}'),('2e4a60536f734f41bd503d3029491ba5','2024-06-06 13:23:46.383569','2024-06-06 13:23:46.383518','fe3d0d7d6eb34eb7a6b7985426cf8af7','fe3d0d7d6eb34eb7a6b7985426cf8af7','KIMI','','{}'),('33e922f1cf8948d9a3b43a1f86d7fa1e','2024-06-06 13:23:46.337469','2024-06-06 13:23:46.337404','fe3d0d7d6eb34eb7a6b7985426cf8af7','fe3d0d7d6eb34eb7a6b7985426cf8af7','QIAN_FAN',NULL,'{}'),('669fcbda19c944f7b3db969e10286c52','2024-06-06 13:23:46.399686','2024-06-06 13:23:46.399661','fe3d0d7d6eb34eb7a6b7985426cf8af7','fe3d0d7d6eb34eb7a6b7985426cf8af7','OPEN_AI',NULL,'{}'),('7cb1c8740a9b484884e3ce438d11f7c0','2024-06-06 13:23:46.375296','2024-06-06 13:23:46.375251','fe3d0d7d6eb34eb7a6b7985426cf8af7','fe3d0d7d6eb34eb7a6b7985426cf8af7','ZHI_PU',NULL,'{}'),('965acfeb985343c883fc4aa74eb13a9d','2024-06-06 13:23:46.392704','2024-06-06 13:24:31.406150','fe3d0d7d6eb34eb7a6b7985426cf8af7','fe3d0d7d6eb34eb7a6b7985426cf8af7','DASH_SCOPE',NULL,'{\"apiKey\": \"sk-9846f3a9c6604c488650f0d7936565f0\"}'),('9c1c47209a5e49c5a34dd9d983e8dcc6','2024-06-01 15:39:01.268677','2024-07-18 17:42:33.543081','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','KIMI','','{\"apiKey\": \"sk-qIKdlKGBP3TdG2hbeBvKdbyOYHEkE67ILnBngdaQwg4vmuw8\"}'),('9fd26f081bb54395a5b2038d28670ef7','2024-06-06 13:23:46.408551','2024-06-06 13:23:46.408434','fe3d0d7d6eb34eb7a6b7985426cf8af7','fe3d0d7d6eb34eb7a6b7985426cf8af7','SPARK','','{}'),('a227db836ff3443ba4bc1a18864298e7','2024-06-01 15:39:48.065468','2024-06-05 22:03:46.841035','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','DASH_SCOPE',NULL,'{}'),('c90be53fc75748cb9b07bfd8fdfd900a','2024-06-01 15:41:43.215477','2024-06-05 22:03:46.842033','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','OPEN_AI',NULL,'{}'),('ea9eb6f9adb54e128a6e7d097e7ea826','2024-06-01 15:41:19.753474','2024-06-05 22:03:46.845031','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','SPARK','','{}');
/*!40000 ALTER TABLE `ai_factory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_message`
--

DROP TABLE IF EXISTS `ai_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ai_message` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(32) NOT NULL,
  `editor_id` varchar(32) NOT NULL,
  `type` varchar(32) NOT NULL COMMENT '消息类型(用户/助手/系统)',
  `content` text NOT NULL COMMENT '消息内容',
  `ai_session_id` varchar(32) NOT NULL COMMENT '会话id',
  PRIMARY KEY (`id`),
  KEY `ai_message_ai_session_id_fk` (`ai_session_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_message`
--

LOCK TABLES `ai_message` WRITE;
/*!40000 ALTER TABLE `ai_message` DISABLE KEYS */;
INSERT INTO `ai_message` VALUES ('03f1eb45dc67405587303ddd2cf3cecc','2024-06-09 21:53:36.786152','2024-06-09 21:53:36.786152','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','ASSISTANT','[{\"text\":\"`DictConstants.MENU_TYPE` 的值取决于你的应用程序中如何定义这个常量。在很多基于Spring或者其他Java框架的应用程序中，开发者会创建一个包含常量的类（比如 `DictConstants`）来管理字典类型、菜单类型等配置信息。\\n\\n通常，这类常量类的定义可能如下所示：\\n\\n```java\\npublic final class DictConstants {\\n\\n    public static final String MENU_TYPE_DIR = \\\"D\\\"; // 目录类型\\n    public static final String MENU_TYPE_MENU = \\\"M\\\"; // 菜单类型\\n    public static final String MENU_TYPE_BUTTON = \\\"B\\\"; // 按钮类型\\n\\n    // 防止实例化\\n    private DictConstants() {}\\n}\\n```\\n\\n在这个示例中，`MENU_TYPE` 可能是上面三个常量之一，具体是哪个则取决于你的应用场景——是否指的是目录(Dir)、菜单(Menu)还是按钮(Button)类型。如果你需要查询的是具体的值，请确认你的应用中 `DictConstants` 类的确切定义，或者提供更多的上下文信息，这样我可以给出更准确的答案。\"}]','0bcdca19d9e54ca6bee6f8a45fdaabaf'),('060d07abd118436b93d8312c37c4a0a1','2024-06-09 21:48:52.615596','2024-06-09 21:48:52.615596','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','ASSISTANT','[{\"text\":\"为了提供关于 `@SpringSpELService` 这个bean的信息，我需要更多上下文或者具体的代码片段，因为直接从文本中无法直接访问或评估实际应用中的Bean定义。`@SpringSpELService` 看起来像是一个自定义的Spring服务注解，用于标记某个类作为Spring的Bean，并可能涉及Spring Expression Language (SpEL)的特定处理逻辑。\\n\\n如果你是在询问如何在Spring框架中定义、使用这样的服务或者想要了解通过SpEL能够实现的功能，我可以提供一般性的指导：\\n\\n- **定义带有SpEL的服务Bean**：通常，你可能会在一个类上使用`@Service`注解来定义它作为一个业务服务层的Bean。如果这个服务类内部需要使用Spring Expression Language，你可以在配置类中、XML配置或者使用`@Value`等注解来注入并执行SpEL表达式。\\n\\n- **使用SpEL**：SpEL可以用于属性注入、条件判断、集合操作等多种场景。例如，你可以用它来动态计算属性值、验证条件或者执行复杂的逻辑表达式。\\n\\n- **示例**:\\n  ```java\\n  @Service\\n  public class MySpELService {\\n  \\n      @Value(\\\"#{T(java.lang.Math).random() * 100}\\\")\\n      private double randomValue;\\n      \\n      public void performAction() {\\n          // 使用SpEL计算得到的randomValue进行某些操作\\n          System.out.println(\\\"A random value: \\\" + randomValue);\\n      }\\n  }\\n  ```\\n  在这个例子中，`@Value`注解使用了SpEL表达式 `#{T(java.lang.Math).random() * 100}` 来生成一个0到100之间的随机数，并将其注入到`randomValue`字段中。\\n\\n如果你有关于如何在具体代码中应用SpEL，或者需要解析特定SpEL表达式的执行结果，请提供详细信息或表达式，我可以进一步协助。\"}]','0bcdca19d9e54ca6bee6f8a45fdaabaf'),('39a510f316274981bf2e489362c335be','2024-06-06 13:26:04.864392','2024-06-06 13:26:04.864382','fe3d0d7d6eb34eb7a6b7985426cf8af7','fe3d0d7d6eb34eb7a6b7985426cf8af7','ASSISTANT','[{\"text\":\"您的名字是林家成。\"}]','e06418c4016f49f3bb02f338f6216ad6'),('515c261d06f440e08a91115261f68ad1','2024-06-09 22:02:37.560084','2024-06-09 22:02:37.560084','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','ASSISTANT','[{\"text\":\"根据您的要求，我使用了SpringSpEL表达式语言来执行计算，结果为2。\"}]','3c87ecb7d1b74940a9c5b196c6b78c03'),('5462ca60be724817ac862caa01db0ebc','2024-06-09 21:48:52.347890','2024-06-09 21:48:52.347890','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','USER','[{\"text\":\"@SpringSpELService这个bean的信息\"}]','0bcdca19d9e54ca6bee6f8a45fdaabaf'),('61a6e8f76541469ab9f8ccdac6a00d61','2024-06-09 21:45:23.749336','2024-06-09 21:45:23.749336','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','USER','[{\"text\":\"20+30+90/2+10等于多少？\"}]','0bcdca19d9e54ca6bee6f8a45fdaabaf'),('6b5f47980cfa4e36a83ccf5478ed35da','2024-06-09 22:02:06.135053','2024-06-09 22:02:06.135053','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','ASSISTANT','[{\"text\":\"数学表达式的计算结果是2。\"}]','3c87ecb7d1b74940a9c5b196c6b78c03'),('759be31944fd48ff943adbd14319a413','2024-06-09 21:54:38.426712','2024-06-09 21:54:38.426712','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','USER','[{\"text\":\"执行一下这个表达式DictConstants.MENU_TYPE\"}]','0bcdca19d9e54ca6bee6f8a45fdaabaf'),('82817480b1af4755ae646c61ebf7fd32','2024-06-09 21:54:38.695875','2024-06-09 21:54:38.695875','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','ASSISTANT','[{\"text\":\"我作为一个基于文本的助手，无法直接执行代码或访问特定应用程序的常量。但我可以帮助指导你如何在Java环境中测试这样的表达式。\\n\\n如果你想要在运行时获取 `DictConstants.MENU_TYPE` 的值，并且你确实在一个支持Spring Expression Language (SpEL)的环境中，理论上可以通过SpEL表达式访问静态字段，表达式会像这样：`@com.example.yourpackage.DictConstants@MENU_TYPE`，这里假设 `DictConstants` 类位于 `com.example.yourpackage` 包下。\\n\\n不过，由于我目前无法直接执行代码或访问外部系统，我不能直接为你获取该值。但你可以尝试在你的Java应用中使用以下方式来获取它：\\n\\n1. **在Java代码中**：\\n   ```java\\n   String menuType = DictConstants.MENU_TYPE;\\n   System.out.println(menuType);\\n   ```\\n\\n2. **如果确实需要通过SpEL表达式**，并且你有办法执行SpEL（例如在Spring环境中），可以这样做：\\n   ```java\\n   // 假设 spelContext 是一个可以执行SpEL的上下文\\n   String expr = \\\"@com.example.yourpackage.DictConstants@MENU_TYPE\\\";\\n   String menuType = (String) spelContext.evaluate(expr, new Object[] {}, String.class);\\n   System.out.println(menuType);\\n   ```\\n\\n如果你希望我使用模拟功能来演示如何通过SpEL函数调用来解析一个假设的表达式结构，请告诉我，我可以帮你构造一个模拟调用的例子。但请注意，这不会真正访问到你的应用程序上下文或实际的常量值。\"}]','0bcdca19d9e54ca6bee6f8a45fdaabaf'),('8aa28977e860450b813fe610ee0c3fee','2024-06-09 22:02:05.759873','2024-06-09 22:02:05.759873','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','USER','[{\"text\":\"90*100+20-1+20.512+22/2+1+22*100等于多少？\"}]','3c87ecb7d1b74940a9c5b196c6b78c03'),('9fd0baea7a32481895f4b2b6516f9911','2024-06-09 21:45:24.104054','2024-06-09 21:45:24.104054','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','ASSISTANT','[{\"text\":\"计算表达式 20 + 30 + 90 / 2 + 10 的结果是 105。\"}]','0bcdca19d9e54ca6bee6f8a45fdaabaf'),('a1e225ccdffb4d06ae03943736654ec7','2024-06-09 22:02:37.159605','2024-06-09 22:02:37.159605','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','USER','[{\"text\":\"90*100+20-1+20.512+22/2+1+22-100等于多少？\"}]','3c87ecb7d1b74940a9c5b196c6b78c03'),('a70fc1b888764c0487b31af4a2966fe5','2024-06-09 22:00:15.787323','2024-06-09 22:00:15.786779','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','USER','[{\"text\":\"90*100+20-1+20.512+22/2等于多少？\"}]','3c87ecb7d1b74940a9c5b196c6b78c03'),('c476ab765e5e420c9883b3e32ca626d3','2024-06-09 21:53:36.438922','2024-06-09 21:53:36.438922','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','USER','[{\"text\":\"DictConstants,MENU_TYPE的值是什么\"}]','0bcdca19d9e54ca6bee6f8a45fdaabaf'),('c8cf0f096dba463f823ac5dff2588b49','2024-06-06 13:26:04.829485','2024-06-06 13:26:04.829463','fe3d0d7d6eb34eb7a6b7985426cf8af7','fe3d0d7d6eb34eb7a6b7985426cf8af7','USER','[{\"text\":\"我叫什么名字？\"}]','e06418c4016f49f3bb02f338f6216ad6'),('cff066209fc14614a882643ab162b9bd','2024-06-09 22:00:16.173166','2024-06-09 22:00:16.173166','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','ASSISTANT','[{\"text\":\"计算结果是 9050.512。\"}]','3c87ecb7d1b74940a9c5b196c6b78c03'),('ed2f09e374f2474e8e882ecdb39b75de','2024-07-18 17:42:42.231700','2024-07-18 17:42:42.231700','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','USER','[{\"text\":\"你好\"}]','04fe5f347aff46e98363ff7eb106f03a'),('fb51c9afd0d54203b873a245e35fbe16','2024-07-18 17:42:42.477450','2024-07-18 17:42:42.477450','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','ASSISTANT','[{\"text\":\"你好！很高兴见到你。请问有什么问题我可以帮您解答吗？作为一个AI，我在这里为您提供信息和帮助。\"}]','04fe5f347aff46e98363ff7eb106f03a');
/*!40000 ALTER TABLE `ai_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_model`
--

DROP TABLE IF EXISTS `ai_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ai_model` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(32) NOT NULL,
  `editor_id` varchar(32) NOT NULL,
  `name` varchar(40) NOT NULL COMMENT '模型',
  `factory` varchar(32) NOT NULL COMMENT '厂家',
  `options` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_model`
--

LOCK TABLES `ai_model` WRITE;
/*!40000 ALTER TABLE `ai_model` DISABLE KEYS */;
INSERT INTO `ai_model` VALUES ('0c6810422b0a4c7eb08b8b7aec659a27','2024-05-28 20:21:06.532043','2024-05-28 23:23:18.230835','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','gpt-3.5-turbo','OPEN_AI','{\"model\": \"gpt-3.5-turbo\"}'),('1c1a46c482cc45a5b3ed25be7938c201','2024-05-28 20:16:12.595486','2024-05-28 23:23:33.641802','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','Spark3.5 Max','SPARK','{\"model\": \"Spark3.5 Max\", \"domain\": \"generalv3.5\", \"baseUrl\": \"https://spark-api.xf-yun.com/v3.5/chat\"}'),('323115205073473693aa1198340daa7c','2024-05-30 20:52:44.735580','2024-05-30 20:52:44.735580','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','text-embedding-3-small','OPEN_AI','{\"model\": \"text-embedding-3-small\"}'),('3e7e7dbec30f4dff86e63b4fdacc2734','2024-05-28 20:19:58.864966','2024-05-28 23:23:30.733401','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','gpt-4','OPEN_AI','{\"model\": \"gpt-4\"}'),('40c5497adee9451fbf705c85d9700aaa','2024-05-30 21:56:29.563513','2024-05-30 21:56:29.563513','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','bge-large-en','QIAN_FAN','{\"model\": \"bge-large-en\"}'),('4230ef97b0b14f4e8c7ec3f5fc2f5fd9','2024-05-27 23:33:00.310000','2024-05-31 23:39:26.330231','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','glm-4','ZHI_PU','{\"model\": \"GLM-4\"}'),('4d33d126e21041298c4f22c052de5a5f','2024-05-28 00:05:44.546448','2024-05-30 21:56:49.312673','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','text-embedding-v2','DASH_SCOPE','{\"model\": \"text-embedding-v1\"}'),('50c577b875c145ad9d5de20db431bb24','2024-05-27 23:37:12.639000','2024-05-31 23:39:20.443308','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','glm-4v','ZHI_PU','{\"model\": \"GLM-4V\"}'),('54602dd4c375478a9d1eb51ed1f1ea8c','2024-05-27 22:09:34.540000','2024-06-01 22:09:34.540929','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','glm-3-turbo','ZHI_PU','{\"model\": \"glm-3-turbo\"}'),('5fe9795163a948ac96b4986c7e4d5fff','2024-05-28 00:04:20.235905','2024-05-28 23:23:48.045932','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','qwen-turbo','DASH_SCOPE','{\"model\": \"qwen-turbo\"}'),('634eeb7cd91d416c981360dbf54076d2','2024-05-31 10:27:41.400906','2024-05-31 10:27:41.400906','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','moonshot-v1-128k','KIMI','{\"model\": \"moonshot-v1-128k\"}'),('63bc434366744b1383d95373c4c94bac','2024-06-04 22:05:04.522274','2024-06-04 22:06:13.482817','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','cogview-3','ZHI_PU','{\"n\": 1, \"model\": \"cogview-3\", \"width\": 1024, \"height\": 1024}'),('82706c5eb9e94668b331c0981f6aba5c','2024-05-31 23:37:43.272220','2024-06-01 00:25:26.787218','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','embedding-2','ZHI_PU','{\"model\": \"Embedding-2\", \"dimension\": 1024}'),('92c1303d6eb14bf4bff68be0cdcdad65','2024-06-09 10:50:13.526215','2024-06-09 10:50:13.526215','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','qwen-max','DASH_SCOPE','{\"model\": \"qwen-max\"}'),('973639abcfb34dbfb641bff7301655d3','2024-05-28 00:04:59.010448','2024-05-28 23:23:45.468529','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','qwen-vl-plus','DASH_SCOPE','{\"model\": \"qwen-vl-plus\"}'),('9eea9fc391d5451eaef5c1dbc8a23f8a','2024-05-31 09:34:46.536082','2024-05-31 09:34:46.535083','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','qwen-plus','DASH_SCOPE','{\"model\": \"qwen-plus\"}'),('a1ca2afdf9334f91b928fba47e7f6fa0','2024-06-04 16:17:25.082940','2024-06-04 21:09:10.962896','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','wanx-v1','DASH_SCOPE','{\"n\": 1, \"model\": \"wanx-v1\", \"width\": 1024, \"height\": 1024}'),('b003f33cf90e4e72b926c0eb24d0cafb','2024-05-31 10:27:22.151984','2024-05-31 10:27:22.151984','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','moonshot-v1-32k','KIMI','{\"model\": \"moonshot-v1-32k\"}'),('b4b5e915f79246c3bc8ecfb5a8d04e20','2024-05-29 10:49:01.142772','2024-05-29 10:49:01.142772','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','ERNIE-4.0-8K','QIAN_FAN','{\"model\": \"ERNIE-4.0-8K\"}'),('b90979cd07cd4abb97634fbeac1f3233','2024-05-28 00:05:17.786545','2024-06-04 21:57:45.538255','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','qwen-vl-max','DASH_SCOPE','{\"topK\": null, \"topP\": null, \"model\": \"qwen-vl-max\", \"maxTokens\": null}'),('bd03bd177a864135a0c9523249d45887','2024-05-31 10:26:55.061303','2024-05-31 10:26:55.061303','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','moonshot-v1-8k','KIMI','{\"model\": \"moonshot-v1-8k\"}'),('df228edebe8d4b79b3866a2dac36f60b','2024-05-29 10:50:30.644854','2024-05-29 10:50:30.644854','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','ERNIE-4.0-8K-0329','QIAN_FAN','{\"model\": \"ERNIE-4.0-8K-0329\"}'),('ff751fcdac82451a91d7baad46fab88c','2024-05-28 20:20:08.924085','2024-05-28 23:23:28.198581','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','gpt-4-vision-preview','OPEN_AI','{\"model\": \"gpt-4-vision-preview\"}');
/*!40000 ALTER TABLE `ai_model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_model_tag_rel`
--

DROP TABLE IF EXISTS `ai_model_tag_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ai_model_tag_rel` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(32) NOT NULL,
  `editor_id` varchar(32) NOT NULL,
  `ai_tag_id` varchar(32) NOT NULL,
  `ai_model_id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ai_model_id` (`ai_model_id`,`ai_tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_model_tag_rel`
--

LOCK TABLES `ai_model_tag_rel` WRITE;
/*!40000 ALTER TABLE `ai_model_tag_rel` DISABLE KEYS */;
INSERT INTO `ai_model_tag_rel` VALUES ('09fef84b68b54e61a58d7915464bbd69','2024-05-28 23:23:28.256834','2024-05-28 23:23:28.256834','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','c50e7e2beb184ae6b95fb9a7c8d59ccc','ff751fcdac82451a91d7baad46fab88c'),('0a9a10e3c2cc4b29b08f266ae497f703','2024-05-30 21:56:49.331071','2024-05-30 21:56:49.331071','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','5934aaeeaf1942d19d80dbc5ccdf3d1f','4d33d126e21041298c4f22c052de5a5f'),('19f1fda880504ad7a116e383c2c06a6a','2024-05-30 21:56:29.593598','2024-05-30 21:56:29.593598','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','7c566ab76ac94bf798c83fa8c62f5418','40c5497adee9451fbf705c85d9700aaa'),('1dbfb779a4e94b4c9fa416283df5f2fc','2024-06-01 00:25:26.795486','2024-06-01 00:25:26.795486','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','07cd4919d75942bbb4480ee18dd4341e','82706c5eb9e94668b331c0981f6aba5c'),('31feb239d22e4f0a82ad2001826f37a3','2024-05-31 09:34:46.571111','2024-05-31 09:34:46.571111','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','069cf7c3483d462fb2dfdcc0abec81f7','9eea9fc391d5451eaef5c1dbc8a23f8a'),('49f1faf1787c467681855c9c87888c27','2024-05-31 10:27:41.418934','2024-05-31 10:27:41.418934','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','5283d6f3c9524ccb8c19ec61a27db1a8','634eeb7cd91d416c981360dbf54076d2'),('4b68a1f8b98844cba7365684a82d80da','2024-05-28 23:23:18.251560','2024-05-28 23:23:18.251560','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','4345032f8198403ea2bfc061e983668e','0c6810422b0a4c7eb08b8b7aec659a27'),('4b8d4e8c37d1449eb3f8110fe154530c','2024-05-29 10:50:30.690443','2024-05-29 10:50:30.690443','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','bffcdd2ae7884319acf719cac439ec0f','df228edebe8d4b79b3866a2dac36f60b'),('513299ee9ce84c8396252de7331a46a4','2024-05-31 23:39:20.450631','2024-05-31 23:39:20.450631','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','07bf2608ef77496eb37426c59fc1e686','50c577b875c145ad9d5de20db431bb24'),('5e84e175664c4495a8e17f4b0421ffbd','2024-06-04 21:57:45.569316','2024-06-04 21:57:45.568765','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','069cf7c3483d462fb2dfdcc0abec81f7','b90979cd07cd4abb97634fbeac1f3233'),('6d0e07555cb2447d86d4dea7ca3992b8','2024-05-28 23:23:28.249536','2024-05-28 23:23:28.249536','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','4345032f8198403ea2bfc061e983668e','ff751fcdac82451a91d7baad46fab88c'),('6ed0d77c6ffe421bbba83a1fad1d5026','2024-05-28 23:23:48.061640','2024-05-28 23:23:48.061640','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','069cf7c3483d462fb2dfdcc0abec81f7','5fe9795163a948ac96b4986c7e4d5fff'),('738d07fa6954426082c9f383c5bfb886','2024-05-31 23:39:26.337744','2024-05-31 23:39:26.337744','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','07bf2608ef77496eb37426c59fc1e686','4230ef97b0b14f4e8c7ec3f5fc2f5fd9'),('814025b712d043359448375d253eddae','2024-05-28 23:23:33.656042','2024-05-28 23:23:33.656042','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','8c4bad02c25a4bdba76cead6fe4c8c17','1c1a46c482cc45a5b3ed25be7938c201'),('89b0ccf3270f4c8f916fdfb268c53485','2024-06-04 22:06:13.494004','2024-06-04 22:06:13.494004','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','57e9189e9ba54a2094ebf3776649726b','63bc434366744b1383d95373c4c94bac'),('96bb3c798f6843b58f1bfb2f88c4adb4','2024-05-30 20:52:44.751103','2024-05-30 20:52:44.751103','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','7214c324a160419fb2f2af84ebc501b0','323115205073473693aa1198340daa7c'),('9e13ad0073c744acbd034983eeb4856f','2024-05-28 23:23:30.748003','2024-05-28 23:23:30.748003','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','4345032f8198403ea2bfc061e983668e','3e7e7dbec30f4dff86e63b4fdacc2734'),('a4cb08a0f6894e1984609962ef021611','2024-06-01 22:09:34.557034','2024-06-01 22:09:34.557034','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','07bf2608ef77496eb37426c59fc1e686','54602dd4c375478a9d1eb51ed1f1ea8c'),('abec138ebf1b408dba8523a1c3c40567','2024-06-09 10:50:13.616606','2024-06-09 10:50:13.616606','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','069cf7c3483d462fb2dfdcc0abec81f7','92c1303d6eb14bf4bff68be0cdcdad65'),('ae3aba320f814aaea3cc594a5e8584a4','2024-05-31 23:39:20.454630','2024-05-31 23:39:20.454630','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','8c09f0af95c244e68fddec1c3b4be8a9','50c577b875c145ad9d5de20db431bb24'),('beddbee143c14ae09012ae12228a356b','2024-05-28 23:23:45.492778','2024-05-28 23:23:45.492778','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','be2693e676524f45b011c22b694b9360','973639abcfb34dbfb641bff7301655d3'),('cc6134c6d19d43fb8d0aff9074e86f77','2024-05-31 10:27:22.161873','2024-05-31 10:27:22.161873','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','5283d6f3c9524ccb8c19ec61a27db1a8','b003f33cf90e4e72b926c0eb24d0cafb'),('d7ac6810bb894c39a599e424ba77be79','2024-06-04 21:57:45.578883','2024-06-04 21:57:45.578883','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','be2693e676524f45b011c22b694b9360','b90979cd07cd4abb97634fbeac1f3233'),('d9ccc596f8b5433c8cda77e7c32847d7','2024-05-31 10:26:55.081312','2024-05-31 10:26:55.081312','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','5283d6f3c9524ccb8c19ec61a27db1a8','bd03bd177a864135a0c9523249d45887'),('e4f950044b3b4483b7b98385e77cd29b','2024-05-29 10:49:01.199438','2024-05-29 10:49:01.199438','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','bffcdd2ae7884319acf719cac439ec0f','b4b5e915f79246c3bc8ecfb5a8d04e20'),('ea46bd331711411daaf2427add29c3c8','2024-06-04 21:09:11.052367','2024-06-04 21:09:11.052367','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','bd3ec54c65c947b9ab654213a4528f7f','a1ca2afdf9334f91b928fba47e7f6fa0'),('fc09c9b8c6d147809f5c47c8c8e90086','2024-05-28 23:23:45.482188','2024-05-28 23:23:45.482188','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','069cf7c3483d462fb2dfdcc0abec81f7','973639abcfb34dbfb641bff7301655d3');
/*!40000 ALTER TABLE `ai_model_tag_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_plugin`
--

DROP TABLE IF EXISTS `ai_plugin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ai_plugin` (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `editor_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '函数名称',
  `description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '函数描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI插件';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_plugin`
--

LOCK TABLES `ai_plugin` WRITE;
/*!40000 ALTER TABLE `ai_plugin` DISABLE KEYS */;
INSERT INTO `ai_plugin` VALUES ('4703133f93874c51bdc7860cc781632f','2024-06-08 15:27:29.806904','2024-06-08 15:39:58.594491','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','springSpELFunction','执行符合SpringSpE语法的表达式');
/*!40000 ALTER TABLE `ai_plugin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_role`
--

DROP TABLE IF EXISTS `ai_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ai_role` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(32) NOT NULL,
  `editor_id` varchar(32) NOT NULL,
  `name` varchar(255) NOT NULL COMMENT '角色名称',
  `description` varchar(32) NOT NULL,
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `prompts` json NOT NULL COMMENT '预置提示词',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_role`
--

LOCK TABLES `ai_role` WRITE;
/*!40000 ALTER TABLE `ai_role` DISABLE KEYS */;
INSERT INTO `ai_role` VALUES ('87dde7fd46b24b9c8836dd1d48348307','2024-06-01 21:07:10.219035','2024-06-01 21:07:10.219035','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','python助手','可以帮助你编写python代码','https://my-community.oss-cn-qingdao.aliyuncs.com/20240601210542起凡.jpg','[{\"type\": \"SYSTEM\", \"content\": [{\"text\": \"你是一个python助手可以帮助用户编写python程序\"}]}]');
/*!40000 ALTER TABLE `ai_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_session`
--

DROP TABLE IF EXISTS `ai_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ai_session` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(32) NOT NULL,
  `editor_id` varchar(32) NOT NULL,
  `name` varchar(32) NOT NULL COMMENT '会话名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_session`
--

LOCK TABLES `ai_session` WRITE;
/*!40000 ALTER TABLE `ai_session` DISABLE KEYS */;
INSERT INTO `ai_session` VALUES ('04fe5f347aff46e98363ff7eb106f03a','2024-07-18 17:37:44.045118','2024-07-18 17:37:44.045118','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','新的聊天'),('0bcdca19d9e54ca6bee6f8a45fdaabaf','2024-06-09 21:44:31.937565','2024-06-09 21:44:31.937565','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','新的聊天'),('3c87ecb7d1b74940a9c5b196c6b78c03','2024-06-09 21:44:05.720036','2024-06-09 21:44:05.720036','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','新的聊天'),('9d45c3988d5b4cff8648cbbb43cb0397','2024-06-09 21:38:07.481500','2024-06-09 21:38:07.481500','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','新的聊天'),('e06418c4016f49f3bb02f338f6216ad6','2024-06-06 13:24:44.545883','2024-06-06 13:24:44.545858','fe3d0d7d6eb34eb7a6b7985426cf8af7','fe3d0d7d6eb34eb7a6b7985426cf8af7','新的聊天');
/*!40000 ALTER TABLE `ai_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_tag`
--

DROP TABLE IF EXISTS `ai_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ai_tag` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(32) NOT NULL,
  `editor_id` varchar(32) NOT NULL,
  `name` varchar(32) NOT NULL COMMENT '标签',
  `factory` varchar(32) NOT NULL COMMENT '厂家',
  `service` varchar(32) NOT NULL COMMENT 'UniAiService',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='spring ai model标签';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_tag`
--

LOCK TABLES `ai_tag` WRITE;
/*!40000 ALTER TABLE `ai_tag` DISABLE KEYS */;
INSERT INTO `ai_tag` VALUES ('069cf7c3483d462fb2dfdcc0abec81f7','2024-05-27 23:43:02.500927','2024-05-28 23:25:39.139009','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','AIGC','DASH_SCOPE','DashScopeAiChatService'),('07bf2608ef77496eb37426c59fc1e686','2024-05-31 23:26:04.446401','2024-05-31 23:26:04.446401','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','AIGC','ZHI_PU','ZhiPuAiChatService'),('07cd4919d75942bbb4480ee18dd4341e','2024-05-31 23:26:47.404189','2024-05-31 23:27:04.368923','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','EMBEDDINGS','ZHI_PU','ZhiPuAiEmbeddingService'),('24bddddf2ec24a4fb9b0c74779cb779c','2024-06-04 16:15:12.333321','2024-06-04 16:15:12.333321','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','IMAGE','OPEN_AI','OpenAiImageService'),('4345032f8198403ea2bfc061e983668e','2024-05-27 23:44:19.471442','2024-05-28 23:25:15.650626','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','AIGC','OPEN_AI','OpenAiChatService'),('5283d6f3c9524ccb8c19ec61a27db1a8','2024-05-31 10:26:21.006136','2024-05-31 10:26:21.006136','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','AIGC','KIMI','KimiAiChatService'),('57e9189e9ba54a2094ebf3776649726b','2024-06-04 16:14:54.535008','2024-06-04 16:14:54.535008','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','IMAGE','ZHI_PU','ZhiPuAiImageService'),('5934aaeeaf1942d19d80dbc5ccdf3d1f','2024-05-27 23:43:34.461077','2024-05-30 16:18:40.134320','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','EMBEDDINGS','DASH_SCOPE','DashScopeAiEmbeddingService'),('7214c324a160419fb2f2af84ebc501b0','2024-05-27 23:44:43.470784','2024-05-30 20:48:22.342354','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','EMBEDDINGS','OPEN_AI','OpenAiEmbeddingService'),('7c566ab76ac94bf798c83fa8c62f5418','2024-05-30 21:55:56.015829','2024-05-30 21:55:56.015829','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','EMBEDDINGS','QIAN_FAN','QifanAiEmbeddingService'),('8c09f0af95c244e68fddec1c3b4be8a9','2024-05-31 23:26:14.366645','2024-05-31 23:26:14.366645','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','VISION','ZHI_PU','ZhiPuAiChatService'),('8c4bad02c25a4bdba76cead6fe4c8c17','2024-05-27 23:43:55.626986','2024-05-28 23:25:27.947963','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','AIGC','SPARK','SparkAiChatService'),('bd3ec54c65c947b9ab654213a4528f7f','2024-06-04 16:15:27.203953','2024-06-04 16:15:27.203953','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','IMAGE','DASH_SCOPE','DashScopeAiImageService'),('be2693e676524f45b011c22b694b9360','2024-05-27 23:42:45.669197','2024-05-28 23:25:49.606526','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','VISION','DASH_SCOPE','DashScopeAiChatService'),('bffcdd2ae7884319acf719cac439ec0f','2024-05-29 10:48:38.658448','2024-05-29 10:48:38.658448','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','AIGC','QIAN_FAN','QianFanAiChatService'),('c50e7e2beb184ae6b95fb9a7c8d59ccc','2024-05-27 23:45:07.621619','2024-05-28 23:24:50.600348','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','VISION','OPEN_AI','OpenAiChatService');
/*!40000 ALTER TABLE `ai_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict`
--

DROP TABLE IF EXISTS `dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dict` (
  `id` varchar(36) DEFAULT NULL,
  `created_time` datetime(6) DEFAULT NULL,
  `edited_time` datetime(6) DEFAULT NULL,
  `creator_id` varchar(36) DEFAULT NULL,
  `editor_id` varchar(36) DEFAULT NULL,
  `key_id` int DEFAULT NULL,
  `key_en_name` varchar(255) DEFAULT NULL,
  `key_name` varchar(36) DEFAULT NULL,
  `dict_id` int DEFAULT NULL,
  `dict_name` varchar(36) DEFAULT NULL,
  `dict_en_name` varchar(255) DEFAULT NULL,
  `order_num` int DEFAULT NULL,
  `visible` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict`
--

LOCK TABLES `dict` WRITE;
/*!40000 ALTER TABLE `dict` DISABLE KEYS */;
INSERT INTO `dict` VALUES ('1f01fa7b-f162-4376-870d-9207735f658d','2024-01-16 09:33:09.151337','2024-01-16 09:33:09.151337','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',2,'BUTTON','按钮',1002,'菜单类型','MENU_TYPE',2,1),('3860dff4-7f22-4ded-bc30-19cd1b4bc098','2024-01-16 09:30:39.144272','2024-01-16 09:33:15.663135','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',0,'PAGE','页面',1002,'菜单类型','MENU_TYPE',1,1),('416c90b4-42e8-4af1-a3f5-7e321c9c3437','2024-01-16 09:32:28.555205','2024-01-16 09:32:28.555205','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',1,'DIRECTORY','目录',1002,'菜单类型','MENU_TYPE',0,1),('5fba34ff-760c-453a-9ce3-284ed68710ca','2024-01-14 15:23:55.954376','2024-01-14 15:23:55.954376','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',2,'PRIVATE','保密',1001,'性别','GENDER',0,1),('a3caf40e-a1ef-4a77-8096-f467fb14060e','2024-01-10 10:50:18.555224','2024-01-11 15:49:22.959501','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',0,'MALE','男',1001,'性别','GENDER',0,1),('b3366061-0be1-45f9-98de-5a86753665ce','2024-01-10 13:55:26.468101','2024-01-10 13:55:26.468101','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',1,'FEMALE','女',1001,'性别','GENDER',1,1),('f0e0c95e7ad249deb9359691d109fd7h','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',1,'RECHARGE','充值',1010,'钱包操作类型','WALLET_RECORD_TYPE',0,1),('f0e0c95e7ad249deb9359691d109zd7h','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',2,'WITHDRAW','提现',1010,'钱包操作类型','WALLET_RECORD_TYPE',0,1),('f0e0c9ze7ad249deb9359691d109bd8a','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',1,'ASSISTANT','助手',1015,'AI消息类型','AI_MESSAGE_TYPE',0,1),('f0e0c9ze7ad249deb9359691d109bd8z','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',0,'USER','用户',1015,'AI消息类型','AI_MESSAGE_TYPE',0,1),('fc930d38-0612-4207-91ab-809a5be03656','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',2,'SYSTEM','系统',1015,'AI消息类型','AI_MESSAGE_TYPE',0,1),('f0e0c95e2ad249deb9359691d109zd7h','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',3,'OCR','OCR',1010,'钱包操作类型','WALLET_RECORD_TYPE',0,1),('f0e0c95e3ad249deb9359691d109zd7h','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',4,'CHAT','聊天',1010,'钱包操作类型','WALLET_RECORD_TYPE',0,1),('416c90b442e84af1a4f57e321c9c3437','2024-01-16 09:32:28.555205','2024-01-16 09:32:28.555205','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',0,'NORMAL','正常',1003,'用户状态','USER_STATUS',0,1),('416c90b442e84af1a5f57e321c9c3437','2024-01-16 09:32:28.555205','2024-01-16 09:32:28.555205','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',1,'BANNED','封禁',1003,'用户状态','USER_STATUS',0,1),('fc930d38-0612-4217-91ab-809a5be03656','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',0,'OPEN_AI','OpenAi',1016,'AI厂家类型','AI_FACTORY_TYPE',0,1),('fc930d38-0612-4227-91ab-809a5be03656','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',1,'DASH_SCOPE','阿里灵积',1016,'AI厂家类型','AI_FACTORY_TYPE',0,1),('fc930d38-1612-4237-91ab-809a5be03656','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',0,'AIGC','文本生成',1017,'AI模型标签','AI_MODEL_TAG',0,1),('fc930d38-2612-4237-91ab-809a5be03656','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',1,'EMBEDDINGS','文本嵌入',1017,'AI模型标签','AI_MODEL_TAG',0,1),('fc930d38-3612-4237-91ab-809a5be03656','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',2,'AUDIO','语言',1017,'AI模型标签','AI_MODEL_TAG',0,1),('fc930d38-4612-4237-91ab-809a5be03656','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',3,'NLU','nlu',1017,'AI模型标签','AI_MODEL_TAG',0,1),('fc930d38-5612-4237-91ab-809a5be03656','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',4,'VISION','图片理解',1017,'AI模型标签','AI_MODEL_TAG',0,1),('fc930d38-0612-4237-91ab-809a5be03651','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',3,'QIAN_FAN','百度千帆',1016,'AI厂家类型','AI_FACTORY_TYPE',0,1),('fc930d38-0612-4237-91ab-809a5be03652','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',4,'KIMI','KIMI',1016,'AI厂家类型','AI_FACTORY_TYPE',0,1),('fc930d38-0612-4237-91ab-809a5be03653','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',5,'ZHI_PU','智谱清言',1016,'AI厂家类型','AI_FACTORY_TYPE',0,1),('fc930d38-5612-4237-91ab-809a5be03626','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',5,'IMAGE','图片生成',1017,'AI模型标签','AI_MODEL_TAG',0,1),('fc930d38-5612-4232-91ab-809a5be03626','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',0,'ALI_YUN_OSS','阿里云OSS',1018,'OSS类型','OSS_TYPE',0,1),('fc930d38-5612-4233-91ab-809a5be03626','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',1,'TENCENT_OSS','腾讯云OSS',1018,'OSS类型','OSS_TYPE',0,1),('fc930d38-5612-4234-91ab-809a5be03626','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',2,'NGINX_OSS','本地nginx',1018,'OSS类型','OSS_TYPE',0,1);
/*!40000 ALTER TABLE `dict` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `name` varchar(20) NOT NULL,
  `path` varchar(2000) NOT NULL,
  `parent_id` varchar(36) DEFAULT NULL,
  `order_num` int DEFAULT NULL,
  `menu_type` varchar(36) NOT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `visible` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES ('00f2290acde54e9592944e7599b1a3f5','2024-01-16 11:21:02.655367','2024-06-05 21:49:21.870729','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','权限管理','/permission',NULL,0,'DIRECTORY','Lock',1),('086673ec52914a68960f0649b78d2820','2024-05-19 09:19:04.863236','2024-05-19 09:19:04.863236','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','价格配置','/setting','4f9449e8d17e4582a420041f0651a56c',2,'PAGE',NULL,1),('0bb6b0f8a349418db2485db92062efc4','2024-06-08 15:18:09.042703','2024-06-08 15:18:09.042703','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','插件','/ai-plugin','78f7fed192b547dd82c62ebfac00a4fa',0,'PAGE',NULL,1),('0c30e2f68cef4ee6999ea18af97a3a26','2024-01-16 14:33:09.062999','2024-01-17 10:10:02.922860','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','用户','/user','00f2290acde54e9592944e7599b1a3f5',1,'PAGE','User',1),('139e0f68e01a4583b6da8afa883af1dd','2024-05-05 10:24:28.466054','2024-06-05 21:48:51.006619','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','会话记录','/ai-session','7704a6b711c3467192c528dcb7d53349',2,'PAGE',NULL,1),('1560f02165ea486f9ce7e0d899eda843','2024-06-05 21:56:23.426212','2024-06-05 21:56:23.426212','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','OSS配置','/oss','78f7fed192b547dd82c62ebfac00a4fa',6,'PAGE',NULL,1),('19c78fe7f1c44b048ef7b2b1b865d784','2024-05-19 11:51:25.063900','2024-05-19 11:51:25.063900','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','流水统计','/wallet-record-stats','4f9449e8d17e4582a420041f0651a56c',4,'PAGE',NULL,1),('2d4cac3af737486999948f5774bf94e3','2024-05-05 10:21:46.714189','2024-06-03 16:52:22.627943','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','角色','/ai-role','78f7fed192b547dd82c62ebfac00a4fa',4,'PAGE',NULL,1),('349b39a595004c229972f22b935aba42','2024-01-17 10:12:54.930459','2024-01-17 10:13:12.046479','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','角色','/role','00f2290acde54e9592944e7599b1a3f5',2,'PAGE','Avatar',1),('3e4a455bd4dc4410989e06d682a94d4a','2024-05-30 15:17:45.508150','2024-05-30 23:46:51.958684','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','文档管理','/ai-document','7704a6b711c3467192c528dcb7d53349',6,'PAGE',NULL,1),('46aeab05d6ce420ab4a7bb5b2ab551e9','2024-05-01 11:09:45.265557','2024-05-01 11:09:45.265557','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','钱包订单','/wallet-order','4f9449e8d17e4582a420041f0651a56c',4,'PAGE',NULL,1),('4f9449e8d17e4582a420041f0651a56c','2024-04-17 13:55:33.679396','2024-06-05 21:49:04.753099','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','钱包管理','wallet-manage',NULL,8,'DIRECTORY',NULL,1),('6152773c1ddc4938853b5c6b51f07d0e','2024-04-17 13:55:50.298812','2024-04-17 13:55:50.298812','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','用户钱包','/wallet','4f9449e8d17e4582a420041f0651a56c',1,'PAGE',NULL,1),('7704a6b711c3467192c528dcb7d53349','2024-04-19 11:01:33.094780','2024-06-03 16:39:43.380988','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','AI助手','/ai',NULL,1,'DIRECTORY','ChatDotRound',1),('78f7fed192b547dd82c62ebfac00a4fa','2024-06-01 15:27:07.343645','2024-06-03 16:40:17.579155','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','AI配置','ai-config',NULL,2,'DIRECTORY','Setting',1),('8b7db6952a884741924e7b209ad06a14','2024-04-19 11:01:59.361801','2024-04-19 11:01:59.361801','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','聊天面板','/chat','7704a6b711c3467192c528dcb7d53349',NULL,'PAGE',NULL,1),('8ea32b3d90e44d76a777413917166a32','2024-01-16 11:20:24.133270','2024-01-17 10:13:01.821007','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','菜单','/menu','00f2290acde54e9592944e7599b1a3f5',3,'PAGE','Menu',1),('a350090e11ff45d088ce5c3c1b6f9ef4','2024-01-26 10:06:09.672544','2024-06-05 21:49:14.348836','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','系统管理','/system',NULL,0,'DIRECTORY','House',1),('a8bedf2759bb4259b685c79578bae01d','2024-05-27 20:15:14.637091','2024-06-01 15:28:08.405792','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','模型','/ai-model','78f7fed192b547dd82c62ebfac00a4fa',2,'PAGE',NULL,1),('a8dc7ef7f3394580ba913fa4f0ac05f8','2024-05-27 23:40:46.635762','2024-06-01 15:27:57.542618','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','模型标签','/ai-tag','78f7fed192b547dd82c62ebfac00a4fa',1,'PAGE',NULL,1),('bf1774f2e06940e08cc2ffe93adfbbce','2024-06-01 15:27:42.614228','2024-06-01 21:04:54.663607','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','厂家','/ai-factory','78f7fed192b547dd82c62ebfac00a4fa',0,'PAGE',NULL,1),('caac5913aa734079a0e486060148feac','2024-05-01 11:09:17.227571','2024-05-01 11:09:17.227571','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','钱包项','/wallet-item','4f9449e8d17e4582a420041f0651a56c',3,'PAGE',NULL,1),('d097dbf9a75d4c1691b21834257d405c','2024-05-05 10:22:13.569197','2024-06-05 21:48:55.864532','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','消息记录','/ai-message','7704a6b711c3467192c528dcb7d53349',1,'PAGE',NULL,1),('ebb8a75b9ef94b969ac750180a74611b','2024-05-30 23:47:14.859517','2024-05-30 23:47:14.859517','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','知识库','/ai-collection','7704a6b711c3467192c528dcb7d53349',7,'PAGE',NULL,1),('fcf86780d4474a6096b84f03c4eddc92','2024-01-26 10:07:04.713450','2024-01-26 10:07:04.713450','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','字典','/dict','a350090e11ff45d088ce5c3c1b6f9ef4',0,'PAGE','Notebook',1),('ffb68dcb420540a4bd7d0a01fc40f43e','2024-04-17 13:56:11.627444','2024-04-17 13:56:11.627444','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','钱包流水','/wallet-record','4f9449e8d17e4582a420041f0651a56c',2,'PAGE',NULL,1);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `name` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_pk` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES ('34c09a5b67904e31ba3be5fd8101089a','2024-04-27 17:52:37.583669','2024-06-08 15:18:25.259206','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','管理员'),('c047f5d1ffa14e1b8c32ae06106426f4','2024-06-05 23:27:41.618495','2024-06-05 23:27:41.618495','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','普通用户');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_menu_rel`
--

DROP TABLE IF EXISTS `role_menu_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_menu_rel` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `role_id` varchar(36) NOT NULL,
  `menu_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_id` (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_menu_rel`
--

LOCK TABLES `role_menu_rel` WRITE;
/*!40000 ALTER TABLE `role_menu_rel` DISABLE KEYS */;
INSERT INTO `role_menu_rel` VALUES ('08493f4c6cae41e496829c2772040b01','2024-06-05 23:27:41.672963','2024-06-05 23:27:41.672963','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','c047f5d1ffa14e1b8c32ae06106426f4','ebb8a75b9ef94b969ac750180a74611b'),('0a0b0480d9734b189f726eae35196708','2024-06-05 23:27:41.658277','2024-06-05 23:27:41.658277','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','c047f5d1ffa14e1b8c32ae06106426f4','7704a6b711c3467192c528dcb7d53349'),('0d516a3fa9d14fb38ef3a6a96e98c29c','2024-04-27 17:52:37.621118','2024-04-27 17:52:37.621118','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','fcf86780d4474a6096b84f03c4eddc92'),('0f2d6719697c470897c18fb64f2a80bd','2024-05-30 15:17:53.282990','2024-05-30 15:17:53.282990','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','3e4a455bd4dc4410989e06d682a94d4a'),('1027660575d14ab09bbb60d0123e4c38','2024-04-27 17:52:37.624627','2024-04-27 17:52:37.624627','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','00f2290acde54e9592944e7599b1a3f5'),('14d4a920af544eaf8f2499a916d20f57','2024-06-05 23:27:41.694574','2024-06-05 23:27:41.694574','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','c047f5d1ffa14e1b8c32ae06106426f4','2d4cac3af737486999948f5774bf94e3'),('1ddb7a2fceb347a084e220377cf1c2ee','2024-04-27 17:52:37.637196','2024-04-27 17:52:37.637196','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','8ea32b3d90e44d76a777413917166a32'),('23447dae30e742b28875ad57da5892c1','2024-05-19 09:19:16.606373','2024-05-19 09:19:16.606373','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','086673ec52914a68960f0649b78d2820'),('4f10b3ad529649e9991ed66c6427819b','2024-04-27 17:52:37.605605','2024-04-27 17:52:37.605605','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','a350090e11ff45d088ce5c3c1b6f9ef4'),('5b451a2b2360401e9760cb430dbd11c4','2024-05-30 23:47:33.552357','2024-05-30 23:47:33.552357','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','ebb8a75b9ef94b969ac750180a74611b'),('5b71563cea97476f8d7ab53af66d5f68','2024-05-01 11:09:59.500573','2024-05-01 11:09:59.500573','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','caac5913aa734079a0e486060148feac'),('6c3a47e0050241eba7b1a011fbc23cb9','2024-04-27 17:52:37.633191','2024-04-27 17:52:37.633191','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','349b39a595004c229972f22b935aba42'),('6e923cd7d4e84c7e8e564f51ec169e3c','2024-05-05 10:31:35.334952','2024-05-05 10:31:35.334952','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','d097dbf9a75d4c1691b21834257d405c'),('76e0d6d0725e4eb4ba0e42e2d99ff2bc','2024-04-27 17:52:37.601600','2024-04-27 17:52:37.601600','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','ffb68dcb420540a4bd7d0a01fc40f43e'),('78299b736300444d9fded49b4d75e778','2024-06-05 23:27:41.665924','2024-06-05 23:27:41.665924','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','c047f5d1ffa14e1b8c32ae06106426f4','3e4a455bd4dc4410989e06d682a94d4a'),('784b1732ff2a4498aaa81245cc485c5c','2024-05-27 23:41:06.263246','2024-05-27 23:41:06.263246','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','a8dc7ef7f3394580ba913fa4f0ac05f8'),('7d0b2b8bad004333b27827539df8873a','2024-06-05 23:27:41.688107','2024-06-05 23:27:41.688107','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','c047f5d1ffa14e1b8c32ae06106426f4','78f7fed192b547dd82c62ebfac00a4fa'),('83980cab4a7447fb80d6bbd4563999d7','2024-04-27 17:52:37.592700','2024-04-27 17:52:37.592700','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','4f9449e8d17e4582a420041f0651a56c'),('8e77baa546104cd7ac8b63bdc8243f3b','2024-05-19 11:51:41.227364','2024-05-19 11:51:41.227364','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','19c78fe7f1c44b048ef7b2b1b865d784'),('8f59541023e64343952c44f9b2c2196e','2024-06-05 23:27:41.701715','2024-06-05 23:27:41.701715','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','c047f5d1ffa14e1b8c32ae06106426f4','1560f02165ea486f9ce7e0d899eda843'),('91db25face41433fb60fa71581a1526c','2024-06-01 15:28:38.369567','2024-06-01 15:28:38.369567','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','bf1774f2e06940e08cc2ffe93adfbbce'),('94fe52adc3b64ac690875a995bd813e8','2024-05-25 22:30:08.690001','2024-05-25 22:30:08.690001','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','8b7db6952a884741924e7b209ad06a14'),('9a2931318e6b4bb286af1bdba661fb62','2024-06-01 15:28:38.362560','2024-06-01 15:28:38.362560','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','78f7fed192b547dd82c62ebfac00a4fa'),('9dd2ab5ea69844949513fdff97b47929','2024-04-27 17:52:37.596699','2024-04-27 17:52:37.596699','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','6152773c1ddc4938853b5c6b51f07d0e'),('a504aaa67cb8408b98076d158a0c6864','2024-05-27 20:15:23.415214','2024-05-27 20:15:23.415214','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','a8bedf2759bb4259b685c79578bae01d'),('a789ccf3525a4feea4b23949ff5a00dd','2024-06-05 23:27:41.680134','2024-06-05 23:27:41.680134','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','c047f5d1ffa14e1b8c32ae06106426f4','8b7db6952a884741924e7b209ad06a14'),('a8c4669a831e45798aba87c16b3c6bfb','2024-05-05 10:33:08.545920','2024-05-05 10:33:08.545920','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','7704a6b711c3467192c528dcb7d53349'),('c0b89b4051344e3da0c6c4ad4d2f6725','2024-06-08 15:18:25.312477','2024-06-08 15:18:25.312477','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','0bb6b0f8a349418db2485db92062efc4'),('c8856a8feb9344f4a75747b38863a169','2024-04-27 17:52:37.628631','2024-04-27 17:52:37.628631','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','0c30e2f68cef4ee6999ea18af97a3a26'),('c99873d4f2774357b0f69696d8dc0798','2024-05-05 10:31:35.346387','2024-05-05 10:31:35.346387','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','2d4cac3af737486999948f5774bf94e3'),('d471c87a608e4af4a721cf69b4b70a03','2024-05-01 11:09:59.505698','2024-05-01 11:09:59.505698','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','46aeab05d6ce420ab4a7bb5b2ab551e9'),('e6a0d7cd10624443aa5cd8aff1327e9d','2024-06-05 22:20:16.163543','2024-06-05 22:20:16.163543','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','1560f02165ea486f9ce7e0d899eda843'),('e6f70d4e87ef4b3d9d8b58bb5cd8f0ac','2024-05-05 10:31:35.340396','2024-05-05 10:31:35.340396','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','139e0f68e01a4583b6da8afa883af1dd'),('ee03f9dea231400bb8c60ef6853b4c16','2024-06-05 23:27:41.709059','2024-06-05 23:27:41.709059','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','c047f5d1ffa14e1b8c32ae06106426f4','bf1774f2e06940e08cc2ffe93adfbbce');
/*!40000 ALTER TABLE `role_menu_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `nickname` varchar(20) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `gender` varchar(36) DEFAULT NULL,
  `phone` varchar(20) NOT NULL,
  `password` varchar(100) NOT NULL,
  `status` varchar(32) NOT NULL DEFAULT 'NORMAL' COMMENT '用户状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('dcd256e2412f4162a6a5fcbd5cfedc84','2024-05-01 16:52:43.364225','2024-05-19 21:30:34.686818','起凡','https://my-community.oss-cn-qingdao.aliyuncs.com/20240501203628ptwondCGhItP67eb5ac72554b07800b22c542245e457.jpeg','MALE','11111111111','$2a$10$o/DHIt/eAMR175TgDV/PeeuEOpqW1N4Klft6obvs2zqBuiwMgLWOW','BANNED'),('fe3d0d7d6eb34eb7a6b7985426cf8af7','2024-06-06 13:23:46.130879','2024-06-06 13:23:46.130817','默认用户',NULL,NULL,'13656987994','$2a$10$q7pey1P1/b3lO9nzFLKOb.ISrX7.lkktMjghwhgvNqvA.EjZZ2mg2','NORMAL');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role_rel`
--

DROP TABLE IF EXISTS `user_role_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role_rel` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `role_id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_id` (`role_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role_rel`
--

LOCK TABLES `user_role_rel` WRITE;
/*!40000 ALTER TABLE `user_role_rel` DISABLE KEYS */;
INSERT INTO `user_role_rel` VALUES ('100af7a7164a4bd0ab80f772c94f7d48','2024-05-01 20:42:43.871421','2024-05-01 20:42:43.871421','60aaf6de424a4df2827380fdf5a23738','60aaf6de424a4df2827380fdf5a23738','34c09a5b67904e31ba3be5fd8101089a','dcd256e2412f4162a6a5fcbd5cfedc84'),('34c09a5b67904e31ba3be5fd8101089a','2024-04-27 17:54:28.000000','2024-04-27 17:54:31.000000','60aaf6de424a4df2827380fdf5a23738','60aaf6de424a4df2827380fdf5a23738','34c09a5b67904e31ba3be5fd8101089a','60aaf6de424a4df2827380fdf5a23738'),('54fe16f1091248af8617a7fa276a8295','2024-06-06 13:23:46.568793','2024-06-06 13:23:46.568770','fe3d0d7d6eb34eb7a6b7985426cf8af7','fe3d0d7d6eb34eb7a6b7985426cf8af7','c047f5d1ffa14e1b8c32ae06106426f4','fe3d0d7d6eb34eb7a6b7985426cf8af7');
/*!40000 ALTER TABLE `user_role_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_we_chat`
--

DROP TABLE IF EXISTS `user_we_chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_we_chat` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `open_id` varchar(30) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `open_id` (`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_we_chat`
--

LOCK TABLES `user_we_chat` WRITE;
/*!40000 ALTER TABLE `user_we_chat` DISABLE KEYS */;
INSERT INTO `user_we_chat` VALUES ('f997e7b2c2694c0184d9494cab51b099','2024-05-01 16:52:43.408780','2024-05-01 16:52:43.408780','oKF8D7W75VMV_Md4FVMUAx0MCDeA','dcd256e2412f4162a6a5fcbd5cfedc84');
/*!40000 ALTER TABLE `user_we_chat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wallet`
--

DROP TABLE IF EXISTS `wallet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wallet` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `balance` decimal(20,10) NOT NULL COMMENT '余额',
  `password` varchar(255) DEFAULT NULL COMMENT '钱包密码',
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户钱包';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wallet`
--

LOCK TABLES `wallet` WRITE;
/*!40000 ALTER TABLE `wallet` DISABLE KEYS */;
INSERT INTO `wallet` VALUES ('196d301c067f4b4e8ad0232448eaaf27','2024-06-05 21:48:11.865298','2024-06-05 21:48:11.865298','196d301c067f4b4e8ad0232448eaaf27','196d301c067f4b4e8ad0232448eaaf27',0.0000000000,'1234566','196d301c067f4b4e8ad0232448eaaf27'),('303aca117f5741e888bf2b38066f14a5','2024-06-05 22:03:46.848028','2024-06-05 22:03:46.848028','303aca117f5741e888bf2b38066f14a5','303aca117f5741e888bf2b38066f14a5',0.0000000000,'1234566','303aca117f5741e888bf2b38066f14a5'),('8c3dbf46b91e49cdae6390317f5de541','2024-06-05 22:08:11.118795','2024-06-05 22:08:11.118795','8c3dbf46b91e49cdae6390317f5de541','8c3dbf46b91e49cdae6390317f5de541',0.0000000000,'1234566','8c3dbf46b91e49cdae6390317f5de541'),('dcd256e2412f4162a6a5fcbd5cfedc84','2024-05-01 16:52:43.390763','2024-05-29 21:48:14.877569','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',6.2398900000,'1234566','dcd256e2412f4162a6a5fcbd5cfedc84'),('fe3d0d7d6eb34eb7a6b7985426cf8af7','2024-06-06 13:23:46.435394','2024-06-06 13:23:46.435363','fe3d0d7d6eb34eb7a6b7985426cf8af7','fe3d0d7d6eb34eb7a6b7985426cf8af7',0.0000000000,'1234566','fe3d0d7d6eb34eb7a6b7985426cf8af7');
/*!40000 ALTER TABLE `wallet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wallet_item`
--

DROP TABLE IF EXISTS `wallet_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wallet_item` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(32) NOT NULL,
  `editor_id` varchar(32) NOT NULL,
  `name` varchar(255) NOT NULL COMMENT '充值项名称',
  `amount` decimal(10,2) NOT NULL COMMENT '购买后得到的金额',
  `price` decimal(10,2) NOT NULL COMMENT '售卖价格',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='钱包充值的可选项';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wallet_item`
--

LOCK TABLES `wallet_item` WRITE;
/*!40000 ALTER TABLE `wallet_item` DISABLE KEYS */;
INSERT INTO `wallet_item` VALUES ('1bfa993661194c1584153b2b626e3bf0','2024-05-01 11:16:57.201881','2024-05-01 11:17:49.066982','60aaf6de424a4df2827380fdf5a23738','60aaf6de424a4df2827380fdf5a23738','冲5赠送5',10.00,5.00),('39fef2d04b694d7bb214221472da764a','2024-05-01 11:18:14.199111','2024-05-01 11:18:14.199111','60aaf6de424a4df2827380fdf5a23738','60aaf6de424a4df2827380fdf5a23738','冲50赠送50',100.00,50.00),('3a732bd72416412e9bd4aadb90e67072','2024-05-01 11:16:38.448530','2024-05-01 11:17:57.323986','60aaf6de424a4df2827380fdf5a23738','60aaf6de424a4df2827380fdf5a23738','冲10赠送10',20.00,10.00),('b1841350f9bc41eb891ece24518a9ad7','2024-05-01 11:17:34.230315','2024-05-01 11:17:34.230315','60aaf6de424a4df2827380fdf5a23738','60aaf6de424a4df2827380fdf5a23738','冲20增20',40.00,20.00),('e5bc8392a6eb4af597e99301085d7b64','2024-05-01 20:43:09.779941','2024-05-01 20:43:09.779941','60aaf6de424a4df2827380fdf5a23738','60aaf6de424a4df2827380fdf5a23738','冲0.01增1',1.00,0.01);
/*!40000 ALTER TABLE `wallet_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wallet_order`
--

DROP TABLE IF EXISTS `wallet_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wallet_order` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(32) NOT NULL,
  `editor_id` varchar(32) NOT NULL,
  `amount` decimal(10,2) NOT NULL COMMENT '支付金额',
  `pay_time` datetime(6) DEFAULT NULL COMMENT '支付时间',
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `wallet_item` json NOT NULL COMMENT '充值的钱包可选项详情',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='钱包充值订单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wallet_order`
--

LOCK TABLES `wallet_order` WRITE;
/*!40000 ALTER TABLE `wallet_order` DISABLE KEYS */;
INSERT INTO `wallet_order` VALUES ('0505c8820ea74118ad79616503a8c279','2024-05-22 22:44:28.692741','2024-05-22 22:44:28.692741','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',0.01,NULL,'dcd256e2412f4162a6a5fcbd5cfedc84','{\"id\": \"e5bc8392a6eb4af597e99301085d7b64\", \"name\": \"冲0.01增1\", \"price\": 0.01, \"amount\": 1.0, \"editedTime\": [2024, 5, 1, 20, 43, 9, 779941000], \"createdTime\": [2024, 5, 1, 20, 43, 9, 779941000]}'),('1ed5c93c4967486c8c5eecbf28fe2290','2024-05-01 20:49:40.883222','2024-05-01 20:49:40.883222','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',0.01,NULL,'dcd256e2412f4162a6a5fcbd5cfedc84','{\"id\": \"e5bc8392a6eb4af597e99301085d7b64\", \"name\": \"冲0.01增1\", \"price\": 0.01, \"amount\": 1.0, \"editedTime\": [2024, 5, 1, 20, 43, 9, 779941000], \"createdTime\": [2024, 5, 1, 20, 43, 9, 779941000]}'),('4d1d779788dc48f187cc11c7ca1d6b18','2024-05-01 21:19:20.905151','2024-05-01 21:19:20.905151','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',0.01,NULL,'dcd256e2412f4162a6a5fcbd5cfedc84','{\"id\": \"e5bc8392a6eb4af597e99301085d7b64\", \"name\": \"冲0.01增1\", \"price\": 0.01, \"amount\": 1.0, \"editedTime\": [2024, 5, 1, 20, 43, 9, 779941000], \"createdTime\": [2024, 5, 1, 20, 43, 9, 779941000]}'),('a535490e99a84a71a87d0475b2ab8aa1','2024-05-05 19:56:39.740441','2024-05-05 19:56:39.740441','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',0.01,NULL,'dcd256e2412f4162a6a5fcbd5cfedc84','{\"id\": \"e5bc8392a6eb4af597e99301085d7b64\", \"name\": \"冲0.01增1\", \"price\": 0.01, \"amount\": 1.0, \"editedTime\": [2024, 5, 1, 20, 43, 9, 779941000], \"createdTime\": [2024, 5, 1, 20, 43, 9, 779941000]}');
/*!40000 ALTER TABLE `wallet_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wallet_record`
--

DROP TABLE IF EXISTS `wallet_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wallet_record` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(32) NOT NULL,
  `editor_id` varchar(32) NOT NULL,
  `wallet_id` varchar(32) NOT NULL COMMENT '钱包id',
  `amount` decimal(20,10) NOT NULL COMMENT '金额',
  `type` varchar(32) NOT NULL COMMENT '类型如：提现，充值，奖励，返佣等等',
  `description` varchar(255) NOT NULL COMMENT '描述信息',
  `balance` decimal(20,10) NOT NULL COMMENT '钱包当前余额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='钱包流水记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wallet_record`
--

LOCK TABLES `wallet_record` WRITE;
/*!40000 ALTER TABLE `wallet_record` DISABLE KEYS */;
INSERT INTO `wallet_record` VALUES ('071f9eb709fc4427b93e7c2e99558063','2024-05-21 20:45:48.389894','2024-05-21 20:45:48.389894','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',-0.1500000000,'OCR','ocr识别',9.8586400000),('07ea6be5e03145c99a8a99e661867215','2024-05-19 20:28:29.234449','2024-05-19 20:28:29.234449','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',-0.0068200000,'CHAT','682',22.7745600000),('2886900c714b4f7a88d212b377f3c3a3','2024-05-19 09:38:28.924940','2024-05-19 09:38:28.924940','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',-0.0060700000,'CHAT','607',22.9878600000),('2fde441a28df4b1081ceae676f513a4a','2024-05-28 23:59:58.038101','2024-05-28 23:59:58.038101','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',0.0000000000,'CHAT','0',8.4834400000),('3238511f093e40dca59d42729022f83d','2024-05-29 21:05:02.319694','2024-05-29 21:05:02.319694','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',-0.0054000000,'CHAT','36',8.4397900000),('462baa420731407c9abdeac5bc3f7398','2024-05-29 21:39:52.283153','2024-05-29 21:39:52.283153','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',-0.0054000000,'CHAT','36',7.5366400000),('5b8ba9cb0edf445ab519b9deb0eeb247','2024-05-28 23:53:00.868779','2024-05-28 23:53:00.868779','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',0.0000000000,'CHAT','0',8.4834400000),('5cc8143e42ae401585f6030ffac0747b','2024-05-29 21:37:06.208838','2024-05-29 21:37:06.208838','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',-0.1647000000,'CHAT','1098',7.5420400000),('657d91f36d5542538bd44189e9814a25','2024-05-29 10:52:12.082058','2024-05-29 10:52:12.082058','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',-0.0211500000,'CHAT','141',8.4622900000),('6724a76595274ad294ec816ca9517ce0','2024-05-29 00:01:35.722740','2024-05-29 00:01:35.722740','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',0.0000000000,'CHAT','0',8.4834400000),('6f69483f7aff4cbe81b3a32578d3f6ec','2024-05-19 09:39:07.916225','2024-05-19 09:39:07.916225','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',-0.0064800000,'CHAT','648',22.9813800000),('73e7c7c4bd0d491bbbfebe6d5c4b96f3','2024-05-29 21:02:11.845178','2024-05-29 21:02:11.845178','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',-0.0054000000,'CHAT','36',8.4451900000),('7e2e2ce59d854528a79d13382c8cc958','2024-05-28 23:47:20.244844','2024-05-28 23:47:20.244844','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',-0.0106500000,'CHAT','71',9.4513900000),('7f4f5239d18c47e2810630073ad47160','2024-05-21 20:05:14.362805','2024-05-21 20:05:14.362805','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',-22.7500000000,'WITHDRAW','11',0.0086400000),('8a2650ee367640699697ae114279b5f0','2024-05-28 23:50:08.690997','2024-05-28 23:50:08.690997','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',0.0000000000,'CHAT','0',8.4834400000),('8c73443c32324917ac565a9f4245f55e','2024-05-19 09:40:05.931583','2024-05-19 09:40:05.931583','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',-0.2000000000,'OCR','5148a200dff5411eb2c6db51e57bdcba',22.7813800000),('9821e43e34204077bb15a0113cd000ac','2024-05-28 23:59:31.105302','2024-05-28 23:59:31.105302','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',0.0000000000,'CHAT','0',8.4834400000),('9b7354cb3a52488481d7ef0c1a285140','2024-05-28 23:51:42.471042','2024-05-28 23:51:42.471042','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',0.0000000000,'CHAT','0',8.4834400000),('a3604679ca53418481150d2e699023cb','2024-05-19 20:29:26.080365','2024-05-19 20:29:26.080365','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',-0.0071700000,'CHAT','717',22.7673900000),('a466d5e90a1944249e010ffa82b2cd02','2024-05-28 23:58:25.498176','2024-05-28 23:58:25.498176','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',0.0000000000,'CHAT','0',8.4834400000),('a4ff7d8d390149bbb175b7acb7f89483','2024-05-29 21:48:14.870567','2024-05-29 21:48:14.870567','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',-0.2884500000,'CHAT','1923',6.2398900000),('bb17e85fc60d4293bc522f0dc62bbfd4','2024-05-29 00:02:11.821623','2024-05-29 00:02:11.821623','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',0.0000000000,'CHAT','0',8.4834400000),('bc80387061964dd0a0a748270df6f73a','2024-05-29 21:00:45.756264','2024-05-29 21:00:45.756264','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',-0.0117000000,'CHAT','78',8.4505900000),('c38fb3f889184c02b3319e908bf721b3','2024-05-29 21:12:23.178737','2024-05-29 21:12:23.178737','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',-0.2835000000,'CHAT','1890',8.1454900000),('c46916ea26ab407a8b87c0e49530b02a','2024-05-28 23:55:36.336321','2024-05-28 23:55:36.336321','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',0.0000000000,'CHAT','0',8.4834400000),('c5b09f33800a4b03afe98fdccb3526e0','2024-05-01 16:53:11.783856','2024-05-01 16:53:11.783856','60aaf6de424a4df2827380fdf5a23738','60aaf6de424a4df2827380fdf5a23738','dcd256e2412f4162a6a5fcbd5cfedc84',20.0000000000,'RECHARGE','111',20.0000000000),('c62e7819504b4b46a6ffa390f8e74c7e','2024-05-19 09:34:03.184734','2024-05-19 09:34:03.184734','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',-0.0060700000,'CHAT','607',22.9939300000),('c920649459cc47308c9b0d6e511a1641','2024-05-29 21:40:35.285201','2024-05-29 21:40:35.285201','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',-0.4677000000,'CHAT','3118',7.0689400000),('c9dd26c2e30b43f4ba79f1545c63101e','2024-05-29 21:30:05.291259','2024-05-29 21:30:05.291259','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',-0.1648500000,'CHAT','1099',7.8633400000),('caf772b102d64d3eafc23b061f131330','2024-05-29 21:07:41.778838','2024-05-29 21:07:41.778838','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',-0.0054000000,'CHAT','36',8.4343900000),('ceb0b6528c4548208fb3c61a5a3efcd1','2024-05-28 23:58:58.238743','2024-05-28 23:58:58.238743','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',0.0000000000,'CHAT','0',8.4834400000),('d0edc3b7e32e4731969b0286faff09b6','2024-05-29 21:47:24.893464','2024-05-29 21:47:24.893464','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',-0.5266500000,'CHAT','3511',6.5422900000),('d9de1c465fe54cc3a2a47c2c5ca9f061','2024-05-19 20:39:25.986010','2024-05-19 20:39:25.986010','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',-0.0087500000,'CHAT','875',22.7586400000),('dcaa9f9a283c488885d040195ec6c0c2','2024-05-01 21:19:35.301937','2024-05-01 21:19:35.301937','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',1.0000000000,'RECHARGE','冲0.01增1',21.0000000000),('de85ebf7215343698d51094f8f1095c7','2024-05-29 21:31:10.521103','2024-05-29 21:31:10.521103','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',-0.1566000000,'CHAT','1044',7.7067400000),('e467052a6f214bf4a242be448baec5f2','2024-05-21 20:15:30.424503','2024-05-21 20:15:30.424503','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',10.0000000000,'RECHARGE','10',10.0086400000),('e57b44a5ebaa48f5ac274adde0824539','2024-05-29 21:13:45.882072','2024-05-29 21:13:45.882072','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',-0.1173000000,'CHAT','782',8.0281900000),('eb8e56bc4e884f98904acce8634f32ff','2024-05-28 23:48:05.736637','2024-05-28 23:48:05.736637','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',-0.9679500000,'CHAT','6453',8.4834400000),('ecc43e57603a4d1481524baa4913380a','2024-05-01 21:24:04.754410','2024-05-01 21:24:04.754410','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',1.0000000000,'RECHARGE','冲0.01增1',22.0000000000),('f00ed71ff246418f87101b3deb16f93b','2024-05-27 14:33:18.235280','2024-05-27 14:33:18.235280','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',-0.2659500000,'CHAT','1773',9.4620400000),('f3c751db70db40f18159ee5b488cbd1b','2024-05-29 21:11:36.557310','2024-05-29 21:11:36.557310','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',-0.0054000000,'CHAT','36',8.4289900000),('f71f6f28ca0642a5adb0381fa3cea3bc','2024-05-05 19:56:48.373060','2024-05-05 19:56:48.373060','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',1.0000000000,'RECHARGE','冲0.01增1',23.0000000000),('f7a0e91519ab485f9f9100fc41135947','2024-05-21 20:55:00.773823','2024-05-21 20:55:00.773823','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',-0.1306500000,'CHAT','871',9.7279900000),('fefa53f6bd3f46df9bc21b73582c636b','2024-05-29 21:47:58.091112','2024-05-29 21:47:58.091112','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',-0.0139500000,'CHAT','93',6.5283400000);
/*!40000 ALTER TABLE `wallet_record` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-12 14:01:05
