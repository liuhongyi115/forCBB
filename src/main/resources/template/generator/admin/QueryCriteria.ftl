package ${package}.service.dto;

import lombok.Data;
import com.taiji.cloudmp.pager.BaseModel;

<#if hasTimestamp>
import java.util.Date;
</#if>


/**
* @author ${author}
* @date ${date}
*/
@Data
public class ${className}QueryCriteria extends BaseModel{
<#if queryColumns??>
<#list queryColumns as column>
	private ${column.columnType} ${column.changeColumnName};
</#list>
</#if>
}