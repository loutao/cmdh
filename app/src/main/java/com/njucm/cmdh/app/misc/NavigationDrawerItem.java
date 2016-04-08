package com.njucm.cmdh.app.misc;

/**
 * Created by Michal Bialas on 19/07/14.
 *
 * @author Michal Bialas
 *
 */
public class NavigationDrawerItem {

    private String itemName;

    private int itemIconImage;

    private int itemIconColor;

    private String itemIconBootstrap;

    private boolean mainItem;

    private int itemBackground;

    private boolean selected;

    /**
     *
     * @param itemName  选项名称
     * @param itemIconImage  选项前面的图标
     * @param mainItem  是否为主选项
     */
    public NavigationDrawerItem(String itemName, int itemIconImage, boolean mainItem) {
        this.itemName = itemName;
        this.itemIconImage = itemIconImage;
        this.mainItem = mainItem;
    }

    /**
     *
     * @param itemName  选项名称
     * @param itemIconBootstrap  选项前面的图标
     * @param itemIconColor  颜色
     * @param mainItem  是否为主选项
     */
    public NavigationDrawerItem(String itemName, String itemIconBootstrap, int itemIconColor, boolean mainItem) {
        this.itemName = itemName;
        this.itemIconBootstrap = itemIconBootstrap;
        this.mainItem = mainItem;
        this.itemIconColor = itemIconColor;
    }

    public NavigationDrawerItem(String itemName, boolean mainItem) {
        this(itemName, 0, mainItem);
    }


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isMainItem() {
        return mainItem;
    }

    public void setMainItem(boolean mainItem) {
        this.mainItem = mainItem;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setItemIconBootstrap(String itemIconBootstrap) {
        this.itemIconBootstrap = itemIconBootstrap;
    }

    public String getItemIconBootstrap() {
        return itemIconBootstrap;
    }

    public int getItemIconColor() {
        return itemIconColor;
    }

    public void setItemIconColor(int itemIconColor) {
        this.itemIconColor = itemIconColor;
    }

    public int getItemIconImage() {
        return itemIconImage;
    }

    public void setItemIconImage(int itemIconImage) {
        this.itemIconImage = itemIconImage;
    }

    public int getItemBackground() {
        return itemBackground;
    }

    public void setItemBackground(int itemBackground) {
        this.itemBackground = itemBackground;
    }
}
