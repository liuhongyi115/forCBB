package ${package}.repository;

import java.util.List;
import org.springframework.stereotype.Repository;

import ${package}.domain.${className};
import ${package}.service.dto.${className}QueryCriteria;;

/**
* @author ${author}
* @date ${date}
*/
@Repository
public interface ${className}Repository {

	void add(${className} ${changeClassName});
	
	void delete(${pkColumnType} ${pkChangeColName});
	
	void update(${className} ${changeClassName});
	
	${className} findById(${pkColumnType} ${pkChangeColName});
	
	List<${className}> findAll(${className}QueryCriteria criteria);
	
	Long findTotalCount(${className}QueryCriteria criteria);
}