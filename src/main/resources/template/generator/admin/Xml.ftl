<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package}.repository.${className}Repository">
	<resultMap id="${className}" type="${package}.domain.${className}">
		<#list columns as column>
		<#if column.columnKey == "PRI">
		<id column="${column.columnName}" property="${column.changeColumnName}" />
		<#else>
		<result column="${column.columnName}" property="${column.changeColumnName}" />
		</#if>
		</#list>
	</resultMap>
 	
 	<sql id="columns" >
        <#list columns as column><#if column_index != (columns?size-1)>${column['columnName']}, <#else>${column['columnName']}</#if></#list>
    </sql>
    
    <sql id="dynamicWhere">
		<where>
			<#if queryColumns??>
			<#list queryColumns as column>
			<if test="${column['changeColumnName']} != null">
    		<#if column['columnQuery']?? && (column['columnQuery'] = '2')>
				and ${column['columnName']} = <#noparse>#{</#noparse>${column['changeColumnName']}<#noparse>}</#noparse>
    		<#else>
				and ${column['columnName']} like concat(concat('%', <#noparse>#{</#noparse>${column['changeColumnName']}<#noparse>}</#noparse>),'%')
    		</#if>
        	</if>
        	</#list>
        	</#if>
        </where>
	</sql>
    
	<insert id="add" parameterType="${package}.domain.${className}">
  		insert into ${tableName} 
  		values 
  		( <#list columns as column><#if (column_index != (columns?size-1))><#noparse>#{</#noparse>${column['changeColumnName']}<#noparse>}</#noparse>, <#else><#noparse>#{</#noparse>${column['changeColumnName']}<#noparse>}</#noparse></#if></#list> )
	</insert>
 	
 	<update id="update" parameterType="${package}.domain.${className}">
		update ${tableName} set 
		<#list columns as column>
			${column['columnName']} = <#noparse>#{</#noparse>${column['changeColumnName']}<#noparse>},</#noparse>
		</#list>
			${pkColumnName} = <#noparse>#{</#noparse>${pkChangeColName}<#noparse>}</#noparse>
		where ${pkColumnName} = <#noparse>#{</#noparse>${pkChangeColName}<#noparse>}</#noparse>
	</update>
 	
	<delete id="delete" parameterType="java.lang.String">
		delete from ${tableName} 
		where ${pkColumnName} = <#noparse>#{</#noparse>${pkChangeColName}<#noparse>}</#noparse>
	</delete>
 
	<select id="findAll" parameterType="${package}.domain.${className}" resultMap="${className}">
		select <include refid="columns" />
		from ${tableName}
		<include refid="dynamicWhere" />
		<if test="pager.DBPageCondition != null and pager.DBPageCondition != ''">
			<#noparse>${pager.DBPageCondition}</#noparse>
		</if>
	</select>
 
	<select id="findById" parameterType="java.lang.String" resultMap="${className}">
		select <include refid="columns" />
		from ${tableName}
		where ${pkColumnName} = <#noparse>#{</#noparse>${pkChangeColName}<#noparse>}</#noparse>
	</select>
 	
 	<select id="findTotalCount" resultType="java.lang.Long" >
		select count(1)
		FROM ${tableName}
		<include refid="dynamicWhere" />
	</select>
</mapper>