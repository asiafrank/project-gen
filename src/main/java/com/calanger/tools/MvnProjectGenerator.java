package com.calanger.tools;

import com.calanger.tools.util.DirUtils;
import com.calanger.tools.util.GenerateUtils;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Only generate spring-boot project
 */
public class MvnProjectGenerator {
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String DEFAULT_DATABASE = "mysql";

    public static void main(String[] args) throws Exception {
        String baseDirName = "";
        String basePackageName = "";
        String projectName = "";

        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding(DEFAULT_CHARSET);
        configuration.setTemplateLoader(new ClassTemplateLoader(MvnProjectGenerator.class, "/"));
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Map<Object, Object> context = new HashMap<Object, Object>();
        context.put("basePackageName", basePackageName);
        context.put("projectName", projectName);

        generateParentProject(baseDirName, basePackageName, projectName, configuration, context);
        generateApiProject(baseDirName, basePackageName, projectName, configuration, context);
        generateClientProject(baseDirName, basePackageName, projectName, configuration, context);
        generateCoreProject(baseDirName, basePackageName, projectName, configuration, context);
        generateWebProject(baseDirName, basePackageName, projectName, configuration, context);
        generateAdminProject(baseDirName, basePackageName, projectName, configuration, context);
        generateServiceProject(baseDirName, basePackageName, projectName, configuration, context);
    }

    private static void generateParentProject(String baseDirName, String basePackageName, String projectName, Configuration configuration, Map<Object, Object> context) throws Exception {
        GenerateUtils.generateFile(baseDirName + "/pom.xml", "template/mvn/pom.xml.ftl", configuration, context);
    }

    private static void generateApiProject(String baseDirName, String basePackageName, String projectName, Configuration configuration, Map<Object, Object> context) throws Exception {
        Map<String, String> dirMap = mkdir(baseDirName, basePackageName, projectName, "api");

        GenerateUtils.generateFile(dirMap.get("basedir") + "/pom.xml", "template/mvn/api/pom.xml.ftl", configuration, context);
    }

    private static void generateClientProject(String baseDirName, String basePackageName, String projectName, Configuration configuration, Map<Object, Object> context) throws Exception {
        Map<String, String> dirMap = mkdir(baseDirName, basePackageName, projectName, "client");

        GenerateUtils.generateFile(dirMap.get("basedir") + "/pom.xml", "template/mvn/client/pom.xml.ftl", configuration, context);
    }

    private static void generateCoreProject(String baseDirName, String basePackageName, String projectName, Configuration configuration, Map<Object, Object> context) throws Exception {
        Map<String, String> dirMap = mkdir(baseDirName, basePackageName, projectName, "core");

        GenerateUtils.generateFile(dirMap.get("basedir") + "/pom.xml", "template/mvn/core/pom.xml.ftl", configuration, context);

        String resourcesDir = dirMap.get("/src/main/resources");

        DirUtils.mkdir(resourcesDir + "/spring");
        GenerateUtils.generateFile(resourcesDir + "/spring/applicationContext-dao.xml", "template/mvn/core/spring/applicationContext-dao.xml.ftl", configuration, context);
        GenerateUtils.generateFile(resourcesDir + "/spring/applicationContext-bo.xml", "template/mvn/core/spring/applicationContext-bo.xml.ftl", configuration, context);

        DirUtils.mkdir(resourcesDir + "/mybatis/base");
        DirUtils.mkdir(resourcesDir + "/mybatis/extension");
        GenerateUtils.generateFile(resourcesDir + "/mybatis/mybatis-config.xml", "template/mybatis/mybatis-config.xml.ftl", configuration, context);

        String testResourcesDir = dirMap.get("/src/test/resources");

        DirUtils.mkdir(testResourcesDir + "/spring");
        GenerateUtils.generateFile(testResourcesDir + "/logback.xml", "template/mvn/core/test/logback.xml.ftl", configuration, context);
        GenerateUtils.generateFile(testResourcesDir + "/spring/applicationContext-test.xml", "template/mvn/core/test/applicationContext-test." + DEFAULT_DATABASE + ".xml.ftl", configuration, context);

        String testJavaDir = dirMap.get("/src/test/java");
        DirUtils.mkdir(testJavaDir + "/dao/impl");
        GenerateUtils.generateFile(testJavaDir + "/dao/impl/DefaultSampleDAOTest.java", "template/mvn/core/test/DefaultSampleDAOTest.java.ftl", configuration, context);
    }

