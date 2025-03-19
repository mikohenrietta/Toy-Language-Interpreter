package GUI;
import controller.Controller;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import controller.Controller;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import model.adt.*;
import model.statement.IStatement;
import model.values.IValue;
import model.values.IntValue;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    private Controller controller;

    @FXML
    private TextField prgStatesTextField;
    @FXML
    private TableView<Pair<Integer, IValue>> heapTableView;
    @FXML
    private TableColumn<Pair<Integer, IValue>, Integer> addressColumn;
    @FXML
    private TableColumn<Pair<Integer, IValue>, IValue> valColumn;
    @FXML
    private ListView<IValue> outListView;
    @FXML
    private ListView<String> fileListView;
    @FXML
    private ListView<Integer> prgStatesIdsListView;
    @FXML
    private TableView<Pair<String, IValue>> symTableView;
    @FXML
    private TableColumn<Pair<String, IValue>, String> keyColumn;
    @FXML
    private TableColumn<Pair<String, IValue>, IValue> valueColumn;
    @FXML
    private ListView<IStatement> exeStackListView;
    @FXML
    public void initialize() {
        keyColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getKey())
        );
        valueColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getValue())
        );

        addressColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getKey())
        );
        valColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getValue())
        );
    }
    public void setController(Controller controller) {
        this.controller = controller;
        this.setAllFields();
    }
    @FXML
    public void setAllFields(){
        this.setNrProgramStates();
        this.setHeap();
        this.setOut();
        this.setIdentifiers();
        this.setFileListView();
    }
    @FXML
    public void setNrProgramStates(){
        int nr = controller.getNrOfProgramStates();
        prgStatesTextField.setText(Integer.toString(nr));
    }

    @FXML
    public void programStateChosen(){
        if(controller.getNrOfProgramStates() == 0)
        {
            IStack<IStatement> e = new GenericStack<>();
            this.setExeStack(e);

            return;
        }

        int index = prgStatesIdsListView.getSelectionModel().getSelectedItem();
        IMap<String, IValue> symTable = controller.getSymbolTable(index);
        IStack<IStatement> exeStack = controller.getExeStack(index);
        this.setSymTable(symTable);
        this.setExeStack(exeStack);
    }
    @FXML
    public void setFileListView(){
        ObservableList<String> list = FXCollections.observableArrayList();
        List<String> vals = controller.getFileTable().getElements();
        list.addAll(vals);
        fileListView.setItems(list);
    }

    @FXML
    public void setSymTable(IMap<String, IValue> symTable){
        ObservableList<Pair<String, IValue>> data = FXCollections.observableArrayList();
        symTable.getContent().entrySet().stream().forEach(entry -> data.add(new Pair<>(entry.getKey(), entry.getValue())));
        symTableView.setItems(data);
    }
    @FXML
    public void setExeStack(IStack<IStatement> exeStack){
        ObservableList<IStatement> exe = FXCollections.observableArrayList();
        exe.addAll(exeStack.getStack().reversed());
        exeStackListView.setItems(exe);
    }
    @FXML
    public void setHeap(){
        ObservableList<Pair<Integer, IValue>> data = FXCollections.observableArrayList();
        IHeap heap = controller.getHeap();
        heap.getValues().entrySet().forEach(e-> data.add(new Pair<>(e.getKey(), e.getValue())));
        heapTableView.setItems(data);
    }
    @FXML
    public void setOut(){
        ObservableList<IValue> outList = FXCollections.observableArrayList();
        IList<IValue> out = controller.getOut();
        outList.addAll(out.getAll());
        outListView.setItems(outList);
    }
    @FXML
    public void setIdentifiers(){
        ObservableList<Integer> idsList = FXCollections.observableArrayList();
        List<Integer> ids = controller.getIdentifiers();
        idsList.addAll(ids);
        prgStatesIdsListView.setItems(idsList);
    }
    @FXML
    public void oneStep() throws InterruptedException {
        if(controller.getNrOfProgramStates() <= 0){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("There are no steps left!");
            a.show();
            return;
        }
        controller.oneStep();
        this.setAllFields();
        controller.removePrograms();
        if(controller.getNrOfProgramStates() == 0)
            controller.shutDown();
    }

}
