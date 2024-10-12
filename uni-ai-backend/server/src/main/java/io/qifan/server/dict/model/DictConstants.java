package io.qifan.server.dict.model;
import lombok.Getter;
import lombok.AllArgsConstructor;

public class DictConstants {
  public static final String MENU_TYPE = "MENU_TYPE";
  public static final String GENDER = "GENDER";
  public static final String WALLET_RECORD_TYPE = "WALLET_RECORD_TYPE";
  public static final String AI_MESSAGE_TYPE = "AI_MESSAGE_TYPE";
  public static final String USER_STATUS = "USER_STATUS";
  public static final String AI_FACTORY_TYPE = "AI_FACTORY_TYPE";
  public static final String AI_MODEL_TAG = "AI_MODEL_TAG";
  public static final String OSS_TYPE = "OSS_TYPE";
  @Getter
  @AllArgsConstructor
  public enum MenuType{
        BUTTON(2, "按钮", "BUTTON", 1002, "菜单类型", "MENU_TYPE", 2),
        PAGE(0, "页面", "PAGE", 1002, "菜单类型", "MENU_TYPE", 1),
        DIRECTORY(1, "目录", "DIRECTORY", 1002, "菜单类型", "MENU_TYPE", 0),
  ;
  final int keyId;
  final String keyName;
  final String keyEnName;
  final int dictId;
  final String dictName;
  final String dictEnName;
  final int orderNum;
  }
  @Getter
  @AllArgsConstructor
  public enum Gender{
        PRIVATE(2, "保密", "PRIVATE", 1001, "性别", "GENDER", 0),
        MALE(0, "男", "MALE", 1001, "性别", "GENDER", 0),
        FEMALE(1, "女", "FEMALE", 1001, "性别", "GENDER", 1),
  ;
  final int keyId;
  final String keyName;
  final String keyEnName;
  final int dictId;
  final String dictName;
  final String dictEnName;
  final int orderNum;
  }
  @Getter
  @AllArgsConstructor
  public enum WalletRecordType{
        RECHARGE(1, "充值", "RECHARGE", 1010, "钱包操作类型", "WALLET_RECORD_TYPE", 0),
        WITHDRAW(2, "提现", "WITHDRAW", 1010, "钱包操作类型", "WALLET_RECORD_TYPE", 0),
        OCR(3, "OCR", "OCR", 1010, "钱包操作类型", "WALLET_RECORD_TYPE", 0),
        CHAT(4, "聊天", "CHAT", 1010, "钱包操作类型", "WALLET_RECORD_TYPE", 0),
  ;
  final int keyId;
  final String keyName;
  final String keyEnName;
  final int dictId;
  final String dictName;
  final String dictEnName;
  final int orderNum;
  }
  @Getter
  @AllArgsConstructor
  public enum AiMessageType{
        ASSISTANT(1, "助手", "ASSISTANT", 1015, "AI消息类型", "AI_MESSAGE_TYPE", 0),
        USER(0, "用户", "USER", 1015, "AI消息类型", "AI_MESSAGE_TYPE", 0),
        SYSTEM(2, "系统", "SYSTEM", 1015, "AI消息类型", "AI_MESSAGE_TYPE", 0),
  ;
  final int keyId;
  final String keyName;
  final String keyEnName;
  final int dictId;
  final String dictName;
  final String dictEnName;
  final int orderNum;
  }
  @Getter
  @AllArgsConstructor
  public enum UserStatus{
        NORMAL(0, "正常", "NORMAL", 1003, "用户状态", "USER_STATUS", 0),
        BANNED(1, "封禁", "BANNED", 1003, "用户状态", "USER_STATUS", 0),
  ;
  final int keyId;
  final String keyName;
  final String keyEnName;
  final int dictId;
  final String dictName;
  final String dictEnName;
  final int orderNum;
  }
  @Getter
  @AllArgsConstructor
  public enum AiFactoryType{
        OPEN_AI(0, "OpenAi", "OPEN_AI", 1016, "AI厂家类型", "AI_FACTORY_TYPE", 0),
        DASH_SCOPE(1, "阿里灵积", "DASH_SCOPE", 1016, "AI厂家类型", "AI_FACTORY_TYPE", 0),
        QIAN_FAN(3, "百度千帆", "QIAN_FAN", 1016, "AI厂家类型", "AI_FACTORY_TYPE", 0),
        KIMI(4, "KIMI", "KIMI", 1016, "AI厂家类型", "AI_FACTORY_TYPE", 0),
        ZHI_PU(5, "智谱清言", "ZHI_PU", 1016, "AI厂家类型", "AI_FACTORY_TYPE", 0),
  ;
  final int keyId;
  final String keyName;
  final String keyEnName;
  final int dictId;
  final String dictName;
  final String dictEnName;
  final int orderNum;
  }
  @Getter
  @AllArgsConstructor
  public enum AiModelTag{
        AIGC(0, "文本生成", "AIGC", 1017, "AI模型标签", "AI_MODEL_TAG", 0),
        EMBEDDINGS(1, "文本嵌入", "EMBEDDINGS", 1017, "AI模型标签", "AI_MODEL_TAG", 0),
        AUDIO(2, "语言", "AUDIO", 1017, "AI模型标签", "AI_MODEL_TAG", 0),
        NLU(3, "nlu", "NLU", 1017, "AI模型标签", "AI_MODEL_TAG", 0),
        VISION(4, "图片理解", "VISION", 1017, "AI模型标签", "AI_MODEL_TAG", 0),
        IMAGE(5, "图片生成", "IMAGE", 1017, "AI模型标签", "AI_MODEL_TAG", 0),
  ;
  final int keyId;
  final String keyName;
  final String keyEnName;
  final int dictId;
  final String dictName;
  final String dictEnName;
  final int orderNum;
  }
  @Getter
  @AllArgsConstructor
  public enum OssType{
        ALI_YUN_OSS(0, "阿里云OSS", "ALI_YUN_OSS", 1018, "OSS类型", "OSS_TYPE", 0),
        TENCENT_OSS(1, "腾讯云OSS", "TENCENT_OSS", 1018, "OSS类型", "OSS_TYPE", 0),
        NGINX_OSS(2, "本地nginx", "NGINX_OSS", 1018, "OSS类型", "OSS_TYPE", 0),
  ;
  final int keyId;
  final String keyName;
  final String keyEnName;
  final int dictId;
  final String dictName;
  final String dictEnName;
  final int orderNum;
  }
}
