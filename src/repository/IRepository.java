package repository;

import exceptions.RepositoryException;
import model.state.ProgramState;

import java.util.List;

public interface IRepository {
    void add(ProgramState programState);
    void logPrgStateExec(ProgramState  PrgState) throws RepositoryException;
    List<ProgramState> getPrgList();
    void setPrgList(List<ProgramState> prgList);
    void remove(ProgramState programState);
}
