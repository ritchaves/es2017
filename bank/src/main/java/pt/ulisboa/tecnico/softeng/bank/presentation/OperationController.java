//package pt.ulisboa.tecnico.softeng.bank.presentation;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
//import pt.ulisboa.tecnico.softeng.bank.services.local.BankInterface;
//import pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects.AccountData;
//import pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects.BankData;
//import pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects.BankData.CopyDepth;
//import pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects.BankOperationData;
//
//@Controller
//@RequestMapping(value = "/banks/{bankCode}/operations")
//public class OperationController {
//	private static Logger logger = LoggerFactory.getLogger(OperationController.class);
//	
//	@RequestMapping(method = RequestMethod.GET)
//	public String showOperations(Model model, @PathVariable String bankCode) {
//		logger.info("showOperations code:{}", bankCode);
//		
//		BankData bankData = BankInterface.getBankDataByCode(bankCode, CopyDepth.OPERATION);
//		
//		if (bankData == null) {
//			model.addAttribute("error", "Error: it does not exist a bank with the code " + bankCode);
//			model.addAttribute("bank", new BankData());
//			model.addAttribute("banks", BankInterface.getBanks());
//			return "banks";
//		}
//		else {
//			model.addAttribute("operation", new AccountData());
//			model.addAttribute("bank", bankData);
//			return "operationss";
//		}
//	}
//	
//	@RequestMapping(method = RequestMethod.POST)
//	public String processDeposit(Model model, @PathVariable String bankCode, @ModelAttribute BankOperationData operationData) {
//		logger.info("processDeposit iban:{}, value:{}", operationData.getIban(), operationData.getValue());
//		
//		try {
//			BankInterface.processDep(iban, value);
//		} catch (BankException be) {
//			model.addAttribute("error", "Error: it was not possible to process that operation");
//			model.addAttribute("operation", operationData);
//			model.addAttribute("bank", BankInterface.getBankDataByCode(bankCode, CopyDepth.OPERATION));
//			return "operations";
//		}
//		return "redirect:/banks/" + bankCode + "/operations"; 
//	}
//	
//	@RequestMapping(method = RequestMethod.POST)
//	public String processWithdraw(Model model, @PathVariable String bankCode, @ModelAttribute BankOperationData operationData) {
//		logger.info("processWithdraw iban:{}, value:{}", operationData.getIban(), clientData.getValue());
//		
//		try {
//			BankInterface.processPayment(iban, value);
//		} catch (BankException be) {
//			model.addAttribute("error", "Error: it was not possible to process that operation");
//			model.addAttribute("operation", operationData);
//			model.addAttribute("bank", BankInterface.getBankDataByCode(bankCode, CopyDepth.OPERATION));
//			return "operations";
//		}
//		return "redirect:/banks/" + bankCode + "/operations"; 
//	}
//}
