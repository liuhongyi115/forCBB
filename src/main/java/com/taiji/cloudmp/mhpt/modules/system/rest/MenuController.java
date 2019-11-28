package com.taiji.cloudmp.mhpt.modules.system.rest;

import com.taiji.cloudmp.aop.log.Log;
import com.taiji.cloudmp.mhpt.modules.system.domain.Menu;
import com.taiji.cloudmp.exception.BadRequestException;
import com.taiji.cloudmp.mhpt.modules.system.service.MenuService;
import com.taiji.cloudmp.mhpt.modules.system.service.RoleService;
import com.taiji.cloudmp.mhpt.modules.system.service.UserService;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.MenuDTO;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.MenuQueryCriteria;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.RoleSmallDTO;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.UserDTO;
import com.taiji.cloudmp.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author Zheng Jie
 * @date 2018-12-03
 */
@RestController
@RequestMapping("api")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    private static final String ENTITY_NAME = "menu";

    /**
     * 构建前端路由所需要的菜单
     * @return
     */
    @SuppressWarnings("unchecked")
	@GetMapping(value = "/menus/build")
    public ResponseEntity buildMenus(){
        UserDTO user = userService.findByName(SecurityUtils.getUsername());
        List<RoleSmallDTO> findByUsers_Id = roleService.findByUsers_Id(user.getId());
        List<MenuDTO> menuDTOList = menuService.findByRoles(findByUsers_Id);
        List<MenuDTO> menuDTOTree = (List<MenuDTO>)menuService.buildTree(menuDTOList).get("content");
        return new ResponseEntity(menuService.buildMenus(menuDTOTree),HttpStatus.OK);
    }

    /**
     * 返回全部的菜单
     * @return
     */
    @GetMapping(value = "/menus/tree")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_CREATE','MENU_EDIT','ROLES_SELECT','ROLES_ALL')")
    public ResponseEntity getMenuTree(){
        return new ResponseEntity(menuService.getMenuTree(menuService.findByPid(0L)),HttpStatus.OK);
    }

    @Log("查询菜单")
    @GetMapping(value = "/menus")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_SELECT')")
    public ResponseEntity getMenus(MenuQueryCriteria criteria){
        List<MenuDTO> menuDTOList = menuService.queryAll(criteria);
        return new ResponseEntity(menuService.buildTree(menuDTOList),HttpStatus.OK);
    }

    @Log("新增菜单")
    @PostMapping(value = "/menus")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_CREATE')")
    public ResponseEntity create(@Validated @RequestBody Menu resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(menuService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改菜单")
    @PutMapping(value = "/menus")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_EDIT')")
    public ResponseEntity update(@Validated(Menu.Update.class) @RequestBody Menu resources){
        menuService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除菜单")
    @DeleteMapping(value = "/menus/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        List<Menu> menuList = menuService.findByPid(id);

        // 特殊情况，对级联删除进行处理
        //删除父菜单 递归删除 子菜单
        for (Menu menu : menuList) {
//            roleService.untiedMenu(menu);// 这步是解绑菜单 , 删除 菜单  居然不问 角色让不让删除该菜单 就直接解绑角色 
            menuService.delete(menu.getId());
        }
//        roleService.untiedMenu(menuService.findOne(id)); //  这步是解绑菜单 , 删除 菜单  居然不问 角色让不让删除该菜单 就直接解绑角色 
        menuService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
