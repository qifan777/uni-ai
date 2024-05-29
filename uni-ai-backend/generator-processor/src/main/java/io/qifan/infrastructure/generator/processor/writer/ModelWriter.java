package io.qifan.infrastructure.generator.processor.writer;

import freemarker.cache.StrongCacheStorage;
import freemarker.cache.TemplateLoader;
import freemarker.log.Logger;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import io.qifan.infrastructure.generator.processor.model.common.FileModel;
import io.qifan.infrastructure.generator.processor.model.common.Type;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;

public class ModelWriter {
    private static final Configuration CONFIGURATION;

    static {
        try {
            Logger.selectLoggerLibrary(Logger.LIBRARY_NONE);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        CONFIGURATION = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        CONFIGURATION.setTemplateLoader(new SimpleClasspathLoader());
        CONFIGURATION.setObjectWrapper(new DefaultObjectWrapper(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));
        CONFIGURATION.setSharedVariable("includeModel", new ModelIncludeDirective(CONFIGURATION));
        // do not refresh/gc the cached templates, as we never change them at runtime
        CONFIGURATION.setCacheStorage(new StrongCacheStorage());
        CONFIGURATION.setTemplateUpdateDelay(Integer.MAX_VALUE);
        CONFIGURATION.setLocalizedLookup(false);
    }

    private final String outputPath;

    public ModelWriter(String outputPath) {
        this.outputPath = outputPath;
    }

    public void writeModel(FileModel model, Boolean append) {
        List<String> list = new LinkedList<>(Arrays.asList(model.getFileName().split("/")));
        list.remove(list.size() - 1);
        File dir = Paths.get(outputPath, "/", StringUtils.collectionToDelimitedString(list, "/")).toFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = Paths.get(outputPath, "/", model.getFileName()).toFile();

        writeModel(file, model, append);

    }

    public void writeModel(File file, Writable model, Boolean append) {
        try (FileWriter writer = new FileWriter(file, append)) {
            Map<Class<?>, Object> values = new HashMap<>();
            values.put(Configuration.class, CONFIGURATION);
            model.write(new DefaultModelElementWriterContext(values), writer);
            writer.flush();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void writeModel(String sourcePath, Type type, Writable model) {
        File packageDir = Paths.get(outputPath, sourcePath, type.getPackageDir().replace("/entity","")).toFile();
        if (!packageDir.exists()) {
            packageDir.mkdirs();
        }
        File javaFile = Paths.get(outputPath, sourcePath, type.getPackageDir().replace("/entity",""), type.getFileName()).toFile();

        writeModel(javaFile, model, false);
    }

    private static final class SimpleClasspathLoader implements TemplateLoader {
        @Override
        public Reader getReader(Object name, String encoding) throws IOException {
            URL url = getClass().getClassLoader().getResource(String.valueOf(name));
            if (url == null) {
                throw new IllegalStateException(name + " not found on classpath");
            }
            URLConnection connection = url.openConnection();

            // don't use jar-file caching, as it caused occasionally closed input streams [at least under JDK 1.8.0_25]
            connection.setUseCaches(false);

            InputStream is = connection.getInputStream();

            return new InputStreamReader(is, StandardCharsets.UTF_8);
        }

        @Override
        public long getLastModified(Object templateSource) {
            return 0;
        }

        @Override
        public Object findTemplateSource(String name) throws IOException {
            return name;
        }

        @Override
        public void closeTemplateSource(Object templateSource) throws IOException {
        }
    }

    static class DefaultModelElementWriterContext implements Writable.Context {

        private final Map<Class<?>, Object> values;

        DefaultModelElementWriterContext(Map<Class<?>, Object> values) {
            this.values = new HashMap<>(values);
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T get(Class<T> type) {
            return (T) values.get(type);
        }
    }
}
