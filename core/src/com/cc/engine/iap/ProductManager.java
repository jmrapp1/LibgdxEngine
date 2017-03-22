package com.cc.engine.iap;

import com.badlogic.gdx.pay.Offer;
import com.badlogic.gdx.pay.PurchaseManagerConfig;
import com.badlogic.gdx.pay.PurchaseObserver;
import com.badlogic.gdx.pay.PurchaseSystem;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Jon
 * Date: 3/10/2017
 * Time: 4:09 PM
 */
public class ProductManager {


    /**
     * Include Permissions on Android: https://github.com/libgdx/gdx-pay/blob/master/README.md
     */

    private static final ProductManager instance = new ProductManager();

    private ArrayList<ShopItem> shopItems = new ArrayList<ShopItem>();
    private PurchaseObserver purchaseObserver;

    private String googlePlayKey;
    private boolean useAndroid, useIOS;

    private ProductManager() {
    }

    public void initializeProductSystem() {
        // https://github.com/libgdx/gdx-pay
        // Disposes static instances in case JVM is re-used on restarts
        PurchaseSystem.onAppRestarted();

        //If gdx-pay is called too early, a manager may not be registered which would be ONE of the reasons for hasManager to return false
        if (PurchaseSystem.hasManager()) {
            // purchase system is ready to start. Let's initialize our product list etc...
            PurchaseManagerConfig config = new PurchaseManagerConfig();

            for (ShopItem item : shopItems) {
                config.addOffer(new Offer().setType(item.getOfferType()).setIdentifier(item.getSku()));
            }

            //add any stores you are planning on using (Note, IOS_APPLE doesn't actually have an encoded key so pass any string as the second parameter)
            if (useAndroid)
                config.addStoreParam(PurchaseManagerConfig.STORE_NAME_ANDROID_GOOGLE, googlePlayKey);
            if (useIOS)
                config.addStoreParam(PurchaseManagerConfig.STORE_NAME_IOS_APPLE, "iOS-Key-Not-Needed");

            if (purchaseObserver == null)
                purchaseObserver = new PurchaseHandler(config);
            PurchaseSystem.install(purchaseObserver, config);
        }
    }

    public ProductManager enableIOS() {
        useIOS = true;
        return this;
    }

    public ProductManager enabledAndroid(String googlePlayKey) {
        useAndroid = true;
        this.googlePlayKey = googlePlayKey;
        return this;
    }

    public ProductManager setPurchaseObserver(PurchaseObserver observer) {
        this.purchaseObserver = observer;
        return this;
    }

    public void addShopItem(ShopItem shopItem) {
        shopItems.add(shopItem);
    }

    public ArrayList<ShopItem> getShopItems() {
        return shopItems;
    }

    public ShopItem getShopItem(String sku) {
        for (ShopItem item : shopItems) {
            if (item.getSku().equalsIgnoreCase(sku)) {
                return item;
            }
        }
        return null;
    }

    public boolean isItemPurchased(String sku) {
        ShopItem item = getShopItem(sku);
        if (item != null)
            return item.isPurchased();
        return false;
    }

    public PurchaseObserver getPurchaseHandler() {
        return purchaseObserver;
    }

    public static ProductManager getInstance() {
        return instance;
    }

}
