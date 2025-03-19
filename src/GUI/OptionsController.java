package GUI;
import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.adt.*;
import model.expressions.*;
import model.state.ProgramState;
import model.statement.*;
import model.types.*;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;
import repository.IRepository;
import repository.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import GUI.MainController;

public class OptionsController {
    @FXML
    private ListView<String> programsList;

    private List<Controller> controllers;

    @FXML
    public void runProgram(MouseEvent mouseEvent) throws IOException {
        int index = programsList.getSelectionModel().getSelectedIndex();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();

        ((MainController)loader.getController()).setController(controllers.get(index));

        Stage secondStage = new Stage();
        secondStage.setTitle("Running program");
        secondStage.setScene(new Scene(root));

        secondStage.show();
    }

    @FXML
    public void initialize(){
        controllers = new ArrayList<>();
        programsList.getItems().clear();
        ObservableList<String> programs = FXCollections.observableArrayList();
        IStatement ex1 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))));
        GenericStack<IStatement> e1 = new GenericStack<>();
        GenericMap<String, IValue> s1 = new GenericMap<>();
        GenericList<IValue> o1 = new GenericList<>();
        FileTable f1 = new FileTable();
        Heap h1 = new Heap();
        IMap<String, IType> typeEnv1 = new GenericMap<>();
        try {
            ex1.typecheck(typeEnv1);
            ProgramState prg1 = new ProgramState(e1, s1, o1, ex1, f1, h1);
            IRepository repo1 = new Repository(prg1, "log1.txt");
            Controller ctr1 = new Controller(repo1);
            programs.add(ex1.toString());
            controllers.add(ctr1);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        IStatement ex2 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()), new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression(1, new ValueExpression(new IntValue(2)),
                        new ArithmeticExpression(3, new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                        new CompoundStatement(new AssignmentStatement("b", new ArithmeticExpression(1, new VariableExpression("a"), new ValueExpression(new IntValue(1)))),
                                new PrintStatement(new VariableExpression("b"))))));
        GenericStack<IStatement> e2 = new GenericStack<>();
        GenericMap<String, IValue> s2 = new GenericMap<>();
        GenericList<IValue> o2 = new GenericList<>();
        FileTable f2 = new FileTable();
        Heap h2 = new Heap();
        IMap<String, IType> typeEnv2 = new GenericMap<>();
        try {
            ex2.typecheck(typeEnv2);
            ProgramState prg2 = new ProgramState(e2, s2, o2, ex2, f2, h2);
            IRepository repo2 = new Repository(prg2, "log2.txt");
            Controller ctr2 = new Controller(repo2);
            programs.add(ex2.toString());
            controllers.add(ctr2);

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        IStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"), new AssignmentStatement("v", new ValueExpression(new
                                        IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));
        GenericStack<IStatement> e3 = new GenericStack<>();
        GenericMap<String, IValue> s3 = new GenericMap<>();
        GenericList<IValue> o3 = new GenericList<>();
        FileTable f3 = new FileTable();
        Heap h3 = new Heap();
        IMap<String, IType> typeEnv3 = new GenericMap<>();
        try {
            ex3.typecheck(typeEnv3);
            ProgramState prg3 = new ProgramState(e3, s3, o3, ex3, f3, h3);
            IRepository repo3 = new Repository(prg3, "log3.txt");
            Controller ctr3 = new Controller(repo3);
            programs.add(ex3.toString());
            controllers.add(ctr3);

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }


        IStatement ex4 = new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()),
                new CompoundStatement(new AssignmentStatement("varf", new ValueExpression(new StringValue("C:\\Users\\mkkla\\Desktop\\javalabs\\interpreter\\src\\test.in"))),
                        new CompoundStatement(new OpenReadFileStatement(new VariableExpression("varf")),
                                new CompoundStatement(new VariableDeclarationStatement("varc", new IntType()),
                                        new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseReadFileStatement(new VariableExpression("varf"))))))))));

        GenericStack<IStatement> e4 = new GenericStack<>();
        GenericMap<String, IValue> s4 = new GenericMap<>();
        GenericList<IValue> o4 = new GenericList<>();
        FileTable f4 = new FileTable();
        Heap h4 = new Heap();
        IMap<String, IType> typeEnv4 = new GenericMap<>();
        try {
            ex4.typecheck(typeEnv4);
            ProgramState prg4 = new ProgramState(e4, s4, o4, ex4, f4, h4);
            IRepository repo4 = new Repository(prg4, "log4.txt");
            Controller ctr4 = new Controller(repo4);
            programs.add(ex4.toString());
            controllers.add(ctr4);

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        IStatement ex5 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new HeapAllocation("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(new HeapAllocation("a", new VariableExpression("v")),
                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new VariableExpression("a")))))));
        GenericStack<IStatement> e5 = new GenericStack<>();
        GenericMap<String, IValue> s5 = new GenericMap<>();
        GenericList<IValue> o5 = new GenericList<>();
        FileTable f5 = new FileTable();
        Heap h5 = new Heap();
        IMap<String, IType> typeEnv5 = new GenericMap<>();
        try {
            ex5.typecheck(typeEnv5);
            ProgramState prg5 = new ProgramState(e5, s5, o5, ex5, f5, h5);
            IRepository repo5 = new Repository(prg5, "log5.txt");
            Controller ctr5 = new Controller(repo5);
            programs.add(ex5.toString());
            controllers.add(ctr5);

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        IStatement ex6 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(new WhileStatement(new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), ">"),
                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignmentStatement("v", new ArithmeticExpression(2,new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                                new PrintStatement(new VariableExpression("v")))));

        GenericStack<IStatement> e6 = new GenericStack<>();
        GenericMap<String, IValue> s6 = new GenericMap<>();
        GenericList<IValue> o6 = new GenericList<>();
        FileTable f6 = new FileTable();
        Heap h6 = new Heap();
        IMap<String, IType> typeEnv6 = new GenericMap<>();
        try {
            ex6.typecheck(typeEnv6);
            ProgramState prg6 = new ProgramState(e6, s6, o6, ex6, f6, h6);
            IRepository repo6 = new Repository(prg6, "log6.txt");
            Controller ctr6 = new Controller(repo6);
            programs.add(ex6.toString());
            controllers.add(ctr6);

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        IStatement ex7 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new HeapAllocation("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(new HeapAllocation("a", new VariableExpression("v")),
                                        new CompoundStatement(new PrintStatement(new ReadHeap(new VariableExpression("v"))),
                                                new PrintStatement(new ArithmeticExpression(1, new ReadHeap(new ReadHeap(new VariableExpression("a"))), new ValueExpression(new IntValue(5)))))))));
        GenericStack<IStatement> e7 = new GenericStack<>();
        GenericMap<String, IValue> s7 = new GenericMap<>();
        GenericList<IValue> o7 = new GenericList<>();
        FileTable f7 = new FileTable();
        Heap h7 = new Heap();
        IMap<String, IType> typeEnv7 = new GenericMap<>();
        try {
            ex7.typecheck(typeEnv7);
            ProgramState prg7 = new ProgramState(e7, s7, o7, ex7, f7, h7);
            IRepository repo7 = new Repository(prg7, "log7.txt");
            Controller ctr7 = new Controller(repo7);
            programs.add(ex7.toString());
            controllers.add(ctr7);

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        IStatement ex8 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new HeapAllocation("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new PrintStatement(new ReadHeap(new VariableExpression("v"))),
                                new CompoundStatement(new WriteHeapStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new ArithmeticExpression(1, new ReadHeap(new VariableExpression("v")), new ValueExpression(new IntValue(5))))))));
        GenericStack<IStatement> e8 = new GenericStack<>();
        GenericMap<String, IValue> s8 = new GenericMap<>();
        GenericList<IValue> o8 = new GenericList<>();
        FileTable f8 = new FileTable();
        Heap h8 = new Heap();
        IMap<String, IType> typeEnv8 = new GenericMap<>();
        try {
            ex8.typecheck(typeEnv8);
            ProgramState prg8 = new ProgramState(e8, s8, o8, ex8, f8, h8);
            IRepository repo8 = new Repository(prg8, "log8.txt");
            Controller ctr8 = new Controller(repo8);
            programs.add(ex8.toString());
            controllers.add(ctr8);

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        IStatement ex9 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new HeapAllocation("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(new HeapAllocation("a", new VariableExpression("v")),
                                        new CompoundStatement(new HeapAllocation("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new ReadHeap(new ReadHeap(new VariableExpression("a")))))))));
        GenericStack<IStatement> e9 = new GenericStack<>();
        GenericMap<String, IValue> s9 = new GenericMap<>();
        GenericList<IValue> o9 = new GenericList<>();
        FileTable f9 = new FileTable();
        Heap h9 = new Heap();
        IMap<String, IType> typeEnv9 = new GenericMap<>();
        try {
            ex9.typecheck(typeEnv9);
            ProgramState prg9 = new ProgramState(e9, s9, o9, ex9, f9, h9);
            IRepository repo9 = new Repository(prg9, "log9.txt");
            Controller ctr9 = new Controller(repo9);
            programs.add(ex9.toString());
            controllers.add(ctr9);

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }


        // int v; Ref int a; v=10;new(a,22);
        // fork(wH(a,30);v=32;print(v);print(rH(a)));
        // print(v);print(rH(a))
        IStatement forkAux = new CompoundStatement(new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(32))),
                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                new PrintStatement(new ReadHeap(new VariableExpression("a"))))));
        IStatement ex10 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new IntType())),
                        new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(new HeapAllocation("a", new ValueExpression(new IntValue(20))),
                                        new CompoundStatement(new ForkStatement(forkAux),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new ReadHeap(new VariableExpression("a")))))))));
        GenericStack<IStatement> e10 = new GenericStack<>();
        GenericMap<String, IValue> s10 = new GenericMap<>();
        GenericList<IValue> o10 = new GenericList<>();
        FileTable f10 = new FileTable();
        Heap h10 = new Heap();
        IMap<String, IType> typeEnv10 = new GenericMap<>();
        try {
            ex10.typecheck(typeEnv10);
            ProgramState prg10 = new ProgramState(e10, s10, o10, ex10, f10, h10);
            IRepository repo10 = new Repository(prg10, "log10.txt");
            Controller ctr10 = new Controller(repo10);
            programs.add(ex10.toString());
            controllers.add(ctr10);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        IStatement forkaux2 = new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignmentStatement("v", new ArithmeticExpression(2, new VariableExpression("v"), new ValueExpression(new IntValue(1)))));
        IStatement ex12 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("x", new IntType()),
                        new CompoundStatement(new VariableDeclarationStatement("y", new IntType()),
                                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(0))),
                                        new CompoundStatement(new RepeatStatement(new CompoundStatement(new ForkStatement(forkaux2), new AssignmentStatement("v", new ArithmeticExpression(1, new VariableExpression("v"), new ValueExpression(new IntValue(1))))), new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(3)), "==")),
                                                new CompoundStatement(new AssignmentStatement("x", new ValueExpression(new IntValue(1))),
                                                        new CompoundStatement(new NoOperation(),
                                                                new CompoundStatement(new AssignmentStatement("y", new ValueExpression(new IntValue(3))),
                                                                        new CompoundStatement(new NoOperation(), new PrintStatement(new ArithmeticExpression(3, new VariableExpression("v"), new ValueExpression(new IntValue(10)))))))))))));
        IStatement ex11 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("x", new IntType()),
                        new CompoundStatement(new VariableDeclarationStatement("y", new IntType()),
                                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(0))),
                                        new CompoundStatement(new RepeatStatement(new CompoundStatement(new ForkStatement(forkaux2), new AssignmentStatement("v", new ArithmeticExpression(1, new VariableExpression("v"), new ValueExpression(new IntValue(1))))), new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(3)), "==")),
                                                new CompoundStatement(new AssignmentStatement("x", new ValueExpression(new IntValue(1))),
                                                        new CompoundStatement(new AssignmentStatement("y", new ValueExpression(new IntValue(3))),
                                                                new PrintStatement(new ArithmeticExpression(3, new VariableExpression("v"), new ValueExpression(new IntValue(10)))))))))));


        GenericStack<IStatement> e11 = new GenericStack<>();
        GenericMap<String, IValue> s11 = new GenericMap<>();
        GenericList<IValue> o11 = new GenericList<>();
        FileTable f11 = new FileTable();
        Heap h11 = new Heap();
        IMap<String, IType> typeEnv11 = new GenericMap<>();
        try {
            ex11.typecheck(typeEnv11);
            ProgramState prg11 = new ProgramState(e11, s11, o11, ex11, f11, h11);
            IRepository repo11 = new Repository(prg11, "log11.txt");
            Controller ctr11 = new Controller(repo11);
            programs.add(ex11.toString());
            controllers.add(ctr11);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        programsList.setItems(programs);
    }
}
