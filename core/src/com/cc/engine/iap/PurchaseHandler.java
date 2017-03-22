package com.cc.engine.iap;

import com.badlogic.gdx.pay.PurchaseManagerConfig;
import com.badlogic.gdx.pay.PurchaseObserver;
import com.badlogic.gdx.pay.PurchaseSystem;
import com.badlogic.gdx.pay.Transaction;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * Created with IntelliJ IDEA.
 * User: Jon
 * Date: 3/10/2017
 * Time: 10:40 AM
 */
public class PurchaseHandler implements PurchaseObserver {

    private PurchaseManagerConfig config;

    public PurchaseHandler(PurchaseManagerConfig config) {
        this.config = config;
    }

    @Override
    public void handleInstall() {
        PurchaseSystem.purchaseRestore(); //TODO: ON IOS THIS MUST BE DONE BY USER, CHECK https://github.com/libgdx/gdx-pay/blob/master/README.md
    }

    @Override
    public void handleInstallError(Throwable e) {
        throw new GdxRuntimeException(e);
    }

    @Override
    public void handleRestore(Transaction[] transactions) {
        for (Transaction transaction : transactions) {
            if (transaction.isPurchased()) {
                ProductManager.getInstance().getShopItem(transaction.getIdentifier()).setPurchased(true);
            }
        }
    }

    @Override
    public void handleRestoreError(Throwable e) {
        throw new GdxRuntimeException(e);
    }

    @Override
    public void handlePurchase(Transaction transaction) {
        PurchaseSystem.purchaseRestore(); //Restore
        if (transaction.isPurchased()) {
            ShopItem item = ProductManager.getInstance().getShopItem(transaction.getIdentifier());
            item.getPurchaseCallback().onPurchase();
        }
    }

    @Override
    public void handlePurchaseError(Throwable e) {
        throw new GdxRuntimeException(e);
    }

    @Override
    public void handlePurchaseCanceled() {

    }
}
