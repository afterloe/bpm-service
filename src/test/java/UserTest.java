import cn.cityworks.bpm.Launch;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

/**
 * create by afterloe on 2017/10/14
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Launch.class)
public class UserTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserTest.class);

    @Autowired
    private IdentityService identityService;
    private String userId = "d8606897f52442d881232d7857a87505";

    @Before
    public void insertUser() throws Exception {
        User user = identityService.newUser(userId);
        user.setFirstName("afterloe");
        user.setLastName("liu");
        user.setEmail("lm6289511@gmail.com");
        identityService.saveUser(user);
    }

    @Test
    public void testUser() throws Exception {
        User user = identityService.createUserQuery().userId(userId).singleResult();
        assertNotNull(user);
        LOGGER.info("user info ==>> firstName -> {} | LastName -> {} | Email -> {}", user.getFirstName()
        , user.getLastName(), user.getEmail());
    }

    @After
    public void removeTestData() {
        identityService.deleteUser(userId);
    }
}
