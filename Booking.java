public class Booking {
    private int roomId;
    private String name;
    private String category;

    // Constructors
    Booking(){
        roomId = -1;
        name = "";
        category = "";
    }
    Booking(int roomId, String name,String category){
        this.roomId = roomId;
        this.category = category;
        this.name = name;
    }

    // Getter(Actuators)
    int getRoomId(){
        return roomId;
    }
    String getCategory(){
        return category;
    }
    String getName(){
        return name;
    }

    // Setter(Mutators)
    void setRoomId(int roomId){
        this.roomId = roomId;
    }
    void setCategory(String category){
        this.category = category;
    }
    void setName(String name){
        this.name = name;
    }

}
