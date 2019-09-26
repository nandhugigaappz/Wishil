package com.wishill.wishill.model;

/*
 * Created by nithinp on 27-03-2018.
 */

public class IndividulDashboardModel {
    private String categoryName;
    private String categoryNameDem;
    private int categoryIcon;

    public IndividulDashboardModel(String categoryNameDem,String categoryName, int categoryIcon) {
        this.categoryName = categoryName;
        this.categoryIcon = categoryIcon;
        this.categoryNameDem=categoryNameDem;
    }

    public String getCategoryNameDem() {
        return categoryNameDem;
    }

    public void setCategoryNameDem(String categoryNameDem) {
        this.categoryNameDem = categoryNameDem;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(int categoryIcon) {
        this.categoryIcon = categoryIcon;
    }
}
