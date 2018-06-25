package uniparthenope.grouphub;

import com.google.android.gms.maps.model.LatLng;

public class Events {

    private String NameEvento, InfoEvento, LuogoEvento, DataEvento, user_id;
    private Long Like;
    private LatLng coordinate;

    public Events(){}

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Events(String NameEvento, String InfoEvento, String LuogoEvento, Long Like, String DataEvento, LatLng coordinate) {
        this.user_id = user_id;

        this.NameEvento = NameEvento;
        this.InfoEvento = InfoEvento;
        this.LuogoEvento = LuogoEvento;
        this.Like = Like;
        this.DataEvento = DataEvento;
        this.coordinate = coordinate;
    }

    public void setInfoEvento(String infoEvento) {
        InfoEvento = infoEvento;
    }

    public void setLuogoEvento(String luogoEvento) {
        LuogoEvento = luogoEvento;
    }

    public void setLike(String like) {
        Like = Like;
    }

    public void setDataEvento(String dataEvento) {
        DataEvento = dataEvento;
    }

    public void setCoordinate(LatLng coordinate) {
        this.coordinate = this.coordinate;
    }

    public void setNameEvento(String nameEvento) {
        NameEvento = nameEvento;
    }

    public String getNameEvento() {
        return NameEvento;
    }

    public String getInfoEvento() {
        return InfoEvento;
    }

    public String getLuogoEvento() {
        return LuogoEvento;
    }

    public Long getLike() {
        return Like;
    }

    public String getDataEvento() {
        return DataEvento;
    }

    public LatLng getCoordinate() {
        return coordinate;
    }
}
