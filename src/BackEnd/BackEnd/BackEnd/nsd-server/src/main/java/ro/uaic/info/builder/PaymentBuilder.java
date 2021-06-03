package ro.uaic.info.builder;

import ro.uaic.info.entity.Payment;

public class PaymentBuilder extends Payment {
    public PaymentBuilder ID(Integer ID) {
        setID(ID);
        return this;
    }
    public PaymentBuilder wallet_seller(Integer wallet_seller) {
        setWallet_seller(wallet_seller);
        return this;
    }
    public PaymentBuilder wallet_buyer(Integer wallet_buyer) {
        setWallet_buyer(wallet_buyer);
        return this;
    }
    public PaymentBuilder valoare(Integer valoare) {
        setValoare(valoare);
        return this;
    }
    public PaymentBuilder blanta_noua(Integer blanta_noua) {
        setBlanta_noua(blanta_noua);
        return this;
    }
    public PaymentBuilder title(String title) {
        setTitle(title);
        return this;
    }
}
