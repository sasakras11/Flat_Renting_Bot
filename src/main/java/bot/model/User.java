package bot.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {


    @Id
    private long user_chat_id;

    private String searchURL;

    private int state;

    private String city;

    private int priceFrom;

    private int priceTo;

    private int rooms_number;


    public User(long user_chat_id,int state) {
        this.user_chat_id = user_chat_id;
        this.state = state;
    }

    protected User(){

     }



    public long getUser_chat_id() {
        return user_chat_id;
    }

    public void setUser_chat_id(long user_chat_id) {
        this.user_chat_id = user_chat_id;
    }

    public String getSearchURL() {
        return searchURL;
    }

    public void setSearchURL(String searchURL) {
        this.searchURL = searchURL;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(int priceFrom) {
        this.priceFrom = priceFrom;
    }

    public int getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(int priceTo) {
        this.priceTo = priceTo;
    }

    public int getRooms_number() {
        return rooms_number;
    }

    public void setRooms_number(int rooms_number) {
        this.rooms_number = rooms_number;
    }
}
