package com.github.denrion.internetprovider.constants;

public class ContractsTable {

    /* CONTACT TABLE AND ITS FIELDS */
    private static final String TABLE_CONTRACTS = "contract";
    public static final String COLUMN_CONTRACT_ID = "id";
    public static final String COLUMN_FIRST_NAME = "firstName";
    public static final String COLUMN_LAST_NAME = "lastName";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_SPEED = "speed";
    public static final String COLUMN_BANDWIDTH = "bandwidth";
    public static final String COLUMN_CONTRACT_LENGTH = "contractLength";
    public static final String COLUMN_CREATION_DATE = "creationDate";

    /* SEARCH QUERIES */
    public static final String GET_ALL_CONTRACTS = "SELECT * FROM " + TABLE_CONTRACTS;
    public static final String GET_CONTRACTS_BY_ID = GET_ALL_CONTRACTS + " WHERE id = ?";
    public static final String GET_CONTRACTS_BY_FIRST_NAME = GET_ALL_CONTRACTS + " WHERE firstName LIKE ? ORDER BY " + COLUMN_FIRST_NAME;
    public static final String GET_CONTRACTS_BY_LAST_NAME = GET_ALL_CONTRACTS + " WHERE lastName LIKE ? ORDER BY " + COLUMN_LAST_NAME;
    public static final String GET_CONTRACTS_BY_ADDRESS = GET_ALL_CONTRACTS + " WHERE address LIKE ? ORDER BY " + COLUMN_ADDRESS;
    public static final String GET_CONTRACTS_BY_SPEED = GET_ALL_CONTRACTS + " WHERE speed LIKE ? ORDER BY " + COLUMN_SPEED;
    public static final String GET_CONTRACTS_BY_BANDWIDTH = GET_ALL_CONTRACTS + " WHERE bandwidth LIKE ? ORDER BY " + COLUMN_BANDWIDTH;
    public static final String GET_CONTRACTS_BY_CONTRACT_LENGTH = GET_ALL_CONTRACTS + " WHERE contractLength LIKE ? ORDER BY " + COLUMN_CONTRACT_LENGTH;

    /* INSERT QUERIES */
    private static final String INSERT_INTO_CONTRACTS_FIELDS = " (firstName, lastName, address, speed, bandwidth, contractLength, creationDate)";
    private static final String INSERT_INTO_CONTRACTS_VALUES = " VALUES(?, ?, ?, ?, ?, ?, ?)";
    public static final String INSERT_INTO_CONTRACTS_QUERY = "INSERT INTO " + TABLE_CONTRACTS + INSERT_INTO_CONTRACTS_FIELDS + INSERT_INTO_CONTRACTS_VALUES;

    /* UPDATE QUERIES */
    private static final String UPDATE_CONTRACT_FIELDS = " SET firstName = ?, lastName = ?, address = ?, speed = ?, bandwidth = ?, contractLength = ?";
    public static final String UPDATE_CONTRACT_BY_ID = "UPDATE " + TABLE_CONTRACTS + UPDATE_CONTRACT_FIELDS + " WHERE id = ?";

    /* DELETE QUERIES */
    public static final String DELETE_CONTRACT_BY_ID = "DELETE FROM " + TABLE_CONTRACTS + " WHERE id = ?";

    private ContractsTable() {

    }
}
