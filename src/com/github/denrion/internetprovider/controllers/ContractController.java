package com.github.denrion.internetprovider.controllers;

import com.github.denrion.internetprovider.Datasource;
import com.github.denrion.internetprovider.utils.ContractsFilter;
import com.github.denrion.internetprovider.entities.Contract;
import com.github.denrion.internetprovider.managers.ContractManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class ContractController {
    /* ************************* Personal information *************************** */
    @FXML
    private TextField firstNameTF;

    @FXML
    private TextField lastNameTF;

    @FXML
    private TextField addressTF;

    /* ************************** Contract information **************************** */
    @FXML
    private ChoiceBox<String> speedCB;

    @FXML
    private ChoiceBox<String> bandwidthCB;

    @FXML
    private ToggleGroup contractLength;

    @FXML
    private RadioButton year1RB;

    @FXML
    private RadioButton year2RB;

    /* ******************************** Buttons **************************** */
    @FXML
    private Button clearBtn;

    @FXML
    private Button addBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button deleteBtn;

    /* ******************************** View ***************************** */
    @FXML
    private TableView<Contract> contractTable;

    @FXML
    private ComboBox<String> searchByCB;

    @FXML
    private TextField searchByTF;
    private boolean isEmptySearchByTF;

    /* ******************************* Events ************************** */
    @FXML
    private void handleSearchOnKeyReleased() {
        String selectedItem = searchByCB.getSelectionModel().getSelectedItem();
        String searchText = searchByTF.getText();

        if (searchText.isBlank() && !isEmptySearchByTF) {
            // query all contracts
            listContracts(ContractsFilter.NONE, searchText);

            // stop triggering event if field was already empty
            isEmptySearchByTF = true;
        } else if (selectedItem != null && !searchText.isBlank()) {
            ContractsFilter filter = ContractsFilter.byDisplayName(selectedItem);
            listContracts(filter, searchText);
            isEmptySearchByTF = false;
        }
    }

    @FXML
    private void handleClearFieldsBtnPressed() {
        clearFields();
    }

    @FXML
    private void handleAddOnBtnPressed() {
        Contract contract = getContractByTableFields();
        addContract(contract);
    }

    @FXML
    private void handleDeleteOnBtnPressed() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Trying to delete the selected contract");
        alert.setContentText("Are you sure you want to delete this contract?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Contract selectedItem = getSelectedContract();
                deleteSelectedContract(selectedItem);
            }
        });
    }

    @FXML
    private void handleClickOnTableView() {
        getSelectedContractFields(getSelectedContract());
    }

    @FXML
    private void handleUpdateOnBtnPressed() {
        final Contract selectedContract = getSelectedContract();
        final Contract updatedContract = getContractByTableFields();

        updateContract(selectedContract, updatedContract);
    }

    /* ************************* Helper methods ************************** */
    public void listContracts(ContractsFilter filter, String searchText) {
        Task<ObservableList<Contract>> task = new GetContractsTask(filter, searchText);
        contractTable.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
    }

    private void addContract(Contract contract) {
        if (contract != null && !isInTableAlready(contract)) {
            Task<Integer> task = new Task<>() {
                @Override
                protected Integer call() {
                    return ContractManager.getInstance().addContract(contract);
                }
            };
            task.setOnSucceeded(e -> {
                if (task.valueProperty().get() != -1) {
                    contract.setId(task.valueProperty().get());
                    contract.setCreationDate(Timestamp.valueOf(LocalDateTime.now()).toString());
                    contractTable.getItems().add(contract);
                    clearFields();
                    disableButtons();
                }
            });

            new Thread(task).start();

        } else if (contract != null && isInTableAlready(contract)) {
            showInfoAlert("Error", "Could not add the contract", "This contract already exists");
        }
    }

    private void deleteSelectedContract(Contract selectedContract) {
        if (selectedContract != null) {
            Task<Integer> task = new Task<>() {
                @Override
                protected Integer call() {
                    return ContractManager.getInstance().deleteContractById(selectedContract.getId());
                }
            };

            task.setOnSucceeded(e -> {
                if (task.valueProperty().get() > 0) {
                    contractTable.getItems().remove(selectedContract);
                    clearFields();
                    disableButtons();
                }
            });

            new Thread(task).start();
        }
    }

    private void updateContract(Contract selectedContract, Contract updatedContract) {
        if (isInTableAlready(updatedContract)) {
            showInfoAlert("Error", "Could not update contract", "This contract already exists");
        }

        if (selectedContract != null && updatedContract != null && !isInTableAlready(updatedContract)) {
            selectedContract.setFirstName(updatedContract.getFirstName());
            selectedContract.setLastName(updatedContract.getLastName());
            selectedContract.setAddress(updatedContract.getAddress());
            selectedContract.setSpeed(updatedContract.getSpeed());
            selectedContract.setBandwidth(updatedContract.getBandwidth());
            selectedContract.setContractLength(updatedContract.getContractLength());

            Task<Integer> task = new Task<>() {
                @Override
                protected Integer call() {
                    return ContractManager.getInstance().updateContract(selectedContract);
                }
            };
            task.setOnSucceeded(e -> {
                if (task.valueProperty().get() > 0) {
                    contractTable.refresh();
                    clearFields();
                    disableButtons();
                }
            });

            new Thread(task).start();
        }
    }

    private void getSelectedContractFields(Contract contract) {
        if (contract != null) {
            firstNameTF.setText(contract.getFirstName());
            lastNameTF.setText(contract.getLastName());
            addressTF.setText(contract.getAddress());
            speedCB.setValue(contract.getSpeed());
            bandwidthCB.setValue(contract.getBandwidth());
            contractLength.selectToggle(contract.getContractLength().equals("1 year") ? year1RB : year2RB);
            deleteBtn.setDisable(false);
            updateBtn.setDisable(false);
        }
    }

    private Contract getSelectedContract() {
        return contractTable.getSelectionModel().getSelectedItem();
    }

    private Contract getContractByTableFields() {
        Contract contract = null;
        String firstName = this.firstNameTF.getText().trim();
        String lastName = this.lastNameTF.getText().trim();
        String address = this.addressTF.getText().trim();
        String speed = this.speedCB.getSelectionModel().getSelectedItem().trim();
        String bandwidth = this.bandwidthCB.getSelectionModel().getSelectedItem().trim();
        String contractLength = ((RadioButton) this.contractLength.getSelectedToggle()).getText();
        if (!firstName.isBlank() && !lastName.isBlank() && !address.isBlank() && !speed.isBlank() && !bandwidth.isBlank() && !contractLength.isBlank()) {
            contract = ContractManager.getInstance().createContract(firstName, lastName, address, speed, bandwidth, contractLength);
        } else {
            showInfoAlert("Error!", "Could not add the contract", "Please fill out all fields");
        }
        return contract;
    }

    private void clearFields() {
        this.firstNameTF.setText("");
        this.lastNameTF.setText("");
        this.addressTF.setText("");
        this.speedCB.setValue("2 MBit");
        this.bandwidthCB.setValue("1 GB");
        this.contractLength.selectToggle(year1RB);
        contractTable.getSelectionModel().clearSelection();
        disableButtons();
    }

    public void disableButtons() {
        updateBtn.setDisable(true);
        deleteBtn.setDisable(true);
    }

    private boolean isInTableAlready(Contract contract) {
        return contractTable.getItems().contains(contract);
    }

    private void showInfoAlert(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.show();
    }

}

class GetContractsTask extends Task {
    private String searchText;
    private ContractsFilter filter;

    GetContractsTask(ContractsFilter filter, String searchText) {
        this.searchText = searchText;
        this.filter = filter;
    }

    @Override
    public ObservableList<Contract> call() {
        return FXCollections.observableArrayList(getContracts(filter, searchText));
    }

    private List<Contract> getContracts(ContractsFilter filter, String searchText) {
        return ContractManager.getInstance().getContracts(filter, searchText);
    }

}
