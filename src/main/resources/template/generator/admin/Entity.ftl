package ${package}.domain;

import lombok.Data;
<#if hasTimestamp>
import java.util.Date;
</#if>
<#if hasBigDecimal>
import java.math.BigDecimal;
</#if>
import java.io.Serializable;

/**
* @author ${author}
* @date ${date}
*/
@Data
public class ${className} implements Serializable {
<#if columns??>
<#list columns as column>
    <#if column.columnComment != ''>
	// ${column.columnComment}
    </#if>
    private ${column.columnType} ${column.changeColumnName};
</#list>
</#if>
}