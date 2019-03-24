package com.github.denrion.internetprovider.dao;

import com.github.denrion.internetprovider.Datasource;
import com.github.denrion.internetprovider.utils.ContractsFilter;
import com.github.denrion.internetprovider.entities.Contract;

import java.sql.SQLException;
import java.util.List;

public class ContractDAO {

    public List<Contract> getContracts(ContractsFilter filter, String param) throws SQLException {
        return Datasource.getInstance().getContracts(filter, param);
    }

    public int addContract(Contract contract) throws SQLException {
        return Datasource.getInstance().addContract(contract);
    }

    public int deleteContractById(int id) throws SQLException {
        return Datasource.getInstance().deleteContractById(id);
    }

    public int updateContract(Contract selectedContract) throws SQLException {
        return Datasource.getInstance().updateContract(selectedContract);
    }
}
