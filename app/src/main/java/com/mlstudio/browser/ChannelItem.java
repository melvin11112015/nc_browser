
package com.mlstudio.browser;

import java.io.Serializable;

public class ChannelItem implements Serializable {

    public String id;
    public String name;
    public Integer orderId;
    public Integer selected;
    private String siteId;

    public ChannelItem() {
    }

    public ChannelItem(String siteId, String id, String name, int orderId, int selected) {
        this.siteId=siteId;
        this.id = id;
        this.name = name;
        this.orderId = Integer.valueOf(orderId);
        this.selected = Integer.valueOf(selected);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String paramInt) {
        this.id = paramInt;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String paramString) {
        this.name = paramString;
    }

    public int getOrderId() {
        return this.orderId.intValue();
    }

    public void setOrderId(int paramInt) {
        this.orderId = Integer.valueOf(paramInt);
    }

    public Integer getSelected() {
        return this.selected;
    }

    public void setSelected(Integer paramInteger) {
        this.selected = paramInteger;
    }

    @Override
    public String toString() {
        return "ChannelItem [id=" + this.id + ", name=" + this.name
                + ", selected=" + this.selected + "]";
    }

    public String getSiteId() {
        return siteId;
    }
}
