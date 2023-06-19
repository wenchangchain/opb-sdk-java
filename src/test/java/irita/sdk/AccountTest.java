package irita.sdk;

import irita.sdk.client.IritaClient;
import irita.sdk.constant.enums.BroadcastMode;
import irita.sdk.exception.IritaSDKException;
import irita.sdk.key.KeyManager;
import irita.sdk.key.KeyManagerFactory;
import irita.sdk.model.*;
import irita.sdk.module.account.AccountClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest extends ConfigTest {
	private AccountClient accountClient;
	private final BaseTx baseTx = new BaseTx(200000, new Fee("300000", "ugas"), BroadcastMode.Commit);

	@BeforeEach
	public void init() {
		IritaClient client = getTestClient();
		accountClient = client.getAccountClient();
	}

	@Test
	@Disabled
	public void queryAccount() throws Exception {
		KeyManager termKm = KeyManagerFactory.createDefault();
		termKm.add();
		String addr = termKm.getCurrentKeyInfo().getAddress();

		BaseAccount account = accountClient.queryAccount(addr);
		List<Coin> coins = account.getCoins();
		Optional<Coin> iritaCoin = coins.stream().filter(x -> x.getDenom().equals("ugas")).findFirst();
		if (!iritaCoin.isPresent()) {
			throw new IritaSDKException("ugas not found from list");
		}

		assertEquals(iritaCoin.get().getAmount(), "10");

	}
}
