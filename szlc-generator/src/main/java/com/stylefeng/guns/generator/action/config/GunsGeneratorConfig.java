package com.stylefeng.guns.generator.action.config;

import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * 默认的代码生成的配置
 */
public class GunsGeneratorConfig extends AbstractGeneratorConfig {

    /**___________生成设置：代码生成空间目录*/
    public static final String codeSpace = "D:\\szlc_code";

    protected void globalConfig() {
        globalConfig.setOutputDir(GunsGeneratorConfig.codeSpace);//写自己项目的绝对路径,注意具体到java目录
        globalConfig.setFileOverride(true);
        globalConfig.setEnableCache(false);
        globalConfig.setBaseResultMap(true);
        globalConfig.setBaseColumnList(true);
        globalConfig.setOpen(false);
        /**___________生成设置：作者*/
        globalConfig.setAuthor("szlc");
    }

    protected void dataSourceConfig() {
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        dataSourceConfig.setUsername("szlc");
        dataSourceConfig.setPassword("Szlc2018");
        dataSourceConfig.setUrl("jdbc:mysql://show.sinata.cn:3306/szlc?characterEncoding=utf8");
    }

    protected void strategyConfig() {
        /**___________生成设置：修改表前缀*/
        strategyConfig.setTablePrefix(new String[]{"app_"});// 此处可以修改为您的表前缀
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
    }

    protected void packageConfig() {
        /**___________生成设置：model、dao、mapping代码目录*/
        packageConfig.setParent(null);
        packageConfig.setEntity("com.stylefeng.guns.rest.modular.system.model");
        packageConfig.setMapper("com.stylefeng.guns.rest.modular.system.dao");
        packageConfig.setXml("com.stylefeng.guns.rest.modular.system.dao.mapping");
    }

    protected void contextConfig() {
        /**___________生成设置：service目录*/
        contextConfig.setProPackage("com.stylefeng.guns.rest");
        contextConfig.setCoreBasePackage("com.stylefeng.guns.core");
        contextConfig.setBizChName("字典管理");
        contextConfig.setBizEnName("sysDict");
        contextConfig.setModuleName("system");
        contextConfig.setProjectPath(GunsGeneratorConfig.codeSpace + "\\guns-admin");//写自己项目的绝对路径
        contextConfig.setEntityName("SysDict");
        sqlConfig.setParentMenuName(null);//这里写已有菜单的名称,当做父节点

        /**
         * mybatis-plus 生成器开关
         */
        contextConfig.setEntitySwitch(true);
        contextConfig.setDaoSwitch(true);
        contextConfig.setServiceSwitch(true);

        /**
         * guns 生成器开关
         */
        contextConfig.setControllerSwitch(true);
        contextConfig.setIndexPageSwitch(true);
        contextConfig.setAddPageSwitch(true);
        contextConfig.setEditPageSwitch(true);
        contextConfig.setJsSwitch(true);
        contextConfig.setInfoJsSwitch(true);
        contextConfig.setSqlSwitch(true);
    }

    @Override
    protected void config() {
        globalConfig();
        dataSourceConfig();
        strategyConfig();
        packageConfig();
        contextConfig();
    }
}
