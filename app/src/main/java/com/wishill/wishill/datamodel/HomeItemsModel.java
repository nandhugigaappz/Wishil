package com.wishill.wishill.datamodel;

/*
 * Created by nithinp on 27-03-2018.
 */

public class HomeItemsModel {
    private String categoryName;
    private int categoryIcon;
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
// private  Class<? extends AppCompatActivity> activity_name;


    public HomeItemsModel(String categoryName, int categoryIcon,String status) {
        this.categoryName = categoryName;
        this.categoryIcon = categoryIcon;
        this.status=status;
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
