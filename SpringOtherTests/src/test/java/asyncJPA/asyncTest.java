package asyncJPA;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;



//@ActiveProfiles()
@SpringBootTest
//@RunWith(SpringRunner.class)
@Slf4j
public class asyncTest {

  @Autowired
  private UserRepo ur;
  @Autowired
  private UserCustomRepo ucr;
  @Autowired
  private UserService us;
  
  private void delay() throws InterruptedException {
//    us.delay(3000);
    Thread.sleep(3000);
  }  

  @Test
  public void testSynchronousEvent() throws InterruptedException, ExecutionException {  
    
//    System.out.println();
//    User user = ur.findById(1).orElse(null);
//    log.info("User: " + user.info());
    
//    System.out.println();
//    List<User> users = ur.getUsers();
//    log.info("users: " + users.size());
    
//    System.out.println();
//    List<User> usersSrv = us.getUsers();
//    log.info("delay started");
//    delay();
//    log.info("delay ended");
//    log.info("usersSrv: " + usersSrv.size());
    
//    System.out.println();
//    CompletableFuture<List<User>> usersSrvAsyncCompl = us.getUsersAsyncCompl();
//    log.info("delay started: getUsersAsyncCompl");
//    delay();
//    log.info("delay ended: getUsersAsyncCompl");
//    log.info("usersSrv: " + usersSrvAsyncCompl.get().size());
    
    System.out.println();
    CompletableFuture<List<Object>> usersCustorCompl = ucr.getUsersCustomComp();
    log.info("delay started: usersCustorCompl");
    delay();
    log.info("delay ended: usersCustorCompl");
    log.info("usersSrv: " + usersCustorCompl.get().size());
    
//    System.out.println();
//    List<User> usersSrvAsync = us.getUsersAsync();
//    log.info("delay started: getUsersAsync");
//    delay();
//    log.info("delay ended: getUsersAsync");
//    log.info("usersSrv: " + usersSrvAsync.size());
//
//    System.out.println();
//    List<User> usersSrvComp = us.getUsersComp();
//    log.info("delay started: getUsersComp");
//    delay();
//    log.info("delay ended: getUsersComp");
//    log.info("usersSrvComp: " + usersSrvComp.size());
//    
//    System.out.println();
//    List<User> usersSrvCompComp = us.getUsersCompComp().get();
//    log.info("delay started: getUsersCompComp");
//    delay();
//    log.info("delay ended: getUsersCompComp");
//    log.info("usersSrvCompComp: " + usersSrvComp.size());
  }
}
