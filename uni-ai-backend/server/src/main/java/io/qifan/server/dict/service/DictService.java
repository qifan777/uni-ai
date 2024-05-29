package io.qifan.server.dict.service;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import freemarker.template.Configuration;
import freemarker.template.Template;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.dict.entity.Dict;
import io.qifan.server.dict.entity.dto.DictInput;
import io.qifan.server.dict.entity.dto.DictSpec;
import io.qifan.server.dict.model.DictGenContext;
import io.qifan.server.dict.repository.DictRepository;
import io.qifan.server.infrastructure.model.QueryRequest;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class DictService {

  private final DictRepository dictRepository;
  private final Configuration configuration;

  public Dict findById(String id) {
    return dictRepository.findById(id, DictRepository.COMPLEX_FETCHER)
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "数据不存在"));
  }

  public String save(DictInput dictInput) {
    return dictRepository.save(dictInput).id();
  }

  public Page<Dict> query(QueryRequest<DictSpec> queryRequest) {
    return dictRepository.findPage(queryRequest, DictRepository.COMPLEX_FETCHER);
  }

  public boolean delete(List<String> ids) {
    dictRepository.deleteAllById(ids);
    return true;
  }

  public DictGenContext getDictGenContext() {
    Converter<String, String> converter = CaseFormat.UPPER_UNDERSCORE.converterTo(
        CaseFormat.UPPER_CAMEL);
    List<Dict> all = dictRepository.findAll();
    Map<String, List<Dict>> dictMaps = new HashMap<>();
    all.forEach(dict -> {
      String dictEnName = converter.convert(dict.dictEnName());
      dictMaps.putIfAbsent(dictEnName, new ArrayList<>());
      List<Dict> dictList = dictMaps.get(dictEnName);
      dictList.add(dict);
    });
    return new DictGenContext(
        all.stream().map(dict -> converter.convert(dict.dictEnName())).distinct()
            .collect(Collectors.toList()),
        dictMaps);
  }

  @SneakyThrows
  public void generateJava() {
    DictGenContext dictGenContext = getDictGenContext();
    // 获取模板
    Template template = configuration.getTemplate("dict-java.ftl");
    // 创建输出文件
    File outputFile = new File(
        "uni-ai-backend/server/src/main/java/io/qifan/server/dict/model/DictConstants.java");
    outputFile.createNewFile();
    // 创建Writer对象
    Writer writer = new FileWriter(outputFile, false);
    // 渲染模板
    template.process(dictGenContext, writer);
    writer.flush();
    writer.close();
  }

  @SneakyThrows
  public String generateTS() {
    DictGenContext dictGenContext = getDictGenContext();
    // 获取模板
    Template template = configuration.getTemplate("dict-ts.ftl");
    // 创建Writer对象
    StringWriter stringWriter = new StringWriter();
    // 渲染模板
    template.process(dictGenContext, stringWriter);
    return stringWriter.getBuffer().toString();
  }
}