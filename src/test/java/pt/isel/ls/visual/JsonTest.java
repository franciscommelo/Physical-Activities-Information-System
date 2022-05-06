package pt.isel.ls.visual;

import org.junit.Assert;
import org.junit.Test;
import pt.isel.ls.model.User;
import pt.isel.ls.view.json.JsonView;

public class JsonTest {

    @Test
    public void jsontest() throws Exception {

        User u = new User(1,"Francisco","francisco1@gmail.com");
        String jsonText = new JsonView().convertResultSetToJson(u);
        String jsonExpeted = "{ \"id\": 1, \"name\": \"Francisco\", \"email\": \"francisco1@gmail.com\"}";
        Assert.assertEquals(jsonText, jsonExpeted);
    }
}
