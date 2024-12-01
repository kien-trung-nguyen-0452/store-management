package com.javaProject.shopManagement.dao.implementation;

import com.javaProject.shopManagement.config.DbUtils;
import com.javaProject.shopManagement.dao.interfaces.BatchInfoDAO;
import com.javaProject.shopManagement.exception.GlobalExceptionHandler;
import com.javaProject.shopManagement.entity.BatchInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BatchInfoDAOImpl implements BatchInfoDAO {
    public static BatchInfoDAOImpl getInstance(){
        return new BatchInfoDAOImpl();
    }

    @Override
    public List<BatchInfo> getAll() {
        List<BatchInfo> batchInfoList = new ArrayList<>();
        String query = "SELECT * FROM batch_infor ORDER BY create_date DESC ";

        try (Connection conn = DbUtils.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                BatchInfo batchInfo = new BatchInfo();
                readAllFromResultSet(resultSet, batchInfo);
                batchInfoList.add(batchInfo);
            }

        } catch (SQLException e) {
            GlobalExceptionHandler.handleException(e);
        }

        return batchInfoList;

    }

    @Override
    public BatchInfo getById(int id) {
        String query = "SELECT * FROM batch_infor WHERE batch_id = ?";
        BatchInfo batchInfo = null;

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                batchInfo = new BatchInfo();
                readAllFromResultSet(resultSet, batchInfo);
            }
        } catch (SQLException e) {
            GlobalExceptionHandler.handleException(e);
        }

        return batchInfo;
    }

    @Override
    public List<BatchInfo> getByCondition(String condition) {
        List<BatchInfo> batchInfoList = new ArrayList<>();
        if(condition == null || condition.isEmpty()){
            return null;
        }
        String query = "SELECT * FROM batch_infor WHERE supplier Like ?";
        String keyword = "%" + condition + "%";
        try(Connection conn = DbUtils.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query)){
            preparedStatement.setString(1, keyword);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                BatchInfo batchInfo = new BatchInfo();
                readAllFromResultSet(resultSet, batchInfo);
                batchInfoList.add(batchInfo);
            }

        }catch (SQLException e){
            GlobalExceptionHandler.handleException(e);
        }
        return batchInfoList;
    }

    @Override
    public void add(BatchInfo entity) {
        String query = "INSERT INTO batch_infor(batch_id, batch_name, create_date, description, supplier, total_price) VALUES (?,?,?,?,?,?)";
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
             GlobalExceptionHandler.handleException(e);
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
            GlobalExceptionHandler.handleException(e);
        }

    }

    @Override
    public void update(BatchInfo entity) {

        String query = "UPDATE batch_infor SET batch_name = ?, description = ? WHERE batch_id = ?";

        try(Connection conn = DbUtils.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);)
        {
            preparedStatement.setString(1, entity.getBatchName());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setInt(3, entity.getBatchId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<BatchInfo> getByDateRange(Timestamp start, Timestamp end) {
        String query = "SELECT * FROM batch_infor WHERE create_date BETWEEN ? AND ? ORDER BY create_date DESC ";
        List<BatchInfo> batchInfoList = new ArrayList<>();
        try(Connection conn = DbUtils.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(query)){
            preparedStatement.setTimestamp(1, start);
            preparedStatement.setTimestamp(2, end);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                BatchInfo batchInfo = new BatchInfo();
                readAllFromResultSet(resultSet, batchInfo);
                batchInfoList.add(batchInfo);
            }
        }
        catch (SQLException e){
            GlobalExceptionHandler.handleException(e);
        }
        return batchInfoList;
    }

    @Override
    public BatchInfo getByBatchName(String batchName) {
        String query = "SELECT * FROM batch_infor WHERE batch_name LIKE?";

        String keyword = "%" + batchName + "%";
        try(Connection conn = DbUtils.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(query);){
            preparedStatement.setString(1, keyword);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                BatchInfo batchInfo = new BatchInfo();
                readAllFromResultSet(resultSet, batchInfo);
                return batchInfo;
            }
        }
        catch (SQLException e){
            GlobalExceptionHandler.handleException(e);
        }
        return null;
    }

    private void readAllFromResultSet(ResultSet resultSet, BatchInfo batchInfo) throws SQLException {
        batchInfo.setBatchId(resultSet.getInt("batch_id"));
        batchInfo.setBatchName(resultSet.getString("batch_name"));
        batchInfo.setCreateDate(resultSet.getTimestamp("create_date"));
        batchInfo.setDescription(resultSet.getString("description"));
        batchInfo.setSupplier(resultSet.getString("supplier"));
        batchInfo.setTotalPrice(resultSet.getDouble("total_price"));
    }
}
