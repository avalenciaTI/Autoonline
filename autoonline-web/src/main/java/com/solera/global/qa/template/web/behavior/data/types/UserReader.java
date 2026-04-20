package com.solera.global.qa.template.web.behavior.data.types;


import com.solera.global.qa.template.web.behavior.data.AolWebPropertiesReader;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserReader {

    private final AolWebUser masterUser;
    private final AolWebUser physicalBuyerUser;
    private final AolWebUser physicalBuyerUser2;
    private final AolWebUser physicalBuyerUser3;
    private final  AolWebUser craneUser;

    public UserReader(String fileName) {
        String usersFile;
        usersFile = System.getenv("JENKINS_HOME") != null ? "jenkins" : fileName;
        AolWebPropertiesReader webPropertiesReader = new AolWebPropertiesReader();
        Properties user = webPropertiesReader.getPropertiesFromPath(AolWebPropertiesReader.getFileUserName(usersFile));
        this.masterUser = new AolWebUser(user.getProperty("master_username"),
                user.getProperty("master_password"),
                "master");
        this.physicalBuyerUser = new AolWebUser(user.getProperty("physical_buyer_username"),
                user.getProperty("physical_buyer_password"), "physical_buyer");
        this.physicalBuyerUser2 = new AolWebUser(user.getProperty("physical_buyer_username2"),
                user.getProperty("physical_buyer_password2"), "physical_buyer2");
        this.physicalBuyerUser3 = new AolWebUser(user.getProperty("physical_buyer_username3"),
                user.getProperty("physical_buyer_password3"), "physical_buyer3");
        this.craneUser = new AolWebUser(user.getProperty("crane_username"),
                user.getProperty("crane_password"), "crane");
    }

    public AolWebUser getMasterUser() {
        return masterUser;
    }

    public AolWebUser getPhysicalBuyerUser() {
        return physicalBuyerUser;
    }
    public AolWebUser getPhysicalBuyerUser2() {
        return physicalBuyerUser2;
    }
    public AolWebUser getPhysicalBuyerUser3() {
        return physicalBuyerUser3;
    }
    public AolWebUser getCraneUser() { return craneUser; }
}
