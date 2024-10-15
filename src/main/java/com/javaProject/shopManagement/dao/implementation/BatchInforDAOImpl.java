package com.javaProject.shopManagement.dao.implementation;

import com.javaProject.shopManagement.config.DbUtils;
import com.javaProject.shopManagement.dao.interfaces.BacthInforDAO;
import com.javaProject.shopManagement.exception.GlobalExeptionHandler;
import com.javaProject.shopManagement.models.BatchInfor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BatchInforDAOImpl implements BacthInforDAO {
    public static BatchInforDAOImpl getInstance(){
        return new BatchInforDAOImpl();
    }

    @Override
    public List<BatchInfor> getAll() {
        List<BatchInfor> batchInforList = new ArrayList<>();
        String query = "SELECT * FROM batch_infor";

        try (Connection conn = DbUtils.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                BatchInfor batchInfor = new BatchInfor();
                readAllFromResultSet(resultSet, batchInfor);
                batchInforList.add(batchInfor);
            }

        } catch (SQLException e) {
            GlobalExeptionHandler.handleException(e);
        }

        return batchInforList;

    }

    @Override
    public BatchInfor getById(int id) {
        String query = "SELECT * FROM batch_infor WHERE id = ?";
        BatchInfor batchInfor = new BatchInfor();

        try(Connection conn = DbUtils.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                readAllFromResultSet(resultSet, batchInfor);
            }
        } catch (SQLException e) {
            GlobalExeptionHandler.handleException(e);
        }
       return batchInfor;
    }

    @Override
    public List<BatchInfor> getByCondition(String condition) {
        List<BatchInfor> batchInforList = new ArrayList<>();
        if(condition == null || condition.isEmpty()){
            return null;
        }
        String query = "SELECT * FROM batch_infor WHERE "+ condition;

        try(Connection conn = DbUtils.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                BatchInfor batchInfor = new BatchInfor();
                readAllFromResultSet(resultSet, batchInfor);
                batchInforList.add(batchInfor);
            }

        }catch (SQLException e){
            GlobalExeptionHandler.handleException(e);
        }
        return batchInforList;
    }

    @Override
    public void add(BatchInfor entity) {
        String query = "INSERT INTO batch_infor(batch_id, batch_name, created_date, description, supplier, total_price) VALUES (?,?,?,?,?,?)";
         try(Connection conn = DbUtils.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)
         ){
             preparedStatement.setInt(1, entity.getBatchId());
             preparedStatement.setString(2, entity.getBatchName());
             preparedStatement.setTimestamp(3, entity.getCreateDate());
             preparedStatement.setString(4, entity.getDescription());
             preparedStatement.setString(5, entity.getSupplier());
             preparedStatement.setDouble(6, entity.getTotalPrice());
             preparedStatement.executeUpdate();

         }catch (SQLException e){
             GlobalExeptionHandler.handleException(e);
         }

    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM batch_infor WHERE id = ?";

        try(
                Connection conn = DbUtils.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(query)){
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            GlobalExeptionHandler.handleException(e);
        }

    }

    private void readAllFromResultSet(ResultSet resultSet, BatchInfor batchInfor) throws SQLException {
        batchInfor.setBatchId(resultSet.getInt("batch_id"));
        batchInfor.setBatchName(resultSet.getString("batch_name"));
        batchInfor.setCreateDate(resultSet.getTimestamp("create_date"));
        batchInfor.setDescription(resultSet.getString("description"));
        batchInfor.setSupplier(resultSet.getString("supplier"));
        batchInfor.setTotalPrice(resultSet.getDouble("total_price"));
    }
}
