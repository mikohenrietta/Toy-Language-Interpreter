package repository;

import exceptions.RepositoryException;
import model.state.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    private List<ProgramState> states;
    private int currentStatePosition;
    private String filename;

    public Repository(ProgramState prg, String file) {
        states = new ArrayList<ProgramState>();
        this.add(prg);
        currentStatePosition = 0;
        this.filename = file;
    }

    public void add(ProgramState p) {
        states.add(p);
    }

    public void logPrgStateExec(ProgramState PrgState) throws RepositoryException {
        try{
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
            writer.println(PrgState);
            writer.close();
        }
        catch(IOException e){
            throw new RepositoryException("Cannot write in the file");
        }
    }

    public List<ProgramState> getPrgList() {
        return states;
    }
    public void setPrgList(List<ProgramState> prgList) {
        states = prgList;
    }

    @Override
    public void remove(ProgramState programState) {
        states.remove(programState);
    }
}
