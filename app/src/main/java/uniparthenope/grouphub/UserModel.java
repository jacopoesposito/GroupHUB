package uniparthenope.grouphub;

public class UserModel {
    private String Name, Email;
    public void UserModel(){}

    public void UserModel(String Email, String Name){
        this.Name = Name;
        this.Email = Email;
    }

    public String getName(){
        return Name;
    }

    public void setName(String Name){
        this.Name = Name;
    }

    public String getEmail(){
        return Email;
    }

    public void setEmail(){
        this.Email = Email;
    }
}
