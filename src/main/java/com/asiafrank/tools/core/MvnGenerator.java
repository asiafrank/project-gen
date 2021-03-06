package com.asiafrank.tools.core;

import com.asiafrank.tools.util.ProjectInfo;
import com.asiafrank.tools.util.DB;
import com.asiafrank.tools.util.FTLs;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author asiafrank created at 1/5/2017.
 */
public class MvnGenerator extends Generator {
    private static final Logger log = Logger.getGlobal();

    private final char sp;

    private final ProjectInfo project;

    private final DBParam dbParam;

    private final Configuration ftlConfig;

    private final Map<Object, Object> context = new HashMap<Object, Object>();

    public MvnGenerator(ProjectInfo projectInfo, DBParam dbParam) {
        this.project = projectInfo;
        this.dbParam = dbParam;

        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateLoader(new ClassTemplateLoader(MvnGenerator.class, "/"));
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        this.ftlConfig = configuration;

        context.put("basePackageName", projectInfo.getPackageName());
        context.put("projectName", projectInfo.getProjectName());
        context.put("isMySQL", dbParam.getDb() == DB.MYSQL ? 1 : 0);
        context.put("jerseyStyle", projectInfo.isJerseyStyle());

        this.sp = projectInfo.getSp();
    }

    @Override
    public void execute() {
        mkMvnDir();
        mkPoms();
        mkConfig();
        mkMybatisConfig();
        mkCoreBaseClass();
        mkServiceBaseClass();

        if (dbParam.getTableNames() == null
                || dbParam.getTableNames().length == 0) {
            return;
        }
        CoreGenerator cg = new CoreGenerator(project, dbParam, ftlConfig);
        cg.execute();
    }

    private void mkCoreBaseClass() {
        final String coreBaseClassDir = project.getCorePackageDir() + sp + "base";
        final String coreConfigDir = project.getCorePackageDir() + sp + "config";

        mkdir(coreBaseClassDir);
        mkdir(coreConfigDir);

        gen(coreBaseClassDir + sp + "AbstractBO.java",
                FTLs.base_abstract_bo, ftlConfig, context);
        gen(coreBaseClassDir + sp + "AbstractDAO.java",
                FTLs.base_abstract_dao, ftlConfig, context);
        gen(coreBaseClassDir + sp + "BO.java",
                FTLs.base_bo, ftlConfig, context);
        gen(coreBaseClassDir + sp + "DAO.java",
                FTLs.base_dao, ftlConfig, context);
        gen(coreBaseClassDir + sp + "DAOUtils.java",
                FTLs.base_dao_utils, ftlConfig, context);
        gen(coreBaseClassDir + sp + "Expression.java",
                FTLs.base_expression, ftlConfig, context);
        gen(coreBaseClassDir + sp + "ExpressionChain.java",
                FTLs.base_expression_chain, ftlConfig, context);
        gen(coreBaseClassDir + sp + "Expressions.java",
                FTLs.base_expressions, ftlConfig, context);
        gen(coreBaseClassDir + sp + "OrderBy.java",
                FTLs.base_orderby, ftlConfig, context);
        gen(coreBaseClassDir + sp + "Page.java",
                FTLs.base_page, ftlConfig, context);
        gen(coreBaseClassDir + sp + "Pageable.java",
                FTLs.base_pageable, ftlConfig, context);

        gen(coreConfigDir + sp + "CoreConfig.java",
                FTLs.core_config, ftlConfig, context);
    }

    private void mkServiceBaseClass() {
        final String serviceBaseClassDir = project.getServicePackageDir();
        final String serviceConfigDir = serviceBaseClassDir + sp + "config";
        final String serviceControllerDir = serviceBaseClassDir + sp + "controller";
        final String serviceResolverDir = serviceBaseClassDir + sp + "resolver";

        mkdir(serviceBaseClassDir);
        mkdir(serviceConfigDir);
        mkdir(serviceControllerDir);
        mkdir(serviceResolverDir);

        gen(serviceBaseClassDir + sp + "Main.java",
                FTLs.service_main, ftlConfig, context);
        gen(serviceControllerDir + sp + "SampleController.java",
                FTLs.service_sample_controller, ftlConfig, context);
        gen(serviceResolverDir + sp + "PageDefault.java",
                FTLs.service_page_default, ftlConfig, context);
        if (project.isJerseyStyle()) {
            gen(serviceConfigDir + sp + "JerseyConfig.java",
                    FTLs.service_jersey_config, ftlConfig, context);
            gen(serviceResolverDir + sp + "PageDefaultFactoryProvider.java",
                    FTLs.service_page_default_factory_provider, ftlConfig, context);
        } else {
            gen(serviceConfigDir + sp + "WebConfig.java",
                    FTLs.service_web_config, ftlConfig, context);
            gen(serviceResolverDir + sp + "PageDefaultResolver.java",
                    FTLs.service_page_default_resolver, ftlConfig, context);
        }

    }

    private void mkConfig() {
        Map<Object, Object> cxt = new HashMap<>();
        cxt.put("url", dbParam.getUrl());
        cxt.put("username", dbParam.getUsername());
        cxt.put("password", dbParam.getPassword());
        cxt.put("driver", dbParam.getDriver());
        cxt.put("jerseyStyle", project.isJerseyStyle());
        gen(project.getServiceConfigDir() + sp + "application.properties",
                FTLs.config_application, ftlConfig, cxt);
    }

    private void mkMybatisConfig() {
        Map<Object, Object> context = new HashMap<>();
        context.put("tableNames", dbParam.getTableNames());
        gen(project.getCoreResourcesDir() + sp + "mybatis-config.xml", FTLs.mybatis_mybatis_config,
                ftlConfig, context);
    }

    private void mkMvnDir() {
        mkdir(project.getBaseDir());
        mkdir(project.getCoreBaseDir());
        mkdir(project.getCorePackageDir());
        mkdir(project.getCoreResourcesDir());
        mkdir(project.getServiceBaseDir());
        mkdir(project.getServicePackageDir());
        mkdir(project.getServiceConfigDir());
        mkdir(project.getServiceResourcesDir());
    }

    private void mkPoms() {
        final String pom = "pom.xml";
        gen(project.getBaseDir() + sp + pom, FTLs.pom,
                ftlConfig, context);
        gen(project.getCoreBaseDir() + sp + pom, FTLs.core_pom,
                ftlConfig, context);
        gen(project.getServiceBaseDir() + sp + pom, FTLs.service_pom,
                ftlConfig, context);
        gen(project.getServiceBaseDir() + sp + "start.sh", FTLs.service_start,
                ftlConfig, context);
        gen(project.getServiceBaseDir() + sp + "stop.sh", FTLs.service_shutdown,
                ftlConfig, context);
        gen(project.getServiceBaseDir() + sp + "assembly.xml", FTLs.service_assembly,
                ftlConfig, context);
    }
}
