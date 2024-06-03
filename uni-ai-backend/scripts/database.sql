-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: uni_ai
-- ------------------------------------------------------
-- Server version	8.0.36

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
INSERT INTO `ai_collection` VALUES ('2bfc728b1d4e4582a7bb43285c1501ea','2024-05-31 09:47:45.974985','2024-05-31 09:47:45.974985','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','百度千帆知识库','qianfan','40c5497adee9451fbf705c85d9700aaa'),('6984123a99a8417f8cd7be8f39b339ae','2024-05-30 23:47:56.457289','2024-05-31 09:48:03.485217','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','阿里领积知识库','default','4d33d126e21041298c4f22c052de5a5f'),('6c689aaa8714402a8966271d682c24a3','2024-05-31 08:56:10.690445','2024-05-31 09:47:52.050193','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','OpenAi知识库','openai_test','323115205073473693aa1198340daa7c'),('9a953782ef54461a9431d6c9a9dd5d96','2024-05-31 23:38:32.675684','2024-05-31 23:38:32.675684','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','智谱清言知识库','zhipu','82706c5eb9e94668b331c0981f6aba5c');
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
  `url` varchar(255) NOT NULL COMMENT '文档链接',
  `ai_collection_id` varchar(36) NOT NULL COMMENT '知识库',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='知识库文档';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_document`
--

LOCK TABLES `ai_document` WRITE;
/*!40000 ALTER TABLE `ai_document` DISABLE KEYS */;
INSERT INTO `ai_document` VALUES ('1e2296b98cec42f1a00ad3f35b13f343','2024-06-02 22:32:10.324640','2024-06-02 22:32:10.324640','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','简历','https://my-community.oss-cn-qingdao.aliyuncs.com/20240602214400Resume.pdf','9a953782ef54461a9431d6c9a9dd5d96'),('8fb19bac476e461f8ac881540b2820b6','2024-06-03 10:03:34.787806','2024-06-03 10:03:34.787806','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','uni-ai','https://my-community.oss-cn-qingdao.aliyuncs.com/20240603100328README.md','9a953782ef54461a9431d6c9a9dd5d96');
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
INSERT INTO `ai_factory` VALUES ('1736ce830fc244af87a001f6a7a5d19c','2024-06-01 15:39:32.393714','2024-06-01 21:26:52.246327','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','QIAN_FAN',NULL,'{}'),('2db4ef18b31944719f80716667f73f3c','2024-06-01 15:38:32.128076','2024-06-01 21:23:42.127817','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','ZHI_PU',NULL,'{}'),('9c1c47209a5e49c5a34dd9d983e8dcc6','2024-06-01 15:39:01.268677','2024-06-01 21:27:02.724072','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','KIMI','','{}'),('a227db836ff3443ba4bc1a18864298e7','2024-06-01 15:39:48.065468','2024-06-01 21:26:37.648099','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','DASH_SCOPE',NULL,'{}'),('c90be53fc75748cb9b07bfd8fdfd900a','2024-06-01 15:41:43.215477','2024-06-01 21:23:06.853410','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','OPEN_AI',NULL,'{}'),('ea9eb6f9adb54e128a6e7d097e7ea826','2024-06-01 15:41:19.753474','2024-06-01 21:26:18.245957','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','SPARK','','{}');
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
INSERT INTO `ai_message` VALUES ('0760435423cc4bc1bd4746a989a35fc3','2024-06-03 15:51:01.404578','2024-06-03 15:51:01.404578','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','USER','[{\"text\":\"请你自我介绍一下\"}]','47a1962ce9744c8a8d88e0a4f64dc4fd'),('1f9cd7ad4ec64bfa8c89956e62b9ebdb','2024-06-03 15:33:29.628821','2024-06-03 15:33:29.628821','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','ASSISTANT','[{\"text\":\"你好！很高兴见到你。请问有什么我可以帮助您的吗？\"}]','38cca99c92694fbdaa7fff0ddf2406dc'),('21bf75097ba04f16a8107e7b9035d102','2024-06-03 10:19:02.663514','2024-06-03 10:19:02.663514','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','ASSISTANT','[{\"text\":\"冒泡排序是一种简单的排序算法，它重复地遍历要排序的数列，一次比较两个元素，如果它们的顺序错误就把它们交换过来。遍历数列的工作是重复地进行直到没有再需要交换，也就是说该数列已经排序完成。\\n\\n下面是一个使用Java编写的冒泡排序算法示例：\\n\\n```java\\npublic class BubbleSort {\\n    public static void main(String[] args) {\\n        int[] arr = {64, 34, 25, 12, 22, 11, 90};\\n        bubbleSort(arr);\\n        System.out.println(\\\"Sorted array:\\\");\\n        for (int value : arr) {\\n            System.out.print(value + \\\" \\\");\\n        }\\n    }\\n\\n    public static void bubbleSort(int[] arr) {\\n        int n = arr.length;\\n        for (int i = 0; i < n - 1; i++) {\\n            for (int j = 0; j < n - i - 1; j++) {\\n                if (arr[j] > arr[j + 1]) {\\n                    // 交换 arr[j] 和 arr[j + 1]\\n                    int temp = arr[j];\\n                    arr[j] = arr[j + 1];\\n                    arr[j + 1] = temp;\\n                }\\n            }\\n        }\\n    }\\n}\\n```\\n\\n这个程序首先定义了一个名为 `bubbleSort` 的方法，该方法接受一个整数数组作为参数。在这个方法中，我们使用嵌套循环来遍历数组并执行冒泡排序。外层循环负责控制排序的总轮数，内层循环负责在每一轮中进行相邻元素的比较和交换。\\n\\n在 `main` 方法中，我们创建了一个整数数组 `arr`，然后调用 `bubbleSort` 方法对其进行排序。排序完成后，我们使用一个增强的 for 循环遍历并打印排序后的数组。\"}]','38cca99c92694fbdaa7fff0ddf2406dc'),('2c75c51a065945469bf96600cefcdd39','2024-06-03 10:19:02.633272','2024-06-03 10:19:02.633272','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','USER','[{\"text\":\"java写一个冒泡排序\"}]','38cca99c92694fbdaa7fff0ddf2406dc'),('3d05b42394e6457b827da1302222138a','2024-06-03 10:12:15.814120','2024-06-03 10:12:15.814120','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','USER','[{\"text\":\"这张图片是什么？\\n\"},{\"image\":\"https://my-community.oss-cn-qingdao.aliyuncs.com/20240603101203微信图片_20240330115005.jpg\"}]','47a1962ce9744c8a8d88e0a4f64dc4fd'),('3deafe5ae9004edfa9d7aa00cbbcb0fe','2024-06-03 10:18:32.254664','2024-06-03 10:18:32.254664','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','ASSISTANT','[{\"text\":\"当然可以！我是一个人工智能助手，专门为了帮助人们找到他们需要的信息。我可以通过阅读和分析大量的文本数据来回答问题。我的目标是尽可能地提供准确、有用的信息。如果你有任何问题，随时可以问我，我会尽我所能帮助你。\"}]','38cca99c92694fbdaa7fff0ddf2406dc'),('40d65f5303de4a3ca89fe33a03730291','2024-06-03 10:05:10.414160','2024-06-03 10:05:10.414160','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','ASSISTANT','[{\"text\":\"uni-ai是一个AI聚合项目，它整合了多个AI厂家的文本对话、图片理解、图片生成、知识库（向量数据库）和预置角色等功能。项目的目标是提供一个统一的平台，方便用户接入和使用不同AI厂家的服务。\\n\\n项目的结构包括uni-ai-admin（管理端）、uni-ai-server（服务端）和generator-core（代码生成器）。管理端使用Vue3、ElementUI Plus等技术栈，服务端使用SpringBoot3、Jimmer等技术栈。项目还提供了详细的技术栈说明和项目运行步骤。\\n\\nuni-ai项目的特点包括：\\n1. 整合多个AI厂家的服务，提供统一的接口和体验。\\n2. 提供管理端和后端服务，方便用户进行管理和开发。\\n3. 使用现代前端和后端技术栈，提高开发效率和性能。\\n4. 提供详细的文档和示例，方便用户学习和使用。\\n\\n总的来说，uni-ai是一个综合性的AI聚合项目，旨在为用户提供一个方便、高效、统一的AI服务平台。\"}]','bd31f52927d747e5a87b015cd06f8082'),('6a4ba0168d054c07a2bd929c7ece83c6','2024-06-03 10:18:32.224416','2024-06-03 10:18:32.224416','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','USER','[{\"text\":\"请你自我介绍一下\\n\"}]','38cca99c92694fbdaa7fff0ddf2406dc'),('7ccaec6d4cf649fbbac1a6f01c0c75d3','2024-06-03 10:11:09.825780','2024-06-03 10:11:09.825780','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','ASSISTANT','[{\"text\":\"这是一张动漫风格的插画，描绘的是一个戴着护目镜和围巾的角色。他有着黑色的头发和大大的眼睛，看起来非常可爱和友好。角色似乎在微笑并向观众敬礼，给人一种积极、阳光的印象。背景是柔和的黄色渐变色，与角色的颜色搭配起来很和谐。整个画面给人一种温暖而愉快的感觉。\"}]','2da6b22626cd4300bd889838160e1efc'),('7efedc86624642ac8149f8c9aa8bcb66','2024-06-03 10:12:15.843445','2024-06-03 10:12:15.843445','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','ASSISTANT','[{\"text\":\"这张图片展示了一个动漫角色，他是日本动漫《火影忍者》中的主角漩涡鸣人。他穿着标志性的橙色连帽衫，戴着护目镜，脸上带着坚定的表情。\"}]','47a1962ce9744c8a8d88e0a4f64dc4fd'),('85436d5d7b864f639f653e2a9d166f2e','2024-06-03 10:11:09.786401','2024-06-03 10:11:09.786401','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','USER','[{\"text\":\"这张图片是什么？\"},{\"image\":\"https://my-community.oss-cn-qingdao.aliyuncs.com/20240603101057微信图片_20240330115005.jpg\"}]','2da6b22626cd4300bd889838160e1efc'),('8a09c88cc39340a99f58d66ebd3ef538','2024-06-03 15:51:01.505470','2024-06-03 15:51:01.505470','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','ASSISTANT','[{\"text\":\"大家好涡，我是漩鸣人，一个拥有独特力量和坚定的意志的年轻忍者。我在火影忍者村长大，这个村子以其强大的忍术和忍者而闻名。我出生时就被视为一个不祥的存在，因为我体内封印着九尾狐——一种强大的邪恶生物。然而，我并没有被这个标签所束缚，通过不断的努力和奋斗，我成为了一名出色的忍者和村庄的英雄。\"}]','47a1962ce9744c8a8d88e0a4f64dc4fd'),('d818f3414e3a488f813bde3f767b7baa','2024-06-03 15:33:29.525661','2024-06-03 15:33:29.525661','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','USER','[{\"text\":\"你好\"}]','38cca99c92694fbdaa7fff0ddf2406dc'),('f07881022ad948218aafef190963b581','2024-06-03 10:05:10.376116','2024-06-03 10:05:10.376116','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','USER','[{\"text\":\"uni-ai是什么？\"}]','bd31f52927d747e5a87b015cd06f8082');
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
  `ai_factory_id` varchar(32) NOT NULL COMMENT '厂家',
  `options` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_model`
--

LOCK TABLES `ai_model` WRITE;
/*!40000 ALTER TABLE `ai_model` DISABLE KEYS */;
INSERT INTO `ai_model` VALUES ('0c6810422b0a4c7eb08b8b7aec659a27','2024-05-28 20:21:06.532043','2024-05-28 23:23:18.230835','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','gpt-3.5-turbo','c90be53fc75748cb9b07bfd8fdfd900a','{\"model\": \"gpt-3.5-turbo\"}'),('1c1a46c482cc45a5b3ed25be7938c201','2024-05-28 20:16:12.595486','2024-05-28 23:23:33.641802','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','Spark3.5 Max','ea9eb6f9adb54e128a6e7d097e7ea826','{\"model\": \"Spark3.5 Max\", \"domain\": \"generalv3.5\", \"baseUrl\": \"https://spark-api.xf-yun.com/v3.5/chat\"}'),('323115205073473693aa1198340daa7c','2024-05-30 20:52:44.735580','2024-05-30 20:52:44.735580','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','text-embedding-3-small','c90be53fc75748cb9b07bfd8fdfd900a','{\"model\": \"text-embedding-3-small\"}'),('3e7e7dbec30f4dff86e63b4fdacc2734','2024-05-28 20:19:58.864966','2024-05-28 23:23:30.733401','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','gpt-4','c90be53fc75748cb9b07bfd8fdfd900a','{\"model\": \"gpt-4\"}'),('40c5497adee9451fbf705c85d9700aaa','2024-05-30 21:56:29.563513','2024-05-30 21:56:29.563513','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','bge-large-en','1736ce830fc244af87a001f6a7a5d19c','{\"model\": \"bge-large-en\"}'),('4230ef97b0b14f4e8c7ec3f5fc2f5fd9','2024-05-27 23:33:00.310000','2024-05-31 23:39:26.330231','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','glm-4','2db4ef18b31944719f80716667f73f3c','{\"model\": \"GLM-4\"}'),('4d33d126e21041298c4f22c052de5a5f','2024-05-28 00:05:44.546448','2024-05-30 21:56:49.312673','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','text-embedding-v2','a227db836ff3443ba4bc1a18864298e7','{\"model\": \"text-embedding-v1\"}'),('50c577b875c145ad9d5de20db431bb24','2024-05-27 23:37:12.639000','2024-05-31 23:39:20.443308','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','glm-4v','2db4ef18b31944719f80716667f73f3c','{\"model\": \"GLM-4V\"}'),('54602dd4c375478a9d1eb51ed1f1ea8c','2024-05-27 22:09:34.540000','2024-06-01 22:09:34.540929','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','glm-3-turbo','2db4ef18b31944719f80716667f73f3c','{\"model\": \"glm-3-turbo\"}'),('5fe9795163a948ac96b4986c7e4d5fff','2024-05-28 00:04:20.235905','2024-05-28 23:23:48.045932','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','qwen-turbo','a227db836ff3443ba4bc1a18864298e7','{\"model\": \"qwen-turbo\"}'),('634eeb7cd91d416c981360dbf54076d2','2024-05-31 10:27:41.400906','2024-05-31 10:27:41.400906','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','moonshot-v1-128k','9c1c47209a5e49c5a34dd9d983e8dcc6','{\"model\": \"moonshot-v1-128k\"}'),('82706c5eb9e94668b331c0981f6aba5c','2024-05-31 23:37:43.272220','2024-06-01 00:25:26.787218','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','embedding-2','2db4ef18b31944719f80716667f73f3c','{\"model\": \"Embedding-2\", \"dimension\": 1024}'),('973639abcfb34dbfb641bff7301655d3','2024-05-28 00:04:59.010448','2024-05-28 23:23:45.468529','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','qwen-vl-plus','a227db836ff3443ba4bc1a18864298e7','{\"model\": \"qwen-vl-plus\"}'),('9eea9fc391d5451eaef5c1dbc8a23f8a','2024-05-31 09:34:46.536082','2024-05-31 09:34:46.535083','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','qwen-plus','a227db836ff3443ba4bc1a18864298e7','{\"model\": \"qwen-plus\"}'),('b003f33cf90e4e72b926c0eb24d0cafb','2024-05-31 10:27:22.151984','2024-05-31 10:27:22.151984','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','moonshot-v1-32k','9c1c47209a5e49c5a34dd9d983e8dcc6','{\"model\": \"moonshot-v1-32k\"}'),('b4b5e915f79246c3bc8ecfb5a8d04e20','2024-05-29 10:49:01.142772','2024-05-29 10:49:01.142772','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','ERNIE-4.0-8K','1736ce830fc244af87a001f6a7a5d19c','{\"model\": \"ERNIE-4.0-8K\"}'),('b90979cd07cd4abb97634fbeac1f3233','2024-05-28 00:05:17.786545','2024-05-28 23:23:42.959310','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','qwen-vl-max','a227db836ff3443ba4bc1a18864298e7','{\"topP\": 0.01, \"model\": \"qwen-vl-max\", \"maxTokens\": 2048}'),('bd03bd177a864135a0c9523249d45887','2024-05-31 10:26:55.061303','2024-05-31 10:26:55.061303','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','moonshot-v1-8k','9c1c47209a5e49c5a34dd9d983e8dcc6','{\"model\": \"moonshot-v1-8k\"}'),('df228edebe8d4b79b3866a2dac36f60b','2024-05-29 10:50:30.644854','2024-05-29 10:50:30.644854','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','ERNIE-4.0-8K-0329','1736ce830fc244af87a001f6a7a5d19c','{\"model\": \"ERNIE-4.0-8K-0329\"}'),('ff751fcdac82451a91d7baad46fab88c','2024-05-28 20:20:08.924085','2024-05-28 23:23:28.198581','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','gpt-4-vision-preview','c90be53fc75748cb9b07bfd8fdfd900a','{\"model\": \"gpt-4-vision-preview\"}');
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
INSERT INTO `ai_model_tag_rel` VALUES ('09fef84b68b54e61a58d7915464bbd69','2024-05-28 23:23:28.256834','2024-05-28 23:23:28.256834','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','c50e7e2beb184ae6b95fb9a7c8d59ccc','ff751fcdac82451a91d7baad46fab88c'),('0a9a10e3c2cc4b29b08f266ae497f703','2024-05-30 21:56:49.331071','2024-05-30 21:56:49.331071','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','5934aaeeaf1942d19d80dbc5ccdf3d1f','4d33d126e21041298c4f22c052de5a5f'),('19342c822c234f67921af6331c33a7ae','2024-05-28 23:23:42.978176','2024-05-28 23:23:42.978176','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','069cf7c3483d462fb2dfdcc0abec81f7','b90979cd07cd4abb97634fbeac1f3233'),('19f1fda880504ad7a116e383c2c06a6a','2024-05-30 21:56:29.593598','2024-05-30 21:56:29.593598','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','7c566ab76ac94bf798c83fa8c62f5418','40c5497adee9451fbf705c85d9700aaa'),('1dbfb779a4e94b4c9fa416283df5f2fc','2024-06-01 00:25:26.795486','2024-06-01 00:25:26.795486','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','07cd4919d75942bbb4480ee18dd4341e','82706c5eb9e94668b331c0981f6aba5c'),('31feb239d22e4f0a82ad2001826f37a3','2024-05-31 09:34:46.571111','2024-05-31 09:34:46.571111','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','069cf7c3483d462fb2dfdcc0abec81f7','9eea9fc391d5451eaef5c1dbc8a23f8a'),('49f1faf1787c467681855c9c87888c27','2024-05-31 10:27:41.418934','2024-05-31 10:27:41.418934','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','5283d6f3c9524ccb8c19ec61a27db1a8','634eeb7cd91d416c981360dbf54076d2'),('4b68a1f8b98844cba7365684a82d80da','2024-05-28 23:23:18.251560','2024-05-28 23:23:18.251560','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','4345032f8198403ea2bfc061e983668e','0c6810422b0a4c7eb08b8b7aec659a27'),('4b8d4e8c37d1449eb3f8110fe154530c','2024-05-29 10:50:30.690443','2024-05-29 10:50:30.690443','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','bffcdd2ae7884319acf719cac439ec0f','df228edebe8d4b79b3866a2dac36f60b'),('513299ee9ce84c8396252de7331a46a4','2024-05-31 23:39:20.450631','2024-05-31 23:39:20.450631','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','07bf2608ef77496eb37426c59fc1e686','50c577b875c145ad9d5de20db431bb24'),('6d0e07555cb2447d86d4dea7ca3992b8','2024-05-28 23:23:28.249536','2024-05-28 23:23:28.249536','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','4345032f8198403ea2bfc061e983668e','ff751fcdac82451a91d7baad46fab88c'),('6ed0d77c6ffe421bbba83a1fad1d5026','2024-05-28 23:23:48.061640','2024-05-28 23:23:48.061640','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','069cf7c3483d462fb2dfdcc0abec81f7','5fe9795163a948ac96b4986c7e4d5fff'),('738d07fa6954426082c9f383c5bfb886','2024-05-31 23:39:26.337744','2024-05-31 23:39:26.337744','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','07bf2608ef77496eb37426c59fc1e686','4230ef97b0b14f4e8c7ec3f5fc2f5fd9'),('814025b712d043359448375d253eddae','2024-05-28 23:23:33.656042','2024-05-28 23:23:33.656042','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','8c4bad02c25a4bdba76cead6fe4c8c17','1c1a46c482cc45a5b3ed25be7938c201'),('96bb3c798f6843b58f1bfb2f88c4adb4','2024-05-30 20:52:44.751103','2024-05-30 20:52:44.751103','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','7214c324a160419fb2f2af84ebc501b0','323115205073473693aa1198340daa7c'),('9e13ad0073c744acbd034983eeb4856f','2024-05-28 23:23:30.748003','2024-05-28 23:23:30.748003','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','4345032f8198403ea2bfc061e983668e','3e7e7dbec30f4dff86e63b4fdacc2734'),('a234cfe1a06e4d49b489273dace867c1','2024-05-28 23:23:42.987665','2024-05-28 23:23:42.987665','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','be2693e676524f45b011c22b694b9360','b90979cd07cd4abb97634fbeac1f3233'),('a4cb08a0f6894e1984609962ef021611','2024-06-01 22:09:34.557034','2024-06-01 22:09:34.557034','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','07bf2608ef77496eb37426c59fc1e686','54602dd4c375478a9d1eb51ed1f1ea8c'),('ae3aba320f814aaea3cc594a5e8584a4','2024-05-31 23:39:20.454630','2024-05-31 23:39:20.454630','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','8c09f0af95c244e68fddec1c3b4be8a9','50c577b875c145ad9d5de20db431bb24'),('beddbee143c14ae09012ae12228a356b','2024-05-28 23:23:45.492778','2024-05-28 23:23:45.492778','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','be2693e676524f45b011c22b694b9360','973639abcfb34dbfb641bff7301655d3'),('cc6134c6d19d43fb8d0aff9074e86f77','2024-05-31 10:27:22.161873','2024-05-31 10:27:22.161873','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','5283d6f3c9524ccb8c19ec61a27db1a8','b003f33cf90e4e72b926c0eb24d0cafb'),('d9ccc596f8b5433c8cda77e7c32847d7','2024-05-31 10:26:55.081312','2024-05-31 10:26:55.081312','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','5283d6f3c9524ccb8c19ec61a27db1a8','bd03bd177a864135a0c9523249d45887'),('e4f950044b3b4483b7b98385e77cd29b','2024-05-29 10:49:01.199438','2024-05-29 10:49:01.199438','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','bffcdd2ae7884319acf719cac439ec0f','b4b5e915f79246c3bc8ecfb5a8d04e20'),('fc09c9b8c6d147809f5c47c8c8e90086','2024-05-28 23:23:45.482188','2024-05-28 23:23:45.482188','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','069cf7c3483d462fb2dfdcc0abec81f7','973639abcfb34dbfb641bff7301655d3');
/*!40000 ALTER TABLE `ai_model_tag_rel` ENABLE KEYS */;
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
  `ai_role_id` varchar(32) DEFAULT NULL COMMENT '角色id',
  `ai_model_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ai_session_ai_role_id_fk` (`ai_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_session`
--

LOCK TABLES `ai_session` WRITE;
/*!40000 ALTER TABLE `ai_session` DISABLE KEYS */;
INSERT INTO `ai_session` VALUES ('2da6b22626cd4300bd889838160e1efc','2024-06-03 10:10:17.203638','2024-06-03 10:10:17.203638','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','千问VL','','b90979cd07cd4abb97634fbeac1f3233'),('38cca99c92694fbdaa7fff0ddf2406dc','2024-06-03 10:18:21.562926','2024-06-03 10:18:21.562926','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','kimi','','634eeb7cd91d416c981360dbf54076d2'),('47a1962ce9744c8a8d88e0a4f64dc4fd','2024-06-03 10:11:58.729043','2024-06-03 10:11:58.729043','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','glm-4v','','50c577b875c145ad9d5de20db431bb24'),('bd31f52927d747e5a87b015cd06f8082','2024-06-03 10:04:51.785991','2024-06-03 10:04:51.785991','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','测试会话','','634eeb7cd91d416c981360dbf54076d2');
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
  `ai_factory_id` varchar(32) NOT NULL COMMENT '厂家',
  `service` varchar(32) NOT NULL COMMENT 'UniAiService',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='spring ai model标签';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_tag`
--

LOCK TABLES `ai_tag` WRITE;
/*!40000 ALTER TABLE `ai_tag` DISABLE KEYS */;
INSERT INTO `ai_tag` VALUES ('069cf7c3483d462fb2dfdcc0abec81f7','2024-05-27 23:43:02.500927','2024-05-28 23:25:39.139009','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','AIGC','a227db836ff3443ba4bc1a18864298e7','DashScopeAiChatService'),('07bf2608ef77496eb37426c59fc1e686','2024-05-31 23:26:04.446401','2024-05-31 23:26:04.446401','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','AIGC','2db4ef18b31944719f80716667f73f3c','ZhiPuAiChatService'),('07cd4919d75942bbb4480ee18dd4341e','2024-05-31 23:26:47.404189','2024-05-31 23:27:04.368923','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','EMBEDDINGS','2db4ef18b31944719f80716667f73f3c','ZhiPuAiEmbeddingService'),('4345032f8198403ea2bfc061e983668e','2024-05-27 23:44:19.471442','2024-05-28 23:25:15.650626','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','AIGC','c90be53fc75748cb9b07bfd8fdfd900a','OpenAiChatService'),('5283d6f3c9524ccb8c19ec61a27db1a8','2024-05-31 10:26:21.006136','2024-05-31 10:26:21.006136','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','AIGC','9c1c47209a5e49c5a34dd9d983e8dcc6','KimiAiChatService'),('5934aaeeaf1942d19d80dbc5ccdf3d1f','2024-05-27 23:43:34.461077','2024-05-30 16:18:40.134320','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','EMBEDDINGS','a227db836ff3443ba4bc1a18864298e7','DashScopeAiEmbeddingService'),('7214c324a160419fb2f2af84ebc501b0','2024-05-27 23:44:43.470784','2024-05-30 20:48:22.342354','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','EMBEDDINGS','c90be53fc75748cb9b07bfd8fdfd900a','OpenAiEmbeddingService'),('7c566ab76ac94bf798c83fa8c62f5418','2024-05-30 21:55:56.015829','2024-05-30 21:55:56.015829','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','EMBEDDINGS','1736ce830fc244af87a001f6a7a5d19c','QifanAiEmbeddingService'),('8c09f0af95c244e68fddec1c3b4be8a9','2024-05-31 23:26:14.366645','2024-05-31 23:26:14.366645','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','VISION','2db4ef18b31944719f80716667f73f3c','ZhiPuAiChatService'),('8c4bad02c25a4bdba76cead6fe4c8c17','2024-05-27 23:43:55.626986','2024-05-28 23:25:27.947963','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','AIGC','ea9eb6f9adb54e128a6e7d097e7ea826','SparkAiChatService'),('be2693e676524f45b011c22b694b9360','2024-05-27 23:42:45.669197','2024-05-28 23:25:49.606526','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','VISION','a227db836ff3443ba4bc1a18864298e7','DashScopeAiVLChatService'),('bffcdd2ae7884319acf719cac439ec0f','2024-05-29 10:48:38.658448','2024-05-29 10:48:38.658448','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','AIGC','1736ce830fc244af87a001f6a7a5d19c','QianFanAiChatService'),('c50e7e2beb184ae6b95fb9a7c8d59ccc','2024-05-27 23:45:07.621619','2024-05-28 23:24:50.600348','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','VISION','c90be53fc75748cb9b07bfd8fdfd900a','OpenAiChatService');
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
INSERT INTO `dict` VALUES ('1f01fa7b-f162-4376-870d-9207735f658d','2024-01-16 09:33:09.151337','2024-01-16 09:33:09.151337','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',2,'BUTTON','按钮',1002,'菜单类型','MENU_TYPE',2,1),('3860dff4-7f22-4ded-bc30-19cd1b4bc098','2024-01-16 09:30:39.144272','2024-01-16 09:33:15.663135','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',0,'PAGE','页面',1002,'菜单类型','MENU_TYPE',1,1),('416c90b4-42e8-4af1-a3f5-7e321c9c3437','2024-01-16 09:32:28.555205','2024-01-16 09:32:28.555205','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',1,'DIRECTORY','目录',1002,'菜单类型','MENU_TYPE',0,1),('5fba34ff-760c-453a-9ce3-284ed68710ca','2024-01-14 15:23:55.954376','2024-01-14 15:23:55.954376','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',2,'PRIVATE','保密',1001,'性别','GENDER',0,1),('a3caf40e-a1ef-4a77-8096-f467fb14060e','2024-01-10 10:50:18.555224','2024-01-11 15:49:22.959501','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',0,'MALE','男',1001,'性别','GENDER',0,1),('b3366061-0be1-45f9-98de-5a86753665ce','2024-01-10 13:55:26.468101','2024-01-10 13:55:26.468101','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',1,'FEMALE','女',1001,'性别','GENDER',1,1),('f0e0c95e7ad249deb9359691d109fd7h','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',1,'RECHARGE','充值',1010,'钱包操作类型','WALLET_RECORD_TYPE',0,1),('f0e0c95e7ad249deb9359691d109zd7h','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',2,'WITHDRAW','提现',1010,'钱包操作类型','WALLET_RECORD_TYPE',0,1),('f0e0c9ze7ad249deb9359691d109bd8a','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',1,'ASSISTANT','助手',1015,'AI消息类型','AI_MESSAGE_TYPE',0,1),('f0e0c9ze7ad249deb9359691d109bd8z','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',0,'USER','用户',1015,'AI消息类型','AI_MESSAGE_TYPE',0,1),('fc930d38-0612-4207-91ab-809a5be03656','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',2,'SYSTEM','系统',1015,'AI消息类型','AI_MESSAGE_TYPE',0,1),('f0e0c95e2ad249deb9359691d109zd7h','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',3,'OCR','OCR',1010,'钱包操作类型','WALLET_RECORD_TYPE',0,1),('f0e0c95e3ad249deb9359691d109zd7h','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',4,'CHAT','聊天',1010,'钱包操作类型','WALLET_RECORD_TYPE',0,1),('416c90b442e84af1a4f57e321c9c3437','2024-01-16 09:32:28.555205','2024-01-16 09:32:28.555205','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',0,'NORMAL','正常',1003,'用户状态','USER_STATUS',0,1),('416c90b442e84af1a5f57e321c9c3437','2024-01-16 09:32:28.555205','2024-01-16 09:32:28.555205','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',1,'BANNED','封禁',1003,'用户状态','USER_STATUS',0,1),('fc930d38-0612-4217-91ab-809a5be03656','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',0,'OPEN_AI','OpenAi',1016,'AI厂家类型','AI_FACTORY_TYPE',0,1),('fc930d38-0612-4227-91ab-809a5be03656','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',1,'DASH_SCOPE','阿里灵积',1016,'AI厂家类型','AI_FACTORY_TYPE',0,1),('fc930d38-0612-4237-91ab-809a5be03656','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',2,'SPARK','讯飞星火',1016,'AI厂家类型','AI_FACTORY_TYPE',0,1),('fc930d38-1612-4237-91ab-809a5be03656','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',0,'AIGC','aigc',1017,'AI模型标签','AI_MODEL_TAG',0,1),('fc930d38-2612-4237-91ab-809a5be03656','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',1,'EMBEDDINGS','embeddings',1017,'AI模型标签','AI_MODEL_TAG',0,1),('fc930d38-3612-4237-91ab-809a5be03656','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',2,'AUDIO','audio',1017,'AI模型标签','AI_MODEL_TAG',0,1),('fc930d38-4612-4237-91ab-809a5be03656','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',3,'NLU','nlu',1017,'AI模型标签','AI_MODEL_TAG',0,1),('fc930d38-5612-4237-91ab-809a5be03656','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',4,'VISION','vision',1017,'AI模型标签','AI_MODEL_TAG',0,1),('fc930d38-0612-4237-91ab-809a5be03651','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',3,'QIAN_FAN','百度千帆',1016,'AI厂家类型','AI_FACTORY_TYPE',0,1),('fc930d38-0612-4237-91ab-809a5be03652','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',4,'KIMI','KIMI',1016,'AI厂家类型','AI_FACTORY_TYPE',0,1),('fc930d38-0612-4237-91ab-809a5be03653','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',5,'ZHI_PU','智谱清言',1016,'AI厂家类型','AI_FACTORY_TYPE',0,1);
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
INSERT INTO `menu` VALUES ('00f2290acde54e9592944e7599b1a3f5','2024-01-16 11:21:02.655367','2024-06-03 16:51:45.072224','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','权限管理','/permission',NULL,0,'DIRECTORY','Lock',0),('086673ec52914a68960f0649b78d2820','2024-05-19 09:19:04.863236','2024-05-19 09:19:04.863236','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','价格配置','/setting','4f9449e8d17e4582a420041f0651a56c',2,'PAGE',NULL,1),('0c30e2f68cef4ee6999ea18af97a3a26','2024-01-16 14:33:09.062999','2024-01-17 10:10:02.922860','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','用户','/user','00f2290acde54e9592944e7599b1a3f5',1,'PAGE','User',1),('139e0f68e01a4583b6da8afa883af1dd','2024-05-05 10:24:28.466054','2024-06-03 16:50:55.866276','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','会话记录','/ai-session','7704a6b711c3467192c528dcb7d53349',2,'PAGE',NULL,0),('19c78fe7f1c44b048ef7b2b1b865d784','2024-05-19 11:51:25.063900','2024-05-19 11:51:25.063900','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','流水统计','/wallet-record-stats','4f9449e8d17e4582a420041f0651a56c',4,'PAGE',NULL,1),('2d4cac3af737486999948f5774bf94e3','2024-05-05 10:21:46.714189','2024-06-03 16:52:22.627943','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','角色','/ai-role','78f7fed192b547dd82c62ebfac00a4fa',4,'PAGE',NULL,1),('349b39a595004c229972f22b935aba42','2024-01-17 10:12:54.930459','2024-01-17 10:13:12.046479','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','角色','/role','00f2290acde54e9592944e7599b1a3f5',2,'PAGE','Avatar',1),('3e4a455bd4dc4410989e06d682a94d4a','2024-05-30 15:17:45.508150','2024-05-30 23:46:51.958684','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','文档管理','/ai-document','7704a6b711c3467192c528dcb7d53349',6,'PAGE',NULL,1),('46aeab05d6ce420ab4a7bb5b2ab551e9','2024-05-01 11:09:45.265557','2024-05-01 11:09:45.265557','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','钱包订单','/wallet-order','4f9449e8d17e4582a420041f0651a56c',4,'PAGE',NULL,1),('4f9449e8d17e4582a420041f0651a56c','2024-04-17 13:55:33.679396','2024-06-03 16:40:27.576844','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','钱包管理','wallet-manage',NULL,8,'DIRECTORY',NULL,0),('6152773c1ddc4938853b5c6b51f07d0e','2024-04-17 13:55:50.298812','2024-04-17 13:55:50.298812','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','用户钱包','/wallet','4f9449e8d17e4582a420041f0651a56c',1,'PAGE',NULL,1),('7704a6b711c3467192c528dcb7d53349','2024-04-19 11:01:33.094780','2024-06-03 16:39:43.380988','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','AI助手','/ai',NULL,1,'DIRECTORY','ChatDotRound',1),('78f7fed192b547dd82c62ebfac00a4fa','2024-06-01 15:27:07.343645','2024-06-03 16:40:17.579155','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','AI配置','ai-config',NULL,2,'DIRECTORY','Setting',1),('8b7db6952a884741924e7b209ad06a14','2024-04-19 11:01:59.361801','2024-04-19 11:01:59.361801','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','聊天面板','/chat','7704a6b711c3467192c528dcb7d53349',NULL,'PAGE',NULL,1),('8ea32b3d90e44d76a777413917166a32','2024-01-16 11:20:24.133270','2024-01-17 10:13:01.821007','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','菜单','/menu','00f2290acde54e9592944e7599b1a3f5',3,'PAGE','Menu',1),('a350090e11ff45d088ce5c3c1b6f9ef4','2024-01-26 10:06:09.672544','2024-06-03 16:51:34.559012','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','系统管理','/system',NULL,0,'DIRECTORY','House',0),('a8bedf2759bb4259b685c79578bae01d','2024-05-27 20:15:14.637091','2024-06-01 15:28:08.405792','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','模型','/ai-model','78f7fed192b547dd82c62ebfac00a4fa',2,'PAGE',NULL,1),('a8dc7ef7f3394580ba913fa4f0ac05f8','2024-05-27 23:40:46.635762','2024-06-01 15:27:57.542618','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','模型标签','/ai-tag','78f7fed192b547dd82c62ebfac00a4fa',1,'PAGE',NULL,1),('bf1774f2e06940e08cc2ffe93adfbbce','2024-06-01 15:27:42.614228','2024-06-01 21:04:54.663607','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','厂家','/ai-factory','78f7fed192b547dd82c62ebfac00a4fa',0,'PAGE',NULL,1),('caac5913aa734079a0e486060148feac','2024-05-01 11:09:17.227571','2024-05-01 11:09:17.227571','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','钱包项','/wallet-item','4f9449e8d17e4582a420041f0651a56c',3,'PAGE',NULL,1),('d097dbf9a75d4c1691b21834257d405c','2024-05-05 10:22:13.569197','2024-06-03 16:51:01.010069','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','消息记录','/ai-message','7704a6b711c3467192c528dcb7d53349',1,'PAGE',NULL,0),('ebb8a75b9ef94b969ac750180a74611b','2024-05-30 23:47:14.859517','2024-05-30 23:47:14.859517','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','知识库','/ai-collection','7704a6b711c3467192c528dcb7d53349',7,'PAGE',NULL,1),('fcf86780d4474a6096b84f03c4eddc92','2024-01-26 10:07:04.713450','2024-01-26 10:07:04.713450','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','字典','/dict','a350090e11ff45d088ce5c3c1b6f9ef4',0,'PAGE','Notebook',1),('ffb68dcb420540a4bd7d0a01fc40f43e','2024-04-17 13:56:11.627444','2024-04-17 13:56:11.627444','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','钱包流水','/wallet-record','4f9449e8d17e4582a420041f0651a56c',2,'PAGE',NULL,1);
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
INSERT INTO `role` VALUES ('34c09a5b67904e31ba3be5fd8101089a','2024-04-27 17:52:37.583669','2024-06-01 15:28:38.344005','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','管理员');
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
INSERT INTO `role_menu_rel` VALUES ('0d516a3fa9d14fb38ef3a6a96e98c29c','2024-04-27 17:52:37.621118','2024-04-27 17:52:37.621118','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','fcf86780d4474a6096b84f03c4eddc92'),('0f2d6719697c470897c18fb64f2a80bd','2024-05-30 15:17:53.282990','2024-05-30 15:17:53.282990','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','3e4a455bd4dc4410989e06d682a94d4a'),('1027660575d14ab09bbb60d0123e4c38','2024-04-27 17:52:37.624627','2024-04-27 17:52:37.624627','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','00f2290acde54e9592944e7599b1a3f5'),('1ddb7a2fceb347a084e220377cf1c2ee','2024-04-27 17:52:37.637196','2024-04-27 17:52:37.637196','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','8ea32b3d90e44d76a777413917166a32'),('23447dae30e742b28875ad57da5892c1','2024-05-19 09:19:16.606373','2024-05-19 09:19:16.606373','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','086673ec52914a68960f0649b78d2820'),('4f10b3ad529649e9991ed66c6427819b','2024-04-27 17:52:37.605605','2024-04-27 17:52:37.605605','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','a350090e11ff45d088ce5c3c1b6f9ef4'),('5b451a2b2360401e9760cb430dbd11c4','2024-05-30 23:47:33.552357','2024-05-30 23:47:33.552357','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','ebb8a75b9ef94b969ac750180a74611b'),('5b71563cea97476f8d7ab53af66d5f68','2024-05-01 11:09:59.500573','2024-05-01 11:09:59.500573','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','caac5913aa734079a0e486060148feac'),('6c3a47e0050241eba7b1a011fbc23cb9','2024-04-27 17:52:37.633191','2024-04-27 17:52:37.633191','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','349b39a595004c229972f22b935aba42'),('6e923cd7d4e84c7e8e564f51ec169e3c','2024-05-05 10:31:35.334952','2024-05-05 10:31:35.334952','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','d097dbf9a75d4c1691b21834257d405c'),('76e0d6d0725e4eb4ba0e42e2d99ff2bc','2024-04-27 17:52:37.601600','2024-04-27 17:52:37.601600','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','ffb68dcb420540a4bd7d0a01fc40f43e'),('784b1732ff2a4498aaa81245cc485c5c','2024-05-27 23:41:06.263246','2024-05-27 23:41:06.263246','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','a8dc7ef7f3394580ba913fa4f0ac05f8'),('83980cab4a7447fb80d6bbd4563999d7','2024-04-27 17:52:37.592700','2024-04-27 17:52:37.592700','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','4f9449e8d17e4582a420041f0651a56c'),('8e77baa546104cd7ac8b63bdc8243f3b','2024-05-19 11:51:41.227364','2024-05-19 11:51:41.227364','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','19c78fe7f1c44b048ef7b2b1b865d784'),('91db25face41433fb60fa71581a1526c','2024-06-01 15:28:38.369567','2024-06-01 15:28:38.369567','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','bf1774f2e06940e08cc2ffe93adfbbce'),('94fe52adc3b64ac690875a995bd813e8','2024-05-25 22:30:08.690001','2024-05-25 22:30:08.690001','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','8b7db6952a884741924e7b209ad06a14'),('9a2931318e6b4bb286af1bdba661fb62','2024-06-01 15:28:38.362560','2024-06-01 15:28:38.362560','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','78f7fed192b547dd82c62ebfac00a4fa'),('9dd2ab5ea69844949513fdff97b47929','2024-04-27 17:52:37.596699','2024-04-27 17:52:37.596699','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','6152773c1ddc4938853b5c6b51f07d0e'),('a504aaa67cb8408b98076d158a0c6864','2024-05-27 20:15:23.415214','2024-05-27 20:15:23.415214','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','a8bedf2759bb4259b685c79578bae01d'),('a8c4669a831e45798aba87c16b3c6bfb','2024-05-05 10:33:08.545920','2024-05-05 10:33:08.545920','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','7704a6b711c3467192c528dcb7d53349'),('c8856a8feb9344f4a75747b38863a169','2024-04-27 17:52:37.628631','2024-04-27 17:52:37.628631','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','0c30e2f68cef4ee6999ea18af97a3a26'),('c99873d4f2774357b0f69696d8dc0798','2024-05-05 10:31:35.346387','2024-05-05 10:31:35.346387','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','2d4cac3af737486999948f5774bf94e3'),('d471c87a608e4af4a721cf69b4b70a03','2024-05-01 11:09:59.505698','2024-05-01 11:09:59.505698','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','46aeab05d6ce420ab4a7bb5b2ab551e9'),('e6f70d4e87ef4b3d9d8b58bb5cd8f0ac','2024-05-05 10:31:35.340396','2024-05-05 10:31:35.340396','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84','34c09a5b67904e31ba3be5fd8101089a','139e0f68e01a4583b6da8afa883af1dd');
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
INSERT INTO `user` VALUES ('dcd256e2412f4162a6a5fcbd5cfedc84','2024-05-01 16:52:43.364225','2024-05-19 21:30:34.686818','起凡','https://my-community.oss-cn-qingdao.aliyuncs.com/20240501203628ptwondCGhItP67eb5ac72554b07800b22c542245e457.jpeg','MALE','11111111111','$2a$10$o/DHIt/eAMR175TgDV/PeeuEOpqW1N4Klft6obvs2zqBuiwMgLWOW','BANNED');
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
INSERT INTO `user_role_rel` VALUES ('100af7a7164a4bd0ab80f772c94f7d48','2024-05-01 20:42:43.871421','2024-05-01 20:42:43.871421','60aaf6de424a4df2827380fdf5a23738','60aaf6de424a4df2827380fdf5a23738','34c09a5b67904e31ba3be5fd8101089a','dcd256e2412f4162a6a5fcbd5cfedc84'),('34c09a5b67904e31ba3be5fd8101089a','2024-04-27 17:54:28.000000','2024-04-27 17:54:31.000000','60aaf6de424a4df2827380fdf5a23738','60aaf6de424a4df2827380fdf5a23738','34c09a5b67904e31ba3be5fd8101089a','60aaf6de424a4df2827380fdf5a23738');
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
INSERT INTO `wallet` VALUES ('dcd256e2412f4162a6a5fcbd5cfedc84','2024-05-01 16:52:43.390763','2024-05-29 21:48:14.877569','dcd256e2412f4162a6a5fcbd5cfedc84','dcd256e2412f4162a6a5fcbd5cfedc84',6.2398900000,'1234566','dcd256e2412f4162a6a5fcbd5cfedc84');
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

-- Dump completed on 2024-06-03 17:05:07
