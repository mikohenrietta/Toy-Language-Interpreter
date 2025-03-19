package view;

import controller.Controller;
import model.expressions.ArithmeticExpression;
import model.expressions.ValueExpression;
import model.expressions.VariableExpression;
import model.statement.*;
import model.types.BoolType;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IntValue;

import java.util.Scanner;

public class View {
    Controller controller;
    public View(Controller controller) {
        this.controller = controller;
    }
    public void inputProgram() {
        IStatement ex1 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))));
        IStatement ex2 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()), new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression(1, new ValueExpression(new IntValue(2)),
                        new ArithmeticExpression(3, new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                        new CompoundStatement(new AssignmentStatement("b", new ArithmeticExpression(1, new VariableExpression("a"), new ValueExpression(new IntValue(1)))),
                                new PrintStatement(new VariableExpression("b"))))));
        IStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"), new AssignmentStatement("v", new ValueExpression(new
                                        IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
                                        VariableExpression("v"))))));

        System.out.println("Select a program to execute:");
        System.out.println("1. Example 1: int v; v=2; Print(v)");
        System.out.println("2. Example 2: int a; int b; a=2+3*5; b=a+1; Print(b)");
        System.out.println("3. Example 3: bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)");
        Scanner scanner = new Scanner(System.in);

        int programChoice = scanner.nextInt();
        IStatement selectedProgram = null;
        switch (programChoice) {
            case 1:
                selectedProgram = ex1;
                break;
            case 2:
                selectedProgram = ex2;
                break;
            case 3:
                selectedProgram = ex3;
                break;
            default:
                System.out.println("Invalid program choice");
                break;

        }
    }

    public void executeProgram(int flag){
        try {
            controller.allSteps(flag);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void start(){
        this.inputProgram();

        System.out.println("Would you like to set the display flag to on?(1/0)");
        Scanner scanner = new Scanner(System.in);
        int flag = scanner.nextInt();
        this.executeProgram(flag);
    }
}
