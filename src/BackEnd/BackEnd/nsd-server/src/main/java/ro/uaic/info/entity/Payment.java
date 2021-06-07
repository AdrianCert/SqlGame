package ro.uaic.info.entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Payment model
 */
public class Payment {
    private int ID;
    private int wallet_seller;
    private int wallet_buyer;
    private int valoare;
    private int blanta_noua;
    private String title;

    /**
     * getter
     */
    public int getID() {
        return ID;
    }
    public int getWallet_seller() {
        return wallet_seller;
    }
    public int getWallet_buyer() {
        return wallet_buyer;
    }
    public int getValoare() {
        return valoare;
    }
    public int getBlanta_noua() {
        return blanta_noua;
    }
    public String getTitle() {
        return title;
    }

    /**
     * setter
     */
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setWallet_seller(int wallet_seller) {
        this.wallet_seller = wallet_seller;
    }
    public void setWallet_buyer(int wallet_buyer) {
        this.wallet_buyer = wallet_buyer;
    }
    public void setValoare(int valoare) {
        this.valoare = valoare;
    }
    public void setBlanta_noua(int blanta_noua) {
        this.blanta_noua = blanta_noua;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void bind(ResultSet r) {
        try {
            setID(r.getInt(1));
            setWallet_seller(r.getInt(2));
            setWallet_buyer(r.getInt(3));
            setValoare(r.getInt(4));
            setBlanta_noua(r.getInt(5));
            setTitle(r.getString(6));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void prepare(PreparedStatement stm) throws SQLException {
        stm.setInt(1, ID);
        stm.setInt(2, wallet_seller);
        stm.setInt(3, wallet_buyer);
        stm.setInt(4, valoare);
        stm.setInt(5, blanta_noua);
        stm.setString(6, title);

    }
}
