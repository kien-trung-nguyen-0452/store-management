package com.javaProject.shopManagement.controllers.history;

import com.javaProject.shopManagement.dto.BatchInfoDTO;
import com.javaProject.shopManagement.services.implementation.BatchInfoServiceImpl;
import com.javaProject.shopManagement.services.implementation.SearchServiceImpl;
import com.javaProject.shopManagement.util.effectHandler.EffectHandler;
import com.javaProject.shopManagement.util.effectHandler.EffectType;
import com.javaProject.shopManagement.util.motion.DraggableNode;
import io.github.palexdev.materialfx.controls.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

public class StockInTabController {
        //--General FXML
        @FXML
        private TextField searchBar;
        @FXML private MFXButton searchButton;
        @FXML private AnchorPane tabPane;
        @FXML private MFXButton openFilterBtn;
        @FXML private MFXButton refreshDataBtn;
        @FXML private TableView<BatchInfoDTO> tableView;
        @FXML private TableColumn<BatchInfoDTO, String> batchCodeCol;
        @FXML private TableColumn<BatchInfoDTO, String> batchNameCol;
        @FXML private TableColumn<BatchInfoDTO, String> batchDateCol;
        @FXML private TableColumn<BatchInfoDTO, String> batchTotalAmountCol;
        @FXML private TableColumn<BatchInfoDTO, String> batchSupplierCol;
        @FXML private TableColumn<BatchInfoDTO, String> batchDescriptionCol;
        @FXML private MFXPagination pagination;

        //--Filter pane FXML
        @FXML private BorderPane filterPane;
        @FXML private MFXDatePicker startDatePicker;
        @FXML private MFXDatePicker endDatePicker;
        @FXML private MFXButton filterBtn;
        @FXML private MFXButton resetFilterPaneBtn;
        @FXML private MFXButton closeFilterPaneBtn;
        @FXML private MFXComboBox<String> filterComboBox;
        @FXML private StackPane filterContent;
        @FXML private HBox dateRangeOption;
        @FXML private MFXTextField supplierField;
        @FXML private MFXTextField batchNameField;

        //--Details card
       @FXML private AnchorPane batchDetailsCard;

        private enum FilterOption {
            DATE_RANGE, SUPPLIER, BATCH_NAME
        }

        private HashMap<FilterOption, String> filterOptions;

        //--Local state
        ObservableList<BatchInfoDTO> data;
        private final int ROWS_PER_PAGE =10;

