package model;

public class Credential {
    private String siteName;
    private String username;
    private String password;

    public Credential(String siteName, String username, String password) {
        this.siteName = siteName;
        this.username = username;
        this.password = password;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return siteName + " (" + username + ")";
    }
}