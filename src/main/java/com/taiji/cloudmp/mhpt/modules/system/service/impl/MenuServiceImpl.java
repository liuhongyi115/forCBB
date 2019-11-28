package com.taiji.cloudmp.mhpt.modules.system.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.cloudmp.exception.BadRequestException;
import com.taiji.cloudmp.exception.EntityExistException;
import com.taiji.cloudmp.mhpt.modules.system.domain.Menu;
import com.taiji.cloudmp.mhpt.modules.system.domain.vo.MenuMetaVo;
import com.taiji.cloudmp.mhpt.modules.system.domain.vo.MenuVo;
import com.taiji.cloudmp.mhpt.modules.system.repository.MenuRepository;
import com.taiji.cloudmp.mhpt.modules.system.service.MenuService;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.MenuDTO;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.MenuQueryCriteria;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.RoleSmallDTO;
import com.taiji.cloudmp.mhpt.modules.system.service.mapper.MenuMapper;
import com.taiji.cloudmp.utils.ValidationUtil;

import cn.hutool.core.util.StrUtil;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MenuServiceImpl implements MenuService {
	@Autowired
	private MenuRepository menuRepository;
	
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List queryAll(MenuQueryCriteria criteria){
    	 List<Menu> findAll = menuRepository.findAll(criteria);
        return menuMapper.toDto(findAll);
    }

    @Override
    public MenuDTO findById(long id) {
    	Menu menu = menuRepository.findById(id);
    	Optional<Menu> om = Optional.of(menu); 
        ValidationUtil.isNull(om,"Menu","id",id);
        return menuMapper.toDto(om.get());
    }

    @Override
    public List<MenuDTO> findByRoles(List<RoleSmallDTO> roles) {
        Set<Menu> menus = new LinkedHashSet<>();
        for (RoleSmallDTO role : roles) {
            List<Menu> menus1 = menuRepository.findByRoles_IdOrderBySortAsc(role.getId()).stream().collect(Collectors.toList());
            menus.addAll(menus1);
        }
        return menus.stream().map(menuMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public MenuDTO create(Menu resources) {
        if(menuRepository.findByName(resources.getName()) != null){
            throw new EntityExistException(Menu.class,"name",resources.getName());
        }
        if(resources.getIFrame()){
            if (!(resources.getPath().toLowerCase().startsWith("http://")||resources.getPath().toLowerCase().startsWith("https://"))) {
                throw new BadRequestException("外链必须以http://或者https://开头");
            }
        }
        resources.setCreateTime(new Timestamp(new Date().getTime()));
        menuRepository.save(resources);
        
        return menuMapper.toDto(resources);
    }
    /**
     * 传自身的id  将 自己的id和 子子孙孙id查出来
     * @param id
     * @return
     */
    private Set<Long> getMenuByConnectByPrior(Long id){
    	Set<Long> ids = new HashSet<Long>();
    	//将自身传进去 查出来自己的子
    	List<Menu> findByPid = menuRepository.findByPid(id);
    	
    	for(Menu m:findByPid){
    		//先加自己
    		ids.add(m.getId());
    		//递归找自己的子孙 加进去
    		Set<Long> menuids = getMenuByConnectByPrior(m.getId());
    		ids.addAll(menuids);
    	}
    	return ids;
    }
    
    
    
    @Override
    public void update(Menu resources) {
        if(resources.getId().equals(resources.getPid())) {
            throw new BadRequestException("上级不能为自己");
        }
        Menu findById = menuRepository.findById(resources.getId());
        Optional<Menu> optionalPermission = Optional.of(findById); 
        
        ValidationUtil.isNull(optionalPermission,"Permission","id",resources.getId());

        if(resources.getIFrame()){
            if (!(resources.getPath().toLowerCase().startsWith("http://")||resources.getPath().toLowerCase().startsWith("https://"))) {
                throw new BadRequestException("外链必须以http://或者https://开头");
            }
        }
        Menu menu = optionalPermission.get();
        Menu menu1 = menuRepository.findByName(resources.getName());

        if(menu1 != null && !menu1.getId().equals(menu.getId())){
            throw new EntityExistException(Menu.class,"name",resources.getName());
        }
        
        Set<Long> menuByConnectByPrior = this.getMenuByConnectByPrior(resources.getId());
        menuByConnectByPrior.add(resources.getId());
        
        if(menuByConnectByPrior.contains(resources.getPid())){
        	throw new BadRequestException("不可以选择自己的子孙菜单做上级菜单");
        }
        
        menu.setName(resources.getName());
        menu.setComponent(resources.getComponent());
        menu.setPath(resources.getPath());
        menu.setIcon(resources.getIcon());
        menu.setIFrame(resources.getIFrame());
        menu.setPid(resources.getPid());
        menu.setSort(resources.getSort());
        menuRepository.update(menu);
    }

    @Override
    public void delete(Long id) {
    	Long roleCount = menuRepository.findRoleCountByMenuId(id);
    	if(roleCount >0){
    		//菜单绑定的 角色数量 大于0，不可以删除该菜单
    		 throw new BadRequestException("该菜单存在绑定角色, 不可以删除该菜单");
    	}
        menuRepository.deleteById(id);
    }

    @Override
    public Object getMenuTree(List<Menu> menus) {
        List<Map<String,Object>> list = new LinkedList<>();
        menus.forEach(menu -> {
                    if (menu!=null){
                        List<Menu> menuList = menuRepository.findByPid(menu.getId());
                        Map<String,Object> map = new HashMap<>();
                        map.put("id",menu.getId());
                        map.put("label",menu.getName());
                        if(menuList!=null && menuList.size()!=0){
                            map.put("children",getMenuTree(menuList));
                        }
                        list.add(map);
                    }
                }
        );
        return list;
    }

    @Override
    public List<Menu> findByPid(long pid) {
        return menuRepository.findByPid(pid);
    }

    @Override
    public Map buildTree(List<MenuDTO> menuDTOS) {
        List<MenuDTO> trees = new ArrayList<MenuDTO>();

        for (MenuDTO menuDTO : menuDTOS) {

            if ("0".equals(menuDTO.getPid().toString())) {
                trees.add(menuDTO);
            }

            for (MenuDTO it : menuDTOS) {
                if (it.getPid().equals(menuDTO.getId())) {
                    if (menuDTO.getChildren() == null) {
                        menuDTO.setChildren(new ArrayList<MenuDTO>());
                    }
                    menuDTO.getChildren().add(it);
                }
            }
        }
        Map map = new HashMap();
        map.put("content",trees.size() == 0?menuDTOS:trees);
        map.put("totalElements",menuDTOS!=null?menuDTOS.size():0);
        return map;
    }

    @Override
    public List<MenuVo> buildMenus(List<MenuDTO> menuDTOS) {
        List<MenuVo> list = new LinkedList<>();
        menuDTOS.forEach(menuDTO -> {
            if (menuDTO!=null){
                List<MenuDTO> menuDTOList = menuDTO.getChildren();
                MenuVo menuVo = new MenuVo();
                menuVo.setName(menuDTO.getName());
                menuVo.setPath(menuDTO.getPath());

                // 如果不是外链
                if(!menuDTO.getIFrame()){
                    if(menuDTO.getPid().equals(0L)){
                        //一级目录需要加斜杠，不然访问 会跳转404页面
                        menuVo.setPath("/" + menuDTO.getPath());
                        menuVo.setComponent(StrUtil.isEmpty(menuDTO.getComponent())?"Layout":menuDTO.getComponent());
                    }else if(!StrUtil.isEmpty(menuDTO.getComponent())){
                        menuVo.setComponent(menuDTO.getComponent());
                    }
                }
                menuVo.setMeta(new MenuMetaVo(menuDTO.getName(),menuDTO.getIcon()));
                if(menuDTOList!=null && menuDTOList.size()!=0){
                    menuVo.setAlwaysShow(true);
                    menuVo.setRedirect("noredirect");
                    menuVo.setChildren(buildMenus(menuDTOList));
                    // 处理是一级菜单并且没有子菜单的情况
                } else if(menuDTO.getPid().equals(0L)){
                    MenuVo menuVo1 = new MenuVo();
                    menuVo1.setMeta(menuVo.getMeta());
                    // 非外链
                    if(!menuDTO.getIFrame()){
                        menuVo1.setPath("index");
                        menuVo1.setName(menuVo.getName());
                        menuVo1.setComponent(menuVo.getComponent());
                    } else {
                        menuVo1.setPath(menuDTO.getPath());
                    }
                    menuVo.setName(null);
                    menuVo.setMeta(null);
                    menuVo.setComponent("Layout");
                    List<MenuVo> list1 = new ArrayList<MenuVo>();
                    list1.add(menuVo1);
                    menuVo.setChildren(list1);
                }
                list.add(menuVo);
            }
        }
        );
        return list;
    }

    @Override
    public Menu findOne(Long id) {
        Menu findById = menuRepository.findById(id);
        Optional<Menu> menu = Optional.of(findById); 
        ValidationUtil.isNull(menu,"Menu","id",id);
        return menu.get();
    }
}
