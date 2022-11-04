package com;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;

public class CodeGenerator {
    public static void main(String args[])
    {
        AutoGenerator autoGenerator=new AutoGenerator();
        autoGenerator.setDataSource(dataConfig());
        autoGenerator.setGlobalConfig(globalConfig());
        autoGenerator.setPackageInfo(packageConfig());
        autoGenerator.setStrategy(strategyConfig());
        autoGenerator.execute();
    }

    private static DataSourceConfig dataConfig(){
        DataSourceConfig dataSourceConfig=new DataSourceConfig();
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/goods?serverTimezone=UTC");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("123456");
        return dataSourceConfig;
    }

    private static GlobalConfig globalConfig(){
        GlobalConfig globalConfig=new GlobalConfig();
        globalConfig.setOutputDir(System.getProperty("user.dir")+
                "\\src\\main\\java");
        globalConfig.setOpen(false);
        globalConfig.setAuthor("bbg");
        globalConfig.setFileOverride(false);
        globalConfig.setMapperName("%sDao");
        globalConfig.setIdType(IdType.INPUT);
        return globalConfig;
    }

    private static PackageConfig packageConfig(){
        PackageConfig packageConfig=new PackageConfig();
        packageConfig.setParent("com");
        packageConfig.setEntity("entity");
        packageConfig.setMapper("dao");
        return packageConfig;
    }

    private static StrategyConfig strategyConfig(){
        StrategyConfig strategyConfig=new StrategyConfig();
        strategyConfig.setInclude("goods","supplier","goods_list");
        strategyConfig.setRestControllerStyle(true);
        strategyConfig.setEntityLombokModel(true);
        return strategyConfig;
    }

}
