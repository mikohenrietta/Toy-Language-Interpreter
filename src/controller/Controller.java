package controller;

import exceptions.*;
import model.adt.*;
import model.state.ProgramState;
import model.statement.IStatement;
import model.values.IValue;
import model.values.RefValue;
import repository.IRepository;
import repository.Repository;

import java.io.File;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repository;
    private ExecutorService executor;
    private List<ProgramState> prgList;

    public Controller(IRepository repository) {
        this.repository = repository;
        this.prgList = removeCompletedPrg(repository.getPrgList());
        executor = Executors.newFixedThreadPool(2);
    }
    public List<ProgramState> removeCompletedPrg(List<ProgramState> inPrgList){
        List<ProgramState> list =  inPrgList.stream().filter(p->p.isNotCompleted()).collect(Collectors.toList());
        return list;
    }
    public void shutDown(){
        executor.shutdown();
    }
    public String displayState(ProgramState prg){
        return prg.toString();
    }

    public int getNrOfProgramStates(){
        return this.prgList.size();
    }

    public IHeap getHeap(){
        return this.prgList.getFirst().getHeap();
    }

    public IList<IValue> getOut(){
        return this.prgList.getFirst().getOut();
    }

    public IFileTable getFileTable(){
        return this.prgList.getFirst().getFileTable();
    }

    public List<Integer> getIdentifiers(){
        List<Integer> ids = new ArrayList<>();
        prgList.forEach(p->ids.add(p.getId()));
        return ids;
    }
    public IMap<String, IValue> getSymbolTable(int id){
        for (ProgramState programState : prgList) {
            if (programState.getId() == id) {
                return programState.getSymTable();
            }
        }
        return null;
    }

    public IStack<IStatement> getExeStack(int id){
        for (ProgramState programState : prgList) {
            if (programState.getId() == id) {
                return programState.getExeStack();
            }
        }
        return null;
    }
    public Map<Integer, IValue> safeGarbColl(List<Integer> symTableAddr, Map<Integer, IValue> heap){
        List<Integer> keys = heap.values().stream().filter(iValue -> iValue instanceof RefValue).map(e->((RefValue)e).getAddress()).toList();
        return heap.entrySet().stream().filter(e->symTableAddr.contains(e.getKey()) || keys.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<Integer> getAddrFromSymTable(List<ProgramState> states, Map<Integer, IValue> heap){
        List<Integer> heapAddresses = new ArrayList<>();
        states.stream().forEach(s -> {
            List<Integer> keys = s.getSymTable().getContent().values().stream().filter(v->v instanceof RefValue).map(v->{RefValue v1 = (RefValue) v; return v1.getAddress();}).collect(Collectors.toList());
            heap.entrySet().forEach(e->{if (keys.contains(e.getKey()) && !heapAddresses.contains(e.getKey())) {heapAddresses.add(e.getKey());}});});
        return heapAddresses;
    }
    public void oneStepForAllPrg(List<ProgramState> prgList) throws InterruptedException {
        prgList.forEach(prg-> {
            try {
                repository.logPrgStateExec(prg);
            } catch (RepositoryException e) {
                throw new RuntimeException(e);
            }
        });
        List<Callable<ProgramState>> callList = prgList.stream().map((ProgramState p)->(Callable<ProgramState>)(()->{return p.oneStep();})).collect(Collectors.toList());
        List<ProgramState> newPrgList = executor.invokeAll(callList).stream().map(future->{try{return future.get();}catch(Exception e){throw new RuntimeException(e);}}).filter(p->p!=null).collect(Collectors.toList());
        prgList.addAll(newPrgList);
        prgList.forEach(prg-> {
            try {
                repository.logPrgStateExec(prg);
            } catch (RepositoryException e) {
                throw new RuntimeException(e);
            }
        });
        repository.setPrgList(prgList);
    }
    public void removePrograms(){
        prgList = removeCompletedPrg(repository.getPrgList());
    }

    public void allSteps(int flag) throws InterruptedException {
        executor = Executors.newFixedThreadPool(2);

        while(prgList.size() > 0){
            prgList.getFirst().getHeap().setContent(safeGarbColl(getAddrFromSymTable(prgList, prgList.getFirst().getHeap().getValues()), prgList.getFirst().getHeap().getValues()));
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repository.getPrgList());
        }
        executor.shutdownNow();
        repository.setPrgList(prgList);
    }

    public void oneStep() throws InterruptedException {
        prgList.getFirst().getHeap().setContent(safeGarbColl(getAddrFromSymTable(prgList, prgList.getFirst().getHeap().getValues()), prgList.getFirst().getHeap().getValues()));
        oneStepForAllPrg(prgList);
    }
}
