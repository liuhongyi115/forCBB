package ${package}.service;

import org.springframework.data.domain.Pageable;

import ${package}.domain.${className};
import ${package}.service.dto.${className}DTO;
import ${package}.service.dto.${className}QueryCriteria;

/**
* @author ${author}
* @date ${date}
*/
public interface ${className}Service {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    Object queryAll(${className}QueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    public Object queryAll(${className}QueryCriteria criteria);

    /**
     * findById
     * @param ${pkChangeColName}
     * @return
     */
    ${className}DTO findById(${pkColumnType} ${pkChangeColName});

    /**
     * create
     * @param resources
     * @return
     */
    ${className}DTO create(${className} resources);

    /**
     * update
     * @param resources
     */
    void update(${className} resources);

    /**
     * delete
     * @param ${pkChangeColName}
     */
    void delete(${pkColumnType} ${pkChangeColName});
}