    private static void generateWebProject(String baseDirName, String basePackageName, String projectName, Configuration configuration, Map<Object, Object> context) throws Exception {
        Map<String, String> dirMap = mkdir(baseDirName, basePackageName, projectName, "web");

        GenerateUtils.generateFile(dirMap.get("basedir") + "/pom.xml", "template/mvn/web/pom.xml.ftl", configuration, context);

        String resourcesDir = dirMap.get("/src/main/resources");

        GenerateUtils.generateFile(resourcesDir + "/freemarker.properties", "template/mvn/web/freemarker.properties.ftl", configuration, context);
        GenerateUtils.generateFile(resourcesDir + "/config.properties", "template/mvn/web/config.properties.ftl", configuration, context);
        GenerateUtils.generateFile(resourcesDir + "/logback.xml", "template/mvn/web/logback.xml.ftl", configuration, context);

        DirUtils.mkdir(dirMap.get("basedir") + "/src/main/webapp/WEB-INF/view");
        DirUtils.mkdir(dirMap.get("basedir") + "/src/main/webapp/WEB-INF/jsp");
        GenerateUtils.generateFile(dirMap.get("basedir") + "/src/main/webapp/WEB-INF/web.xml", "template/mvn/web/web.xml.ftl", configuration, context);
        GenerateUtils.generateFile(dirMap.get("basedir") + "/src/main/webapp/WEB-INF/dispatcher-servlet.xml", "template/mvn/web/dispatcher-servlet.xml.ftl", configuration, context);
        GenerateUtils.generateFile(dirMap.get("basedir") + "/src/main/webapp/WEB-INF/jsp/403.jsp", "template/mvn/web/jsp/403.jsp.ftl", configuration, context);
        GenerateUtils.generateFile(dirMap.get("basedir") + "/src/main/webapp/WEB-INF/jsp/404.jsp", "template/mvn/web/jsp/404.jsp.ftl", configuration, context);
        GenerateUtils.generateFile(dirMap.get("basedir") + "/src/main/webapp/WEB-INF/jsp/500.jsp", "template/mvn/web/jsp/500.jsp.ftl", configuration, context);
        GenerateUtils.generateFile(dirMap.get("basedir") + "/src/main/webapp/WEB-INF/jsp/error.jsp", "template/mvn/web/jsp/error.jsp.ftl", configuration, context);

        DirUtils.mkdir(dirMap.get("basedir") + "/src/test/META-INF");
        GenerateUtils.generateFile(dirMap.get("basedir") + "/src/test/META-INF/context.xml", "template/mvn/web/context." + DEFAULT_DATABASE + ".xml.ftl", configuration, context);
    }

    private static void generateAdminProject(String baseDirName, String basePackageName, String projectName, Configuration configuration, Map<Object, Object> context) throws Exception {
        Map<String, String> dirMap = mkdir(baseDirName, basePackageName, projectName, "admin");

        GenerateUtils.generateFile(dirMap.get("basedir") + "/pom.xml", "template/mvn/admin/pom.xml.ftl", configuration, context);

        String resourcesDir = dirMap.get("/src/main/resources");

        GenerateUtils.generateFile(resourcesDir + "/freemarker.properties", "template/mvn/admin/freemarker.properties.ftl", configuration, context);
        GenerateUtils.generateFile(resourcesDir + "/config.properties", "template/mvn/admin/config.properties.ftl", configuration, context);
        GenerateUtils.generateFile(resourcesDir + "/logback.xml", "template/mvn/admin/logback.xml.ftl", configuration, context);

        DirUtils.mkdir(dirMap.get("basedir") + "/src/main/webapp/WEB-INF/view");
        DirUtils.mkdir(dirMap.get("basedir") + "/src/main/webapp/WEB-INF/jsp");
        GenerateUtils.generateFile(dirMap.get("basedir") + "/src/main/webapp/WEB-INF/web.xml", "template/mvn/admin/web.xml.ftl", configuration, context);
        GenerateUtils.generateFile(dirMap.get("basedir") + "/src/main/webapp/WEB-INF/dispatcher-servlet.xml", "template/mvn/admin/dispatcher-servlet.xml.ftl", configuration, context);
        GenerateUtils.generateFile(dirMap.get("basedir") + "/src/main/webapp/WEB-INF/jsp/403.jsp", "template/mvn/admin/jsp/403.jsp.ftl", configuration, context);
        GenerateUtils.generateFile(dirMap.get("basedir") + "/src/main/webapp/WEB-INF/jsp/404.jsp", "template/mvn/admin/jsp/404.jsp.ftl", configuration, context);
        GenerateUtils.generateFile(dirMap.get("basedir") + "/src/main/webapp/WEB-INF/jsp/500.jsp", "template/mvn/admin/jsp/500.jsp.ftl", configuration, context);
        GenerateUtils.generateFile(dirMap.get("basedir") + "/src/main/webapp/WEB-INF/jsp/error.jsp", "template/mvn/admin/jsp/error.jsp.ftl", configuration, context);

        DirUtils.mkdir(dirMap.get("basedir") + "/src/test/META-INF");
        GenerateUtils.generateFile(dirMap.get("basedir") + "/src/test/META-INF/context.xml", "template/mvn/admin/context." + DEFAULT_DATABASE + ".xml.ftl", configuration, context);
    }

