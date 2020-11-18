package com.fishpound.accountservice.result;

import com.fishpound.accountservice.entity.Menu;

import java.util.Set;

public class ResultMenu {
    private String name;
    private String path;
    private Set<Menu> children;

    public ResultMenu() {
    }

    public ResultMenu(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public ResultMenu(String name, String path, Set<Menu> children) {
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

    public Set<Menu> getChildren() {
        return children;
    }

    public void setChildren(Set<Menu> children) {
        this.children = children;
    }
}
