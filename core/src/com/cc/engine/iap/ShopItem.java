package com.cc.engine.iap;

import com.badlogic.gdx.pay.OfferType;

/**
 * Created with IntelliJ IDEA.
 * User: Jon
 * Date: 3/10/2017
 * Time: 4:11 PM
 */
public class ShopItem {

    private String sku;
    private OfferType offerType;
    private String price, displayName, description;
    private IOnPurchaseCallback purchaseCallback;
    private boolean purchased; //Used for one-time purchase items

    public ShopItem(String sku, String price, OfferType offerType, String displayName, String description, IOnPurchaseCallback purchaseCallback) {
        this.sku = sku;
        this.price = price;
        this.offerType = offerType;
        this.displayName = displayName;
        this.description = description;
        this.purchaseCallback = purchaseCallback;
    }

    public String getPrice() {
        return price;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public OfferType getOfferType() {
        return offerType;
    }

    public String getSku() {
        return sku;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public IOnPurchaseCallback getPurchaseCallback() {
        return purchaseCallback;
    }

}
