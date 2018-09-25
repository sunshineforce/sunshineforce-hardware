package com.sunshineforce.hardware.base.mybatis.impl;

import com.github.abel533.mapperhelper.EntityHelper;
import com.github.abel533.mapperhelper.MapperHelper;
import com.github.abel533.mapperhelper.MapperTemplate;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * Created with IntelliJ IDEA
 * ProjectName: sunshineforce-hardware
 * CreateUser:  lixiaopeng
 * CreateTime : 2018/9/19 18:33
 * ModifyUser: bjlixiaopeng
 * Class Description:
 * To change this template use File | Settings | File and Code Template
 */

public class InProvider extends MapperTemplate {

    public InProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String selectByInKey(MappedStatement ms) {
        Class<?> entityClass = getSelectReturnType(ms);
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);
        EntityHelper.EntityTable table = EntityHelper.getEntityTable(entityClass);
        EntityHelper.EntityColumn ec = table.getEntityClassPKColumns().toArray(new EntityHelper.EntityColumn[table.getEntityClassPKColumns().size()])[0];
        //开始拼sql
        StringBuilder sql = new StringBuilder();
        sql.append("select "+EntityHelper.getSelectColumns(entityClass)+ " from ");
        sql.append(table.getName());
        sql.append(" where ");
        sql.append(ec.getProperty()+" in ");
        sql.append(" <foreach collection=\"list\" item=\"record\"  index=\"index\" open=\"(\" close=\")\" separator=\",\" >");
        sql.append(" #{record} ");
        sql.append(" </foreach> ");
        return sql.toString();
    }
}
