public class Room {

    private int num;
    private String category;
    private String status;

    // Getter(Actuators)
    int getNum(){
        return num;
    }
    String getCategory(){
        return category;
    }
    String getStatus(){
        return status;
    }

    // Setter(Mutators)
    void setNum(int num){
        this.num = num;
    }
    void setCategory(String category){
        this.category = category;
    }
    void setStatus(String status){
        this.status = status;
    }

    // Constructors
    Room(){
        num = 0;
        category = "Standard";//By default
        status = "available";
    }
    Room(int num, String category,String status){
        this.num = num;
        this.category = category;
        this.status = status;
    }

    void display(){
        System.out.printf("Room ID %d | Category %s \n",num,category);
    }
}
