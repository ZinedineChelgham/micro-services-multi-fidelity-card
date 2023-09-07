package fr.univcotedazur.polytech.multifidelity.commands;

import fr.univcotedazur.polytech.multifidelity.CliContext;
import fr.univcotedazur.polytech.multifidelity.models.DSIMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@ShellComponent
public class DSICommands {

    public static final String BASE_URI = "/DSI";

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private CliContext context;

    @ShellMethod("Get the list of all the DSI member registered (getAllDSIMembers)")
    public void get_alldsi() {
        DSIMember[] res = restTemplate.getForObject(BASE_URI + "/getAll", DSIMember[].class);
        for(DSIMember dsi : res) {
            context.getLogger().info(dsi.toString());
        }
    }

    @ShellMethod("Register a new DSI member (register DSI_MEMBER_NAME DSI_MEMBER_MAIL DSI_MEMBER_PASSWORD)")
    public void register_dsi(String name, String mail, String password) {
        DSIMember dsiMember = new DSIMember(name, mail, password);
        context.getLogger().info(dsiMember.toString());
        context.getLogger().info(name);
        context.getLogger().info(mail);
        context.getLogger().info(password);
        context.getLogger().info(dsiMember.getName());
        context.getLogger().info(dsiMember.getEmail());
        context.getLogger().info(dsiMember.getPassword());

        DSIMember res = restTemplate.postForObject(BASE_URI + "/register", dsiMember, DSIMember.class);
        System.out.println(res);
        context.addDSI(res);
    }

    @ShellMethod("Login as a DSI member (login DSI_MEMBER_MAIL DSI_MEMBER_PASSWORD)")
    public void login_dsi(String mail, String password) {
        HashMap<String, String> data = new HashMap<>();
        data.put("mail", mail);
        data.put("password", password);
        DSIMember res = restTemplate.postForObject(BASE_URI + "/login", data, DSIMember.class);
        context.setCurrentDSIMember(res);
    }

    @ShellMethod("Logout as a DSI member (logout)")
    public void logout_dsi() {
        context.setCurrentDSIMember(null);
    }
    @ShellMethod("Get the current DSI member (current)")
    public void current_dsi() {
        if (context.getCurrentDSIMember() != null) {
            context.getLogger().info(context.getCurrentDSIMember().toString());
        } else {
            context.getLogger().info("No DSI member logged in");
        }
    }
}
