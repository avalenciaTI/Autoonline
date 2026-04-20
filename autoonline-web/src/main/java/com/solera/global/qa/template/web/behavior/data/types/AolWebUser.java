package com.solera.global.qa.template.web.behavior.data.types;

import com.solera.global.qa.template.web.behavior.data.AolWebPropertiesReader;
import java.util.Properties;

public class AolWebUser {

    private String username;
    private String password;
    private String role;


    public AolWebUser(String fileUserName) {
        String usersFile = "jenkins";
        usersFile = System.getenv("JENKINS_HOME") != null ? "jenkins" : fileUserName;
        AolWebPropertiesReader webPropertiesReader = new AolWebPropertiesReader();
        Properties pm = webPropertiesReader.getPropertiesFromPath(AolWebPropertiesReader.getFileUserName(usersFile));
        this.username = pm.getProperty("master_username");
        this.password = pm.getProperty("master_password");
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }


    public AolWebUser(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
