package cam.onlinelibrarymanagementsystem.model;

public class AuthenticationResponce {
    private String token;
    private String role;

    public AuthenticationResponce(String token, String role) {
        this.token = token;
        this.role = role;
    }
    
    public AuthenticationResponce(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }
}
