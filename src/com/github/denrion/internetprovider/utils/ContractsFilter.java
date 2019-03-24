package com.github.denrion.internetprovider.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

import static com.github.denrion.internetprovider.constants.ContractsTable.*;

public enum ContractsFilter {

    NONE("", GET_ALL_CONTRACTS) {
        @Override
        public void setParameter(PreparedStatement ps, String param) throws SQLException {
            // there is no param, get all contracts
        }
    },
    ID("ID", GET_CONTRACTS_BY_ID) {
        @Override
        public void setParameter(PreparedStatement ps, String param) throws SQLException {
            ps.setInt(1, Integer.parseInt(param));
        }
    },
    FIRST_NAME("First Name", GET_CONTRACTS_BY_FIRST_NAME) {
        @Override
        public void setParameter(PreparedStatement ps, String param) throws SQLException {
            ps.setString(1, "%" + param + "%");
        }
    },
    LAST_NAME("Last Name", GET_CONTRACTS_BY_LAST_NAME) {
        @Override
        public void setParameter(PreparedStatement ps, String param) throws SQLException {
            ps.setString(1, "%" + param + "%");
        }
    },
    ADDRESS("Address", GET_CONTRACTS_BY_ADDRESS) {
        @Override
        public void setParameter(PreparedStatement ps, String param) throws SQLException {
            ps.setString(1, "%" + param + "%");
        }
    },
    SPEED("Speed", GET_CONTRACTS_BY_SPEED) {
        @Override
        public void setParameter(PreparedStatement ps, String param) throws SQLException {
            ps.setString(1, param + "%");
        }
    },
    BANDWIDTH("Bandwidth", GET_CONTRACTS_BY_BANDWIDTH) {
        @Override
        public void setParameter(PreparedStatement ps, String param) throws SQLException {
            ps.setString(1, param + "%");
        }
    },
    CONTRACTS_LENGTH("Contract Length", GET_CONTRACTS_BY_CONTRACT_LENGTH) {
        @Override
        public void setParameter(PreparedStatement ps, String param) throws SQLException {
            ps.setString(1, param + "%");
        }
    };
//    CREATION_DATE("Creation Date", GET_CONTRACTS_BY_CREATION_DATE) {
//        @Override
//        public void setParameter(PreparedStatement ps, String param) {
//            // TODO Auto-generated method stub
//
//        }
//    };

    private String query;
    private String displayName;

    ContractsFilter(String displayName, String query) {
        this.displayName = displayName;
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public static ContractsFilter byDisplayName(String name) {
        return Arrays.stream(values())
                .filter(cf -> cf.displayName.equals(name))
                .findAny()
                .orElseGet(null);
    }

    public abstract void setParameter(PreparedStatement ps, String param) throws SQLException;

}
