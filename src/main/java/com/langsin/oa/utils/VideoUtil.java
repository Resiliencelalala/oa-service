package com.langsin.oa.utils;

import com.langsin.oa.dto.MenuRouterDto;
import com.langsin.oa.dto.MenuRouterMetaDto;
import com.langsin.oa.dto.PermissionDto;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Classname VideoUtil
 * @Description 系统用户工具类
 * @author wyy
 * @since 2019-09-10
 * @Version 1.0
 */
@UtilityClass
public class VideoUtil {


    public List<MenuRouterDto> buildMenus(List<PermissionDto> permissionDtos) {
        List<MenuRouterDto> list = new LinkedList<>();

        permissionDtos.forEach(permissionDto -> {
                    if (permissionDto != null) {
                        List<PermissionDto> menuDTOList = permissionDto.getChildren();
                        MenuRouterDto menuVo = new MenuRouterDto();
                        menuVo.setName(permissionDto.getName());
                        menuVo.setPath(permissionDto.getPath());
                        if (permissionDto.getParentId()==0) {
                            menuVo.setPath(permissionDto.getPath());
                            menuVo.setRedirect("noredirect");
                            menuVo.setComponent("Layout");
                        } else {
                            menuVo.setComponent(permissionDto.getPath());
                        }
                        menuVo.setMeta(new MenuRouterMetaDto(permissionDto.getName(), permissionDto.getIcon()));
                        if (menuDTOList != null && menuDTOList.size() != 0 && permissionDto.getType() == 0) {
                            menuVo.setChildren(buildMenus(menuDTOList));
                        }
                        list.add(menuVo);
                    }
                });
        return list;
    }
    public MenuRouterDto buildHiddenMenus(List<PermissionDto> permsButton) {
        List<PermissionDto> permissionDtos = new ArrayList<>();
        permsButton.forEach(perms -> {
            if (VideoUtil.exists(permissionDtos, perms)) {
                permissionDtos.add(perms);
            }
        });

        MenuRouterDto hiddenMenu = new MenuRouterDto();
        hiddenMenu.setName("业务数据");
        hiddenMenu.setPath("/hidden");
        hiddenMenu.setRedirect("noredirect");
        hiddenMenu.setComponent("Layout");
        hiddenMenu.setHidden(true);
        hiddenMenu.setMeta(new MenuRouterMetaDto("业务数据",null));
        List<MenuRouterDto> children = new ArrayList<>();
        for (PermissionDto p: permissionDtos) {
            MenuRouterDto hiddenMenuChildren = new MenuRouterDto();
            hiddenMenuChildren.setName(p.getMenuName());
            hiddenMenuChildren.setPath(p.getPath());
            hiddenMenuChildren.setComponent(p.getPath());
            hiddenMenuChildren.setHidden(true);
            hiddenMenuChildren.setMeta(new MenuRouterMetaDto(p.getName(),null));
            children.add(hiddenMenuChildren);
        }
        hiddenMenu.setChildren(children);
        return hiddenMenu;
    }

    /**
     * 遍历菜单
     *
     * @param menuList 目录
     * @param menus 菜单+按钮
     * @param menuType
     */
    public void findChildren(List<PermissionDto> menuList, List<PermissionDto> menus, int menuType) {
        for (PermissionDto sysMenu : menuList) {
            List<PermissionDto> children = new ArrayList<>();
            for (PermissionDto menu : menus) {
                if (menuType == 1 && menu.getType() == 2) {
                    // 如果是获取类型不需要按钮，且菜单类型是按钮的，直接过滤掉
                    continue;
                }
                if (sysMenu.getId() != null && sysMenu.getId().equals(menu.getParentId())) {
                    menu.setParentName(sysMenu.getName());
                    menu.setLevel(sysMenu.getLevel() + 1);
                    if (exists(children, menu)) {
                        children.add(menu);
                    }
                }
            }
            sysMenu.setChildren(children);
            children.sort((o1, o2) -> o1.getSort().compareTo(o2.getSort()));
            findChildren(children, menus, menuType);
        }
    }

//    /**
//     * 构建部门tree
//     *
//     * @param sysDepts
//     * @param depts
//     */
//    public void findChildren(List<SysDept> sysDepts, List<SysDept> depts) {
//
//        for (SysDept sysDept : sysDepts) {
//            DeptTreeVo deptTreeVo = new DeptTreeVo();
//            deptTreeVo.setId(sysDept.getDeptId());
//            deptTreeVo.setLabel(sysDept.getName());
//            List<SysDept> children = new ArrayList<>();
//            List<DeptTreeVo> children1 = new ArrayList<>();
//            for (SysDept dept : depts) {
//                if (sysDept.getDeptId() != null && sysDept.getDeptId().equals(dept.getParentId())) {
//                    dept.setParentName(sysDept.getName());
//                    dept.setLevel(sysDept.getLevel() + 1);
//                    DeptTreeVo deptTreeVo1 = new DeptTreeVo();
//                    deptTreeVo1.setLabel(dept.getName());
//                    deptTreeVo1.setId(dept.getDeptId());
//                    children.add(dept);
//                    children1.add(deptTreeVo1);
//                }
//            }
//            sysDept.setChildren(children);
//            deptTreeVo.setChildren(children1);
//            findChildren(children, depts);
//        }
//    }

//    /**
//     * 构建部门tree
//     *
//     * @param sysDepts
//     * @param depts
//     */
//    public void findChildren1(List<DeptTreeVo> sysDepts, List<SysDept> depts) {
//
//        for (DeptTreeVo sysDept : sysDepts) {
//            sysDept.setId(sysDept.getId());
//            sysDept.setLabel(sysDept.getLabel());
//            List<DeptTreeVo> children = new ArrayList<>();
//            for (SysDept dept : depts) {
//                if (sysDept.getId() == dept.getParentId()) {
//                    DeptTreeVo deptTreeVo1 = new DeptTreeVo();
//                    deptTreeVo1.setLabel(dept.getName());
//                    deptTreeVo1.setId(dept.getDeptId());
//                    children.add(deptTreeVo1);
//                }
//            }
//            sysDept.setChildren(children);
//            findChildren1(children, depts);
//        }
//    }

    /**
     * 判断菜单是否存在
     *
     * @param sysMenus
     * @param sysMenu
     * @return
     */
    public boolean exists(List<PermissionDto> sysMenus, PermissionDto sysMenu) {
        boolean exist = false;
        for (PermissionDto menu : sysMenus) {
            if (menu.getId().equals(sysMenu.getId())) {
                exist = true;
            }
        }
        return !exist;
    }
}
