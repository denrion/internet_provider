package com.github.denrion.internetprovider;

import com.github.denrion.internetprovider.utils.ContractsFilter;
import com.github.denrion.internetprovider.entities.Contract;
import com.github.denrion.internetprovider.managers.ContractManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.github.denrion.internetprovider.constants.ConnectionConstants.*;
import static com.github.denrion.internetprovider.constants.ContractsTable.*;

public class Datasource {

    /* Datasource SINGLETON */
    private static Datasource instance;

    private Datasource() {

    }

    public static Datasource getInstance() {
        if (instance == null) {
            instance = new Datasource();
        }
        return instance;
    }

    /* OPEN / CLOSE DATABASE CONNECTION */
    private Connection conn;

    void connect() throws SQLException {
        conn = DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
    }

    void disconnect() throws SQLException {
        conn.close();
    }

    /* SEARCH */

    /**
     * @param filter
     * @param param
     * @return List of contracts
     * @throws SQLException
     */
    public List<Contract> getContracts(ContractsFilter filter, String param) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(filter.getQuery());
        filter.setParameter(ps, param);
        ResultSet rs = ps.executeQuery();

        List<Contract> contracts = new ArrayList<>();
        while (rs.next()) {
            Contract contract = ContractManager.getInstance().createContract(
                    rs.getInt(COLUMN_CONTRACT_ID),
                    rs.getString(COLUMN_FIRST_NAME),
                    rs.getString(COLUMN_LAST_NAME),
                    rs.getString(COLUMN_ADDRESS),
                    rs.getString(COLUMN_SPEED),
                    rs.getString(COLUMN_BANDWIDTH),
                    rs.getString(COLUMN_CONTRACT_LENGTH),
                    rs.getString(COLUMN_CREATION_DATE));
            contracts.add(contract);
        }

        return contracts;
    }

    /* INSERT */

    /**
     * @param contract
     * @return id of inserted contract or -1 if insertion was unsuccessful
     * @throws SQLException
     */
    public int addContract(Contract contract) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(INSERT_INTO_CONTRACTS_QUERY, Statement.RETURN_GENERATED_KEYS);
        prepareCommonStatementsForAddAndUpdate(stmt, contract);
        stmt.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));

        int affectedRows = stmt.executeUpdate();
        if (affectedRows < 1) {
            throw new SQLException("Error encountered while trying to create a contract, no rows affected.");
        }

        ResultSet rs = stmt.getGeneratedKeys();
        int id = -1;
        if (rs.next()) {
            id = rs.getInt(1);
        }

        return id;
    }

    /* UPDATE */

    /**
     * @param contract
     * @return num of affected rows
     * @throws SQLException
     */
    public int updateContract(Contract contract) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(UPDATE_CONTRACT_BY_ID);
        prepareCommonStatementsForAddAndUpdate(stmt, contract);
        stmt.setInt(7, contract.getId());

        int affectedRows = stmt.executeUpdate();
        if (affectedRows < 1) {
            throw new SQLException("Error encountered while trying to update a contract: No rows affected.");
        }

        System.out.println("Contract updated successfully");

        return affectedRows;
    }

    /* DELETE */

    /**
     * @param id
     * @return num of affected rows
     * @throws SQLException
     */
    public int deleteContractById(int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(DELETE_CONTRACT_BY_ID);
        stmt.setInt(1, id);

        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected < 1) {
            throw new SQLException("Error encountered while trying to delete a contract: No rows affected.");
        }

        System.out.println("Successfully deleted " + rowsAffected + (rowsAffected > 1 ? " rows" : " row"));

        return rowsAffected;
    }

    /* HELPER METHODS */
    private void prepareCommonStatementsForAddAndUpdate(PreparedStatement preparedStatement, Contract contract) throws SQLException {
        preparedStatement.setString(1, contract.getFirstName());
        preparedStatement.setString(2, contract.getLastName());
        preparedStatement.setString(3, contract.getAddress());
        preparedStatement.setString(4, contract.getSpeed());
        preparedStatement.setString(5, contract.getBandwidth());
        preparedStatement.setString(6, contract.getContractLength());
    }
}

