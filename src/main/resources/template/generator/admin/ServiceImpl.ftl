package ${package}.service.impl;

import ${package}.domain.${className};

import com.taiji.cloudmp.utils.ValidationUtil;
import ${package}.repository.${className}Repository;
import ${package}.service.${className}Service;
import ${package}.service.dto.${className}DTO;
import ${package}.service.dto.${className}QueryCriteria;
import ${package}.service.mapper.${className}Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cn.hutool.core.util.IdUtil;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;


/**
* @author ${author}
* @date ${date}
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ${className}ServiceImpl implements ${className}Service {

    @Autowired
    private ${className}Repository ${changeClassName}Repository;

    @Autowired
    private ${className}Mapper ${changeClassName}Mapper;

    @Override
    public Object queryAll(${className}QueryCriteria criteria, Pageable pageable){
    	criteria.Pageable2BaseModel(pageable);
    	Long rowCount = ${changeClassName}Repository.findTotalCount(criteria);
    	criteria.getPager().setRowCount(rowCount);
    	List<${className}> list = ${changeClassName}Repository.findAll(criteria);
        Page<${className}> page = new PageImpl<${className}>(list,pageable,rowCount);
        return page;
    }

    @Override
    public Object queryAll(${className}QueryCriteria criteria){
    	Long rowCount = ${changeClassName}Repository.findTotalCount(criteria);
    	criteria.getPager().setRowCount(rowCount);
    	List<${className}> list = ${changeClassName}Repository.findAll(criteria);
        return ${changeClassName}Mapper.toDto(list);
    }

    @Override
    public ${className}DTO findById(${pkColumnType} ${pkChangeColName}) {
        Optional<${className}> optional${className} = Optional.ofNullable(${changeClassName}Repository.findById(${pkChangeColName}));
        ValidationUtil.isNull(optional${className},"${className}","${pkChangeColName}",${pkChangeColName});
        return ${changeClassName}Mapper.toDto(optional${className}.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ${className}DTO create(${className} resources) {
    	resources.set${pkCapitalColName}(IdUtil.simpleUUID());
		${changeClassName}Repository.add(resources);
        return ${changeClassName}Mapper.toDto(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(${className} resources) {
        Optional<${className}> optional${className} = Optional.ofNullable(${changeClassName}Repository.findById(resources.getId()));;
        ValidationUtil.isNull( optional${className},"${className}","id",resources.getId());
        ${className} ${changeClassName} = optional${className}.get();
        ${changeClassName}Repository.update(${changeClassName});
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(${pkColumnType} ${pkChangeColName}) {
        ${changeClassName}Repository.delete(${pkChangeColName});
    }
}