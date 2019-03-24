package com.github.denrion.internetprovider.managers;

import com.github.denrion.internetprovider.utils.ContractsFilter;
import com.github.denrion.internetprovider.dao.ContractDAO;
import com.github.denrion.internetprovider.entities.Contract;

import java.sql.SQLException;
import java.util.List;

public class ContractManager {
    private static ContractManager instance;
    private static ContractDAO dao = new ContractDAO();

    private ContractManager() {
    }

    public static ContractManager getInstance() {
        if (instance == null) {
            instance = new ContractManager();
        }
        return instance;
    }

    public Contract createContract(int id, String firstName, String lastName, String address, String speed, String bandwidth, String contractLength, String creationDate) {

        Contract contract = createContract(firstName, lastName, address, speed, bandwidth, contractLength);

        contract.setId(id);
        contract.setCreationDate(creationDate);

        return contract;
    }

    public Contract createContract(String firstName, String lastName, String address, String speed, String bandwidth, String contractLength) {

        Contract contract = new Contract();

        contract.setFirstName(firstName);
        contract.setLastName(lastName);
        contract.setAddress(address);
        contract.setSpeed(speed);
        contract.setBandwidth(bandwidth);
        contract.setContractLength(contractLength);

        return contract;
    }

    public List<Contract> getContracts(ContractsFilter filter, String param) {

        // SHOULD I CHECK THIS???!!!

        if (filter == null) {
            filter = ContractsFilter.NONE;
        }
        if (param == null || param.isBlank()) {
            param = null;
        }

        try {
            return dao.getContracts(filter, param);
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public int addContract(Contract contract) {
        try {
            return dao.addContract(contract);
        } catch (SQLException e) {
            System.out.println("Insert failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public int deleteContractById(int id) {
        try {
            return dao.deleteContractById(id);
        } catch (SQLException e) {
            System.out.println("Delete failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public int updateContract(Contract selectedContract) {
        try {
            return dao.updateContract(selectedContract);
        } catch (SQLException e) {
            System.out.println("Update failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
