import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ContigData {

	String contigName;
	int maxLength;
	int[] startPos ;
	ArrayList<Integer>  windPos;
	int windLength;
	int maxWindows;
	static double COV_RATE;//10 default ratio by which the average coverage of each sliding window  is mutiplied
						  //the bigger, the more detailed will be the readsDistribution bar chart
	

	public ContigData(String name, int length) {
		contigName = name;
		maxLength = length;
		startPos =  new int[maxLength] ;
		this.COV_RATE=Ploest.COV_RATE;
		//System.out.println("new ContigData "+name+" startPos.length:"+startPos.length);
		
	}
	
	public void setPos(int p) {
		startPos[p]= startPos[p] + 1;
	}

	public String getContigName() {
		return contigName;
	}

	public int windPos(int wl) throws FileNotFoundException, UnsupportedEncodingException {//computes the windPos vector, storing 
			//at each window position, the number of reads found in that window. Returns the maximum value found at that window
											
		int max=0;
		windLength = wl;
		windPos = new ArrayList<Integer>((startPos.length / wl) * 2);//ensure capacity		
		for (int i=0;i<((startPos.length / wl) * 2);i++){//initialize the list with zeros
			windPos.add( 0);
		}
		//System.out.println("windPos "+windPos.size()+" startPos.length:"+startPos.length+ "wl:"+wl);
	
		
		
		int stIndex = 0;// index in startPos array
		int wdIndex = 0;
		int wsum = 0;// window sum
		//PrintWriter writer = new PrintWriter(Ploest.outputFile + "//" + Ploest.projectName+"//windPositionsTest.txt", "UTF-8");
		//String line="";
		while (stIndex < (startPos.length-windLength) && (wdIndex<windPos.size()-1)) {
			for (int i = 0; i < windLength; i++) {
				wsum += startPos[stIndex++];		
			}
			
			windPos.set(wdIndex++, (int) (wsum /(windLength/COV_RATE)));//[wdIndex++] =(int) (wsum /(windLength/COV_RATE));// relative average of coverage over
			//if(wdIndex>15 && wdIndex<35)System.out.print(" (s:"+wsum+" , c:"+ (int) (wsum /(windLength/COV_RATE))+")");								// the range of the window;
			if (windPos.get(wdIndex-1)>max)max=windPos.get(wdIndex-1);
			
		
			stIndex = stIndex - (wl / 2);// window slides over all positions for
											// a length of wl , but a new window
											// is computed after each wl/2;
			wsum=0;
			//writer.println(line);
		}
		//System.out.println();
		//writer.close();
		maxWindows=max;
		
		return max;
	}
	
	
	
}