        public StockInTabController(){
            try{
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/javaProject/shopManagement/public/views/stock_in_tab.fxml"));
                fxmlLoader.setController(this);
                tabPane = fxmlLoader.load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @FXML
        public void initialize() {
            data = FXCollections.observableArrayList();
            reFresh();
            setTableColumns();
            setTableItems();
            setDetailsCard();
            setGeneralButtons();
            setFilterPane();


        }


        private void reFresh(){
            List<BatchInfoDTO> batchInfoDTOList = BatchInfoServiceImpl.getInstance().getAll();
            reFresh(batchInfoDTOList);
        }
        private void reFresh(List<BatchInfoDTO> newDataList){
            closeDetailsCard();
            data.clear();
            data.addAll(newDataList);
            configurePagination();

        }
        private void reFresh(BatchInfoDTO batchInfoDTO){
            closeDetailsCard();
            data.clear();
            if(batchInfoDTO != null){
                data.add(batchInfoDTO);
            }
            configurePagination();
        }

        private void setGeneralButtons(){
            openFilterBtn.setOnAction(event -> {
                openFilterPane();
            });

            refreshDataBtn.setOnAction(event -> reFresh());
            searchButton.setOnAction(event -> searchByBatchCode());

        }

    private void searchByBatchCode(){
        reFresh(SearchServiceImpl.getInstance().searchBatchInfoById(searchBar.getText()));

    }

        private void setTableItems(){
            tableView.setItems(data);
        }

        private void setTableColumns(){
            batchCodeCol.setCellValueFactory(batchInfo ->new SimpleStringProperty(String.valueOf(batchInfo.getValue().getBatchId())));
            batchNameCol.setCellValueFactory(batchInfo ->new SimpleStringProperty(String.valueOf(batchInfo.getValue().getBatchName())));
            batchDateCol.setCellValueFactory(batchInfo ->new SimpleStringProperty(String.valueOf(batchInfo.getValue().getCreateDate())));
            batchTotalAmountCol.setCellValueFactory(batchInfo ->new SimpleStringProperty(String.valueOf(batchInfo.getValue().getTotalPrice())));
            batchSupplierCol.setCellValueFactory(batchInfo ->new SimpleStringProperty(String.valueOf(batchInfo.getValue().getSupplier())));
            batchDescriptionCol.setCellValueFactory(batchInfo ->new SimpleStringProperty(String.valueOf(batchInfo.getValue().getDescription())));
            setTableItems();

        }

        private void setFilterPane(){
             setFilterOptions();
             setComboBox();
             resetFilterPaneBtn.setOnAction(event -> resetFilterPane());
             closeFilterPaneBtn.setOnAction(event -> closeFilterPane());
             DraggableNode draggableNode = new DraggableNode();
             draggableNode.makeNodeDraggable(filterPane);

    }

        private void setFilterOptions(){
            filterOptions = new HashMap<>();
            filterOptions.put(FilterOption.DATE_RANGE, "Filter by date range");
            filterOptions.put(FilterOption.SUPPLIER, "Filter by Supplier");
            filterOptions.put(FilterOption.BATCH_NAME, "Filter by Batch Name");
        }

        private void setComboBox(){
            filterComboBox.getItems().add(filterOptions.get(FilterOption.DATE_RANGE));
            filterComboBox.getItems().add(filterOptions.get(FilterOption.SUPPLIER));
            filterComboBox.getItems().add(filterOptions.get(FilterOption.BATCH_NAME));

            filterComboBox.setOnAction(event -> {
                String value = filterComboBox.getSelectionModel().getSelectedItem();
                if(filterOptions.get(FilterOption.DATE_RANGE).equals(value)){
                   setOptionSelected(dateRangeOption);
                   filterBtn.setOnAction(e->filterByDateRange());
                }
                else if (filterOptions.get(FilterOption.SUPPLIER).equals(value)){
                       setOptionSelected(supplierField);
                       filterBtn.setOnAction(e->filterBySupplier());
                }else if (filterOptions.get(FilterOption.BATCH_NAME).equals(value)){
                    setOptionSelected(batchNameField);
                    filterBtn.setOnAction(e->filterByBatchName());
                }
            });
        }


        private void setOptionSelected(Node optionField){
            filterContent.getChildren().clear();
            filterContent.getChildren().addAll(optionField);
            optionField.setVisible(true);
            EffectHandler.getEffect(EffectType.SCROLL_DOWN, filterContent.getChildren().getFirst());
        }

        private void filterByDateRange(){
            Timestamp start = Timestamp.valueOf(startDatePicker.getValue().atStartOfDay());
            Timestamp end = Timestamp.valueOf(endDatePicker.getValue().atTime(23,59,59));
            reFresh(SearchServiceImpl.getInstance().searchBatchInfoByDateRange(start,end));
        }
        private void filterBySupplier(){
            reFresh(SearchServiceImpl.getInstance().searchBatchInfoBySupplier(supplierField.getText()));
        }
        private void filterByBatchName(){
            reFresh(SearchServiceImpl.getInstance().searchBatchInfoByBatchName(batchNameField.getText()));
        }
        private void resetFilterPane(){
            if(filterContent.getChildren().getFirst().equals(dateRangeOption)){
                startDatePicker.clear();
                endDatePicker.clear();
            }
            if(filterContent.getChildren().getFirst().equals(supplierField)){
                supplierField.clear();
            }
            if(filterContent.getChildren().getFirst().equals(batchNameField)){
                batchNameField.clear();
            }
        }
        private void closeFilterPane(){
            resetFilterPane();
            filterPane.setVisible(false);
        }
        private void openFilterPane(){
            EffectHandler.setShowUP(filterPane, filterPane.isVisible());
        }

        //--handle details card
    private void setDetailsCard(){
            tableView.setOnMouseClicked(event -> {
                batchDetailsCard.getChildren().clear();
                batchDetailsCard.setVisible(true);
                BatchInfoDTO batchInfoDTO = tableView.getSelectionModel().getSelectedItem();
                if(batchInfoDTO != null){
                    BatchDetailsCardController batchDetailsCardController = new BatchDetailsCardController(batchInfoDTO, this);
                    batchDetailsCard.getChildren().add(batchDetailsCardController.getNode());
                    System.out.println(batchDetailsCard.getChildren().isEmpty());
                    EffectHandler.getEffect(EffectType.FADE_IN, batchDetailsCard);
                }




            });
    }
    public void closeDetailsCard(){
        EffectHandler.getEffect(EffectType.FADE_OUT, batchDetailsCard);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), event -> {
            batchDetailsCard.getChildren().clear();
        }));
        timeline.setCycleCount(1);
        timeline.play();
    }

    //--config pagination
    private void configurePagination() {
        int totalPages = (int) Math.ceil((double) data.size() / ROWS_PER_PAGE);
        pagination.setMaxPage(totalPages);
        pagination.setCurrentPage(1); // Default to first page

        // Add listener to handle page change
        pagination.currentPageProperty().addListener((observable, oldValue, newValue) -> {
            updateTableView(newValue.intValue());
        });

        // Load the first page
        updateTableView(1);
    }

    private void updateTableView(int page){
        int fromIndex = (page - 1) * ROWS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, data.size());
        List<BatchInfoDTO> pageData = data.subList(fromIndex, toIndex);
        tableView.setItems(FXCollections.observableArrayList(pageData));
    }




    public Node getNode() {
            return tabPane;
        }



    }



