package pt.ulisboa.tecnico.softeng.bank.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.bank.services.local.BankInterface;
import pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects.BankData;
import pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects.ClientData;
import pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects.BankData.CopyDepth;
import pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects.AccountData;

@Controller
@RequestMapping(value = "/banks/{bankCode}/accounts")
public class AccountController {
	private static Logger logger = LoggerFactory.getLogger(AccountController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String showAccounts(Model model, @PathVariable String bankCode) {
		logger.info("showAccounts code:{}", bankCode);
		
		BankData bankData = BankInterface.getBankDataByCode(bankCode, CopyDepth.CLIENT);
		
		if (bankData == null) {
			model.addAttribute("error", "Error: it does not exist a bank with the code " + bankCode);
			model.addAttribute("bank", new BankData());
			model.addAttribute("banks", BankInterface.getBanks());
			return "banks";
		}
		else {
			model.addAttribute("account", new AccountData());
			model.addAttribute("bank", bankData);
			return "accounts";
		}
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String submitAccount(Model model, @PathVariable String bankCode, @ModelAttribute AccountData accountData) {
		logger.info("accountSubmit IBAN:{}, balance:{}", accountData.getIBAN(), accountData.getBalance());
		
		try {
			BankInterface.createAccount(bankCode, accountData);
		} catch (BankException be) {
			model.addAttribute("error", "Error: it was not possible to create the account");
			model.addAttribute("account", accountData);
			model.addAttribute("bank", BankInterface.getBankDataByCode(bankCode, CopyDepth.ACCOUNT));
			return "accounts";
		}
		
		return "redirect:/banks/" + bankCode + "accounts"; 
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/clients/{clientID}")
	public String showAccountsByClient(Model model, @PathVariable String bankCode, @PathVariable String clientID) {
		logger.info("showAccounts code:{}, clientID:{}", bankCode, clientID);
		
		BankData bankData = BankInterface.getBankDataByCode_Client(bankCode, clientID, CopyDepth.ACCOUNT);
		
		if (bankData == null) {
			model.addAttribute("error", "Error: it does not exist a bank with the code " + bankCode);
			model.addAttribute("bank", new BankData());
			model.addAttribute("banks", BankInterface.getBanks());
			return "banks";
		}
		if (bankData.getAccountsSize() == 0) {
			model.addAttribute("error", "Error: it does not exist an account for the client " + clientID);
			model.addAttribute("account", new AccountData());
			model.addAttribute("bank", bankData);
			return "accounts";
		}
		else {
			model.addAttribute("account", new AccountData());
			model.addAttribute("bank", bankData);
			return "accounts";
		}
	}
}