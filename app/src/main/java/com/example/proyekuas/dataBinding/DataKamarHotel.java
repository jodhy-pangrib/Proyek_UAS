package com.example.proyekuas.dataBinding;

import java.util.ArrayList;

public class DataKamarHotel {
    public ArrayList<DataHotel> Room;

    public DataKamarHotel(){
        Room = new ArrayList<>();
        Room.add(Standar);
        Room.add(Suite);
        Room.add(Single);
        Room.add(Deluxe);
    }

    public static final DataHotel Standar       = new DataHotel("Standar Room","Single Bed or Double Bed", "AC", "TV", "For 1 Night, Inculde Task", 350000,"https://www.kokoonhotelsvillas.com/surabaya/wp-content/uploads/sites/2/2020/01/standard-room.jpg");
    public static final DataHotel Suite         = new DataHotel("Suite Room","Include all facility for Deluxe Room", "2 Living Rooms, Work Office", "Mini Kitchen", "For 1 Night, Inculde Task", 2500000,"https://banyuwangi.el-hotels.com/wp-content/uploads/sites/47/2019/09/Junior-Suite-room.jpg");
    public static final DataHotel Single        = new DataHotel("Single Room","Include all facility for Standard Room", "Bathroom + Shower", "Free WiFi", "For 1 Night, Inculde Task", 450000,"https://pix10.agoda.net/hotelImages/554/5547527/5547527_18080111100067219956.png?s=1024x768");
    public static final DataHotel Deluxe        = new DataHotel("Deluxe Room","Include all facility for Standard Room", "Refrigerator, Coffe/Tea Maker", "Bathroom + Shower, Free WiFi", "For 1 Night, Inculde Task", 550000,"https://c4.wallpaperflare.com/wallpaper/844/53/319/miami-florida-hotel-room-wallpaper-preview.jpg");
}
