package com.techelevator.tenmo;


import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;
import com.techelevator.util.BasicLogger;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;

import java.math.BigDecimal;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private AuthenticatedUser currentUser;
    private final AccountServiceRest accountService = new AccountServiceRest(API_BASE_URL);
    private TransferServiceRest transferService = new TransferServiceRest(API_BASE_URL);

    private UserService userService = new UserService(API_BASE_URL);

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }

    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

    private void viewCurrentBalance() {

        Balance balance = accountService.getBalance(currentUser);
        System.out.println();
        System.out.println("Your current balance is: $" + balance.getBalance());

    }

    private void viewTransferHistory() {

        System.out.println("------------------------------------------");
        System.out.println("Transfers");
        System.out.println("ID            From/To               Amount");
        System.out.println("------------------------------------------");

        Transfer[] transfers = transferService.getAllTransfers(currentUser);

        for (Transfer transfer : transfers) {
            if (transfer.getAccountFrom() == accountService.getAccountByUserId(currentUser.getUser().getId(), currentUser).getAccountId()) {
                System.out.println(transfer.getTransferId() + "          To:" + transfer.getAccountTo() + "      " + transfer.getAmount());
            } else if (transfer.getAccountTo() == accountService.getAccountByUserId(currentUser.getUser().getId(), currentUser).getAccountId()) {
                System.out.println(transfer.getTransferId() + "          From:" + transfer.getAccountFrom() + "      " + transfer.getAmount());
            }
        }


        int choice = consoleService.promptForInt("Please enter transfer ID to view details(0 to cancel): ");
        for (Transfer transfer : transfers) {

            if (choice == transfer.getTransferId()) {
                String status = "";
                String type = "";
                if (transfer.getTransferTypeId() == 2) {
                    type = "Send";
                } else {
                    type = "Request";
                }
                if (transfer.getTransferStatusId() == 2) {
                    status = "Approved";
                }
                Long to = Long.valueOf(accountService.getAccount(transfer.getAccountTo(), currentUser).getUserId());
                Long from = Long.valueOf(accountService.getAccount(transfer.getAccountFrom(), currentUser).getUserId());
                System.out.println("------------------------------------------");
                System.out.println("Transfer Details");
                System.out.println("------------------------------------------");
                System.out.println("Transfer Id: " + transfer.getTransferId());
                System.out.println("From:        " + userService.getUser(from, currentUser).getUsername());
                System.out.println("To:          " + userService.getUser(to, currentUser).getUsername());
                System.out.println("Type:        " + type);
                System.out.println("Status:      " + status);
                System.out.println("Amount:      $" + transfer.getAmount());

            }
        }
    }


    private void viewPendingRequests() {
        // TODO Auto-generated method stub

    }


    private void sendBucks() {

        System.out.println();
        System.out.println("--------------------------------------------");
        System.out.println("Users ID" + "    " + "Name");
        System.out.println("--------------------------------------------");

        User[] users = accountService.getAllUsers(currentUser);
        for (User user : users) {
            if (user.getUsername().equals(currentUser.getUser().getUsername())) {
                continue;
            }
            System.out.println(user.getId() + "        " + user.getUsername());
        }

        System.out.println("--------------------------------------------");
        System.out.println();

        long userInput = consoleService.promptForLong("Enter ID of user you are sending to (0 to cancel): ");
        if (userInput == 0 || userInput == currentUser.getUser().getId()) {
            System.out.println("Transaction cancelled");
        } else {
            long toId = 0L;
            for (User user : users) {
                if (user.getId() == userInput) {
                    toId = userInput;

                }

            }
        }

        BigDecimal transferAmount = consoleService.promptForBigDecimal("Enter amount: ");
        Account account1 = accountService.getAccountByUserId(currentUser.getUser().getId(), currentUser);
        Account account2 = accountService.getAccountByUserId(userInput, currentUser);
        int accountF = account1.getAccountId();
        int accountT = account2.getAccountId();

        try {
            if (transferAmount.compareTo(new BigDecimal(0)) > 0) {
                if (accountService.getBalance(currentUser).getBalance().compareTo(transferAmount) == 1) {

                    Transfer transfer = new Transfer();
                    transfer.setTransferTypeId(2);
                    transfer.setTransferStatusId(2);
                    transfer.setAccountFrom(accountF);
                    transfer.setAccountTo(accountT);
                    transfer.setAmount(transferAmount);
                    transferService.createTransfer(currentUser, transfer);
                    System.out.println("Transfer made successfully.");

                } else {

                    System.out.println("Not enough funds.");

                }
            } else {

                System.out.println("Invalid amount.");

            }
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

    }

    private void requestBucks() {
        System.out.println();
        System.out.println("--------------------------------------------");
        System.out.println("Users ID" + "    " + "Name");
        System.out.println("--------------------------------------------");

        User[] users = accountService.getAllUsers(currentUser);
        for (User user : users) {
            if (user.getUsername().equals(currentUser.getUser().getUsername())) {
                continue;
            }
            System.out.println(user.getId() + "        " + user.getUsername());
        }

        System.out.println("--------------------------------------------");
        System.out.println();

        long userInput = consoleService.promptForLong("Enter ID of user you are requesting from (0 to cancel): ");
        if (userInput == 0 || userInput == currentUser.getUser().getId()) {
            System.out.println("Transaction cancelled");
        } else {
            long fromId = 0L;
            for (User user : users) {
                if (user.getId() == userInput) {
                    fromId = userInput;
                }
            }
        }

        BigDecimal transferAmount = consoleService.promptForBigDecimal("Enter amount: ");

        Account account1 = accountService.getAccountByUserId(userInput, currentUser);
        Account account2 = accountService.getAccountByUserId(currentUser.getUser().getId(), currentUser);
        int accountF = account1.getAccountId();

        int accountT = account2.getAccountId();


        try {
            if (transferAmount.compareTo(new BigDecimal(0)) > 0) {
                if (accountService.getBalance(currentUser).getBalance().compareTo(transferAmount) == 1) {

                    Transfer transfer = new Transfer();
                    transfer.setTransferTypeId(1);
                    transfer.setTransferStatusId(2);
                    transfer.setAccountFrom(accountF);
                    transfer.setAccountTo(accountT);
                    transfer.setAmount(transferAmount);
                    transferService.createTransfer(currentUser, transfer);
                    System.out.println("Transfer made successfully.");

                } else {
                    System.out.println("Not enough funds.");

                }
            } else {
                System.out.println("Invalid amount.");
            }
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
    }


}