    private static void generateServiceProject(String baseDirName, String basePackageName, String projectName, Configuration configuration, Map<Object, Object> context) throws Exception {
        Map<String, String> dirMap = mkdir(baseDirName, basePackageName, projectName, "service");

        GenerateUtils.generateFile(dirMap.get("basedir") + "/pom.xml", "template/mvn/service/pom.xml.ftl", configuration, context);

        String resourcesDir = dirMap.get("/src/main/resources");

        GenerateUtils.generateFile(resourcesDir + "/freemarker.properties", "template/mvn/service/freemarker.properties.ftl", configuration, context);
        GenerateUtils.generateFile(resourcesDir + "/config.properties", "template/mvn/service/config.properties.ftl", configuration, context);
        GenerateUtils.generateFile(resourcesDir + "/logback.xml", "template/mvn/service/logback.xml.ftl", configuration, context);

        DirUtils.mkdir(dirMap.get("basedir") + "/src/main/webapp/WEB-INF");
        GenerateUtils.generateFile(dirMap.get("basedir") + "/src/main/webapp/WEB-INF/web.xml", "template/mvn/service/web.xml.ftl", configuration, context);
        GenerateUtils.generateFile(dirMap.get("basedir") + "/src/main/webapp/WEB-INF/dispatcher-servlet.xml", "template/mvn/service/dispatcher-servlet.xml.ftl", configuration, context);

        DirUtils.mkdir(dirMap.get("basedir") + "/src/test/META-INF");
        GenerateUtils.generateFile(dirMap.get("basedir") + "/src/test/META-INF/context.xml", "template/mvn/service/context." + DEFAULT_DATABASE + ".xml.ftl", configuration, context);
    }

    private static Map<String, String> mkdir(String baseDirName, String basePackageName, String projectName, String subProjectSuffix) {
        Map<String, String> dirMap = new HashMap<String, String>();

        String subProjectBaseDirName = baseDirName + "/" + projectName + "-" + subProjectSuffix;
        DirUtils.mkdir(subProjectBaseDirName);
        dirMap.put("basedir", subProjectBaseDirName);

        String subPackageName = "core".equals(subProjectSuffix) ? "" : ("/" + subProjectSuffix);

        String dirName = subProjectBaseDirName + "/src/main/java/" + basePackageName.replace('.', '/') + "/" + projectName + subPackageName;
        DirUtils.mkdir(dirName);
        dirMap.put("/src/main/java", dirName);

        dirName = subProjectBaseDirName + "/src/main/resources";
        DirUtils.mkdir(dirName);
        dirMap.put("/src/main/resources", dirName);

        dirName = subProjectBaseDirName + "/src/test/java/" + basePackageName.replace('.', '/') + "/" + projectName + subPackageName;
        DirUtils.mkdir(dirName);
        dirMap.put("/src/test/java", dirName);

        dirName = subProjectBaseDirName + "/src/test/resources";
        DirUtils.mkdir(dirName);
        dirMap.put("/src/test/resources", dirName);

        return dirMap;
    }
}
