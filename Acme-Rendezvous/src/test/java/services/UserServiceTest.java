
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Reply;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class UserServiceTest {

	//Service under test--------------------------------------------------
	@Autowired
	private UserService		userService;
	@Autowired
	private ReplyService	replyService;


	//Tests----------------------------------------------------------

	@Test
	public void testCreate() {
		final User user = this.userService.create();
		Assert.isNull(user.getName());
		Assert.isNull(user.getSurname());
		Assert.isNull(user.getPostalAddress());
		Assert.isNull(user.getPhoneNumber());
		Assert.isNull(user.getEmail());
		Assert.notNull(user.getUserAccount());
		Assert.notNull(user.getReplies());
		Assert.notNull(user.getComments());
		Assert.notNull(user.getAttendances());
		System.out.println(user.getUserAccount());
		System.out.println(user.getUserAccount().getAuthorities());
		System.out.println(user.getReplies());
		System.out.println(user.getAttendances());
		System.out.println(user.getComments());

	}

	@Test
	public void testSave() {
		final User user = this.userService.create();
		user.getUserAccount().setUsername("user33");
		user.getUserAccount().setPassword("user33");
		user.setName("Sample");
		user.setSurname("Sample Sample");
		user.setEmail("sample@gmail.com");
		user.setPhoneNumber("632541789");
		user.setPostalAddress(32165);
		final User saved = this.userService.save(user);
		Assert.isTrue(this.userService.findAll().contains(saved));
		System.out.println(saved.getUserAccount().getUsername());
		System.out.println(saved.getUserAccount().getPassword());
		System.out.println(saved.getName());
		System.out.println(saved.getSurname());
		System.out.println(saved.getEmail());
		System.out.println(saved.getPhoneNumber());
		System.out.println(saved.getPostalAddress());

	}
	@Test
	public void testFindAll() {
		final Collection<User> users = this.userService.findAll();
		System.out.println(users);
	}
	@Test
	public void testFindByReply() {
		final List<Reply> replies = (List<Reply>) this.replyService.findAll();
		final Reply reply = replies.get(0);
		System.out.println(reply.getText() + "-->");
		final User user = this.userService.findByReplyId(reply.getId());
		System.out.println(user.getName());
	}
}
