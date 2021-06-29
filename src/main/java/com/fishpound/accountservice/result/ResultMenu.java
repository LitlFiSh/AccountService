package com.fishpound.accountservice.result;

import com.fishpound.accountservice.entity.Menu;

import java.util.List;
import java.util.Set;

public class ResultMenu {
    private String name;   //菜单名称
    private String path;   //菜单路径
    private List<Menu> children;   //子菜单

    public ResultMenu() {
    }

    public ResultMenu(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public ResultMenu(String name, String path, List<Menu> children) {
        this.name = name;
        this.path = path;
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }
}
