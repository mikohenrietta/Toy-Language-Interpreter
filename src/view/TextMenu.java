package view;

import model.adt.GenericList;
import model.adt.IMap;
import view.commands.Command;

import java.util.*;

public class TextMenu {
    private Map<String, Command> commands;
    public TextMenu() {
        commands = new HashMap<>();
    }
    public void addCommand(Command command) {
        commands.put(command.getKey(), command);
    }
    private void printMenu(){
        for(Command command : commands.values()){
            String line = String.format("%4s: %s\n", command.getKey(), command.getDescription());
            System.out.println(line);
        }
    }
    public void show(){
        Scanner scanner = new Scanner(System.in);
        List<String> list = new ArrayList<>();
        while(true){
            printMenu();
            System.out.print("Input the option:");
            String key = scanner.nextLine();
            if(list.contains(key)){
                System.out.println("Option was already run");
                continue;
            }
            list.add(key);
            Command command = commands.get(key);
            if(command == null){
                System.out.println("Invalid option!");
                continue;
            }
            command.execute();
        }
    }
}
