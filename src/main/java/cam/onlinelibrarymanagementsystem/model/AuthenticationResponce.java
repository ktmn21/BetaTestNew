package cam.onlinelibrarymanagementsystem.model;

public class AuthenticationResponce {
    private String token;

    public AuthenticationResponce(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
