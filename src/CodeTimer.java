
public class CodeTimer {
    
    private long executionTime;
    
    public CodeTimer(){
        
        this.executionTime = 0;
    }
    
    public void StartTimer() {
        
        this.executionTime = System.nanoTime();
        
    }
    
    public void StopTimer() {
        
        this.executionTime = System.nanoTime() - executionTime;
        
    }
    
    public void ResetTimer() {
        
        this.executionTime = 0;
        
    }
    
    public long getTime() {
        
        return executionTime/100;
    }
            
}
