import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.verify;

public class AccountServiceTestEasyMock {
	private AccountManager mockAccountManager;

	@BeforeEach
	public void setUp() {
		mockAccountManager = createMock("mockAccountManager",
				AccountManager.class);
	}

	@Test
	public void testTransferOk() {
		Account senderAccount = new Account("1", 200);
		Account beneficiaryAccount = new Account("2", 100);

		// Start defining the expectations
		mockAccountManager.updateAccount(senderAccount);
		mockAccountManager.updateAccount(beneficiaryAccount);

		expect(mockAccountManager.findAccountForUser("1"))
				.andReturn(senderAccount);
		expect(mockAccountManager.findAccountForUser("2"))
				.andReturn(beneficiaryAccount);

		// End defining the expectations
		replay(mockAccountManager);

		AccountService accountService = new AccountService();
		accountService.setAccountManager(mockAccountManager);
		accountService.transfer("1", "2", 50);

		assertEquals(150, senderAccount.getBalance());
		assertEquals(150, beneficiaryAccount.getBalance());
	}

	@AfterEach
	public void tearDown() {
		verify(mockAccountManager);
	}

}
