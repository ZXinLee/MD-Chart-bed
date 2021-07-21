package com.cy.controller;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 这里的controller可以看成是一个系统资源
 * 访问时需要进行权限检查
 */
@RestController
public class ResourceController {
    /**
     * 添加操作
     *
     * @PreAuthorize 注解由SpringSecurity框架提供, 用于描述方法, 此注解描述
     * 方法以后,再访问方法首先要进行权限检测
     */
    //假如登录用户具备admin这个角色可以访问
    //@PreAuthorize("hasRole('admin')")
    //登录用户具备sys:res:create权限才可访问资源
    @PreAuthorize("hasAuthority('sys:res:create')")
    @RequestMapping("/doCreate")
    public String doCreate(HttpServletResponse response) {

        return "create resource (insert data) ok";
    }

    /**
     * 查询操作
     */
    @PreAuthorize("hasAuthority('sys:res:retrieve')")
    @RequestMapping("/doRetrieve")
    public String doRetrieve() {//Retrieve 表示查询
        return "query resource (select data) ok";
    }

    /**
     * 修改操作
     */
    @PreAuthorize("hasAuthority('sys:res:update')")
    @RequestMapping("/doUpdate")
    public String doUpdate() {
        return "update resource (update data) ok";
    }

    /**
     * 删除操作
     */
    @PreAuthorize("hasAuthority('sys:res:delete')")
    @RequestMapping("/doDelete")
    public String doDelete() {
        return "delete resource (dalete data) ok";
    }
}
