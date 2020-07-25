package kenichia.quip.api.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import kenichia.quip.api.QuipClient;
import kenichia.quip.api.QuipFolder;
import kenichia.quip.api.QuipUser;

public class QuipUserTest {
	@BeforeAll
	static void init() throws Exception {
		QuipClient.enableDebug(true);
		QuipClient.setAccessToken(System.getenv("QUIP_ACCESS_TOKEN"));
	}

	@Test
	void getCurrentUser() throws Exception {
		QuipUser user = QuipUser.getCurrentUser();
	    assertNotNull(user);

	    assertTrue(user.getId().length() > 0);
	    assertTrue(user.getName().length() > 0);
	    assertTrue(user.getEmails()[0].length() > 0);
	    assertFalse(user.isDisabled());
	    assertFalse(user.isRobot());
		assertNotNull(user.getCreatedUsec());
		assertTrue(user.getProfilePictureUrl().length() > 0);
		assertTrue(user.getAffinity() >= 0);
		assertTrue(user.getArchiveFolderId().length() > 0);
		assertTrue(user.getPrivateFolderId().length() > 0);
		assertTrue(user.getStarredFolderId().length() > 0);
		assertTrue(user.getDesktopFolderId().length() > 0);
		assertTrue(user.getTrashFolderId().length() > 0);
		assertTrue(user.getGroupFolderIds()[0].length() > 0);
		assertTrue(user.getUrl().length() > 0);
		assertTrue(user.getSubDomain().length() > 0);

		System.out.println(user.getChatThreadId());
		System.out.println(Arrays.toString(user.getSharedFolderIds()));
		for (String fid: user.getSharedFolderIds()) {
			QuipFolder f = QuipFolder.getFolder(fid);
			System.out.println(f.getTitle());
		}
	}

	@Test
	void getUser() throws Exception {
		QuipUser user = QuipUser.getCurrentUser();
		QuipUser user1 = QuipUser.getUser(user.getId());
		assertEquals(user.getId(), user1.getId());
	}

	@Test
	void getUsers() throws Exception {
		QuipUser user = QuipUser.getCurrentUser();
		QuipUser[] users = QuipUser.getUsers(new String[] { user.getId(), user.getId() });
		assertEquals(user.getId(), users[0].getId());
	}
}